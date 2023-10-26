package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspCompOpr extends AspSyntax {
    private static TokenKind[] compOprs = {
        lessToken,
        greaterToken,
        doubleEqualToken,
        greaterEqualToken,
        lessEqualToken,
        notEqualToken,
    };
    public TokenKind kind;
    
    public AspCompOpr(int n) {
        super(n);
    }

    /**
     * @param tokKind {@code TokenKind} to check if is a comparison operator
     * @return        {@code true} if parameter is a comparison operator, {@code false} if not
     */
    public static boolean isCompOpr(TokenKind tokKind) {
        for (TokenKind compOpr : AspCompOpr.compOprs) {
            if (tokKind == compOpr)
                return true;
        }

        return false;
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspCompOpr}
      * @return  {@code AspCompOpr} with information about what type of
      *          comparison operator it is
      */
    public static AspCompOpr parse(Scanner s) {
        enterParser("comp opr");
        AspCompOpr compOpr = new AspCompOpr(s.curLineNum());

        compOpr.kind = s.curToken().kind;
        skip(s, s.curToken().kind);
        
        leaveParser("comp opr");
        return compOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(" " + kind.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
