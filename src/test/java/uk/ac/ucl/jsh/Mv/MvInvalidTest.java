package uk.ac.ucl.jsh.Mv;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;
import java.util.ArrayList;

public class MvInvalidTest {
    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "mv";
    ArrayList<File> files = new ArrayList<>();


    @Before
    public void constructTestEnvironment() throws Exception
    {
        File dir1 = new File(testDirectory + File.separator + "dir1");
        dir1.mkdir();
        files.add(dir1);
        files.add(quickCreateFile("Got more keys than Khadija", "file1.txt"));
        files.add(quickCreateFile("Got more keys than Lykissas", "file2.txt"));
    }

    public void deleteAllFiles(ArrayList<File> files)
    {
        for(File file : files)
        {
            file.delete();
        }
    }

    @After
    public void deconstructTestEnvironment()
    {
        deleteAllFiles(files);
    }

    private File quickCreateFile(String content, String fileName) throws Exception {
        File file = new File(testDirectory + File.separator + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
        return file;

    }

    public MvInvalidTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Test(expected = Exception.class)
    public void noArgs() throws Exception
    {
        jsh.eval.eval("mv");
    }

    @Test(expected = Exception.class)
    public void tooManyArgs() throws Exception
    {
        jsh.eval.eval("mv a b c");
    }

    @Test(expected = Exception.class)
    public void fileDoesntExist() throws Exception
    {
        jsh.eval.eval("mv wonga.txt file2.txt");
    }
}
