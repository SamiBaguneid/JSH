package uk.ac.ucl.jsh.Parser;

import antlr4.jshLexer;
import antlr4.jshParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;


public class Parser {

    private boolean error = false;
    private ArrayList<Error> errors = new ArrayList<>();

    public Commands parse(String rawCommand) throws Exception
    {
        Commands commands;

        CharStream stream = CharStreams.fromString(rawCommand);

        //Create lexer and removes its error listener so that no garbage errors are printed to terminal
        jshLexer lexer = new jshLexer(stream);
        lexer.removeErrorListeners();
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        //Creates parser and adds our own custom error listener (see parserErrorListener)
        jshParser parser = new jshParser(commonTokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new ParserErrorListener(this, rawCommand));

        //Constructs and visits the parse tree
        parserVisitor visitor = new parserVisitor();
        ParseTree tree = parser.command();
        commands = (Commands) visitor.visit(tree);

        //The parserErrorListener can be called at anytime in this section of the code, so the error
        //boolean can change from false to true here if one is called
        if(error)
        {
            //We throw the exception of the first error on the call stack
            //(there can be more than one error, as a design choice we chose to just throw the first one available)
            throw new Exception(errors.get(0).getErrorMessage());
        }

        return commands;
    }

    public void addError(Error err){
        errors.add(err);
    }

    public void callError(){
        error = true;
    }
}
