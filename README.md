# CSIRO-Project-

  Collection of semantic technology (RDFS,OWL2,SKOS) applied into the Graph to provide enriched mature and easy instruction data out of complex ODM object design .By this method technical researched data can be redden in detail and easy even by none technical person compare to ODM xml that have to apply extra layout by secondary system to be able to extract the information.
 Design follows standard of CDISC and adopting the ODM information, also implement Ontology by leveraging vocabularies such as LcdcCore, LcdcOdm, Qb, etc .
  
The scope of this project is focused on Cardio study for 3 categories :
* Vital signs
* Blood measurements 
* medications.

  The system split each of the data about particular category into the Three linked graphs internally by ontology and also linkset to external ontology snomed.
  Each generated file complement the related linked file in manner of ODM instruction.
Each group includes 3 type of Model as smaller Graphs: Variable, Cardio, Observation.




#Installation 

##System dependencies:##

This code is compatible with Java 7+ 

Dependencies: 
*	JaxB2: already include in Java sdk 7+
*	Log4j: already include in Java sdk 7+
*	Apache Jena Library version: 2.7.4 
*	Apache Jena TDB  version: 2.7.4
*	Apache maven: version 3+
*	OpenCSV: version 2.3

To run this project please add all the dependencies first or use Maven ants to build the project by running pom.xml file.
(visit this link https://maven.apache.org  for more information about maven installation)
  Please be advised you need to have Apache Jena added into your environment path. Refer to Apache Jena documentation for more information.

 After installation you can run the Project as a stand alone JVM. Main function is placed in src/Java/mycompany/app.java file.  All the Graphs are going to store into TDB (Triple store database) and also can be access as .ttl files in Model_Maker folder.

  
    #System calling and functions:

*	RDFModelHelper class:
In the java system RDFModelHelper class is the main caller for all the graphs generator functions. RdfModelHelper has a function called modelHandler that accepts path for ODM.xml file as a String and generate main graph containing all the of our graphs.
For more information check the Java docs.


#Running Java application Main function:
	
 Main function for this system contains String filePath, this variable provides the path to your ODM.xml file (Default file with controlled data is includes in resources folder, for testing), for using with different ODM format xml file change the String file path to your file path.

You can find the main function in package com.mycompany.app . Please read the comments for more information.

 The generated files will be saved in Model_Maker folder with .ttl (RDF Turtle) format.

 Dataset handles by DatabaseHandler class and always should be only one instant of this class instantiated and disposed (closed) before creating a new instant.(Database concurrency)
 For more information about Apache Jena TDB please refer to this link https://jena.apache.org/documentation/tdb/
