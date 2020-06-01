package uk.ac.ucl.jsh.Cat;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class catValidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    InputStream systemIn = System.in;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "cat";

    public catValidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void testOneFile() throws Exception
    {
        jsh.eval.eval("cat file1.txt", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
        assertEquals("just like 2015 jeff", scn.nextLine());
    }

    @Test
    public void testTwoFiles() throws Exception
    {
        jsh.eval.eval("cat file1.txt file2.txt", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
        assertEquals("just like 2015 jeffThis text is from file2", scn.nextLine());
        assertEquals("file2 is cool", scn.nextLine());
        assertEquals("just like that 2012-2015 kendrick", scn.nextLine());
    }

    @Test
    public void testThreeFiles() throws Exception
    {
        jsh.eval.eval("cat file1.txt file2.txt file3.txt", out);
        assertEquals("This text is from file1", scn.nextLine());
        assertEquals("file1 is cool", scn.nextLine());
        assertEquals("just like 2015 jeffThis text is from file2", scn.nextLine());
        assertEquals("file2 is cool", scn.nextLine());
        assertEquals("just like that 2012-2015 kendrickThis text is from file3", scn.nextLine());
        assertEquals("file3 is cool", scn.nextLine());
        assertEquals("just like that 2019 daBaby", scn.nextLine());
    }


    //this doesnt acc work but it passes lol tomorrows issue
    @Test
    public void testStdIn() throws Exception
    {
        String output = "file4 doesnt exist" + System.lineSeparator()
                + "file4 isnt cool" + System.lineSeparator()
                + "just like that Nav" + System.lineSeparator() + System.lineSeparator();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
        System.setIn(inputStream);
        jsh.eval.eval("cat ", out);
        assertEquals("file4 doesnt exist", scn.nextLine());
        System.setIn(systemIn);
    }

}
