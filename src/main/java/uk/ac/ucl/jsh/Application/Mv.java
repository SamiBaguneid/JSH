package uk.ac.ucl.jsh.Application;

import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.IO.FileIO;
import java.io.File;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.IOException;

public class Mv implements Application {
    public void run(Command cmd, OutputStreamWriter out) {
        execute(cmd);
    }

    public ArrayList<String> execute(Command cmd) {
        validateCommand(cmd);      
        moveIntoSecond(cmd);
        ArrayList<String> fileResult = new ArrayList<>();
        fileResult.add(cmd.getArguments().get(1)); //return the file destination absolute path
        return fileResult;
    }

    public void validateCommand(Command cmd) {
        ArrayList<String> appArgs = cmd.getArguments();

        if(appArgs.size()!= 2){
            throw new RuntimeException("This command requires 2 arguments!");
        }
        String firstFile = appArgs.get(0);
        String secondFile = appArgs.get(1);

        if(!FileIO.isExistingFile(firstFile)){
            throw new RuntimeException("First file does not exist");
        }
    }

    private String pathResolver(ArrayList<String> args)
    {
        String file = args.get(0);
        String path = args.get(1);

        if(FileIO.isDirectory(path)) //if the second argument given is a directory make the new file a file with the first files name in the directory given
        {
            path += File.separator + file;
        }
        return path;
    }

    private void moveIntoSecond(Command cmd){
        ArrayList<String> appArgs = cmd.getArguments();
        String path = pathResolver(appArgs);

        try{
            File firstFile = new File(Jsh.currentDirectory + File.separator + appArgs.get(0));
            String copyOfFirst = FileIO.FileToString(firstFile); //gets the contents in the first file

            if(!FileIO.isExistingFile(path))
            {
                File secondFile = new File(Jsh.currentDirectory + File.separator + path);
                secondFile.createNewFile(); //if the second file location doesnt already exist then create the file and then we will add to it
            }

            FileIO.writeToFile(copyOfFirst, Jsh.currentDirectory + File.separator + path); //if the second file location did exist overwrite it, otherwise add to the blank file
            firstFile.delete(); 
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }   
    }
}
