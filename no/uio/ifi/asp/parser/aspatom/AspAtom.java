package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.*;

public abstract class AspAtom extends AspSyntax {
    public AspAtom(int n) {
        super(n);
    }
    
    public static AspAtom parse(Scanner s) {
        enterParser("atom");

        AspAtom aa = null;

        switch (s.curToken().kind) {
        case falseToken:
        case trueToken:
            aa = AspBooleanLiteral.parse(s);
            break;
        case integerToken:
            aa = AspIntegerLiteral.parse(s);
            break;
        case leftBraceToken:
            aa = AspDictDisplay.parse(s);
            break;
        case leftBracketToken:
            aa = AspListDisplay.parse(s);
            break;
        case leftParToken:
            aa = AspInnerExpr.parse(s);
            break;
        case nameToken:
            aa = AspName.parse(s);
            break;
        case noneToken:
            aa = AspNoneLiteral.parse(s);
            break;
        case stringToken:
            aa = AspStringLiteral.parse(s);
            break;
        default:
            parserError("Expected an expression atom but found a " + s.curToken().kind + "!",
                        s.curLineNum());
        }

        leaveParser("atom");

        return aa;
    }
}
