package uk.ac.ucl.jsh.Parser;


import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class singleCallWithQuotedArgsTest {
    Parser parser = new Parser();

    @Test
    public void doubleQuotes() throws Exception
    {
        Commands commands = parser.parse("cmd \"double quoted arg\"");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);

        Command c = commands.getCommand(0);
        assertEquals(c.getCommandName(), "cmd");
        assertEquals(c.getArguments().size(), 1);
        assertEquals(c.getArguments().get(0), "double quoted arg");
    }

    @Test
    public void singleQuotes() throws Exception
    {
        Commands commands = parser.parse("cmd \'single quoted arg\'");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);

        Command c = commands.getCommand(0);
        assertEquals(c.getCommandName(), "cmd");
        assertEquals(c.getArguments().size(), 1);
        assertEquals(c.getArguments().get(0), "single quoted arg");
    }

    @Test
    public void backQuotes() throws Exception
    {
        Commands commands = parser.parse("cmd `back quoted arg`");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);

        Command c = commands.getCommand(0);
        assertEquals(c.getCommandName(), "cmd");
        assertEquals(c.getArguments().size(), 1);
        assertEquals(c.getArguments().get(0), "`back quoted arg`");
    }

    @Test
    public void backquotesInDoubleQuotes() throws Exception
    {
        Commands commands = parser.parse("cmd \"a double quote with a `back quoted arg`\"");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 1);

        Command c = commands.getCommand(0);
        assertEquals(c.getCommandName(), "cmd");
        assertEquals(c.getArguments().size(), 1);
        assertEquals(c.getArguments().get(0), "a double quote with a `back quoted arg`");
    }

}
