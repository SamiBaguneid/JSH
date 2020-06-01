package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.IO.FileIO;
import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.Parser.Command;

import java.util.ArrayList;
import java.io.OutputStreamWriter;

public class Sed implements Application{
    private boolean rawFromArgs = false;

    /**
     * Prints a file to standard output but for each line containing a match to a specified
     * pattern, replaces the matched substring with the specified string.
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
     * Returns a file but for each line containing a match to a specified
     * pattern, replaces the matched substring with the specified string.
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * ArrayList<String> where the first index is the output
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        ArrayList<String> appArgs = cmd.getArguments();
        if(rawFromArgs) {
            return replace(separateReplacement(appArgs.get(0)), appArgs.get(1)); 
        } else if(FileIO.isExistingFile(appArgs.get(1))) {
            String fileContents = FileIO.FileToString(FileIO.getFile(appArgs.get(1)));
            return replace(separateReplacement(appArgs.get(0)), fileContents);
        } else {
            throw new Exception("file could not be found or opened");
        }

    }

    public void validateCommand(Command cmd) throws Exception{
        if(cmd.getArguments().size() == 1) { //no file has been provided so ask for user input
            rawFromArgs = true;
            cmd.addArgument(StdIO.stdIn());
        }
        if(cmd.getArguments().isEmpty()) {
            throw new RuntimeException("Missing replacement string!");
        } else if(cmd.getArguments().size() > 2) {
            throw new RuntimeException("Too many arguments given!");
        }
        String replacement = cmd.getArguments().get(0);
        validateReplacement(replacement);
        validateSeparators(replacement);
        if(cmd.isPipe()){
            rawFromArgs = true;
        }
    }

    private ArrayList<String> replace(String[] replacementArg, String contents){
        String sed;
        if(replacementArg.length == 3) { // if form is s/a/b then only replace first occurrence on each line
            sed = replaceFirst(replacementArg, contents);
        } else { //if form is s/a/b/g then replace all occurrences of a with b on every line
            sed = replaceAll(replacementArg, contents);
        }
        ArrayList<String> replacedText = new ArrayList<>();
        replacedText.add(sed);
        return replacedText;
    }

    private String replaceFirst(String[] replacementArg, String contents){
        StringBuilder sb = new StringBuilder();
        String[] lines = contents.split(System.getProperty("line.separator"));

        for(int i = 0; i < lines.length - 1; i++) {
            sb.append(lines[i].replaceFirst(replacementArg[1], replacementArg[2])); //in form s/a/b replacementArg[1] is a and replacementArg[2] is b
            sb.append(System.getProperty("line.separator"));
        }
        sb.append(lines[lines.length - 1].replaceFirst(replacementArg[1], replacementArg[2])); //this way we dont add a new line after the last line
        return sb.toString();
    }

    private String replaceAll(String[] replacementArg, String contents){
        StringBuilder sb = new StringBuilder();
        String[] lines = contents.split(System.getProperty("line.separator"));

        for(int i = 0; i < lines.length - 1; i++) {
            sb.append(lines[i].replace(replacementArg[1], replacementArg[2])); // in form s/a/b/g replace all occurences of a with b
            sb.append(System.getProperty("line.separator"));
        }
        sb.append(lines[lines.length  - 1].replace(replacementArg[1], replacementArg[2])); //this way we dont add a new line after the last line
        return sb.toString();
    }

    private void validateReplacement(String replacement) throws Exception{
        if(replacement.charAt(0)!='s'){ //the form is s/a/b, must start with an s
            throw new Exception("Invalid replacement string!");
        }
    }

    private void validateSeparators(String replacement) throws Exception{
        char separatingChar = replacement.charAt(1); //the form is s/a/b however the / can be any char
        int separatorCount = replacement.split("" + separatingChar).length;

        if(separatorCount < 3 || separatorCount > 4) { //replacement can take form s/a/b or s/a/b/g therefore only 3 or 4 seperated elements
            throw new Exception("Separator is formatted incorrectly!");
        } else if(separatorCount == 4 && !replacement.split("" + separatingChar)[3].trim().equals("g")) { //if seperated into 4 segments the last segment has to be a g (meaning global)
            throw new Exception("Unknown character at the end of replacement string");
        }
    }

    private String[] separateReplacement(String replacement){
        char separator = replacement.charAt(1);
        String strSeparator = String.valueOf(separator);
        String[] args = replacement.split(strSeparator);
        return args;
    }
}

