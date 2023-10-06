package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspstmt.AspSmallStmtList;
import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

/*
 * My implementation of this class differs from the class hierarchy
 * suggested by the course by splitting the class {@code AspSuite}
 * into three separate classes. One abstract and two non abstract, one
 * for each type of {@code AspSuite} you might see in the asp language.
 *
 * I believe this architecture makes more sense as it makes it possible
 * to avoid every instance of the class having an unused variable for
 * stmts or smallStmtList.
 *
 * This change also makes the logging different from that of the reference
 * interpreter. However the lines that would cause this are commented out in
 * order to generate the same logfiles as the reference interpreter.
 *
 * If you want the logfiles to represent the architectural changes made
 * look at the comments inside the parse methods of the subclasses of
 * {@code AspSuite}
 */
public abstract class AspSuite extends AspSyntax {
    public AspSuite(int n) {
        super(n);
    }

    public static AspSuite parse(Scanner s) {
        enterParser("suite");
        AspSuite suite = null;

        if (s.curToken().kind == newLineToken)
            suite = AspSuiteStmts.parse(s);
        else
            suite = AspSuiteSmallStmtList.parse(s);

        leaveParser("suite");
        return suite;
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
