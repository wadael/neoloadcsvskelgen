package wadael.csvskelgen;

import org.junit.Assert;
import org.junit.Test;
import wadael.csvskelgen.Hint;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Checking for the expected behaviors
 */
public class HintTest {

    @Test
    public void testFivePersonsOk() throws Exception{
        Hint hint = new Hint("Person:5");
        assertTrue(hint.getColumnCount()==5);
        assertTrue( hint.getLabelName().equals("Person"));
    }

    @Test
    public void testFivePersonsKO(){
        boolean caught = false;
        try {
            Hint hint = new Hint("Person5");
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }
        Assert.assertTrue(caught);
    }

    @Test
    public void testNoHint(){
        boolean caught = false;
        try {
            Hint hint = new Hint(null);
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }
        Assert.assertTrue(caught);
    }

    @Test
    public void testEmptyHint(){
        boolean caught = false;
        try {
            Hint hint = new Hint("");
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }
        Assert.assertTrue(caught);
    }

    @Test
    public void testSpacyHint(){
        boolean caught = false;
        try {
            Hint hint = new Hint("   ");
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }
        Assert.assertTrue(caught);
    }

    @Test
    public void shouldReturnHints() throws Exception{
        List<Hint> hints = Hint.getHints("Toto:2,Titi:3,Tutu:4");
        assertNotNull(hints);

        assertTrue( hints.size() == 3);
    }

    @Test
    public void shouldNotReturnHints() throws Exception{
        List<Hint> hints = Hint.getHints("");
        assertNull(hints);

        hints = Hint.getHints(null);
        assertNull(hints);
    }

    @Test
    public void testIncompleteHint() throws Exception{

        boolean caught = false;

        try {
            List<Hint> hints = Hint.getHints("ohwell");
            Assert.assertTrue(hints.size()==0);
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }

        Assert.assertTrue(caught);

        try {
            List<Hint> hints = Hint.getHints(":42");
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }
        Assert.assertTrue(caught);

        caught = false;
        try {
            List<Hint> hints = Hint.getHints("42");
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }
        Assert.assertTrue(caught);


        caught = false;
        try {
            List<Hint> hints = Hint.getHints("42,good:1");
        } catch (Exception e) {
            // "Normal"
            caught = true;
        }
        Assert.assertTrue(caught);
    }

}
