package uk.ac.ucl.jsh.Find;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class findOptionsTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "fileHandling" + File.separator + "find";

    public findOptionsTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }


    @Test
    public void testFileInSubDir() throws Exception {
        jsh.eval.eval("find -name a", out);
        assertEquals("/dir1/a.txt", scn.nextLine().replaceAll("\\\\", "/"));
    }

    @Test
    public void testSubDir() throws Exception {
        jsh.eval.eval("find dir1 -name a", out);
        assertEquals("/dir1/a.txt", scn.nextLine().replaceAll("\\\\", "/"));
    }
}