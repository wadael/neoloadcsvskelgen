package wadael.csvskelgen;

public class StringUtils {

    /**
     * Cleans the CSV columns name in order to have a working property name.
     * @param input a property name
     * @return
     */
    public static String clean(String input){
        String output = input;

        if(input != null && input.trim().length() >0){
            output=output.trim();
            output=output.replace(" ", "");
        }
        return output;
    }
}
