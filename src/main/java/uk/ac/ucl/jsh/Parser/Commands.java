package uk.ac.ucl.jsh.Parser;

import java.util.ArrayList;

//Stores a list of all command calls written to the console
public class Commands {
    private ArrayList<Command> commandList;
    private commandType type;

    public enum commandType
    {
        single,
        piped,
        seq;
    }

    public Commands(ArrayList<Command> commandList, commandType type)
    {
        this.commandList = commandList;
        this.type = type;
    }

    public int getNumOfCommands()
    {
        return commandList.size();
    }

    public Command getCommand(int i)
    {
        return commandList.get(i);
    }

    public commandType getType()
    {
        return type;
    }
}
