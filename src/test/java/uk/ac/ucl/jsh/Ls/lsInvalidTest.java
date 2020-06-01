package uk.ac.ucl.jsh.Ls;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class lsInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "fileHandling" + File.separator + "ls";

    public lsInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        Jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void invalidDir() throws Exception {
        jsh.eval.eval("ls ScoobyDoo", out);
    }

    @Test(expected = Exception.class)
    public void excessArgs() throws Exception {
        jsh.eval.eval("ls \"Random Songs\" \"More Songs\"", out);
    }

}