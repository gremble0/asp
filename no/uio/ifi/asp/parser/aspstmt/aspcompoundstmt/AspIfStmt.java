package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.aspsuite.AspSuite;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspIfStmt extends AspCompoundStmt {
    // TODO: make else field
    public ArrayList<AspExpr> tests = new ArrayList<>();
    public ArrayList<AspSuite> bodies = new ArrayList<>();

    public AspIfStmt(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspIfStmt}
      * @return  {@code AspIfStmt} with parsed information about the if statement
      *          including the tests of all the elif and else branches and their bodies.
      *          if bodies.size() > tests.size(), then the last element of bodies is
      *          the else branch.
      */
    public static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");
        AspIfStmt ifStmt = new AspIfStmt(s.curLineNum());

        parseIfKind(s, ifStmt, ifToken);

        while (s.curToken().kind == elifToken)
            parseIfKind(s, ifStmt, elifToken);

        if (s.curToken().kind == elseToken) {
            skip(s, elseToken);
            skip(s, colonToken);
            ifStmt.bodies.add(AspSuite.parse(s));
        }

        leaveParser("if stmt");
        return ifStmt;
    }

    /**
      * Helper function to skip over a specific type of if (if or elif)
      * when parsing an {@code AspIfStmt}
      *
      * @param s      {@code Scanner} used for parsing the {@code AspIfStmt}
      * @param ifStmt {@code AspIfStmt} modified by the contents of {@code s}
      * @param ifKind the type of if to skip past
      */
    private static void parseIfKind(Scanner s, AspIfStmt ifStmt, TokenKind ifKind) {
        skip(s, ifKind);
        ifStmt.tests.add(AspExpr.parse(s));
        skip(s, colonToken);
        ifStmt.bodies.add(AspSuite.parse(s));
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < tests.size()) {
            if (n == 0) {
                prettyWrite("if ");
                tests.get(n).prettyPrint();
                prettyWrite(": ");
            } else {
                prettyWrite("elif ");
                tests.get(n).prettyPrint();
                prettyWrite(": ");
            }

            bodies.get(n).prettyPrint();
            ++n;
        }

        if (bodies.size() > tests.size()) {
            prettyWrite("else: ");
            bodies.get(bodies.size() - 1).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        int n = 0;

        // Loop until we find a truthy test
        while (n < tests.size() && !tests.get(n).eval(curScope).getBoolValue("if test", this))
            n++;

        // If we stopped looping and there are more bodies left we hit an if True (or else) statement
        if (n < bodies.size()) {
            if (n < tests.size())
                trace("if True alt #" + (n + 1) + ": ...");
            else
                trace("else: ...");
            bodies.get(n).eval(curScope);
        }

        return null;
    }
}
