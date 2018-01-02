package wadael.csvskelgen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A hint is a String composed of a label name, a semicolon and an integer (positive) value.
 * Like Person:7
 * Hints are mandatory, so maybe that will be renamed.
 */
public class Hint {
    private String labelName;
    private int columnCount; // how many contiguous columns of the csv should be used for this label

    public Hint(String hint) throws Exception {
        if (hint ==null || hint.trim().length()==0){
            throw new Exception("Hint is null or empty");
        }
        int posSemiColon = hint.indexOf(":");

        if(posSemiColon!=-1 || posSemiColon == 0){
            this.labelName = hint.substring(0,posSemiColon);
            this.columnCount = Integer.parseInt( hint.substring( posSemiColon+1, hint.length() ) );
        }
        else {
            throw new Exception("Check your parameters: " + hint + " is no good");
        }
    }

    public Hint(String labelName, int columnCount) {
        this.labelName = labelName;
        this.columnCount = columnCount;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }


    /**
     * Generates a list of hints from a comma separated string.
     *
     * This cannot be used for zapping fields. In this case, create a list, and add a hint with a null label name.
     *
     * @param hints string like Town:2,Country:2,Planet:2 with Town a label and 2 the number of columns of the csv file for this label.
     * @return
     * @throws Exception
     */
    public static List<Hint> getHints(String hints) throws Exception {
        if (hints == null || hints.trim().length() == 0) return null;

        StringTokenizer stringTokenizer = new StringTokenizer(hints, ",");
        List<Hint> resul = new ArrayList<>();
        while (stringTokenizer.hasMoreElements()){
            resul.add(new Hint(stringTokenizer.nextToken()));
        }

        return resul;
    }

}
