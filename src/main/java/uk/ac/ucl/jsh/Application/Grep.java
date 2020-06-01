package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.IO.FileIO;

import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements Application {
    private boolean rawFromArgs = false;

    /**
     * Prints to standard output all the lines in the files provided that
     * matched the pattern provided
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
     * Returns the lines in the file that match the pattern provided
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * ArrayList<String> where the first index is the output
     * @throws Exception
     */
    public ArrayList<String> execute(Command cmd) throws Exception{ 
        validateCommand(cmd);
        String pattern = cmd.getArguments().get(0); 
        ArrayList<String> matchedLines = new ArrayList<>();

        if(rawFromArgs) {
            try {
                matchedLines.addAll(getFileLines(cmd, pattern));
            }catch(Exception e){
                matchedLines.addAll(getInputLines(cmd, pattern));
            }
        } else {
            matchedLines.addAll(getFileLines(cmd, pattern));
        }
        if(matchedLines.isEmpty()) {
            matchedLines.add(Jsh.ANSI_YELLOW  + "No matching lines were found" + Jsh.ANSI_RESET);
        }
        return compressALS(matchedLines);
    }

    private ArrayList<String> getFileLines(Command cmd, String pattern) throws Exception{
        ArrayList<String> matchedLines = new ArrayList<>();
        for(int i = 1; i < cmd.getArguments().size(); i++) { //first argument is pattern
            String contents = FileIO.FileToString(FileIO.getFile(cmd.getArguments().get(i))); //gets the contents of the file
            matchedLines.addAll(matcher(pattern, contents));
        }
        return matchedLines;
    }

    private ArrayList<String> getInputLines(Command cmd, String pattern){
        ArrayList<String> matchedLines = new ArrayList<>();
        for(int i = 1; i < cmd.getArguments().size(); i++) { //first argument is the string to match every other argument is a string to check the pattern against
            matchedLines.addAll(matcher(pattern, cmd.getArguments().get(i)));
        }
        return matchedLines;
    }

    private ArrayList<String> matcher(String stringPattern, String text) {
        Pattern pattern = Pattern.compile(stringPattern); //uses the pattern class for efficiency
        ArrayList<String> matchedLines = new ArrayList<>();
        String[] lines = text.split(System.getProperty("line.separator")); //makes a list of strings where each element is a line of text

        for(int i = 0; i < lines.length; i++) {
            Matcher matcher = pattern.matcher(lines[i]);
            if(matcher.find()) { //if the line contains the pattern add it to the arraylist
                matchedLines.add(lines[i]);
            }
        }
        return matchedLines;
    }

    public void validateCommand(Command cmd) throws Exception {
        if(cmd.isPipe()){
            rawFromArgs = true;
        }
        if (cmd.getArguments().size() == 1) { //no file is given therefore it gets the user input
            rawFromArgs = true;
            cmd.addArgument(StdIO.multipleStdIn());
        }

        if(cmd.getArguments().isEmpty()) {
            throw new Exception("No pattern provided!");
        } else if (!rawFromArgs){
            validateFiles(cmd);
        }
    }

    private void validateFiles(Command cmd) throws Exception{
        for (int i = 1; i < cmd.getArguments().size(); i++) {
            String filename = cmd.getArguments().get(i);
            if (!FileIO.isExistingFile(filename)) {
                throw new Exception("File " + filename + " does not exist");
            }
        }
    }

    private ArrayList<String> compressALS(ArrayList<String> list){
        String sum = "";
        for(String s: list){
            if(sum.length() > 0) {
                sum = sum + System.lineSeparator() + s;
            }else{sum = s;}
        }
        ArrayList<String> compressed = new ArrayList<>();
        compressed.add(sum);
        return compressed;
    }
}
