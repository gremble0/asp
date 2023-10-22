package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.noneToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspNoneLiteral extends AspAtom {
    public AspNoneLiteral(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspNoneLiteral}
      * @return  instance of class {@code AspNoneLiteral} that represents the none value
      *          in asp. This is only useful to check the objects class
      */
    public static AspNoneLiteral parse(Scanner s) {
        enterParser("none literal");

        skip(s, noneToken);

        leaveParser("none literal");
        return new AspNoneLiteral(s.curLineNum());
    }

    @Override
    public void prettyPrint() {
        prettyWrite("None");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeNoneValue();
    }
}
