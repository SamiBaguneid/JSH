package uk.ac.ucl.jsh.IO;

import uk.ac.ucl.jsh.Jsh;

import java.io.OutputStreamWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class StdIO{
    public static void stdOut(String output, OutputStreamWriter writer) throws IOException{
        writer.write(output);
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }

    public static void stdOut(ArrayList<String> output, OutputStreamWriter writer) throws IOException{
        for(String s: output){
            stdOut(s, writer);
        }
    }

    //thank you Thug for inspiring me with this genius
    //this is the most revolutionary function i have written so far
    public static void stdOut(ArrayList<String> output, OutputStreamWriter writer, boolean space) throws IOException{
        if(output.size()>0){
            for(int i = 0; i < output.size() - 1; i++){
                writer.write(output.get(i));
                writer.write(" ");
                writer.flush();
            }
            writer.write(output.get(output.size() - 1));
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }

    //Inputs StdIn for one file
    public static String stdIn() throws Exception{
        StringBuilder stdIn = new StringBuilder();
        Scanner input = new Scanner(System.in);

        //This is the prompt that is printed when StdIn is started up
        System.out.print(Jsh.ANSI_YELLOW + ">> " + Jsh.ANSI_RESET);

        //The method gets a single line of input first from the user
        String cmdline = input.nextLine();
        stdIn.append(cmdline);

        //If the input was empty, we throw an exception
        if(cmdline.length() == 0) throw new Exception(" Standard Input is empty!");

        char firstChar = cmdline.trim().charAt(0);
        char lastChar = cmdline.charAt(cmdline.length() - 1);

        //If the string is quoted with single or double quotes, it is allowed to span multiple lines
        //of the terminal. So if the string isn't wrapped with quotes, we return its value
        if(firstChar != '\'' && firstChar != '"' || lastChar == firstChar) {
            return stdIn.toString();
        }

        //However, if it isn't the user is inputting multiple lines and can keep going
        System.out.print("   "); //Added to keep the next part of the input in line in the console

        //This assigns lines from the input until the string is terminated with the correct quote
        stdIn.append(inputNextLine(firstChar, input));

        //Returns the string minus the quotes it was wrapped in
        return stdIn.toString().substring(1, stdIn.toString().length() - 1);
    }

    //Called when a user is entering a multi line input (wrapped in quotes) in stdIn
    private static String inputNextLine(char firstChar, Scanner input)
    {
        String cmdline = input.nextLine();
        StringBuilder result = new StringBuilder();

        //While the quote hasn't been terminated...
        while (cmdline.length() == 0 || cmdline.trim().charAt(cmdline.length() - 1) != firstChar) {
            result.append(System.getProperty("line.separator") + cmdline);
            System.out.print("   ");
            cmdline = input.nextLine();
        }
        //Adds an extra line break to accommodate for the last input and return
        result.append(System.getProperty("line.separator") + cmdline);

        return result.toString();
    }

    //Used for multiple file inputs on StdIn
    public static ArrayList<String> multipleStdIn() throws Exception{
        ArrayList<String> results = new ArrayList<>();
        while(true){
            try {
                String input = stdIn();
                results.add(input);
            }
            //Exception is caused when an input is blank (indicating end of input)
            catch (Exception e){
                //If no input was ever added to the arraylist, then we throw an exception
                //that the entire of StdIn was empty
                if(results.size() == 0) throw new Exception("Missing arguments!");
                else break;
            }
        }
        return results;
    }
}