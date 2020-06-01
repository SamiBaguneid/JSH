package uk.ac.ucl.jsh.Parser;

import antlr4.jshBaseVisitor;
import antlr4.jshParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

public class parserVisitor extends jshBaseVisitor {
    //!!!! The Root node of the parse tree !!!!
    //Parsing begins here
    @Override
    public Commands visitCommand(jshParser.CommandContext ctx) {
        Commands allCommands; //List that stores the resulting list of all commands

        //The command node can have 3 types of children: full call, seq or pipe
        //Pipe and seq return lists, whereas full call returns a single command obj
        Object commands = visitChildren(ctx);

        //If we receive a list of commands (pipe or seq was called)...
        if(commands instanceof Commands) {
            allCommands = (Commands) commands; //Assigns the list of commands
        }
        //If we receive a single command object (full call was called)...
        else
        {
            //Creates a list with a single command and returns it
            ArrayList<Command> commandList = new ArrayList<>(); commandList.add((Command) commands);
            allCommands = new Commands(commandList, Commands.commandType.single);
        }
        return allCommands;
    }

    //Single call of an app e.g. echo foo or grep "abc" file1.txt
    @Override
    public Command visitFullCall(jshParser.FullCallContext ctx) {
        //Command name is always the first word of the string
        Command command = new Command(ctx.getText().trim().split(" ")[0]);

        //Adds all option arguments to the options list
        for(int i = 0; i < ctx.option().size(); i++)
        {
            command.addOption(ctx.option().get(i).getText());
        }

        //Adds all arguments to the arguments list
        for(int i = 0; i < ctx.argument().size(); i++)
        {
            String argument = ctx.argument().get(i).getText();
            //If the argument has double or single quotes, we need to remove the quote characters before storing it
            if(isDoubleOrSingleQuote(argument)) { argument = argument.substring(1, argument.length() - 1); }
            command.addArgument(argument);
        }

        //Adds all the redirections to the redirections list
        for(int i = 0; i < ctx.redirection().size(); i++)
        {
            //Direction is the < or > sign, which comes first in the redirection non terminal
            String dir = ctx.redirection().get(i).getText().split(" ")[0];

            //The destination is the argument part of the redirection non terminal (see antlr grammar)
            String dest = ctx.redirection().get(i).argument().getText();
            command.addRedirection(dir, dest);
        }

        return command;
    }

    //Returns true if the string is wrapped in double or single quotes
    private boolean isDoubleOrSingleQuote(String s)
    {
        if(s.length() == 1) { return false; }

        if(s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\''
                || s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"')
        {
            return true;
        }
        else return false;
    }

    //If there is a sequence of commands e.g. echo a; echo b; echo c
    @Override
    public Commands visitSeq(jshParser.SeqContext ctx) {
        ArrayList<Command> commandList = new ArrayList<>();

        //Gets the number of commands that are isolated full calls (not any seq children)
        int numberOfCommands = ctx.fullCall().size();

        //This adds all the full calls available to the seq call
        for(int i = 0; i < numberOfCommands; i++)
        {
            commandList.add(visitFullCall(ctx.fullCall(i)));
        }


        Commands curNode = new Commands(commandList, Commands.commandType.seq);

        //If the seq has more than 2 commands, then it can have nested seq nodes which then need to be
        //recursively resolved. The seq node will always be at index 0, which we check for here to see
        //if it exists
        if(ctx.getChild(0) instanceof jshParser.SeqContext)
        {
            //We then merge the seq node below with this node
            return mergeCommands((Commands) visit(ctx.getChild(0)), curNode, Commands.commandType.seq);
        }

        return curNode;

    }

    //If this is a pipe command e.g. cat file1.txt file2.txt | echo
    @Override
    public Commands visitPipe(jshParser.PipeContext ctx) {
        ArrayList<Command> commandList = new ArrayList<>();

        //Gets the number of commands that are isolated full calls (not any seq children)
        int numberOfCommands = ctx.fullCall().size();

        //This adds all the full calls available to the pipe call
        for(int i = 0; i < numberOfCommands; i++)
        {
            commandList.add(visitFullCall(ctx.fullCall(i)));
        }

        Commands curNode = new Commands(commandList, Commands.commandType.piped);

        //If the pipe has more than 2 commands, then it can have nested pipe nodes which then need to be
        //recursively resolved. The pipe node will always be at index 0, which we check for here to see
        //if it exists
        if(ctx.getChild(0) instanceof jshParser.PipeContext)
        {
            //We then merge the pipe node below with this node
            return mergeCommands((Commands) visit(ctx.getChild(0)), curNode, Commands.commandType.piped);
        }

        return curNode;
    }


    //A utility function that merges a list of two commands into one list
    private Commands mergeCommands(Commands a, Commands b, Commands.commandType type)
    {
        ArrayList<Command> totalCommands = new ArrayList<>();

        for(int i = 0; i < a.getNumOfCommands(); i++)
        {
            totalCommands.add(a.getCommand(i));
        }

        for(int i = 0; i < b.getNumOfCommands(); i++)
        {
            totalCommands.add(b.getCommand(i));
        }

        return new Commands(totalCommands, type);
    }
}
