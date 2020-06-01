package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;

import java.io.File;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.IOException;

public class Cd implements Application {
    /**
     * Changes the current directory of Jsh
     * @param cmd
     * The Command object which holds arguments and options
     * @param out
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public void run(Command cmd, OutputStreamWriter out) throws Exception{
        execute(cmd);
    }

    /**
     * Changes the current directory of Jsh
     * @param cmd
     * The Command object which holds arguments and options
     * @return null
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        String dir = cmd.getArguments().get(0);
        String newDirectory = getNewDirectory(dir);
        Jsh.currentDirectory = newDirectory;
        return null;
    }

    public void validateCommand(Command cmd) throws Exception{
        ArrayList<String> appArgs = cmd.getArguments();
        if(cmd.getArguments().isEmpty()){
            throw new Exception("no argument given");
        }if(!cmd.getOptions().isEmpty()){
            throw new Exception("invalid option");
        }else if (appArgs.size() > 1) {
            throw new Exception("too many arguments");
        }
    }

    //checks that the argument represents an existing sub directory of the Jsh current directory
    private File validateNewDirectory(String arg) throws Exception{
        File dir = new File(Jsh.currentDirectory, arg);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new Exception(arg + " is not an existing directory");
        }
        return dir;
    }

    //gets the full path of the directory represented by the argument
    private String getNewDirectory(String arg) throws Exception{
        File dir = validateNewDirectory(arg);
        return dir.getCanonicalPath();
    }
}
