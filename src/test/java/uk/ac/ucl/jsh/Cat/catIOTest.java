package uk.ac.ucl.jsh.Cat;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class catIOTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "cat";

    public catIOTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void testOneFile() throws Exception
    {
        jsh.eval.eval("cat file1.txt > createdInTestFile.txt", out);
    }
}
