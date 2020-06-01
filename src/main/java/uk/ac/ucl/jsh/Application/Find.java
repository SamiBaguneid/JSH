package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.IO.FileIO;
import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.StdIO;
import java.io.File;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

public class Find implements Application{
    /**
     * Searches recursively for files that match name specified
     * Prints matching files to standard output
     * @param cmd
     * The Command object which holds arguments and options
     * @param writer
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public void run(Command cmd, OutputStreamWriter writer) throws Exception{
        StdIO.stdOut(execute(cmd), writer);
    }

    /**
     * Searches recursively for files that match name specified
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * Returns the results as an ArrayList<String> where each index contains the
     * relative path of matched file
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        File currDir = getSearchDirectory(cmd);
        String search = getSearchString(cmd);
        return matchingFiles(currDir, search);
    }

    private ArrayList<String> matchingFiles(File currDir, String search) throws Exception{
        File[] listOfFiles = currDir.listFiles(); //get all files in the current directory
        ArrayList<String> findFiles = new ArrayList<>();
        if(listOfFiles.length != 0) {
            findFiles.addAll(subDirectories(listOfFiles, search)); //add all matching files from subdirectories
            for (File file : listOfFiles) {
                if (!file.getName().startsWith(".") && file.getName().contains(search)) {
                    //gets relative path of matching file
                    findFiles.add(file.getAbsolutePath().substring(Jsh.currentDirectory.length()));
                }
            }
        }
        return findFiles;
    }

    private ArrayList<String> subDirectories(File[]  listOfFiles, String search) throws Exception{
        ArrayList<String> findFiles = new ArrayList<>();
        if(listOfFiles.length != 0) {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    //uses indirect recursion to find all matching files
                    findFiles.addAll(matchingFiles(file, search));
                }
            }
        }
        return findFiles;
    }

    private File getSearchDirectory(Command cmd) throws Exception{
        File currDir;
        ArrayList<String> args = cmd.getArguments();
        if (args.size() == 2 || args.size() == 1 && cmd.getOptions().size() == 0) {
            currDir = new File(Jsh.currentDirectory + File.separator + args.get(0));
        } else { //otherwise no directory has been given
            currDir = new File(Jsh.currentDirectory);
        }
        return validateDirectory(currDir);
    }

    private File validateDirectory(File dir) throws Exception{
        if(!dir.isDirectory() || !dir.exists()){
            throw new Exception("this directory does not exist");
        }
        return dir;
    }

    public void validateCommand(Command cmd) throws Exception{
        ArrayList<String> args = cmd.getArguments();
        ArrayList<String> options = cmd.getOptions();
        if (args.size() > 2) {
            throw new Exception("too many arguments");
        }if (options.size() > 1) {
            throw new Exception("too many options");
        } else if(options.size() == 0 && args.size() > 0 && !FileIO.isDirectory(args.get(0))) {
            throw new Exception("invalid directory provided");
        } else if(options.size() > 0 && !options.get(0).equals("-name")) {
            throw new RuntimeException("Invalid option given!");
        }
    }

    private String getSearchString(Command cmd){
        if (!cmd.getOptions().contains("-name")) { //only get a search string if the -name option was provided
            return "";
        }
        ArrayList<String> args = cmd.getArguments();
        if (args.size() == 2){
            return args.get(1);
        }
        return args.get(0);
    }
}

