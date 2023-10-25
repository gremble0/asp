package no.uio.ifi.asp.parser.aspprimarysuffix;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspArguments extends AspPrimarySuffix {
    public ArrayList<AspExpr> args = new ArrayList<>();
    
    public AspArguments(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspArguments}
      * @return  {@code AspArguments} with a list of the {@code AspExpr}s representing
      *          the arguments to a function.
      */
    public static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments arguments = new AspArguments(s.curLineNum());

        skip(s, leftParToken);

        while (s.curToken().kind != rightParToken) {
            arguments.args.add(AspExpr.parse(s));

            ignore(s, commaToken);
        }
        
        skip(s, rightParToken);

        leaveParser("arguments");
        return arguments;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");
        
        int n = 0;
        while (n < args.size()) {
            args.get(n).prettyPrint();

            if (n < args.size() - 1)
                prettyWrite(", ");
            ++n;
        }

        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
