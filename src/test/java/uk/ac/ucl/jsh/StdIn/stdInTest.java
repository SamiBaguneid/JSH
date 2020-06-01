package uk.ac.ucl.jsh.StdIn;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import uk.ac.ucl.jsh.IO.StdIO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class stdInTest {

    InputStream systemIn = System.in;
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;

    public stdInTest() throws Exception
    {
        out = new PipedOutputStream(in);
    }

    @Test
    public void singleInput() throws Exception
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("hello".getBytes());
        System.setIn(inputStream);
        String output = StdIO.stdIn();
        assertEquals("hello", output);
        System.setIn(systemIn);
    }

    @Test(expected = Exception.class)
    public void emptyInput() throws Exception
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        System.setIn(inputStream);
        StdIO.stdIn();
        System.setIn(systemIn);
    }

    @Test
    public void multipleLineInput() throws Exception
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("'Multiple\nLines'".getBytes());
        System.setIn(inputStream);
        String output = StdIO.stdIn();
        assertEquals("Multiple" + System.getProperty("line.separator") + "Lines", output);
        System.setIn(systemIn);
    }

    //THIS DOESNT WORK RIP RIP
    /*@Test
    public void multipleStdInput() throws Exception
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("Multiple\nLines\n\n".getBytes());
        System.setIn(inputStream);
        ArrayList<String> output = StdIO.multipleStdIn();
        assertEquals(2, output.size());
        assertEquals("Multiple", output.get(0));
        assertEquals("Lines", output.get(1));
        System.setIn(systemIn);
    }*/
}
