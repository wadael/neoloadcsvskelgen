# neoloadcsvskelgen  

_Pronounce "niölödsiessvìsköldjen"._  (bless you)
 
It means Neo4j LOAD CSV skeleton generator

This project is a plugin for **Neo4j**, the graph database. 
It contains a **procedure** that will, from little input, **generate** for you some **Cypher code** for importing a given **CSV file** (with headers).

This is a developer-oriented tool, you will run it once for a file, then tweak the output into a working script.   


## Installation
Classic: git clone the project then run 

    mvn clean package
    
after that, copy the generated JAR _original-csvskelgen-0.0.1.jar_ found into the _target_ folder 
into the plugins subfolder of your Neo4j server and restart your server.  
 
You are almost done. Some editing of *neo4j.conf* is needed.
At the end of the file, you need give authorizations to my package. 
Add those lines at the end of your *neo4j.conf* file 

    dbms.security.procedures.unrestricted=wadael.*
    dbms.security.procedures.whitelist=wadael.*
     
And if you have installed the Apoc plugin, then the syntax is like this

    dbms.security.procedures.unrestricted=apoc.*,wadael.*
    dbms.security.procedures.whitelist=apoc.*,wadael.*   
   
## Usage
Example 

     CALL wadael.csvskelgen("/home/jerome/OpenSource/neoloadcsvskelgen/src/test/resources/guardian_most_polluting_companies_list.csv","|",4,"Company:3")

* "/home/jerome/OpenSource/neoloadcsvskelgen/src/test/resources/guardian_most_polluting_companies_list.csv" is the full path to a CSV file. Unlike LOAD CSV, that procedure needs the full path 
* "|" is the field separator used in this file
* 4 is the number of example values to give as a comment
* "Company:3" is the hints (its more directives in fact) means use the label "Company" for the three next fields. Another possible value would be "Company:3,Country:2" meaning three first columns are for Company, the following two are for Country.

For the file [guardian_most_polluting_companies_list.csv](src/test/resources/guardian_most_polluting_companies_list.csv) that starts with 

    rank|company|percentage
    1|China (Coal)|14.3
    2|Saudi Arabian Oil Company (Aramco)|4.5
    3|Gazprom OAO|3.9
    4|National Iranian Oil Co|2.3
    5|ExxonMobil Corp|2.0

The corresponding output is 

    USING PERIODIC COMMIT 5000 
    LOAD CSV WITH HEADERS FROM 'file:/home/jerome/OpenSource/neoloadcsvskelgen/target/test-classes/guardian_most_polluting_companies_list.csv' AS line FIELDTERMINATOR '|'
    CREATE (node0:Company)
    SET node0.rank= line.`rank`// 1,2,3,4,
    SET node0.company= line.`company`// China (Coal),Saudi Arabian Oil Company (Aramco),Gazprom OAO,National Iranian Oil Co,
    SET node0.percentage= line.`percentage`// 14.3,4.5,3.9,2.3,

Should you not want to have examples of values, use 0.
Examples values are here to help you name your properties correctly.

Of course, the generated code can be modified at will. _It must be_. 
The generated skeleton is for helping and avoiding typos, a clean basis for your import script.    

Copy/paste it in your favorite text editor and rename the nodes to something meaningful for you.
Also, check if you allow CSV files to be loaded from anywhere or just from the $NEO_HOME/import folder (default afaik) 

Let me kindly remind you to create the necessary constraints before to run your import.


This project has been motivated 
by a 100-column CSV file I use in my lectures. 
It tends to scare :scream: students although they just have to pick some fields for an exercise.


## Todo list ##
 - [ ] propose the Cypher code for constraint creation, as comment
 - [ ] do some syntax checking on the label names like enforcing camel case.
 - [ ] use something more meaningful that node0, node1, etc ...
 - [X] Add advertisement for my book **"Learning Neo4j 3.x"** Check it out: on [PacktPub](https://www.packtpub.com/big-data-and-business-intelligence/learning-neo4j-3x-second-edition?referrer=wadael), on [Amazon.com](https://www.amazon.com/Learning-Neo4j-3-x-performance-visualization/dp/1786466147)
 - [ ] Use anti-single quote syntax like label.\`field name` only when needed
 - [ ] find an idea to automagically set the identifier for each label
 - [ ] handle CSV files without headers


## Cookbook ##
See [cookbook.md](./cookbook.md) 

## Misc ##
The given pronounciation is a joke.
Have you bought my book ? Please leave a comment on the website where you bought it.

Is this project useful for you ? Send a message ! 
