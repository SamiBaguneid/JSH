package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Parser.Command;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import uk.ac.ucl.jsh.IO.StdIO;

public class Tail implements Application{


    Head tail = new Head();

    /**
     * Prints the last n lines of a given file to standard input
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
     * Returns the last n lines of a given file
     * n is set to 10 if no value is given
     * @param cmd
     * The Command object which holds arguments and options
     * @return
     * ArrayList<String> where the first index contains all the lines
     * @throws Exception
     */
    public ArrayList<String> execute(Command cmd) throws Exception{
        validateCommand(cmd);
        String content = tail.getContent(cmd); //gets the contents of the file
        int linesToPrint = tail.getNumberOfLinesToPrint(cmd); 
        return getLastNLines(content, linesToPrint);
    }

    private ArrayList<String> getLastNLines(String s, int n) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tailFile = new ArrayList<>();
        String[] lines = s.split(System.getProperty("line.separator")); 
        int i = lines.length - n;
        if (i < 0) { //lines.length - n may be negative if more lines were asked for than the app is given, in this case return all the lines (start from the first line)
            i = 0;
        }
        while(i < lines.length){
            sb.append(lines[i] + System.getProperty("line.separator"));
            i++;
        }
        return tail.sbToALS(sb);
    }

    public void validateCommand(Command cmd) throws Exception{
        tail.validateCommand(cmd);
        if(cmd.isPipe()){
            tail.rawFromArgs = true;
        }
    }
}
