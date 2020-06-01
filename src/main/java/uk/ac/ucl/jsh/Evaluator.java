package uk.ac.ucl.jsh;

import uk.ac.ucl.jsh.Application.Application;
import uk.ac.ucl.jsh.Application.ApplicationFactory;
import uk.ac.ucl.jsh.IO.FileIO;
import uk.ac.ucl.jsh.Parser.Command;
import uk.ac.ucl.jsh.Parser.Commands;
import uk.ac.ucl.jsh.Parser.Parser;

import java.util.ArrayList;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Evaluator {
    ApplicationFactory appFactory = new ApplicationFactory();

    public void eval(String cmdline, OutputStream output) throws Exception {
        if(cmdline.trim().length() == 0) { return; }

        OutputStreamWriter writer = new OutputStreamWriter(output);
        Parser p = new Parser();
        Commands commandList = p.parse(cmdline);

        if(commandList.getType() == Commands.commandType.piped) {
            pipe(commandList, writer);
        } else {
            seq(commandList, writer);
        }
    }

    void seq(Commands commandList, OutputStreamWriter writer) throws Exception{
        for(int i = 0; i < commandList.getNumOfCommands(); i++) {
            Command c = commandList.getCommand(i);
            Application app = getAppInstance(c);

            if(c.getNumOfOutputFiles() > 0) {
                FileIO.redirectToFiles(c, app.execute(c));
            }
            else app.run(c, writer);
        }
    }

    void pipe(Commands commandList, OutputStreamWriter writer) throws Exception{
        pipeIntoLast(commandList);
        Command lastCommand = commandList.getCommand(commandList.getNumOfCommands() - 1);
        lastCommand.pipe(true);
        Application app = getAppInstance(lastCommand);

        if(lastCommand.getNumOfOutputFiles() > 0) {
            FileIO.redirectToFiles(lastCommand, app.execute(lastCommand));
        }
        else app.run(lastCommand, writer);
    }

    public ArrayList<String> eval(String cmdline) throws Exception {
        if(cmdline.trim().length() == 0) { return null; }
        Parser p = new Parser();
        Commands commandList = p.parse(cmdline);

        if(commandList.getType() == Commands.commandType.piped) {
            return pipe(commandList);
        } else {
            return seq(commandList);
        }
    }

    private ArrayList<String> seq(Commands commandList) throws Exception{
        ArrayList<String> outputs = new ArrayList<>();
        for(int i = 0; i < commandList.getNumOfCommands(); i++) {
            Command c = commandList.getCommand(i);
            Application app = getAppInstance(c);

            if(c.getNumOfOutputFiles() > 0) {
                FileIO.redirectToFiles(c, app.execute(c));
                return null;
            }
            else outputs.addAll(app.execute(c));
        }
        return outputs;
    }

    private ArrayList<String> pipe(Commands commandList) throws Exception{
        pipeIntoLast(commandList);
        Command lastCommand = commandList.getCommand(commandList.getNumOfCommands() - 1);
        lastCommand.pipe(true);
        Application app = getAppInstance(lastCommand);

        if(lastCommand.getNumOfOutputFiles() > 0) {
            FileIO.redirectToFiles(lastCommand, app.execute(lastCommand));
            return null;
        } else {
            return app.execute(lastCommand);
        }
    }

    private Application getAppInstance(Command c) throws Exception{
        c.resolve();
        String appName = c.getCommandName();
        Application app = appFactory.getInstance(c.isCommandUnSafe(), appName);
        return app;
    }

    private void pipeIntoLast(Commands commandList) throws Exception{
        for(int i = 0; i < commandList.getNumOfCommands() - 1; i++) {
            Command c = commandList.getCommand(i);
            Application app = getAppInstance(c);
            ArrayList<String> arg = app.execute(c);
            commandList.getCommand(i + 1).addArgument(arg);
        }
    }
}
