package uk.ac.ucl.jsh.Tail;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class tailNoOptionsTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "head";

    public tailNoOptionsTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void test() throws Exception
    {
        jsh.eval.eval("tail file1.txt", out);
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
    public void testShortFile() throws Exception
    {
        jsh.eval.eval("tail file2.txt", out);
        assertEquals("Line (01)", scn.nextLine());
        assertEquals("Line (02)", scn.nextLine());
        assertEquals("Line (03)", scn.nextLine());
        assertEquals("Line (04)", scn.nextLine());
        assertEquals("Line (05)", scn.nextLine());
    }

    @Test
    public void testExactly10() throws Exception
    {
        jsh.eval.eval("tail file3.txt", out);
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
    }

    @Test
    public void testEmptyFile() throws Exception
    {
        jsh.eval.eval("tail file4.txt", out);
        assertEquals("", scn.nextLine());
    }
}
