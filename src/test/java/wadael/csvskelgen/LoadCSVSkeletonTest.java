package wadael.csvskelgen;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import static org.junit.Assert.*;

public class LoadCSVSkeletonTest {

    @Test
    public void testToStringWithPolluters() throws Exception {
        URL url = ClassLoader.getSystemResource("guardian_most_polluting_companies_list.csv");
        String[][] filecontent = new String[10][3];
        FileReader fr = new FileReader( url.getFile() );

        BufferedReader br = new BufferedReader(fr);
        String line;

        StringTokenizer stringTokenizer = null;
        int indexRow = 0;

        while( (line = br.readLine()) != null && indexRow < 6 ) {
            stringTokenizer = new StringTokenizer( line, "|");
            int index = 0;
            while(stringTokenizer.hasMoreElements()){
                filecontent[indexRow][index++] = stringTokenizer.nextToken();
            }
            indexRow++;
        }

        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath( url.toString() );
        skel.setExampleValuescount(4);
        skel.setFieldSeparator("|");
        skel.setFileContent(filecontent);
        skel.setHints( Hint.getHints("Company:3")  );

        String result = skel.toString();

        Assert.assertTrue( result.contains("node0.rank")  );
        Assert.assertTrue( result.contains("node0.company")  );
        Assert.assertTrue( result.contains("node0.percentage")  );


        skel = new LoadCSVSkeleton();
        skel.setFileWithPath( url.toString() );
        skel.setExampleValuescount(4);
        skel.setFieldSeparator("|");
        skel.setFileContent(filecontent);
        skel.setHints( null );

        result = skel.toString();
        System.out.println(result);

        Assert.assertTrue( result.contains("node0:UnLabelled")  );

        Assert.assertTrue( result.contains("MERGE (node0:UnLabelled{})")  );
        Assert.assertTrue( result.contains("node0.rank")  );
        Assert.assertTrue( result.contains("node0.company")  );
        Assert.assertTrue( result.contains("node0.percentage")  );

    }

    @Test
    public void testToStringWithTwittos() throws Exception {
        URL url = ClassLoader.getSystemResource("anything.csv");
        String[][] filecontent = new String[5][4];
        FileReader fr = new FileReader(url.getFile());

        BufferedReader br = new BufferedReader(fr);
        String line;

        StringTokenizer stringTokenizer = null;
        int indexRow = 0;

        while ((line = br.readLine()) != null && indexRow < 6) {
            stringTokenizer = new StringTokenizer(line, ";");
            int index = 0;
            while (stringTokenizer.hasMoreElements()) {
                filecontent[indexRow][index++] = stringTokenizer.nextToken();
            }
            indexRow++;
        }

        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath(url.toString());
        skel.setExampleValuescount(0);
        skel.setFieldSeparator(";");
        skel.setFileContent(filecontent);
        skel.setHints(Hint.getHints("Twitto:4"));

        String result = skel.toString();

        System.out.println(result);

        Assert.assertTrue(result.contains("node0.firstname"));
        Assert.assertTrue(result.contains("node0.lastname"));
        Assert.assertTrue(result.contains("node0.twitterhandle"));
        Assert.assertTrue(result.contains("node0.id"));
    }


}