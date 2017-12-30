package wadael.csvskelgen;


/**
 * This class is duplicated from APOC.
 * If ever this project uses more of APOC, APOC will be added as a dependency.
 */
public class StringResult {
    public final static StringResult EMPTY = new StringResult(null);

    public final String value;

    public StringResult(String value) {
        this.value = value;
    }
}
