package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspTermOpr extends AspSyntax {
    public static TokenKind[] termOprs = { plusToken, minusToken };
    public TokenKind termOprKind;
    
    public AspTermOpr(int n) {
        super(n);
    }

    public static boolean isTermOpr(TokenKind tokKind) {
        for (TokenKind termOpr : AspTermOpr.termOprs) {
            if (tokKind == termOpr)
                return true;
        }

        return false;
    }

    public static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr termOpr = new AspTermOpr(s.curLineNum());

        termOpr.termOprKind = s.curToken().kind;
        s.readNextToken();
        
        leaveParser("term opr");
        return termOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(termOprKind.toString());
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
