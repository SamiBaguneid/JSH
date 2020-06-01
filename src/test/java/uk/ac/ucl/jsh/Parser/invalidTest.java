package uk.ac.ucl.jsh.Parser;


import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class invalidTest {
    Parser parser = new Parser();

    @Test(expected = Exception.class)
    public void unclosedSeq() throws Exception
    {
        parser.parse("cmd1 ;");
    }

    @Test(expected = Exception.class)
    public void unclosedPipe() throws Exception
    {
        parser.parse("cmd1 |");
    }

    @Test
    public void emptyCall() throws Exception
    {
        parser.parse("");
    }


    @Test(expected = Exception.class)
    public void unclosedRedirection() throws Exception
    {
        parser.parse("cmd <");
    }

    //When you write a command with multiple parser errors on a pipe command, i.e.
    //there are more than 1 '|' pipe symbols
    @Test(expected = Exception.class)
    public void multiplePipeError() throws Exception
    {
        parser.parse("c|||  ");
    }

    @Test(expected = Exception.class)
    public void openingQuote() throws Exception
    {
        parser.parse("\"");
    }
}
