package uk.ac.ucl.jsh.Parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * This class is called anytime the parser detects an error in the string.
 * It can be called more than once if there is more than 1 error in a string.
 */

public class ParserErrorListener extends BaseErrorListener {

    private Parser p;
    private String cmdLine;

    public ParserErrorListener(Parser p, String cmdLine)
    {
        this.p = p;
        this.cmdLine = cmdLine;
    }

    //Called on syntax error
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        //We only want to store errors on strings greater than 0, because entering an empty line
        //is handled by code in another class
        if(cmdLine.length() > 0) {
            //Creates an error object and adds it to the parsers 'call stack'
            Error error = new Error(cmdLine, charPositionInLine);
            p.callError();
            p.addError(error);
        }
    }
}
