package wadael.csvskelgen;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.Assert.*;

public class LoadCSVSkeletonTest {

    public static final int MAX_LINES_READ = 6;

    @Test
    public void testToStringWithPolluters() throws Exception {
        URL url = ClassLoader.getSystemResource("guardian_most_polluting_companies_list.csv");
        String[][] filecontent = loadFile("guardian_most_polluting_companies_list.csv","|",6);


        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath( url.toString() );
        skel.setExampleValuescount(4);
        skel.setFieldSeparator("|");
        skel.setHints( Hint.getHints("Company:3")  );
        skel.setFileContent(filecontent);

        String result = skel.toString();

        Assert.assertTrue( result.contains("node0.rank")  );
        Assert.assertTrue( result.contains("node0.company")  );
        Assert.assertTrue( result.contains("node0.percentage")  );


        skel = new LoadCSVSkeleton();
        skel.setFileWithPath( url.toString() );
        skel.setExampleValuescount(4);
        skel.setFieldSeparator("|");
        skel.setHints( null );
        skel.setFileContent(filecontent);

        result = skel.toString();

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

    @Test
    public void testToStringWithTownCountryPlanet_Full() throws Exception {
        URL url = ClassLoader.getSystemResource("towncountryplanet.csv");
        String[][] filecontent = loadFile("towncountryplanet.csv",";",4);

        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath(url.toString());
        skel.setExampleValuescount(0);
        skel.setFieldSeparator(";");
        skel.setHints(Hint.getHints("Town:2,Country:2,Planet:2"));
        skel.setFileContent(filecontent);

        String result = skel.toString();

        System.out.println(result);

        Assert.assertTrue(result.contains("MERGE (node0:Town{"));
        Assert.assertTrue(result.contains("MERGE (node1:Country{"));
        Assert.assertTrue(result.contains("MERGE (node2:Planet{"));

        Assert.assertTrue(result.contains("SET node0.id"));
        Assert.assertTrue(result.contains("SET node0.name"));
        Assert.assertTrue(result.contains("SET node1.id"));
        Assert.assertTrue(result.contains("SET node1.name"));
        Assert.assertTrue(result.contains("SET node2.id"));
        Assert.assertTrue(result.contains("SET node2.name"));
    }

    /**
     * Same content and tests except that the planet will not be used
     * @throws Exception
     */
    @Test
    public void testToStringWithTownCountryPlanet_Partial_FolowingFields() throws Exception {
        URL url = ClassLoader.getSystemResource("towncountryplanet.csv");
        String[][] filecontent = loadFile("towncountryplanet.csv",";",5);

        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath(url.toString());
        skel.setExampleValuescount(0);
        skel.setFieldSeparator(";");
        skel.setHints(Hint.getHints("Town:2,Country:2"));
        skel.setFileContent(filecontent);

        String result = skel.toString();

        Assert.assertTrue(result.contains("MERGE (node0:Town{"));
        Assert.assertTrue(result.contains("MERGE (node1:Country{"));
        Assert.assertFalse(result.contains("MERGE (node2:Planet{"));

        Assert.assertTrue(result.contains("SET node0.id"));
        Assert.assertTrue(result.contains("SET node0.name"));
        Assert.assertTrue(result.contains("SET node1.id"));
        Assert.assertTrue(result.contains("SET node1.name"));
        Assert.assertFalse(result.contains("SET node2.id"));
        Assert.assertFalse(result.contains("SET node2.name"));
    }

    /**
     * Same content and tests except that the town will not be used
     * @throws Exception
     */
    @Test
    public void testToStringWithTownCountryPlanet_Partial_DroppingFirstField() throws Exception {
        URL url = ClassLoader.getSystemResource("towncountryplanet.csv");
        String[][] filecontent = loadFile("towncountryplanet.csv",";",3);


        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath(url.toString());
        skel.setExampleValuescount(0);
        skel.setFieldSeparator(";");

        List<Hint> hints = new ArrayList<>();
        hints.add( new Hint(null,2) );
        hints.add( new Hint("Country",2) );
        hints.add( new Hint("Planet",2) );
        skel.setHints( hints );
        skel.setFileContent(filecontent);

        String result = skel.toString();

        System.out.println( result );

        Assert.assertFalse(result.contains("MERGE (node0:Town{"));

        Assert.assertFalse(result.contains("MERGE (node1:Country{"));
        Assert.assertTrue(result.contains("MERGE (node0:Country{"));

        Assert.assertTrue(result.contains("MERGE (node1:Planet{"));

        // As long as nodes are numbered from 0, 0 is now the country in this test
        Assert.assertTrue(result.contains("SET node0.id"));
        Assert.assertTrue(result.contains("SET node0.name"));
        Assert.assertTrue(result.contains("SET node1.id"));
        Assert.assertTrue(result.contains("SET node1.name"));

        Assert.assertFalse(result.contains("SET node2.id"));
        Assert.assertFalse(result.contains("SET node2.name"));
    }


    private String[][] loadFile(String name, String delim, int lineCount){
        String[][] filecontent = null;
        try {
            URL url = ClassLoader.getSystemResource(name);
            FileReader fr = new FileReader(url.getFile());

            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            StringTokenizer stringTokenizer = null;
            stringTokenizer = new StringTokenizer(line, delim);
            filecontent = new String[lineCount][stringTokenizer.countTokens()];

            int indexRow = 0;
            int index = 0;
            while (stringTokenizer.hasMoreElements()) {
                filecontent[indexRow][index++] = stringTokenizer.nextToken(); // first line
            }

            indexRow = 1;
            while ((line = br.readLine()) != null && indexRow < lineCount) {
                stringTokenizer = new StringTokenizer(line, delim);
                index = 0;
                while (stringTokenizer.hasMoreElements()) {
                    filecontent[indexRow][index++] = stringTokenizer.nextToken();
                }
                indexRow++;
            }
        }catch (Exception e){
            // will return null as wanted
        }
        return filecontent;
    }

    @Test
    public void testLoadFile(){
        String[][] res = loadFile("anything.csv",";",3);
        assertNotNull(res);
        assertEquals("id",res[0][0]);
        assertEquals("first name",res[0][1]);
        assertEquals("last name",res[0][2]);
        assertEquals("twitter handle",res[0][3]);
        assertEquals("1",res[1][0]);
        assertEquals("jerome",res[1][1]);
        assertEquals("baton",res[1][2]);
        assertEquals("@wadael",res[1][3]);
    }


    /**
     * No country used
     * @throws Exception
     */
    @Test
    public void testToStringWithTownCountryPlanet_Partial_DroppingMiddleField() throws Exception {
        URL url = ClassLoader.getSystemResource("towncountryplanet.csv");
        String[][] filecontent = loadFile("towncountryplanet.csv",";",3);

        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath(url.toString());
        skel.setExampleValuescount(0);
        skel.setFieldSeparator(";");

        List<Hint> hints = new ArrayList<>();
        hints.add( new Hint("Town",2) );
        hints.add( new Hint(null,2) );
        hints.add( new Hint("Planet",2) );
        skel.setHints( hints );
        skel.setFileContent(filecontent);

        String result = skel.toString();

        System.out.println( result );

        Assert.assertTrue(result.contains("MERGE (node0:Town{"));

        Assert.assertFalse(result.contains("MERGE (node1:Country{"));
        Assert.assertFalse(result.contains("MERGE (node1:Country{"));

        Assert.assertTrue(result.contains("MERGE (node1:Planet{"));

        // As long as nodes are numbered from 0, 1 is now the planet in this test and there is no 2

        Assert.assertTrue(result.contains("SET node0.id"));
        Assert.assertTrue(result.contains("SET node0.name"));
        Assert.assertTrue(result.contains("SET node1.id"));
        Assert.assertTrue(result.contains("SET node1.name"));

        Assert.assertFalse(result.contains("SET node2.id"));
        Assert.assertFalse(result.contains("SET node2.name"));
    }


    @Test
    public void testToStringWithTownCountryPlanet_BadHints_tooMuchColumsWillfail() throws Exception {
        URL url = ClassLoader.getSystemResource("towncountryplanet.csv");
        String[][] filecontent = loadFile("towncountryplanet.csv",";",3);

        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFileWithPath(url.toString());
        skel.setExampleValuescount(0);
        skel.setFieldSeparator(";");

        List<Hint> hints = new ArrayList<>();
        hints.add( new Hint("Town",2) );
        hints.add( new Hint("Country",2) );
        hints.add( new Hint("Planet",2) );
        hints.add( new Hint("Galaxy",4) );
        skel.setHints( hints );
        skel.setFileContent(filecontent);

        boolean caught = false;

        try {
            String result = skel.toString();
        }
        catch (ArrayIndexOutOfBoundsException a){
            caught = true;
        }

        assertTrue(caught);
    }
}