package no.uio.ifi.asp.parser.aspstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspsmallstmt.AspSmallStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();
    
    public AspSmallStmtList(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspSmallStmtList}
      * @return  {@code AspSmallStmtList} containing one or more {@code AspSmallStmt}s
      */
    public static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList smallStmtList = new AspSmallStmtList(s.curLineNum());

        while (s.curToken().kind != newLineToken) {
            if (s.curToken().kind == semicolonToken) {
                s.readNextToken();
                continue;
            }

            smallStmtList.smallStmts.add(AspSmallStmt.parse(s));
        }

        skip(s, newLineToken);

        leaveParser("small stmt list");
        return smallStmtList;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < smallStmts.size()) {
            smallStmts.get(n).prettyPrint();
            if (n < smallStmts.size() - 1)
                prettyWrite("; ");
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
