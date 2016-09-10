package com.sliit.knnanlysis;


import com.sliit.knnanalysis.features.Destination;
import com.sliit.knnanalysis.features.Feature;
import com.sliit.knnanalysis.features.Length;
import com.sliit.knnanalysis.features.Protocol;
import com.sliit.knnanalysis.features.Source;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileReader {
    
    private final String FILE_PATH;
	
    public FileReader(String filePath) {
        this.FILE_PATH = filePath;
    }
    
    public List<Instance> getInstances() throws IOException {
        
        List<Instance> instances = new ArrayList<>();

        File file = new File(FILE_PATH);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));
            
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                List<Feature>  features = new ArrayList<>();
                Instance instance = new Instance();
                
                String source       = tokenizer.nextToken();
                String destination  = tokenizer.nextToken();
                String protocol     = tokenizer.nextToken();
                String length       = tokenizer.nextToken();
                String fraudStatus  = tokenizer.nextToken();
                
                features.add(new Source(Source.Sources.valueOf(source)));
                features.add(new Destination(Destination.Destinations.valueOf(destination)));
                features.add(new Protocol(Protocol.Protocols.valueOf(protocol)));
                features.add(new Length(Double.parseDouble(length)));
                
                instance.setFeatures(features);
                if("1".equals(fraudStatus)) instance.setFraudStatus(true);
                
                instances.add(instance);
            }
        }
        
        return instances;
    }
}
