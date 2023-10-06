package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspSuite;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public class AspIfStmt extends AspCompoundStmt {
    public ArrayList<AspExpr> tests = new ArrayList<>();
    public ArrayList<AspSuite> bodies = new ArrayList<>();

    public AspIfStmt(int n) {
        super(n);
    }

    public static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");
        AspIfStmt ifStmt = new AspIfStmt(s.curLineNum());

        parseIfKind(s, ifStmt, ifToken);

        while (s.curToken().kind == elifToken)
            parseIfKind(s, ifStmt, elifToken);

        while (s.curToken().kind == elseToken)
            parseIfKind(s, ifStmt, elseToken);

        leaveParser("if stmt");
        return ifStmt;
    }

    /**
      * Helper function to skip over a specific type of if (if, elif or else)
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

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
