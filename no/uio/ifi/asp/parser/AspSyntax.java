package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public abstract class AspSyntax {
    public int lineNum;

    public AspSyntax(int n) {
        lineNum = n;
    }

    public abstract void prettyPrint();

    public abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;

    protected static void parserError(String message, int lineNum) {
        String m = "Asp parser error";
        if (lineNum > 0)
            m += " on line " + lineNum;
        m += ": " + message;
        Main.error(m);
    }

    public static void test(Scanner s, TokenKind tk) {
        if (s.curToken().kind != tk)
            parserError("Expected " + tk + " but found " +
                    s.curToken().kind + "!", s.curLineNum());
    }

    public static void test(Scanner s, TokenKind tk1, TokenKind tk2) {
        if (s.curToken().kind != tk1 && s.curToken().kind != tk2)
            parserError("Expected " + tk1 + " or " + tk2 + " but found " +
                    s.curToken().kind + "!", s.curLineNum());
    }

    /**
      * Checks if the current token in the {@code Scanner} is equal to parameter
      * {@code TokenKind}. If it is, skip it, otherwise raise error.
      *
      * @param s  {@code Scanner} to check for current token
      * @param tk {@code TokenKind} to check for equality with current token
      */
    public static void skip(Scanner s, TokenKind tk) {
        test(s, tk);
        s.readNextToken();
    }

    /**
      * Checks if the current token in the {@code Scanner} is equal to parameter
      * {@code TokenKind}. If it is, skip it, otherwise do nothing.
      *
      * @param s  {@code Scanner} to check for current token
      * @param tk {@code TokenKind} to check for equality with current token
      */
    public static void ignore(Scanner s, TokenKind tk) {
        if (s.curToken().kind == tk)
            s.readNextToken();
    }

    protected static void enterParser(String nonTerm) {
        Main.log.enterParser(nonTerm);
    }

    protected static void leaveParser(String nonTerm) {
        Main.log.leaveParser(nonTerm);
    }

    protected static void prettyDedent() {
        Main.log.prettyDedent();
    }

    protected static void prettyIndent() {
        Main.log.prettyIndent();
    }

    protected static void prettyWrite(String s) {
        Main.log.prettyWrite(s);
    }

    protected static void prettyWriteLn() {
        Main.log.prettyWriteLn();
    }

    protected static void prettyWriteLn(String s) {
        Main.log.prettyWriteLn(s);
    }

    void trace(String what) {
        Main.log.traceEval(what, this);
    }
}
