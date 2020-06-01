package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.IO.FileIO;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.lang.StringBuilder;

public class Wc implements Application {
    private boolean rawFromArgs = false;

    /**
     * Prints to standard output the number of bytes, words, and/or lines in given files
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
     * Returns the number of bytes, words, and/or lines in given files
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * ArrayList<String> where the first index is the output
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < cmd.getArguments().size(); i++) {
            if (rawFromArgs) { //if no file was given use stdin
                try {
                    wcFiles(sb, cmd, i);
                } catch (Exception e) {
                    wcStdIn(sb, cmd, i);
                }
            } else {
                wcFiles(sb, cmd, i);
            }
        }
        if(sb.length() > 0)sb.delete(sb.length() - 1, sb.length());
        return sbToArrayList(sb);
    }

    private void wcFiles(StringBuilder sb, Command cmd, int i) throws Exception{
        String fileName = cmd.getArguments().get(i);
        if(FileIO.isExistingFile(fileName)){
            inputTitle(sb, fileName); //adds fancy title to output
            String fileContents = FileIO.FileToString(FileIO.getFile(cmd.getArguments().get(i)));
            sb.append(returnSizes(cmd.getOptions(), fileContents)); //adds the size
        }else{throw new Exception();}
    }

    private void wcStdIn(StringBuilder sb, Command cmd, int i){
        inputTitle(sb, "Input " + (i + 1)); //adds a fancy title to output
        sb.append(returnSizes(cmd.getOptions(), cmd.getArguments().get(i)));//adds the size
    }

    private void inputTitle(StringBuilder sb, String title){
        sb.append(Jsh.ANSI_YELLOW + "<< " + title + " >>" + System.getProperty("line.separator"));
    }

    private String returnSizes(ArrayList<String> options, String contents) {
        StringBuilder sb = new StringBuilder();
        if (options.contains("-l")) {
            getWc(sb, "Lines", getLines(contents));
        }
        if (options.contains("-m")) {
            getWc(sb, "Characters", contents.length());
        }
        if (options.contains("-w")) {
            getWc(sb, "Words", getWordCount(contents));
        }
        if (options.contains("-c")) {
            getWc(sb, "Bytes", contents.getBytes().length);
        }
        return sb.toString();
    }

    private int getLines(String content) {
        if(content.length() == 0) {
            return 0;
        }
        return content.split(System.lineSeparator()).length;
    }

    private int getWordCount(String contents) {
        String[] words = contents.split(" |\n"); //splits content by both spaces and new lines
        int wordCount = words.length;
        for(int i = 0; i < words.length; i++) {
            if(words[i].trim().length() == 0) { //removes unexpected entries such as 3 spaces in a row causing one entry to be a space
                wordCount--;
            }
        }
        return wordCount;
    }

    private void getWc(StringBuilder sb, String countType, int wc){
        sb.append(Jsh.ANSI_GREEN + padTo12(countType + ":") + Jsh.ANSI_RESET);
        sb.append(wc);
        sb.append(System.getProperty("line.separator"));
    }

    private String padTo12(String s){
        while(s.length() < 12){
            s = s + " ";
        }
        return s;
    }

    private void validateAllFiles(Command cmd){
        for (String arg : cmd.getArguments()) {
            if (!FileIO.isExistingFile(arg) || !FileIO.isRegularFile(arg)) { //makes sure each file is in the directory and is of the right type
                throw new RuntimeException(arg + " could not be opened!");
            }
        }
    }

    private void validateAllOptions(Command cmd) throws Exception{
        for (String option : cmd.getOptions()) {
            isOption(option); 
        }
    }

    private boolean isOption(String option) throws Exception{
        if (option.equals("-w") || option.equals("-m") || option.equals("-l") || option.equals("-c")) {
            return true;
        }
        throw new Exception("invalid option argument " + option);
    }

    private void addAllOptions(Command cmd) {
        String[] options = new String[]{"-w", "-m", "-l", "-c"};

        for(String option : options) {
            cmd.addOption(option); // if no option was given then all options are needed 
        }
    }

    public void validateCommand(Command cmd) throws Exception{
        if(cmd.isPipe()){
            rawFromArgs = true;
        }
        if (cmd.getArguments().isEmpty()) { //if no args are given ask user for input
            getStdIn(cmd);
        }
        if(!rawFromArgs){
            validateAllFiles(cmd);
        }
        validateAllOptions(cmd);
        if(cmd.getOptions().isEmpty()) { // if no option is given add all options
            addAllOptions(cmd);
        }
    }

    private void getStdIn(Command cmd) throws Exception{
        rawFromArgs = true;
        ArrayList<String> standardInputArgs = StdIO.multipleStdIn();
        cmd.addArgument(standardInputArgs);
    }

    private ArrayList<String> sbToArrayList(StringBuilder sb){
        ArrayList<String> wc = new ArrayList<>();
        wc.add(sb.toString());
        return wc;
    }
}
