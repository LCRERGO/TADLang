package br.ufscar.lcrergo;

import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class App 
{
    private static void run(String fname) throws IOException {
        var pw = new PrintWriter(System.out);

        var cs = CharStreams.fromFileName(fname);
        var lex = new TADLLexer(cs);
        TADLLexerErrors.treatErrors(lex);
        var lexerErrors = TADLLexerErrors.getLexerErrors();

        if (lexerErrors.isEmpty()) {
            var tokens = new CommonTokenStream(lex);
            var parser = new TADLParser(tokens);

            var errorListener = new TADLErrorListener(pw);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            var tree = parser.application();
            var semantic = new TADLSemantic();
            semantic.visitApplication(tree);

            if (!TADLSemanticUtils.getSemanticErrors().isEmpty()) {
                TADLSemanticUtils.printSemanticErrors();
            }

            System.out.println(semantic.getTable());
        } else {
            TADLLexerErrors.printLexerErrors();
        }
    }
    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.printf("Usage: <progname> <input>%n");
            System.exit(1);
        } 
        try {
            run(args[0]);
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}
