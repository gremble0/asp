package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspAtom extends AspSyntax {
    public AspAtom(int n) {
        super(n);
    }
    
    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspAtom}
      * @return  {@code AspAtom} with the literal value of the scanners current token
      */
    public static AspAtom parse(Scanner s) {
        enterParser("atom");
        AspAtom atom = null;

        switch (s.curToken().kind) {
        case trueToken, falseToken:
            atom = AspBooleanLiteral.parse(s); break;
        case integerToken:
            atom = AspIntegerLiteral.parse(s); break;
        case leftBraceToken:
            atom = AspDictDisplay.parse(s); break;
        case leftBracketToken:
            atom = AspListDisplay.parse(s); break;
        case leftParToken:
            atom = AspInnerExpr.parse(s); break;
        case nameToken:
            atom = AspName.parse(s); break;
        case noneToken:
            atom = AspNoneLiteral.parse(s); break;
        case stringToken:
            atom = AspStringLiteral.parse(s); break;
        default:
            parserError("Expected an expression atom but found a " + s.curToken().kind + "!",
                        s.curLineNum());
        }

        leaveParser("atom");
        return atom;
    }
}
