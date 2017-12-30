package wadael.csvskelgen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A hint is a String composed of a label name, a semicolon and an integer (positive) value.
 * Like Person:7
 */
public class Hint {
    private String labelName;
    private int columnCount;


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
