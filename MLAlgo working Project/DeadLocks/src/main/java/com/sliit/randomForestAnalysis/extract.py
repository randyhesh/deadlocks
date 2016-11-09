#! /usr/bin/python

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

__author__ = "indu"
__date__ = "$Sep 10, 2016 8:10:19 AM$"
import sys
import csv
import string

from collections import defaultdict
def construct_line( label, line ):
      new_line = []
      
      
      if float( label ) == 0.0:
            label = "0"
      new_line.append( label )

      for i, item in enumerate( line ):
            chars = set('.')
            if any((c in chars) for c in item):
                        item = item.replace('.','')
                        
                        
                        
            if item == '' :
                  continue
            
            new_item = "%s:%s" % ( i + 1, item )
            new_line.append( new_item )
      new_line = " ".join( new_line )
      new_line += "\n"
      #new_line = bytes(new_line, 'utf-8')
      return new_line
if __name__ == "__main__":
    print "Hello World";
    sys.argv = ['new.py','FeatureTable.csv','Test2']
    input_file = sys.argv[1]
    output_file = sys.argv[2]

    try:
          label_index = int( sys.argv[3] )
    except IndexError:
          label_index = 0

    try:
          skip_headers = sys.argv[4]
    except IndexError:
          skip_headers = 0
    print (input_file)
    i = open( input_file, 'rb' )
    o = open( output_file, 'wb' )

    reader = csv.reader( i )

    if skip_headers:
          headers = reader.next()

    for line in reader:
          if label_index == -1:
                label = "1"
          else:
                label = line.pop( label_index )

          new_line = construct_line( label, line )

          o.write(new_line )
    o.close()
    print ("success")