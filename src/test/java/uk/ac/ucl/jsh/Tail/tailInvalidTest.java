package uk.ac.ucl.jsh.Tail;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class tailInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "head";

    public tailInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void invalidOption() throws Exception
    {
        jsh.eval.eval("tail -q 10 file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfLines() throws Exception
    {
        jsh.eval.eval("tail -n HARDEN file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidFile() throws Exception {
        jsh.eval.eval("head -n 5 file.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidFolder() throws Exception {
        jsh.eval.eval("tail -n 5 foldr", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfOptions() throws Exception
    {
        jsh.eval.eval("tail -n -n 5 file1.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfFiles() throws Exception
    {
        jsh.eval.eval("tail -n 5 file1.txt file2.txt", out);
    }

    @Test(expected = Exception.class)
    public void invalidAllTheFiles() throws Exception
    {
        jsh.eval.eval("tail -n 5 *", out);
    }

    @Test(expected = Exception.class)
    public void invalidNumberOfNumberOfLines() throws Exception
    {
        jsh.eval.eval("tail -n 5 7 file1.txt", out);
    }
}
