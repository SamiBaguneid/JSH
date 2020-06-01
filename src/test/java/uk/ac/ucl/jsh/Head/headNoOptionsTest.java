package uk.ac.ucl.jsh.Head;

import org.junit.Test;
import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class headNoOptionsTest {
    private Jsh jsh = new Jsh();
    InputStream systemIn = System.in;
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "head";

    public headNoOptionsTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void test() throws Exception
    {
        jsh.eval.eval("head file1.txt", out);
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
    public void testShortFile() throws Exception
    {
        jsh.eval.eval("head file2.txt", out);
        assertEquals("Line (01)", scn.nextLine());
        assertEquals("Line (02)", scn.nextLine());
        assertEquals("Line (03)", scn.nextLine());
        assertEquals("Line (04)", scn.nextLine());
        assertEquals("Line (05)", scn.nextLine());
    }

    @Test
    public void testExactly10() throws Exception
    {
        jsh.eval.eval("head file3.txt", out);
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
        jsh.eval.eval("head file4.txt", out);
        assertEquals("", scn.nextLine());
    }

    @Test
    public void testStdIn() throws Exception
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("'Multiple\nLines\n\n'".getBytes());
        System.setIn(inputStream);
        jsh.eval.eval("head ", out);
        assertEquals("Multiple", scn.nextLine());
        assertEquals("Lines", scn.nextLine());
        System.setIn(systemIn);
    }
}
