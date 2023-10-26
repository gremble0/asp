package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorOpr extends AspSyntax {
    private static TokenKind[] factorOprs = {
        astToken,
        slashToken,
        percentToken,
        doubleSlashToken,
    };
    public TokenKind kind;
    
    public AspFactorOpr(int n) {
        super(n);
    }

    /**
     * @param tokKind {@code TokenKind} to check if is a factor operator
     * @return        {@code true} if parameter is a factor operator, {@code false} if not
     */
    public static boolean isFactorOpr(TokenKind tokKind) {
        for (TokenKind factorOpr : AspFactorOpr.factorOprs) {
            if (tokKind == factorOpr)
                return true;
        }

        return false;
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspFactorOpr}
      * @return  {@code AspFactorOpr} with information about what type of
      *          factor operator it is
      */
    public static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr factorOpr = new AspFactorOpr(s.curLineNum());

        factorOpr.kind = s.curToken().kind;
        s.readNextToken();
        
        leaveParser("factor opr");
        return factorOpr;
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
