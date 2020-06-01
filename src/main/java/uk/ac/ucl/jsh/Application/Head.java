package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.StdIO;
import uk.ac.ucl.jsh.IO.FileIO;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.lang.StringBuilder;

public class Head implements Application{
    boolean rawFromArgs = false;

    /**
     * Prints the first n lines of a given file to standard input
     * n is set to 10 if no value is given
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
     * Returns the first n lines of a given file
     * n is set to 10 if no value is given
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * ArrayList<String> where the first index contains all the lines
     * @throws Exception
     * An exception is thrown if the command is invalid and is caught by Jsh
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        int linesToPrint = getNumberOfLinesToPrint(cmd);
        String content = getContent(cmd);
        return getFirstNLines(content, linesToPrint);
    }

    public String getContent(Command cmd) throws Exception{
        String content; 
        if(cmd.getOptions().size() > 0){ //if n is specified the content is the second argument
            content = cmd.getArguments().get(1);
        }else{
            content = cmd.getArguments().get(0);
        }
        if(!rawFromArgs){
            return FileIO.FileToString(FileIO.getFile(content));
        }
        return content;
    }

    public void validateCommand(Command cmd) throws Exception{
        ArrayList<String> appArgs = cmd.getArguments();
        ArrayList<String> options = cmd.getOptions();

        if (appArgs.size() > 1 && options.isEmpty() || appArgs.size() > 2) {
            throw new Exception("too many arguments given");
        }if (options.size() > 1 ||  (options.size() == 1 && !options.get(0).equals("-n"))){
            throw new Exception("invalid options");
        }if(options.size() > 0 && options.get(0).equals("-n") && !isInteger(appArgs.get(0))) {
            throw new RuntimeException("please specify the number of lines to print");
        }

        if (appArgs.isEmpty() || (appArgs.size() == 1 && options.size() == 1)) {
            //if no file is given ask for user input instead
            rawFromArgs = true;
            cmd.addArgument(StdIO.stdIn());
        }
        if(cmd.isPipe()){
            rawFromArgs = true;
        }
    }

    private ArrayList<String> getFirstNLines(String s, int n) {
        StringBuilder sb = new StringBuilder();
        String[] lines = s.split(System.getProperty("line.separator")); //make list of strings where each element of list is a new line of the input
        int i = 0;
        while(i < n  && i < lines.length){ //add lines until n or end of input
            sb.append(lines[i] + System.getProperty("line.separator"));
            i++;
        }
        return sbToALS(sb);
    }

    public ArrayList<String> sbToALS(StringBuilder sb){
        ArrayList<String> headFile = new ArrayList<>();
        if(sb.length() > 0){sb.delete(sb.length() - 1, sb.length());} //delete excess line break
        headFile.add(sb.toString());
        return headFile;
    }

    public int getNumberOfLinesToPrint(Command cmd){
        //10 is the default number of lines to print
        int linesToPrint = 10;
        if (cmd.getOptions().size() > 0) {
            linesToPrint = Integer.parseInt(cmd.getArguments().get(0));
        }
        return linesToPrint;
    }

    private boolean isInteger(String i){
        try {
            Integer.parseInt(i);
            return true;
        } catch (Exception e){
             return false;
        }
    }

}
