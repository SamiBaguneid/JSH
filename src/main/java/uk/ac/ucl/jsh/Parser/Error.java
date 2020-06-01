package uk.ac.ucl.jsh.Parser;

import static uk.ac.ucl.jsh.Jsh.ANSI_RED;
import static uk.ac.ucl.jsh.Jsh.ANSI_YELLOW;

//An error container for any errors called by the parser error listener (not ones called by apps)
public class Error {
    /**
     * This class captures 3 types of parser errors - Unclosed pipe commands, unclosed sequence commands
     * and unclosed redirections. It does this by moving back through the command and tries to find the last
     * unclosed symbol made by the user, one of the following  { ;   |   <   > }
     */

    //Error codes: 1 for pipe error, 2 for sequence error and 3 for a redirection error
    private int err;

    public Error(String cmdLine, int errPos) {
        //The parser can sometimes call errors at the EOF char, which is past user input, so we need to
        //decrement the pointer back into string range
        if (errPos >= cmdLine.length()) {
            errPos--;
        }

        char errPosAsChar = cmdLine.charAt(errPos);

        //Move backwards through the string until we hit the first non-space character...
        while (errPos > 0 && errPosAsChar == ' ') {
            errPosAsChar = cmdLine.charAt(errPos);
            errPos--;
        }

        //Checks what this first non-space char is and assigns the relevant error code
        switch (errPosAsChar) {
            case '|':
                err = 1;
                break;
            case ';':
                err = 2;
                break;
            case '<':
            case '>':
                err = 3;
                break;
            default:
                //If somehow this error isn't one of the above, it gets a default val of 0
                err = 0;
        }
    }

    //Returns an error message corresponding to the specific error code
    public String getErrorMessage()
    {
        String error = ANSI_RED + "Syntax Error!\n" + ANSI_YELLOW;
        switch(err)
        {
            case 0:
                error += "Check your Syntax!";
                break;
            case 1:
                error += "Piped Command Hint: Use syntax command | command ...";
                break;
            case 2:
                error += "Sequenced Command Hint: Use syntax command ; command ...";
                break;
            case 3:
                error += "Redirection Command Hint: Use < filename OR > filename...";
                break;
        }
        return error;
    }
}
