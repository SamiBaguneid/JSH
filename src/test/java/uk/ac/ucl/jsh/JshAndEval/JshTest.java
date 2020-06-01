package uk.ac.ucl.jsh.JshAndEval;

import org.junit.Test;
import uk.ac.ucl.jsh.Jsh;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class JshTest {
    Jsh jsh = new Jsh();
    InputStream systemIn = System.in;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    BufferedReader br;


    //I AM ACC THE GOAT FOR THIS TEST
    @Test
    public void noArgs() throws Exception
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("echo foo\nexit".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        jsh.main(new String[0]);

        br = new BufferedReader(new StringReader(new String(outContent.toByteArray())));
        String result = br.readLine();
        assertEquals(" " + Jsh.ANSI_RESET + "foo", result.split(">")[1]);

        //Resets system.in and system.out to default java vals
        System.setIn(systemIn);
        System.setOut(System.out);
    }

    //I AM ACC THE GOAT FOR THIS TEST
    @Test
    public void incorrectStdSyntax() throws Exception
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("grep \nexit".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outContent));
        jsh.main(new String[0]);

        br = new BufferedReader(new StringReader(new String(outContent.toByteArray())));
        String result = br.readLine();
        assertEquals(" " + Jsh.ANSI_RESET + Jsh.ANSI_RED + "jsh: No pattern provided!" + Jsh.ANSI_RESET, result.split(">")[1]);

        //Resets system.in and system.out to default java vals
        System.setIn(systemIn);
        System.setOut(System.out);
    }

    //I AM ACC THE UNDISPUTED GOAT FOR THIS TEST
    @Test
    public void args() throws Exception
    {
        System.setOut(new PrintStream(outContent));
        jsh.main(new String[] {"-c", "echo foo"});

        br = new BufferedReader(new StringReader(new String(outContent.toByteArray())));
        assertEquals("foo", br.readLine());

        //Resets system.in and system.out to default java vals
        System.setOut(System.out);
    }

    //I AM ACC THE UNDISPUTED GOAT FOR THIS TEST
    @Test(expected = Exception.class)
    public void tooManyArgs()
    {
        jsh.main(new String[] {"-c", "echo foo", "wangsap"});
    }

    //I AM ACC THE UNDISPUTED GOAT FOR THIS TEST
    @Test(expected = Exception.class)
    public void wrongFirstArg()
    {
        jsh.main(new String[] {"this should be -c", "echo foo"});
    }

    //I AM ACC THE UNDISPUTED GOAT FOR THIS TEST
    @Test
    public void incorrectCommandSyntax() throws Exception
    {
            System.setOut(new PrintStream(outContent));
            jsh.main(new String[] {"-c", "grep"});

            br = new BufferedReader(new StringReader(new String(outContent.toByteArray())));
            assertEquals(Jsh.ANSI_RED + "jsh: No pattern provided!" + Jsh.ANSI_RESET, br.readLine());

            //Resets system.in and system.out to default java vals
            System.setOut(System.out);
    }
}
