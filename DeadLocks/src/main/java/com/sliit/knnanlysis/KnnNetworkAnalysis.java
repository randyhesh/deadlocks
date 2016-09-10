package com.sliit.knnanlysis;

import com.sliit.knnanalysis.features.Destination;
import com.sliit.knnanalysis.features.Feature;
import com.sliit.knnanalysis.features.Length;
import com.sliit.knnanalysis.features.Protocol;
import com.sliit.knnanalysis.features.Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class KnnNetworkAnalysis {

    public static final String FILE_PATH = "featureTable.csv";
    public static final int K = 10;

    public static void main(String[] args) throws IOException {
        List<Instance> instances = new FileReader(FILE_PATH).getInstances();
        Instance classificationInstance = getClassificationInstance();
        List<Neighbor> nearestNeighbors = getKNearestNeighbors(K, instances, classificationInstance);
        Instance classifiedInstance = determineFraudStatus(classificationInstance, nearestNeighbors);

        System.out.println("Frud Status of Classification Instance : " + classifiedInstance.getFeatures());
    }

    private static Instance getClassificationInstance() {
        List<Feature> attributes = new ArrayList<>();
        Instance instance = new Instance();
        attributes.add(new Source(Source.Sources.source2));
        attributes.add(new Destination(Destination.Destinations.destination3));
        attributes.add(new Protocol(Protocol.Protocols.IPv4));
        attributes.add(new Length(22.5));
        instance.setFeatures(attributes);
        return instance;
    }

    private static List<Neighbor> getKNearestNeighbors(int K,
            List<Instance> instances, Instance classificationInstance) {

        HashMap<String, Double> ciFeatureDistances = new HashMap<>();
        for (Feature feature : classificationInstance.getFeatures()) {
            ciFeatureDistances.put(feature.getFeatureName(), feature.getDistance());
        }

        List<Neighbor> neighbors = new ArrayList<>();
        for (Instance instance : instances) {
            double distance = 0;
            for (Feature feature : classificationInstance.getFeatures()) {
                double ciFeatureDistance = ciFeatureDistances.get(feature.getFeatureName());
                distance += Math.pow((feature.getDistance() - ciFeatureDistance), 2);
            }
            Neighbor neighbor = new Neighbor(instance, distance);
            neighbors.add(neighbor);
        }

        if (K < neighbors.size()) {
            Collections.sort(neighbors, new Comparator<Neighbor>() {
                @Override
                public int compare(Neighbor neighbor1, Neighbor neighbor2) {
                    double difference = neighbor1.getDistance() - neighbor2.getDistance();
                    if (difference == 0) {
                        return 0;
                    } else if (difference > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

            neighbors = neighbors.subList(0, K);
        }
        return neighbors;
    }

    private static Instance determineFraudStatus(
            Instance classificationInstance, List<Neighbor> nearestNeighbors) {
        int frudCount = 0, normalCount = 0;

        for (Neighbor neighbor : nearestNeighbors) {
            if (neighbor.getInstance().isFraud()) {
                frudCount++;
            } else {
                normalCount++;
            }
        }

        if (frudCount > normalCount) {
            classificationInstance.setFraudStatus(true);
        } else {
            classificationInstance.setFraudStatus(false);
        }

        return classificationInstance;
    }
}
