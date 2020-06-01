package uk.ac.ucl.jsh.MultiAppTests;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class headCmdSubTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    Scanner scn = new Scanner(in);
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "head";

    public headCmdSubTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test
    public void subFind() throws Exception
    {
        jsh.eval.eval("head -n 7 `find -name file1`", out);
        assertEquals("Line (01)", scn.nextLine());
        assertEquals("Line (02)", scn.nextLine());
        assertEquals("Line (03)", scn.nextLine());
        assertEquals("Line (04)", scn.nextLine());
        assertEquals("Line (05)", scn.nextLine());
        assertEquals("Line (06)", scn.nextLine());
        assertEquals("Line (07)", scn.nextLine());
    }



    @Test
    public void subTwoCat() throws Exception
    {
        jsh.eval.eval("cat `find -name file2` `find -name file4`", out);
        assertEquals("Line (01)", scn.nextLine());
        assertEquals("Line (02)", scn.nextLine());
        assertEquals("Line (03)", scn.nextLine());
        assertEquals("Line (04)", scn.nextLine());
        assertEquals("Line (05)", scn.nextLine());
    }

    //stat padding
    @Test
    public void subPipe() throws Exception
    {
        jsh.eval.eval("echo `_cd | pwd`", out);
        assertEquals(Jsh.currentDirectory, scn.nextLine());
    }
}

