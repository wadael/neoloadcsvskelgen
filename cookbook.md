
# Cookbook

Get the best yummy yummy generated scripts ....

## Not using all fields from the CSV file

Create a hint with the wanted column count, but with a null label name
     
     List<Hint> hints = new ArrayList<>();
     hints.add( new Hint(null,2) );
     hints.add( new Hint("Country",2) );
     hints.add( new Hint("Planet",2) );
     skel.setHints( hints );

## The dreaded SIREN file !

The list of all French companies is published as open data.
It has 130+ columns. It is what made me create this tool.
The command I propose is 
   
   CALL wadael.csvskelgen("/home/jerome/Tools/neo/neo4j-community-3.3.1/import/siren78.csv",";",4,"Etablissement:16,Localisation:6,Region:2,Etablissement:16,NatureEI:2,Activite:3,Effectif:4,Etablissement:11,Entreprise:2,Responsable:4,Entreprise:4,NatureJuridique:2,Etablissement:10,Categorie:1,Etablissement:12")
   
Then, you should tweak the output to group all the SET commands per label.
As each group of column is treated as if if was a new label, some search/replace is needed.  
     
     
 ## Troubleshooting
 This procedure is intended for use by developers. 
 Consequently, it is not as idiocy-proof as a mainstream product.
 
 If the result is an exception, check your parameters.
    
- is it the good file ? path ?
- is the separator correct ?
- is the sum of the column counts given as hints <= to the number of columns of the file ?