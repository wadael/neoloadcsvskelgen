
# Cookbook

Get the best yummy yummy generated scripts ....

## Not using all fields from the CSV file

Create a hint with the wanted column count, but with a null label name
     
     List<Hint> hints = new ArrayList<>();
     hints.add( new Hint(null,2) );
     hints.add( new Hint("Country",2) );
     hints.add( new Hint("Planet",2) );
     skel.setHints( hints );
     
 ## Troubleshooting
 This procedure is intended for use by developers. 
 Consequently, it is not as idiocy-proof as a mainstream product.
 
 If the result is an exception, check your parameters.
    
- is it the good file ? path ?
- is the separator correct ?
- is the sum of the column counts given as hints <= to the number of columns of the file ?