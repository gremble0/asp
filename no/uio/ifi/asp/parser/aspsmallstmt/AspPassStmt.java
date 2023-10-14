package no.uio.ifi.asp.parser.aspsmallstmt;

import static no.uio.ifi.asp.scanner.TokenKind.passToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspPassStmt extends AspSmallStmt {
    public AspPassStmt(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspPassStmt}
      * @return  instance of class {@code AspPassStmt} that represents the pass
      *          keyword in asp. This is only useful to check the objects class
      */
    public static AspPassStmt parse(Scanner s) {
        enterParser("pass stmt");
        AspPassStmt passStmt = new AspPassStmt(s.curLineNum());

        skip(s, passToken);

        leaveParser("pass stmt");
        return passStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("pass");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
