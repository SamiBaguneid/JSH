package uk.ac.ucl.jsh.Cd;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import static org.junit.Assert.assertEquals;

public class cdValidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "cd";

    public cdValidTest() throws IOException {
        out = new PipedOutputStream(in);
        Jsh.currentDirectory = testDirectory;
    }

    @Test
    public void validDirectory() throws Exception {
        jsh.eval.eval("cd which", out);
        assertEquals(testDirectory + File.separator + "which", Jsh.currentDirectory);
    }

    @Test
    public void validSlashDirectory() throws Exception {
        jsh.eval.eval("cd /which", out);
        assertEquals(testDirectory + File.separator + "which", Jsh.currentDirectory);
    }

    @Test
    public void validSubDirectory() throws Exception {
        jsh.eval.eval("cd which/thugger", out);
        assertEquals(testDirectory + File.separator + "which" + File.separator + "thugger", Jsh.currentDirectory);
    }

    @Test
    public void validSpacesDirectory() throws Exception {
        jsh.eval.eval("cd \"which artist is the goat\"", out);
        assertEquals(testDirectory + File.separator + "which artist is the goat", Jsh.currentDirectory);
    }

    @Test
    public void validEnterSubDirectory() throws Exception {
        jsh.eval.eval("cd which", out);
        jsh.eval.eval("cd kendrick", out);
        assertEquals(testDirectory + File.separator + "which" + File.separator + "kendrick", Jsh.currentDirectory);
    }

    @Test
    public void validEnterLeaveDirectory() throws Exception {
        jsh.eval.eval("cd which", out);
        jsh.eval.eval("cd ..", out);
        assertEquals(testDirectory, Jsh.currentDirectory);
    }
}