package wadael.csvskelgen;

import java.util.ArrayList;
import java.util.List;

public class LoadCSVSkeleton {
    private String fileWithPath;
    private String fieldSeparator;
    private String[][] fileContent;
    private List<Hint> hints;
    private long exampleValuescount;

    public String getFileWithPath() {
        return fileWithPath;
    }

    public void setFileWithPath(String fileWithPath) {
        this.fileWithPath = fileWithPath;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setFileContent(String[][] fileContent) {
        this.fileContent = fileContent;
    }

    public void setExampleValuescount(long exampleValuescount) {
        this.exampleValuescount = exampleValuescount;
    }

    public long getExampleValuescount() {
        return exampleValuescount;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("USING PERIODIC COMMIT 6000 " + System.lineSeparator());
        sb.append("LOAD CSV WITH HEADERS FROM '" + fileWithPath
                + "' AS line FIELDTERMINATOR '" + fieldSeparator + "'" + System.lineSeparator());

        int nodeIndex = 0;
        int headerIndex = 0;

        // if no hints then create an UnLabelled node with all fields.
        if (getHints() == null || getHints().size() == 0) {
            try {
                List<Hint> hint = new ArrayList<>();
                hint.add(new Hint("UnLabelled", fileContent[0].length));
                setHints(hint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Hint h : getHints()) {
            sb.append("MERGE (node" + nodeIndex);
            sb.append(":" + h.getLabelName() + "{})" + System.lineSeparator());

            for (int i = 0; i < h.getColumnCount(); i++) {
                sb.append("SET node" + nodeIndex + "." + StringUtils.clean(fileContent[0][headerIndex]) );
                sb.append("= line.`" + fileContent[0][headerIndex] + "`");

                if (getExampleValuescount() > 0) {
                    try {
                        sb.append("// ");
                        for (int y = 1; y <= getExampleValuescount(); y++) {
                            sb.append(fileContent[y][headerIndex]);
                            sb.append(",");
                        }
                    } catch (Exception noWorries) {
                        // probably getExampleValuescount > count of rows in file.
                        noWorries.printStackTrace();
                    }
                }
                sb.append(System.lineSeparator());
                headerIndex++;
            }
            nodeIndex++;
        }

        return sb.toString();
    }
}
