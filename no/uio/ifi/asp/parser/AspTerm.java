package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspTerm extends AspSyntax {
    public ArrayList<AspFactor> factors = new ArrayList<>();
    public ArrayList<AspTermOpr> termOprs = new ArrayList<>();

    public AspTerm(int n) {
        super(n);
    }
    
    static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm term = new AspTerm(s.curLineNum());

        while (true) {
            term.factors.add(AspFactor.parse(s));

            if (!AspTermOpr.isTermOpr(s.curToken().kind))
                break;

            term.termOprs.add(AspTermOpr.parse(s));
        }
        
        leaveParser("term");
        return term;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < factors.size()) {
            if (n > 0) {
                prettyWrite(" ");
                termOprs.get(n - 1).prettyPrint();
                prettyWrite(" ");
            }

            factors.get(n).prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
