package uk.ac.ucl.jsh.Grep;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class grepValidTest {
    private Jsh jsh = new Jsh();
    InputStream systemIn = System.in;
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "grep";

    public grepValidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void testOneFile() throws Exception
    {
        jsh.eval.eval("grep file file1.txt", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
    }

    @Test
    public void grepTwoFiles() throws Exception
    {
        jsh.eval.eval("grep file file1.txt file2.txt", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
        assertEquals("This text is from file2", scn.nextLine());
        assertEquals("file2 is cool", scn.nextLine());
    }

    @Test
    public void testThreeFiles() throws Exception
    {
        jsh.eval.eval("grep file file1.txt file2.txt file3.txt", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
        assertEquals("This text is from file2", scn.nextLine());
        assertEquals("file2 is cool", scn.nextLine());
        assertEquals("This text is from file3", scn.nextLine());
        assertEquals("file3 is cool", scn.nextLine());
    }

    @Test
    public void notMatching() throws Exception
    {
        jsh.eval.eval("grep kendrick file1.txt", out);
        assertEquals("\u001B[33mNo matching lines were found\u001B[0m", scn.nextLine());
    }

    @Test
    public void testStdIn() throws Exception
    {
        String output = "\"file4 doesnt exist" + System.lineSeparator()
                + "file4 isnt cool" + System.lineSeparator()
                + "just like that Nav\"" + System.lineSeparator()
                + "" + System.lineSeparator() + "" + System.lineSeparator() + System.lineSeparator();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
        System.setIn(inputStream);
        jsh.eval.eval("grep file ", out);
        assertEquals("file4 doesnt exist", scn.nextLine());
        assertEquals("file4 isnt cool", scn.nextLine());
        System.setIn(systemIn);
    }
}
