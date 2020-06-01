package uk.ac.ucl.jsh.Cd;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class cdInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "cd";

    public cdInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void noDirectory() throws Exception {
        jsh.eval.eval("cd", out);
    }

    @Test(expected = Exception.class)
    public void invalidDirectory() throws Exception {
        jsh.eval.eval("cd which", out);
        jsh.eval.eval("cd quavo", out);
    }

    @Test(expected = Exception.class)
    public void invalidSubDirectory() throws Exception {
        jsh.eval.eval("cd kendrick", out);
    }

    @Test(expected = Exception.class)
    public void twoValidDirectories() throws Exception {
        jsh.eval.eval("cd which empty", out);
    }

    @Test(expected = Exception.class)
    public void ValidDirectoriesSpaced() throws Exception {
        jsh.eval.eval("cd which artist is the goat", out);
    }

    @Test(expected = Exception.class)
    public void validFile() throws Exception {
        jsh.eval.eval("cd file.txt", out);
    }


    @Test(expected = Exception.class)
    public void anyKindOfOption() throws Exception {
        jsh.eval.eval("cd -q empty", out);
    }
}
