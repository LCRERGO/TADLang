package br.ufscar.lcrergo;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class TADLLexerErrors {
    private static List<String> lexerErrors = new ArrayList<>();

    public static void treatErrors(TADLLexer lex) {
        Token t = null;
        String errMsg = "";

        // Shouldn't stop because in case of STRING_NOT_CLOSED a general error might be recognized first
        while ((t = lex.nextToken()).getType() != Token.EOF) {
            if (TADLLexer.VOCABULARY.getDisplayName(t.getType()).equals("STRING_NOT_CLOSED")) {
                errMsg = "Line " + (lex.getLine() - 1) + ": string not closed"; 
                lexerErrors.add(errMsg);
            } else if (TADLLexer.VOCABULARY.getDisplayName(t.getType()).equals("ERROR")) {
                errMsg = "Line " + lex.getLine() + ": " + t.getText() + " lexeme not identified";
                lexerErrors.add(errMsg);
            }
        }
        lex.reset();
    }

    public static List<String> getLexerErrors() {
        return lexerErrors;
    }

    public static void printLexerErrors() {
        for (var err : lexerErrors) {
            System.err.println(err);
        }
    }
}
