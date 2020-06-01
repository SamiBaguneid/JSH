package uk.ac.ucl.jsh.Sed;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class sedValidTest {
    private Jsh jsh = new Jsh();
    InputStream systemIn = System.in;
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "sed";

    public sedValidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void testReplaceAll() throws Exception
    {
        jsh.eval.eval("sed s/file/doc/g file1.txt", out);
        assertEquals("This doc is from doc1", scn.nextLine());
        assertEquals("doc1 is cool", scn.nextLine());
        assertEquals("just like 2015 jeff", scn.nextLine());
    }

    @Test
    public void testReplaceFirst() throws Exception
    {
        jsh.eval.eval("sed s/file/doc/ file1.txt", out);
        assertEquals("This doc is from file1", scn.nextLine());
        assertEquals("doc1 is cool", scn.nextLine());
        assertEquals("just like 2015 jeff", scn.nextLine());
    }

    @Test
    public void testDifferentSeparator() throws Exception
    {
        jsh.eval.eval("sed s5file5doc5 file1.txt", out);
        assertEquals("This doc is from file1", scn.nextLine());
        assertEquals("doc1 is cool", scn.nextLine());
        assertEquals("just like 2015 jeff", scn.nextLine());
    }


    //sort this out tomorrow
    @Test
    public void testStdIn() throws Exception
    {
        String output = "file4 doesnt exist" + System.lineSeparator()
                + "file4 isnt cool" + System.lineSeparator()
                + "just like that Nav" + System.lineSeparator()
                + System.lineSeparator() + System.lineSeparator();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
        System.setIn(inputStream);
        jsh.eval.eval("sed s/file/Navraj/g ", out);
        assertEquals("Navraj4 doesnt exist", scn.nextLine());
        //assertEquals("Navraj4 isnt cool", scn.nextLine());
        //assertEquals("just like that Nav", scn.nextLine());
        System.setIn(systemIn);
    }
}

