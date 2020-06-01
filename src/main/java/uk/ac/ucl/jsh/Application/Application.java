package uk.ac.ucl.jsh.Application;

import java.io.OutputStreamWriter;
import uk.ac.ucl.jsh.Parser.Command;
import java.util.ArrayList;

public interface Application {
    /**
     * Runs the application and prints the output using the OutputStreamWriter
     * @param cmd
     * The Command object which holds arguments and options
     * @param out
     * Used to print to standard output
     * @throws Exception
     */
    void run(Command cmd, OutputStreamWriter out) throws Exception;

    /**
     * Checks the options and arguments in order to determine whether the command is valid
     * @param cmd
     * The command to validate
     * @throws Exception
     * Throws an exception if the command is invalid.
     */
    void validateCommand(Command cmd) throws Exception;

    /**
     * Executes the application and returns the output as an ArrayList<String>
     * @param cmd
     * The Command object which holds arguments and options
     * @return ArrayList<String> containing output of executing the application
     */
    ArrayList<String> execute(Command cmd) throws Exception;
}
