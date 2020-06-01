package uk.ac.ucl.jsh.Parser;

import uk.ac.ucl.jsh.Evaluator;
import uk.ac.ucl.jsh.Jsh;
import uk.ac.ucl.jsh.IO.FileIO;
import java.io.File;
import java.util.ArrayList;

//A Container for all attributes of a command call
public class Command  {
    private boolean unsafe = false;
    private boolean pipe = false;
    private String commandName;
    private ArrayList<String> arguments;
    private ArrayList<Redirection> redirections;
    private ArrayList<String> options;
    private ArrayList<File> outputFiles;


    public Command(String commandName) {
        this.commandName = commandName;
        arguments = new ArrayList<>();
        redirections = new ArrayList<>();
        options = new ArrayList<>();
        outputFiles = new ArrayList<>();

        //If the command has a '_' prefix we mark it as unsafe and remove the prefix from the name
        if(commandName.length() > 0 && commandName.charAt(0) == '_')
        {
            this.commandName = commandName.substring(1);
            unsafe = true;
        }
        else
        {
            this.commandName = commandName;
        }
    }

    //Resolve kept separate to visitor as a command may be syntactically correct and parse, but have an error with filenames in gob and IO redirection
    public void resolve() throws Exception{
        glob();
        substitute();
        resolveRedirInput();
    }

    public boolean isCommandUnSafe() {
        return unsafe;
    }

    public void addArgument(String s) {
        if(s != null) {
            arguments.add(s);
        }
    }

    public void addArgument(ArrayList<String> ss) {
        for (String s: ss){
            addArgument(s);
        }
    }

    public int numOfRedirections()
    {
        return redirections.size();
    }

    public String getCommandName()
    {
        return commandName;
    }

    public ArrayList<String> getArguments()
    {
        return arguments;
    }

    public void addRedirection(String dir, String dest)
    {
        redirections.add(new Redirection(dir, dest));
    }

    public void pipe(boolean p){
        this.pipe = p;
    }

    public boolean isPipe(){
        return pipe;
    }

    public void addOption(String s)
    {
        options.add(s);
    }

    public ArrayList<String> getOptions()
    {
        return options;
    }

    public int getNumOfOutputFiles()
    {
        return outputFiles.size();
    }

    public ArrayList<File> getOutputFiles()
    {
        return outputFiles;
    }

    private void glob() throws Exception{
        for(int i = 0; i < arguments.size(); i++) {
            String arg = arguments.get(i);
            if(arg.length() > 0 && arg.charAt(arg.length() - 1) == '*'){ //if arg ends in *
                arguments.remove(arg);
                arg = arg.substring(0, arg.length() - 1); //remove the * from the arg
                File dir;
                if(arg.equals("")) { //get the directory represented by the arg
                    dir = new File(Jsh.currentDirectory);
                }else{
                    dir = new File(Jsh.currentDirectory + File.separator + arg);
                }
                if(dir.listFiles() != null) {
                    for (File slime : dir.listFiles()) { //add all files in the directory ass arguments
                        if (slime.isFile()) {
                            arguments.add(arg + slime.getName());
                        }
                    }
                }else throw new Exception("the directory " + dir.getName() + " does not exist");
            }
        }
    }

    private void substitute() throws Exception{
        ArrayList<String> newArgs = new ArrayList<>();
        for(int i = 0; i < arguments.size(); i++){
            String arg = arguments.get(i);
            if(arg.contains("`")){
                arg = sub(arg);
            }
            newArgs.add(arg);
        }
        arguments = newArgs;
    }

    private String sub(String arg) throws Exception{
        int j = arg.indexOf('`'); //j is index of first `
        int run = arg.indexOf('`'); //run is looks for the second
        while(run < arg.length() - 1){
            run++;
            if(j != -1 && arg.charAt(run) == '`') {
                //evaluates the command between j, the first `, and run, the second `
                arg = arg.substring(0, j) + evalSub(arg.substring(j + 1, run)) + arg.substring(++run);
                j = -1;
            }else if (arg.charAt(run) == '`'){
                j = run;
            }
        }
        return arg;
    }

    private String evalSub(String cmd) throws Exception{
        try {
            Evaluator eval = new Evaluator();
            String sub = "";
            ArrayList<String> output = eval.eval(cmd); //evaluates the command
            for(String slime: output){ // adds the result as new arguments
                slime = slime.trim();
                if(sub.length() > 0) {
                    addArgument(slime);
                }else{
                    sub = slime;
                }
            }
            return sub;
        }catch(Exception e){
            throw new Exception("failed to evaluate " + cmd);
        }
    }

    public void resolveRedirInput() throws Exception {

        for(int i = 0; i < numOfRedirections(); i++)
        {
            Redirection r = redirections.get(i);

            //If the redirection is an input (written with the < symbol)...
            if(r.getDirection() == 1)
            {
                //Load file contents as string and add it as an argument to the command
                addArgument(FileIO.FileToString(FileIO.getFile(r.getDestination())));
            }
            //If the redirection is an output (written with the > symbol)...
            else
            {
                File outputFile = new File(Jsh.currentDirectory + File.separator + r.getDestination());
                //If the file doesn't exist, we try to create it
                try
                {
                    outputFile.createNewFile();
                } catch(Exception e) {
                    throw new Exception("could not access or create " + r.getDestination() + "!");
                }

                //All files to write to are stored in the outputFiles list which is used to write to later
                outputFiles.add(outputFile);
            }
        }
    }

    //A redirection container stores information about the file being used (the name)
    //and the direction it is used in (as an input or output)
    class Redirection {
        private String destination;
        private int direction;

        public Redirection(String dir, String dest) {
            destination = dest;
            direction = dir.equals("<") ? 1 : 0; //Stores 1 for input and 0 for output redirection
        }

        //0 for right, 1 for left
        public int getDirection() {
            return direction;
        }

        public String getDestination() {
            return destination;
        }
    }
}
