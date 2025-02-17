package no.uio.ifi.asp.parser.aspsuite;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspstmt.AspSmallStmtList;
import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeNoneValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSuiteStmts extends AspSuite {
    public ArrayList<AspStmt> stmts = new ArrayList<>();
    
    public AspSuiteStmts(int n) {
        super(n);
    }

    /**
      * This method parses {@code AspSuite}s that consist of a list of {@code AspStmt}s
      *
      * @param s {@code Scanner} used for parsing the {@code AspSuiteStmts}
      * @return  {@code AspSuiteStmts} with the body of a compound statement
      */
    public static AspSuiteStmts parse(Scanner s) {
        // To make the logfile represent the architectural changes uncomment the line below
        // enterParser("suite stmts");
        AspSuiteStmts suite = new AspSuiteStmts(s.curLineNum());

        skip(s, newLineToken);
        skip(s, indentToken);
        while (s.curToken().kind != dedentToken && s.curToken().kind != eofToken)
            suite.stmts.add(AspStmt.parse(s));

        if (s.curToken().kind != eofToken)
            skip(s, dedentToken);
        
        // To make the logfile represent the architectural changes uncomment the line below
        // leaveParser("suite stmts");
        return suite;
    }

    @Override
    public void prettyPrint() {
        prettyWriteLn();
        prettyIndent();

        for (AspStmt stmt : stmts) {
            stmt.prettyPrint();
            if (stmt instanceof AspSmallStmtList)
                prettyWriteLn();
        }

        prettyDedent();

        // Reference interpreter adds a blank line here which i'll also add i guess
        // prettyWriteLn("");
    }

    /**
     * Evaluates every statement in {@code this.stmts}
     * Only called for side effects, always returns a new {@code RuntimeNoneValue}
     */
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspStmt stmt : stmts)
            stmt.eval(curScope);
        
        return new RuntimeNoneValue();
    }
}
