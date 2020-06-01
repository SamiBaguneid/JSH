package uk.ac.ucl.jsh.Ls;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class lsValidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "fileHandling" + File.separator + "ls";

    public lsValidTest() throws IOException {
        out = new PipedOutputStream(in);
        Jsh.currentDirectory = testDirectory;
    }

    @Test
    public void validSubDir() throws Exception {
        jsh.eval.eval("ls \"Random Songs\"", out);
        assertEquals("Up To Something.mp3", scn.nextLine());
    }

}
