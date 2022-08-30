package br.ufscar.lcrergo;

import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.ParseException;

public class App 
{
    private static void run(Config cfg) throws IOException {
        var pw = new PrintWriter(System.out);

        var cs = CharStreams.fromFileName(cfg.getInputFileName());
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
            } else {
                var gen = new Generator(cfg.getOutputFileName(), semantic.getTaskList(), semantic.getSchName());
                gen.generate();
            }

            // System.out.println(semantic.getTable());
        } else {
            TADLLexerErrors.printLexerErrors();
        }
    }
    public static void main(String[] args)
    {
        Config cfg;
        try {
            cfg = CmdLineUtils.buildConfig(args);
            run(cfg);
        } catch (ParseException e) {
            CmdLineUtils.printHelpWrapper();
            System.err.println(e);
            System.exit(1);
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}
