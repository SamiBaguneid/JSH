package uk.ac.ucl.jsh.MultiAppTests;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class exceptionThrowersTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir");

    public exceptionThrowersTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void imaginaryApp() throws Exception {
        jsh.eval.eval("helicopter_simulator fly away", out);
    }

    @Test(expected = Exception.class)
    public void imaginaryDir() throws Exception {
        jsh.eval.eval("ls `cd noBro`", out);
    }
}