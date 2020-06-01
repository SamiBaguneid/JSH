package uk.ac.ucl.jsh.Pwd;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class pwdInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "cd";

    public pwdInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void invalidArgument() throws Exception {
        jsh.eval.eval("pwd thug", out);
    }

    @Test(expected = Exception.class)
    public void invalidMultipleArguments() throws Exception {
        jsh.eval.eval("pwd thug daBaby kendrick", out);
    }

    @Test(expected = Exception.class)
    public void invalidOption() throws Exception {
        jsh.eval.eval("pwd -q", out);
    }

    @Test(expected = Exception.class)
    public void invalidMultipleOptions() throws Exception {
        jsh.eval.eval("pwd -q -p", out);
    }
}

