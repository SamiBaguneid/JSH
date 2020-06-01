package uk.ac.ucl.jsh;

import java.util.Scanner;

public class Jsh {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001b[32m";

    public static String currentDirectory = System.getProperty("user.dir");
    public static Evaluator eval = new Evaluator();

    public void run(){
        Scanner input = new Scanner(System.in);
        try {
            boolean quit = false;
            while (!quit) {
                String prompt = ANSI_PURPLE + currentDirectory + "> " + ANSI_RESET;
                System.out.print(prompt);
                try {
                    String cmdline = input.nextLine();
                    if (!cmdline.equals("exit")) eval.eval(cmdline, System.out);
                    else quit = true;
                } catch (Exception e) {
                    System.out.println(ANSI_RED + "jsh: " + e.getMessage() + ANSI_RESET);
                }
            }
        } finally {
            System.out.println(Jsh.ANSI_YELLOW + "Closing Terminal..." + Jsh.ANSI_RESET);
            input.close();
        }
    }

    public void run(String[] args){
        validateArgs(args);
        try {
            eval.eval(args[1], System.out);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "jsh: " + e.getMessage() + ANSI_RESET);
        }
    }

    void validateArgs(String args[]){
        if (args.length != 2) {
            throw new RuntimeException("wrong number of arguments");
        }
        if (!args[0].equals("-c")) {
            throw new RuntimeException("Unexpected argument " + args[0] + ". Expecting: '-c'");
        }
    }

    public static void main(String[] args) {
        Jsh jsh = new Jsh();
        if (args.length > 0) {
            jsh.run(args);
        } else {
            jsh.run();
        }
    }
}
