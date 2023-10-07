package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.integerToken;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspIntegerLiteral extends AspAtom {
    long tokInt;
    
    public AspIntegerLiteral(int n) {
        super(n);
    }

    public static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral integerLiteral = new AspIntegerLiteral(s.curLineNum());

        integerLiteral.tokInt = s.curToken().integerLit;
        skip(s, integerToken);

        leaveParser("integer literal");
        return integerLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(Long.toString(tokInt));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
