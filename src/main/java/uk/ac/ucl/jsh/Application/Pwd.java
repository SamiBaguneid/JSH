package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

public class Pwd implements Application{
    /**
     * Prints the working directory to standard output
     * @param cmd
     * The Command object which holds arguments and options
     * @param writer
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public void run(Command cmd, OutputStreamWriter writer) throws Exception {
        StdIO.stdOut(execute(cmd), writer);
    }

    /**
     * Returns the working directory of Jsh
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * ArrayList<String> where the first index is the working directory
     */
    public ArrayList<String> execute(Command cmd){
        validateCommand(cmd);
        ArrayList<String> wd = new ArrayList<>();
        wd.add(Jsh.currentDirectory);
        return wd;
    }

    public void validateCommand(Command cmd){
        if(cmd.getArguments().size() > 0 || cmd.getOptions().size() > 0){
            throw new RuntimeException("This command doesn't take any arguments or options");
        }
    }
}
