package no.uio.ifi.asp.parser.aspstmt;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspsmallstmt.AspSmallStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();
    
    public AspSmallStmtList(int n) {
        super(n);
    }

    public static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList smallStmtList = new AspSmallStmtList(s.curLineNum());

        while (s.curToken().kind != newLineToken) {
            if (s.curToken().kind == semicolonToken)
                skip(s, semicolonToken);
            smallStmtList.smallStmts.add(AspSmallStmt.parse(s));
        }

        skip(s, newLineToken);

        leaveParser("small stmt list");
        return smallStmtList;
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
