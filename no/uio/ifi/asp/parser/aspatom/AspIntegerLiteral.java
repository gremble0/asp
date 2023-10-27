package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.integerToken;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspIntegerLiteral extends AspAtom {
    long integerLit;
    
    public AspIntegerLiteral(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspIntegerLiteral}
      * @return  {@code AspIntegerLiteral} with the literal value of the scanners current token
      */
    public static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral integerLiteral = new AspIntegerLiteral(s.curLineNum());

        integerLiteral.integerLit = s.curToken().integerLit;
        skip(s, integerToken);

        leaveParser("integer literal");
        return integerLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(Long.toString(integerLit));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeIntValue(integerLit);
    }
}
