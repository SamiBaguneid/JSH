// Generated from C:/Users/adnan/OneDrive/Documents/Uni/Second Year/Software Engineering/reclone/jsh-team-35/src/antlr4\jsh.g4 by ANTLR 4.7.2
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link jshParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface jshVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link jshParser#option}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOption(jshParser.OptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link jshParser#quoted}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuoted(jshParser.QuotedContext ctx);
	/**
	 * Visit a parse tree produced by {@link jshParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(jshParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link jshParser#redirection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRedirection(jshParser.RedirectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link jshParser#fullCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullCall(jshParser.FullCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link jshParser#seq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeq(jshParser.SeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link jshParser#pipe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPipe(jshParser.PipeContext ctx);
	/**
	 * Visit a parse tree produced by {@link jshParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(jshParser.CommandContext ctx);
}