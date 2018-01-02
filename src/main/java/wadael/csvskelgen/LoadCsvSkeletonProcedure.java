package wadael.csvskelgen;

import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 * Procedure for Neo4j and lazy people like me. Or who have a CSV with a hundred columns.
 * Feed it with the url of a CSV file with headers, some optionnal inputs
 * and get a nice hot smelly skeleton of LOAD CSV instructions generated for you.
 * Then, up to you, erase lines, add, rename.
 * Send bitcoins ;)
 */

public class LoadCsvSkeletonProcedure {

    @Procedure(value = "wadael.csvskelgen", mode = Mode.READ)
    @Description("Will create a LOAD CSV skeleton. Use full path for the moment. \n" +
            "SeparatorChar is probably ;,| \n " +
            " Parameter exampleValuescount is how many example values you want to be given as a comment in the generated Cypher." +
            " hints is a comma separated list of pairs giving the name of the label to use, a semicolon, and a numerical value giving the number of columns for this label (like Person:10,Town:4,Country:2)" )
    public Stream<StringResult> createSkeleton (
            @Name("csvPath")String csvFilename,
            @Name("separatorChar") String separatorChar,
            @Name("exampleValuescount") long exampleValuescount,
            @Name("hints") String hints ) throws Exception {

        LoadCSVSkeleton skel = new LoadCSVSkeleton();
        skel.setFieldSeparator(separatorChar);
        skel.setFileWithPath(csvFilename);
        skel.setExampleValuescount(exampleValuescount);
        skel.setHints( Hint.getHints(hints));

        File file =  Paths.get(csvFilename).toFile() ;
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        line = br.readLine();

        StringTokenizer stringTokenizer = new StringTokenizer( line, separatorChar );
        if(stringTokenizer.countTokens() ==0) throw new Exception("Tokenizing failed, please check that file content matches parameters and vice versa");

        int nbLines = ((int)exampleValuescount)+1;
        final String[][] fileContent = new String[nbLines][stringTokenizer.countTokens()] ;

        fr = new FileReader(file);
        br = new BufferedReader(fr);

        int indexRow = 0;
        while((line = br.readLine()) != null && indexRow < nbLines){
            stringTokenizer = new StringTokenizer( line, separatorChar );
            int index = 0;
            while(stringTokenizer !=null && stringTokenizer.hasMoreElements()){
                fileContent[indexRow][index++] = stringTokenizer.nextToken();
            }
            indexRow++;
        }

        skel.setFileContent( fileContent );
        List<StringResult> resultList = new ArrayList<StringResult>() ;
        resultList.add(new StringResult(skel.toString()));
        return resultList.stream();
    }
}
