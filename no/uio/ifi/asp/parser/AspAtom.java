package no.uio.ifi.asp.parser;

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
            // aa = AspBooleanLiteral.parse(s);
            break;
        case integerToken:
            break;
        case leftBraceToken:
            break;
        case leftBracketToken:
            break;
        case leftParToken:
            break;
        case nameToken:
            break;
        case noneToken:
            break;
        case stringToken:
            break;
        default:
            parserError("Expected an expression atom but found a " + s.curToken().kind + "!",
                        s.curLineNum());
        }

        return aa;
    }
}
