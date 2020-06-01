package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.StdIO;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

public class Echo implements Application{

    /**
     * Prints the arguments given to standard output separated by spaces
     * @param cmd
     * The Command object which holds arguments and options
     * @param writer
     * @throws Exception
     * An exception is thrown if there is an error writing to standard output
     */
    public void run(Command cmd, OutputStreamWriter writer) throws Exception{
        validateCommand(cmd);
        StdIO.stdOut(execute(cmd), writer, true); //the true variable symbolises the need for a space between arguments
    }

    /**
     * Returns an ArrayList<String> containing the arguments separated by spaces
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * An ArrayList<String> where the first index holds the output of execution
     */
    public ArrayList<String> execute(Command cmd){
        ArrayList<String> echoOutput = new ArrayList<>();
        for(String arg: cmd.getArguments()){
            echoOutput.add(arg);
        }
        if(echoOutput.size() == 0){
            echoOutput.add("");
        }
        return echoOutput;
    }

    public void validateCommand(Command cmd){
    }
}
