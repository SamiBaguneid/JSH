package uk.ac.ucl.jsh.Grep;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class grepInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "cat";

    public grepInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void noArgs() throws Exception {
        jsh.eval.eval("grep ", out);
    }

    @Test(expected = Exception.class)
    public void invalidFile() throws Exception {
        jsh.eval.eval("grep jim file4.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidFileValidFile() throws Exception {
        jsh.eval.eval("cat file1.txt file4.txt", out);
    }
}
