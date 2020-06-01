package uk.ac.ucl.jsh.Mv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;
import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.ArrayList;

public class mvNormalTest {
    ArrayList<File> files = new ArrayList<>();

    private Jsh jsh = new Jsh();
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out;
    String testDirectory = System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "textHandling" + File.separator + "mv";

    public mvNormalTest() throws IOException {
        out = new PipedOutputStream(in);
        jsh.currentDirectory = testDirectory;
    }

    @Before
    public void constructTestEnvironment() throws Exception
    {
        File dir1 = new File(testDirectory + File.separator + "dir1");
        dir1.mkdir();
        files.add(dir1);
        files.add(quickCreateFile("Got more keys than Khadija", "file1.txt"));
        files.add(quickCreateFile("Got more keys than Lykissas", "file2.txt"));
    }

    @Test
    public void fileMoveToNonExistingFile() throws Exception
    {
        String originalText = returnFileContent(files.get(1)); //Gets file1.txt
        jsh.eval.eval("mv file1.txt result.txt", out);
        File results = new File(testDirectory + File.separator + "result.txt");
        files.add(results);
        System.out.println(originalText);
        assertEquals(originalText, returnFileContent(results)); //Check if it contains the original content
    }

    @Test
    public void fileMoveToExistingFile() throws Exception
    {
        String originalText = returnFileContent(files.get(1));
        jsh.eval.eval("mv file1.txt file2.txt", out);
        File results = new File(testDirectory + File.separator + "file2.txt");
        assertEquals(originalText, returnFileContent(results));
    }

    @Test
    public void fileMoveToDir() throws Exception
    {
        String originalText = returnFileContent(files.get(1));
        jsh.eval.eval("mv file1.txt dir1", out);
        File results = new File(testDirectory + File.separator + "dir1" + File.separator + "file1.txt");
        files.add(results);
        assertEquals(originalText, returnFileContent(results));
    }

    private void deleteAllFiles(ArrayList<File> files)
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

    private String returnFileContent(File f) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        br.close();
        return sb.toString();
    }
}
