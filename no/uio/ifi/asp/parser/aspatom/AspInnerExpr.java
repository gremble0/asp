package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspInnerExpr extends AspAtom {
    public AspExpr expr;
    
    public AspInnerExpr(int n) {
        super(n);
    }

    /**
      * Parses an expression surrounded by parens
      *
      * @param s {@code Scanner} used and mutated to parse the {@code AspInnerExpr}
      * @return  {@code AspInnerExpr} containing the parsed {@code AspExpr} 
      */
    public static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr innerExpr = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);
        innerExpr.expr = AspExpr.parse(s);
        skip(s, rightParToken);
        
        leaveParser("inner expr");
        return innerExpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");
        expr.prettyPrint();
        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
