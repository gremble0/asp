package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeBoolValue;
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
        RuntimeValue previousComp = terms.get(0).eval(curScope);

        if (terms.size() == 1)
            return previousComp;
        
        ArrayList<RuntimeValue> compResults = new ArrayList<>();

        for (int i = 1; i < terms.size(); ++i) {
            RuntimeValue nextComp = terms.get(i).eval(curScope);
            TokenKind k = compOprs.get(i - 1).kind;
            switch (k) {
            case lessToken:
                compResults.add(previousComp.evalLess(nextComp, this));
                previousComp = nextComp;
                break;
            case greaterToken:
                compResults.add(previousComp.evalGreater(nextComp, this));
                break;
            case doubleEqualToken:
                compResults.add(previousComp.evalEqual(nextComp, this));
                break;
            case greaterEqualToken:
                compResults.add(previousComp.evalGreaterEqual(nextComp, this));
                break;
            case lessEqualToken:
                compResults.add(previousComp.evalLessEqual(nextComp, this));
                break;
            case notEqualToken:
                compResults.add(previousComp.evalNotEqual(nextComp, this));
                break;
            default:
                Main.panic("Illegal comparison operator: " + k + "!");
            }
        }

        for (RuntimeValue compRes : compResults) {
            boolean curBool = compRes.getBoolValue("Comparison eval", this);
            if (!curBool)
                return new RuntimeBoolValue(false);
        }

        return new RuntimeBoolValue(true);
    }
}
