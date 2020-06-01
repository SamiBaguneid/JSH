package uk.ac.ucl.jsh.Wc;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class wcInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "wc";

    public wcInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void fileDoesntExist() throws Exception
    {
        jsh.eval.eval("wc thisFileDoesntExist.txt");
    }

    @Test(expected = Exception.class)
    public void optionDoesntExist() throws Exception
    {
        jsh.eval.eval("wc -q 'file 1.txt'");
    }

    @Test(expected = Exception.class)
    public void invalidFileType() throws Exception
    {
        jsh.eval.eval("wc dir");
    }
}
