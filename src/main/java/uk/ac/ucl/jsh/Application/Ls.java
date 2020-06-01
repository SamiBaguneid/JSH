package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.IO.FileIO;
import java.io.File;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

public class Ls implements Application{
    /**
     * Prints all folders and files in the current directory to the standard output
     * @param cmd
     * The Command object which holds arguments and options
     * @param writer
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public void run(Command cmd, OutputStreamWriter writer) throws Exception{
        StdIO.stdOut(execute(cmd), writer, true);
    }

    /**
     * Returns all the folders and files in the current directory
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * ArrayList<String> where each index contains a file/folder
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        File currDir = getDirectory(cmd);
        return getAllFiles(currDir);
    }

    public void validateCommand(Command cmd) throws Exception{
        ArrayList<String> args = cmd.getArguments();
        if(args.size() > 1) {
            throw new Exception("too many arguments");
        }
        if(cmd.getArguments().size() > 0 && !FileIO.isExistingFile(cmd.getArguments().get(0))) {
            throw new Exception("directory doesn't exist");
        }

    }

    private File getDirectory(Command cmd){
        File currDir;
        ArrayList<String> args = cmd.getArguments();
        if (args.isEmpty()) { //if no argument is provided remain in current directory
            currDir = new File(Jsh.currentDirectory);
        } else {
            currDir = new File(Jsh.currentDirectory + File.separator + args.get(0));
        }
        return currDir;
    }

    private ArrayList<String> getAllFiles(File currDir){
        File[] listOfFiles = currDir.listFiles();
        ArrayList<String> filesList = new ArrayList<>();
        for (File file : listOfFiles) {
            if (!file.getName().startsWith(".")) {
                filesList.add(file.getName());
            }
        }
        return filesList;
    }
}
