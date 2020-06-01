package uk.ac.ucl.jsh.Wc;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class wcNoOptionsTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "wc";

    public wcNoOptionsTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void wcSingleFile() throws Exception
    {
        jsh.eval.eval("wc 'file 1.txt'", out);

        assertEquals(Jsh.ANSI_YELLOW + "<< file 1.txt >>", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Lines:      " + Jsh.ANSI_RESET +  "6", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Characters: " + Jsh.ANSI_RESET +  "137", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Words:      " + Jsh.ANSI_RESET + "25", scn.nextLine());
        assertEquals( Jsh.ANSI_GREEN + "Bytes:      " + Jsh.ANSI_RESET + "137", scn.nextLine());
    }

    @Test
    public void wcSingleFile2() throws Exception
    {
        jsh.eval.eval("wc 'file 2.txt'", out);

        assertEquals(Jsh.ANSI_YELLOW + "<< file 2.txt >>", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Lines:      " + Jsh.ANSI_RESET +  "6", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Characters: " + Jsh.ANSI_RESET +  "184", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Words:      " + Jsh.ANSI_RESET + "32", scn.nextLine());
        assertEquals( Jsh.ANSI_GREEN + "Bytes:      " + Jsh.ANSI_RESET + "184", scn.nextLine());
    }

    @Test
    public void wcSingleFile3Empty() throws Exception
    {
        jsh.eval.eval("wc 'file 3 (empty).txt'", out);

        assertEquals(Jsh.ANSI_YELLOW + "<< file 3 (empty).txt >>", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Lines:      " + Jsh.ANSI_RESET +  "0", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Characters: " + Jsh.ANSI_RESET +  "0", scn.nextLine());
        assertEquals(Jsh.ANSI_GREEN + "Words:      " + Jsh.ANSI_RESET + "0", scn.nextLine());
        assertEquals( Jsh.ANSI_GREEN + "Bytes:      " + Jsh.ANSI_RESET + "0", scn.nextLine());
    }
}
