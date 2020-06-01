package uk.ac.ucl.jsh.Echo;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class echoValidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);

    public echoValidTest() throws IOException {
        out = new PipedOutputStream(in);
    }

    @Test
    public void echoNoArgs() throws Exception
    {
        jsh.eval.eval("echo", out);
        assertEquals("", scn.nextLine());
    }

    @Test
    public void echoSingleArg() throws Exception
    {
        jsh.eval.eval("echo foo", out);
        assertEquals("foo", scn.nextLine());
    }

    @Test
    public void echoTwoArgs() throws Exception
    {
        jsh.eval.eval("echo foo bar", out);
        assertEquals("foo bar", scn.nextLine());
    }

    @Test
    public void echoArgQuoted() throws Exception
    {
        jsh.eval.eval("echo 'foo bar'", out);
        assertEquals("foo bar", scn.nextLine());
    }

}
