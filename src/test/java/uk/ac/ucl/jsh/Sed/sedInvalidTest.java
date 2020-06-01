package uk.ac.ucl.jsh.Sed;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class sedInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "sed";

    public sedInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void noArgs() throws Exception {
        jsh.eval.eval("sed ", out);
    }

    @Test(expected = Exception.class)
    public void invalidArg() throws Exception {
        jsh.eval.eval("sed t/file/while/ file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void excessNumberOfReplacements() throws Exception {
        jsh.eval.eval("sed s/file/smile/while file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfFiles() throws Exception {
        jsh.eval.eval("sed s/file/smile/while file1.txt file2.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfReplacements() throws Exception {
        jsh.eval.eval("sed s/file/ file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidLastReplacement() throws Exception {
        jsh.eval.eval("sed s/file/nile/z file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void separatorWithinString() throws Exception {
        jsh.eval.eval("sed s:file:C:/Users:g file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void imaginaryFile() throws Exception {
        jsh.eval.eval("sed s:file:C/Users:g eternalAtake.txt", out);
    }
}
