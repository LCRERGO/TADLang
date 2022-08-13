package br.ufscar.lcrergo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.transform.ErrorListener;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class App 
{
    private static void run(String fname) throws IOException {
        var pw = new PrintWriter(System.out);
        var cs = CharStreams.fromFileName(fname);
        var lex = new TADLLexer(cs);
        Token t = null;
        // while ((t = lex.nextToken()).getType() != Token.EOF)
        //    System.out.println("<"+ TADLLexer.VOCABULARY.getDisplayName(t.getType()) + 
                // "," + t.getText()+">");
        var tokens = new CommonTokenStream(lex);
        var parser = new TADLParser(tokens);

        var errorListener = new TADLErrorListener(pw);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        var tree = parser.application();
        var semantic = new TADLSemantic();
        semantic.visitApplication(tree);

        System.out.println(semantic.getTable());

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
