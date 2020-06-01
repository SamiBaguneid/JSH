package uk.ac.ucl.jsh.Parser;


import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class singleCallTest {
    Parser parser = new Parser();

    @Test
    public void singleWordSingleCall() throws Exception
    {
        Commands commands = parser.parse("word");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);

        Command c = commands.getCommand(0);
        assertEquals(c.getCommandName(), "word");
        assertEquals(c.getArguments().size(), 0);
        assertEquals(c.getOptions().size(), 0);
        assertEquals(c.getNumOfOutputFiles(), 0);
    }

    @Test
    public void multipleArgsSingleCall() throws Exception
    {
        Commands commands = parser.parse("word arg1 arg2 arg3");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);

        Command c = commands.getCommand(0);
        assertEquals(c.getCommandName(), "word");
        assertEquals(c.getArguments().size(), 3);
        assertEquals(c.getArguments().get(0), "arg1");
        assertEquals(c.getArguments().get(1), "arg2");
        assertEquals(c.getArguments().get(2), "arg3");
    }

    @Test
    public void multipleOptionsSingleCall() throws Exception
    {
        Commands commands = parser.parse("word -a -b -c");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);

        Command c = commands.getCommand(0);
        assertEquals(c.getCommandName(), "word");
        assertEquals(c.getArguments().size(), 0);
        assertEquals(c.getOptions().get(0), "-a");
        assertEquals(c.getOptions().get(1), "-b");
        assertEquals(c.getOptions().get(2), "-c");
        assertEquals(c.getOptions().size(), 3);
        assertEquals(c.getNumOfOutputFiles(), 0);
    }

    @Test
    public void unsafeSingleCall() throws Exception
    {
        Commands commands = parser.parse("_word");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);
        Command c = commands.getCommand(0);

        assertEquals(c.isCommandUnSafe(), true);
    }

    @Test
    public void redirectedSingleCall() throws Exception
    {
        Commands commands = parser.parse("word > file1");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);
        Command c = commands.getCommand(0);
        assertEquals(c.numOfRedirections(), 1);
    }
}
