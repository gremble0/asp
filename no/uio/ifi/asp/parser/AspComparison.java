package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspComparison extends AspSyntax {
    public ArrayList<AspTerm> terms = new ArrayList<>();
    public ArrayList<AspCompOpr> compOprs = new ArrayList<>();
    
    public AspComparison(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspComparison}
      * @return  {@code AspComparison} with parsed information about the comparison
      *          including all {@code AspTerm}s and any potential {@code AspCompOpr}s
      */
    public static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison comparison = new AspComparison(s.curLineNum());

        do {
            if (AspCompOpr.isCompOpr(s.curToken().kind))
                comparison.compOprs.add(AspCompOpr.parse(s));

            comparison.terms.add(AspTerm.parse(s));
        } while (AspCompOpr.isCompOpr(s.curToken().kind));

        leaveParser("comparison");
        return comparison;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < terms.size()) {
            if (n > 0)
                compOprs.get(n - 1).prettyPrint();

            terms.get(n).prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO: refactor to foreach with v = null etc. also probabyl move some of this to compOprs eval?
        RuntimeValue v = terms.get(0).eval(curScope);

        for (int i = 1; i < terms.size(); ++i) {
            TokenKind k = compOprs.get(i - 1).compOprKind;
            switch (k) {
            case lessToken:
                v = v.evalLess(terms.get(i).eval(curScope), this);
                break;
            case greaterToken:
                v = v.evalGreater(terms.get(i).eval(curScope), this);
                break;
            case doubleEqualToken:
                v = v.evalEqual(terms.get(i).eval(curScope), this);
                break;
            case greaterEqualToken:
                v = v.evalGreaterEqual(terms.get(i).eval(curScope), this);
                break;
            case lessEqualToken:
                v = v.evalLessEqual(terms.get(i).eval(curScope), this);
                break;
            case notEqualToken:
                v = v.evalNotEqual(terms.get(i).eval(curScope), this);
                break;
            default:
                Main.panic("Illegal term operator: " + k + "!");
            }
        }

        return v;
    }
}
