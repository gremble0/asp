package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspTerm extends AspSyntax {
    public ArrayList<AspFactor> factors = new ArrayList<>();
    public ArrayList<AspTermOpr> termOprs = new ArrayList<>();

    public AspTerm(int n) {
        super(n);
    }
    
    /**
      * @param s {@code Scanner} used for parsing the {@code AspTerm}
      * @return  {@code AspTerm} with parsed information about the terminal
      *          consisting of 1 or more {@code AspFactor}s and any potential
      *          {@code AspTermOpr}s
      */
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
        RuntimeValue v = factors.get(0).eval(curScope);

        for (int i = 1; i < factors.size(); ++i) {
            TokenKind k = termOprs.get(i - 1).kind;
            switch (k) {
            case minusToken:
                v = v.evalSubtract(factors.get(i).eval(curScope), this);
                break;
            case plusToken:
                v = v.evalAdd(factors.get(i).eval(curScope), this);
                break;
            default:
                Main.panic("Illegal term operator: " + k + "!");
            }
        }

        return v;
    }
}
