package uk.ac.ucl.jsh.Head;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class headOptionsTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "head";

    public headOptionsTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void validOption() throws Exception
    {
        jsh.eval.eval("head -n 7 file1.txt", out);
        assertEquals("Line (01)", scn.nextLine());
        assertEquals("Line (02)", scn.nextLine());
        assertEquals("Line (03)", scn.nextLine());
        assertEquals("Line (04)", scn.nextLine());
        assertEquals("Line (05)", scn.nextLine());
        assertEquals("Line (06)", scn.nextLine());
        assertEquals("Line (07)", scn.nextLine());
    }

    @Test
    public void testExactlyN() throws Exception {
        jsh.eval.eval("head -n 30 file1.txt", out);
        assertEquals("Line (01)", scn.nextLine());
        assertEquals("Line (02)", scn.nextLine());
        assertEquals("Line (03)", scn.nextLine());
        assertEquals("Line (04)", scn.nextLine());
        assertEquals("Line (05)", scn.nextLine());
        assertEquals("Line (06)", scn.nextLine());
        assertEquals("Line (07)", scn.nextLine());
        assertEquals("Line (08)", scn.nextLine());
        assertEquals("Line (09)", scn.nextLine());
        assertEquals("Line (10)", scn.nextLine());
        assertEquals("Line (11)", scn.nextLine());
        assertEquals("Line (12)", scn.nextLine());
        assertEquals("Line (13)", scn.nextLine());
        assertEquals("Line (14)", scn.nextLine());
        assertEquals("Line (15)", scn.nextLine());
        assertEquals("Line (16)", scn.nextLine());
        assertEquals("Line (17)", scn.nextLine());
        assertEquals("Line (18)", scn.nextLine());
        assertEquals("Line (19)", scn.nextLine());
        assertEquals("Line (20)", scn.nextLine());
        assertEquals("Line (21)", scn.nextLine());
        assertEquals("Line (22)", scn.nextLine());
        assertEquals("Line (23)", scn.nextLine());
        assertEquals("Line (24)", scn.nextLine());
        assertEquals("Line (25)", scn.nextLine());
        assertEquals("Line (26)", scn.nextLine());
        assertEquals("Line (27)", scn.nextLine());
        assertEquals("Line (28)", scn.nextLine());
        assertEquals("Line (29)", scn.nextLine());
        assertEquals("Line (30)", scn.nextLine());
    }

    @Test
    public void shortFile() throws Exception
    {
        jsh.eval.eval("head -n 6 file2.txt", out);
        assertEquals("Line (01)", scn.nextLine());
        assertEquals("Line (02)", scn.nextLine());
        assertEquals("Line (03)", scn.nextLine());
        assertEquals("Line (04)", scn.nextLine());
        assertEquals("Line (05)", scn.nextLine());
    }

    @Test
    public void zeroNumberOfLines() throws Exception
    {
        jsh.eval.eval("head -n 0 file1.txt", out);
        assertEquals("", scn.nextLine());
    }

    @Test
    public void testEmptyFile() throws Exception
    {
        jsh.eval.eval("head -n 65 file4.txt", out);
        assertEquals("", scn.nextLine());
    }

}
