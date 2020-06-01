package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.IO.FileIO;

import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.lang.StringBuilder;

public class Cat implements Application{
    private boolean rawFromArgs = false;

    /**
     * Concatenates the contents of the given files and prints to standard output
     * If no files are given (i.e. if args are empty) takes inputs from stdin
     * @param cmd
     * The Command object which holds arguments and options
     * @param writer
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public void run(Command cmd, OutputStreamWriter writer) throws Exception {
        StdIO.stdOut(execute(cmd), writer);
        // prints the output of execute which returns the value of Cat
    }

    /**
     * Concatenates the contents of the given files and returns them
     * If no files are given takes inputs from stdin
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * Returns an ArrayList<String> in which the first index contains the
     * result of the concatenation
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        return catAllFiles(cmd.getArguments());
        // concatenates all of the files given as arguments
    }

    private ArrayList<String> catAllFiles(ArrayList<String> arguments) throws Exception{
        ArrayList<String> concatFile = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < arguments.size(); i++) {
            sb.append(catFile(arguments.get(i)));
            // gets the contents of the file referred to by the argument and concatenates it
        }
        concatFile.add(sb.toString());
        return concatFile;
    }

    private StringBuilder catFile(String arg) throws Exception{
        StringBuilder sb = new StringBuilder();
        if(rawFromArgs) {
            try {
                sb.append(FileIO.FileToString(FileIO.getFile(arg)));
            }catch(Exception e){
                sb.append(arg);
            }
              // if the user used stdin concatenate the argument itself
        } else{//otherwise concatenate contents of file specified by arg
            sb.append(FileIO.FileToString(FileIO.getFile(arg)));
        }
        return sb;
    }

    public void validateCommand(Command cmd) throws Exception{
        if(cmd.isPipe()){
            rawFromArgs = true;
        }
        //check if files provided exist as arguments
        if(!rawFromArgs) {
            for (String arg : cmd.getArguments()) {
                if (!FileIO.isExistingFile(arg)) {
                    throw new Exception("File " + arg + " does not exist!");
                }
            }
        }
        //If no args provided, use standard input
        if(cmd.getArguments().isEmpty()) {
            rawFromArgs = true;
            ArrayList<String> getArguments = StdIO.multipleStdIn();
            cmd.addArgument(getArguments);
        }
    }
}
