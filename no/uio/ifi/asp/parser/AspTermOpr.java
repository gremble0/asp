package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspTermOpr extends AspSyntax {
    private static TokenKind[] termOprs = {
        plusToken,
        minusToken,
    };
    public TokenKind kind;
    
    public AspTermOpr(int n) {
        super(n);
    }

    /**
     * @param tokKind {@code TokenKind} to check if is a terminal operator
     * @return        {@code true} if parameter is a terminal operator, {@code false} if not
     */
    public static boolean isTermOpr(TokenKind tokKind) {
        for (TokenKind termOpr : AspTermOpr.termOprs) {
            if (tokKind == termOpr)
                return true;
        }

        return false;
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspTermOpr}
      * @return  {@code AspTermOpr} with information about what type of
      *          terminal operator it is
      */
    public static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr termOpr = new AspTermOpr(s.curLineNum());

        termOpr.kind = s.curToken().kind;
        s.readNextToken();
        
        leaveParser("term opr");
        return termOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(kind.toString());
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
