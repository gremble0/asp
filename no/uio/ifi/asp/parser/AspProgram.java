package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.eofToken;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspProgram extends AspSyntax {
    private ArrayList<AspStmt> statements = new ArrayList<>();

    public AspProgram(int n) {
        super(n);
    }

    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken)
            ap.statements.add(AspStmt.parse(s));

        leaveParser("program");
        return ap;
    }

    @Override
    public void prettyPrint() {
        for (AspStmt statement: statements)
            statement.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
