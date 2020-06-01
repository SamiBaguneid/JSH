package uk.ac.ucl.jsh.IO;

import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.Parser.Command;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileIO{

    public static String FileToString(File f) throws Exception{
        StringBuilder sb;
        BufferedReader reader = new BufferedReader(new FileReader(f));
        sb = new StringBuilder();
        String line;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) { //goes through file adds each line to sb until reaches a line that doesnt exist
            sb.append(line); 
            sb.append(ls);
        }
        if(sb.length() > 0){sb.deleteCharAt(sb.length() - 1);} //if at least one line has been added the last line is just a line seperator so remove it
        reader.close();
        return sb.toString();
    }

    public static boolean isExistingFile(String path) {
        File file = new File(Jsh.currentDirectory + File.separator + path);
        if(file.exists() && file.canRead()) { 
            return true;
        }
        else return false;
    }

    public static boolean isRegularFile(String fileName){
        Path file = Paths.get(Jsh.currentDirectory + File.separator + fileName);
        return Files.isRegularFile(file);
    }

    public static boolean isDirectory(String fileName){
        Path file = Paths.get(Jsh.currentDirectory + File.separator + fileName);
        return Files.isDirectory(file);
    }

    public static File getFile(String path) throws Exception{
        File file = new File(Jsh.currentDirectory + File.separator + path);
        if(file.exists()) {
            if(file.canRead()) {
                return file;
            }
            throw new Exception("The file at " + path + " could not be read");
        }
        throw new Exception("The file at " + path +
                " could not be found, it is possible that is has been moved or deleted");
    }

    public static void writeToFile(String toWrite, String filePath) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(toWrite, 0, toWrite.length());
            bw.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("An error occurred writing to file " + filePath +". This file may not exist");
        }
    }

    public static void redirectToFiles(Command cmd, String output) throws Exception{
        ArrayList<File> outputFiles = cmd.getOutputFiles();
        String cleanedOutput = output.replaceAll("\u001B\\[[;\\d]*m", ""); //Removes all non writeable ASCII chars such as colour codes
        for(int i = 0; i < cmd.getNumOfOutputFiles(); i++) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFiles.get(i)));
            writer.write(cleanedOutput);
            writer.close();
        }
    }

    public static void redirectToFiles(Command cmd, ArrayList<String> output) throws Exception{
        for(int i = 0; i < output.size(); i++){
            redirectToFiles(cmd, output.get(i) + "\n");
        }
    }


}

