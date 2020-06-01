package uk.ac.ucl.jsh.MultiAppTests;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class UnsafeTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir");

    public UnsafeTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void unsafeCD() throws Exception
    {
        jsh.eval.eval("_cd johnny test", out);
        assertEquals("too many arguments", scn.nextLine());
        //doesn't throw an exception even tho cd is given too many args
        //cd prints its own exception and terminates
    }

    @Test
    public void pipeUnsafeCD() throws Exception
    {
        jsh.eval.eval("_cd johnny test| pwd", out);
        assertEquals(Jsh.currentDirectory, scn.nextLine());
        //doesn't throw an exception even tho cd is given too many args and still prints pwd
        //cd prints its own exception and terminates
    }
}
