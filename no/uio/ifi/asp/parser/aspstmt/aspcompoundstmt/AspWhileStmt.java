package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.aspsuite.AspSuite;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspWhileStmt extends AspCompoundStmt {
    public AspExpr test;
    public AspSuite body;
    
    public AspWhileStmt(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspWhileStmt}
      * @return  {@code AspWhileStmt} with parsed information about the loop
      *          including its test condition and body
      */
    public static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");
        AspWhileStmt whileStmt = new AspWhileStmt(s.curLineNum());

        skip(s, whileToken);
        whileStmt.test = AspExpr.parse(s);
        skip(s, colonToken);
        whileStmt.body = AspSuite.parse(s);
        
        leaveParser("while stmt");
        return whileStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("while ");
        test.prettyPrint();
        prettyWrite(": ");
        body.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
