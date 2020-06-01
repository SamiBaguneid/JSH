package uk.ac.ucl.jsh.Wc;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class wcOptionsTest {
    private Jsh jsh = new Jsh();
    InputStream systemIn = System.in;
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "wc";

    public wcOptionsTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void wcSingleFileWords() throws Exception
    {
        jsh.eval.eval("wc -w 'file 1.txt'", out);

        assertEquals(Jsh.ANSI_YELLOW + "<< file 1.txt >>", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Words:      " + Jsh.ANSI_RESET + "25", scn.nextLine());
    }

    @Test
    public void wcSingleFileLines() throws Exception
    {
        jsh.eval.eval("wc -l 'file 2.txt'", out);

        assertEquals(Jsh.ANSI_YELLOW + "<< file 2.txt >>", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Lines:      " + Jsh.ANSI_RESET +  "6", scn.nextLine());
    }

    @Test
    public void wcSingleFileBytes() throws Exception
    {
        jsh.eval.eval("wc -c 'file 3 (empty).txt'", out);

        assertEquals(Jsh.ANSI_YELLOW + "<< file 3 (empty).txt >>", scn.nextLine());
        assertEquals( Jsh.ANSI_GREEN + "Bytes:      " + Jsh.ANSI_RESET + "0", scn.nextLine());
    }

    @Test
    public void wcTwoOptions() throws Exception
    {
        jsh.eval.eval("wc -w -l 'file 1.txt'", out);
        assertEquals(Jsh.ANSI_YELLOW + "<< file 1.txt >>", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Lines:      " + Jsh.ANSI_RESET +  "6", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Words:      " + Jsh.ANSI_RESET + "25", scn.nextLine());
    }

    @Test
    public void testStdIn() throws Exception
    {
        String output = "\"file5 doesnt exist" + System.lineSeparator()
                + "file5 isnt cool" + System.lineSeparator()
                + "just like that Lil Xan\"" + System.lineSeparator()
                + "" + System.lineSeparator()+ "" + System.lineSeparator() + System.lineSeparator();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.getBytes());
        System.setIn(inputStream);
        jsh.eval.eval("wc -m", out);
        assertEquals("\u001B[33m<< Input 1 >>", scn.nextLine());
        assertEquals("\u001B[32mCharacters: \u001B[0m57", scn.nextLine());
        System.setIn(systemIn);
    }
}
