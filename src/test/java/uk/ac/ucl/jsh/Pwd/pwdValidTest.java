package uk.ac.ucl.jsh.Pwd;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class pwdValidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "cd";

    public pwdValidTest() throws IOException {
        out = new PipedOutputStream(in);
        Jsh.currentDirectory = testDirectory;
    }

    @Test
    public void valid() throws Exception {
        jsh.eval.eval("pwd", out);
        assertEquals(scn.nextLine(), Jsh.currentDirectory);
    }

}