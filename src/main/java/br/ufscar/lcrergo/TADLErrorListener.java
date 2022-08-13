package br.ufscar.lcrergo;

import java.io.PrintWriter;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

public class TADLErrorListener implements ANTLRErrorListener {
    PrintWriter pw;

    public TADLErrorListener(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4, BitSet arg5,
            ATNConfigSet arg6) { }

    @Override
    public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4, ATNConfigSet arg5) { }
    
    @Override
    public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2, int arg3, int arg4, ATNConfigSet arg5) { }
    
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
            Object offendingSymbol, int line, int charPosition, String msg,
            RecognitionException e) {
                var t = (Token)offendingSymbol;
                String errorMsg;
                if (t.getText().equals("<EOF>")) {
                    errorMsg = line + ": syntatic error near EOF";
                } else {
                    errorMsg = line + ": syntatic error near " + t.getText();
                }
                pw.println(errorMsg);
                // pw.close();
    }
}
