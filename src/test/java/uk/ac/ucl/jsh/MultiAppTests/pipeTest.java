package uk.ac.ucl.jsh.MultiAppTests;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class pipeTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "grep";

    public pipeTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void pipeCatGrep() throws Exception
    {
        jsh.eval.eval("cat file1.txt file2.txt | grep file", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
        assertEquals("just like 2015 jeffThis text is from file2", scn.nextLine());
        assertEquals("file2 is cool", scn.nextLine());
    }

    @Test
    public void pipeGrepCat() throws Exception
    {
        jsh.eval.eval("grep file file1.txt file2.txt | cat", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
        assertEquals("This text is from file2", scn.nextLine());
        assertEquals("file2 is cool", scn.nextLine());
    }

    @Test
    public void pipeGrepWc() throws Exception
    {
        jsh.eval.eval("grep file file1.txt | wc -l", out);
        assertEquals("\u001B[33m<< Input 1 >>", scn.nextLine());
        assertEquals("\u001B[32mLines:      \u001B[0m2", scn.nextLine());
    }
}