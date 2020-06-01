package uk.ac.ucl.jsh.Find;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class findInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "fileHandling" + File.separator + "find";

    public findInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void invalidOption() throws Exception {
        jsh.eval.eval("find -q file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void missingName() throws Exception
    {
        jsh.eval.eval("find -name ", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfOptions() throws Exception
    {
        jsh.eval.eval("find -name -name presidentTimmy", out);
    }

    @Test(expected = Exception.class)
    public void invalidFolder() throws Exception {
        jsh.eval.eval("find foldr", out);
    }

    @Test(expected = Exception.class)
    public void invalidArgOrder() throws Exception
    {
        jsh.eval.eval("find a.txt dir1 -name", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfNames() throws Exception
    {
        jsh.eval.eval("find -name doc1.txt doc2.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidGlob() throws Exception
    {
        jsh.eval.eval("find *", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfNumberOfLines() throws Exception
    {
        jsh.eval.eval("head -n 5 7 file1.txt", out);
    }
}

