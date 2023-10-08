package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.eofToken;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspProgram extends AspSyntax {
    private ArrayList<AspStmt> statements = new ArrayList<>();

    public AspProgram(int n) {
        super(n);
    }

    public static AspProgram parse(Scanner s) {
        enterParser("program");
        AspProgram program = new AspProgram(s.curLineNum());

        while (s.curToken().kind != eofToken)
            program.statements.add(AspStmt.parse(s));

        leaveParser("program");
        return program;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < statements.size()) {
            statements.get(n).prettyPrint();
            if (n != statements.size() - 1)
                prettyWrite("\n");
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
