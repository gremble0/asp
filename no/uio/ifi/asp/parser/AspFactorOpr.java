package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorOpr extends AspSyntax {
    public static TokenKind[] factorOprs = { astToken, slashToken, percentToken, doubleSlashToken };
    public TokenKind factorOprKind;
    
    public AspFactorOpr(int n) {
        super(n);
    }

    public static boolean isFactorOpr(TokenKind tokKind) {
        for (TokenKind factorOpr : AspFactorOpr.factorOprs) {
            if (tokKind == factorOpr)
                return true;
        }

        return false;
    }

    public static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr factorOpr = new AspFactorOpr(s.curLineNum());

        factorOpr.factorOprKind = s.curToken().kind;
        s.readNextToken();
        
        leaveParser("factor opr");
        return factorOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(factorOprKind.toString());
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
