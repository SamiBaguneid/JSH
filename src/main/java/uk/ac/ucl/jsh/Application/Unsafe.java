package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.Parser.Command;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Unsafe implements Application{
    Application app;
    OutputStreamWriter writer;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    Unsafe(Application app){
        this.app = app;
    }

    /**
     * @param cmd
     * The Command object which holds arguments and options
     * @param writer
     * @throws Exception
     * An exception is printed if the command is invalid and the app terminates
     * An IOException is thrown is there is an error writing to standard output
     */
    public void run(Command cmd, OutputStreamWriter writer) throws IOException{
        try{
            validateCommand(cmd);
            app.run(cmd, writer);
        }catch(Exception e){
            StdIO.stdOut(e.getMessage(), writer);
        }
    }

    /**
     * Executes the application and returns the output as an ArrayList<String>
     * Any exception is printed and the app terminates
     * @param cmd
     * The Command object which holds arguments and options
     * @return ArrayList<String> containing output of executing the application
     */
    public ArrayList<String> execute(Command cmd) {
        try{
            validateCommand(cmd);
            return app.execute(cmd);
        }catch(Exception e){
            System.out.println(ANSI_RED +  e.getMessage() + ANSI_RESET);
            return new ArrayList<>();
        }
    }

    public void validateCommand(Command cmd){
    }
}
