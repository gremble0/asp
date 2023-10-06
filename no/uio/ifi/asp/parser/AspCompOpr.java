package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspCompOpr extends AspSyntax {
    // TODO maybe make separate inheritance tree for AspXOpr
    public static TokenKind[] compOprs = { lessToken, greaterToken, doubleEqualToken, greaterEqualToken, lessEqualToken, notEqualToken };
    public TokenKind compOprKind;
    
    public AspCompOpr(int n) {
        super(n);
    }

    public static boolean isCompOpr(TokenKind tokKind) {
        for (TokenKind compOpr : AspCompOpr.compOprs) {
            if (tokKind == compOpr)
                return true;
        }

        return false;
    }

    public static AspCompOpr parse(Scanner s) {
        enterParser("comp opr");
        AspCompOpr compOpr = new AspCompOpr(s.curLineNum());

        compOpr.compOprKind = s.curToken().kind;
        skip(s, s.curToken().kind);
        
        leaveParser("comp opr");
        return compOpr;
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
