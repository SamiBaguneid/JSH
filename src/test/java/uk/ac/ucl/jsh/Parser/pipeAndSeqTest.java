package uk.ac.ucl.jsh.Parser;


import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class pipeAndSeqTest {
    Parser parser = new Parser();

    @Test
    public void seqCall() throws Exception
    {
        Commands commands = parser.parse("cmd1 ; cmd2 ; cmd3");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 3);
        assertEquals(commands.getType(), Commands.commandType.seq);

        assertEquals(commands.getCommand(0).getCommandName(), "cmd1");
        assertEquals(commands.getCommand(1).getCommandName(), "cmd2");
        assertEquals(commands.getCommand(2).getCommandName(), "cmd3");
    }

    @Test
    public void pipeCall() throws Exception
    {
        Commands commands = parser.parse("cmd1 | cmd2 | cmd3");
        //There has to be only 1 command
        assertEquals(commands.getNumOfCommands(), 3);
        assertEquals(commands.getType(), Commands.commandType.piped);

        assertEquals(commands.getCommand(0).getCommandName(), "cmd1");
        assertEquals(commands.getCommand(1).getCommandName(), "cmd2");
        assertEquals(commands.getCommand(2).getCommandName(), "cmd3");
    }

}
