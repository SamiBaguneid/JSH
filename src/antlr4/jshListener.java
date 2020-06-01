// Generated from jsh.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link jshParser}.
 */
public interface jshListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link jshParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(jshParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(jshParser.OptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link jshParser#quoted}.
	 * @param ctx the parse tree
	 */
	void enterQuoted(jshParser.QuotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#quoted}.
	 * @param ctx the parse tree
	 */
	void exitQuoted(jshParser.QuotedContext ctx);
	/**
	 * Enter a parse tree produced by {@link jshParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(jshParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(jshParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link jshParser#redirection}.
	 * @param ctx the parse tree
	 */
	void enterRedirection(jshParser.RedirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#redirection}.
	 * @param ctx the parse tree
	 */
	void exitRedirection(jshParser.RedirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link jshParser#fullCall}.
	 * @param ctx the parse tree
	 */
	void enterFullCall(jshParser.FullCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#fullCall}.
	 * @param ctx the parse tree
	 */
	void exitFullCall(jshParser.FullCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link jshParser#seq}.
	 * @param ctx the parse tree
	 */
	void enterSeq(jshParser.SeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#seq}.
	 * @param ctx the parse tree
	 */
	void exitSeq(jshParser.SeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link jshParser#pipe}.
	 * @param ctx the parse tree
	 */
	void enterPipe(jshParser.PipeContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#pipe}.
	 * @param ctx the parse tree
	 */
	void exitPipe(jshParser.PipeContext ctx);
	/**
	 * Enter a parse tree produced by {@link jshParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(jshParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link jshParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(jshParser.CommandContext ctx);
}