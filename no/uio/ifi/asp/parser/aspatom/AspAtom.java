package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.*;

abstract class AspAtom extends AspSyntax {
    AspAtom(int n) {
        super(n);
    }
    
    static AspAtom parse(Scanner s) {
        AspAtom aa = null;

        switch (s.curToken().kind) {
        case falseToken:
        case trueToken:
            aa = AspBooleanLiteral.parse(s);
            break;
        case integerToken:
            aa = AspBooleanLiteral.parse(s);
            break;
        case leftBraceToken:
            aa = AspInnerExpr.parse(s);
            break;
        case leftBracketToken:
            aa = AspInnerExpr.parse(s);
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

        return aa;
    }
}
