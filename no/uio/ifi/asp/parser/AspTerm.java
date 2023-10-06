package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspTerm extends AspSyntax {
    public ArrayList<AspFactor> factors = new ArrayList<>();
    public ArrayList<AspTermOpr> termOprs = new ArrayList<>();
    public ArrayList<AspFactorPrefix> prefixes = new ArrayList<>();

    public AspTerm(int n) {
        super(n);
    }
    
    static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm term = new AspTerm(s.curLineNum());

        // TODO fix while true loop (do while?)
        while (true) {
            term.factors.add(AspFactor.parse(s));
            if (AspTermOpr.isTermOpr(s.curToken().kind))
                term.termOprs.add(AspTermOpr.parse(s));
            else
                break;
        }
        
        leaveParser("term");
        return term;
    }

    @Override
    public void prettyPrint() {
        for (AspFactor factor : factors)
            factor.prettyPrint();
        for (AspTermOpr termOpr : termOprs)
            termOpr.prettyPrint();
        for (AspFactorPrefix prefix : prefixes)
            prefix.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
