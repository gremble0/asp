package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.HashMap;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeDictValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeStringValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspDictDisplay extends AspAtom {
    public HashMap<AspStringLiteral, AspExpr> dict = new HashMap<>();
    
    public AspDictDisplay(int n) {
        super(n);
    }

    /**
      * Parses tokens from the scanner to populate a dictionarys keys and values
      *
      * @param s {@code Scanner} used and mutated to parse the {@code AspDictDisplay}
      * @return  {@code AspDictDisplay} populated with keys and values
      */
    public static AspDictDisplay parse(Scanner s) {
        enterParser("dict display");
        AspDictDisplay dictDisplay = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);

        while (s.curToken().kind != rightBraceToken) {
            AspStringLiteral key = AspStringLiteral.parse(s);
            skip(s, colonToken);
            dictDisplay.dict.put(key, AspExpr.parse(s));

            test(s, commaToken, rightBraceToken);
            ignore(s, commaToken);
        }

        skip(s, rightBraceToken);
        
        leaveParser("dict display");
        return dictDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");

        int n = 0;
        for (AspStringLiteral key : dict.keySet()) {
            key.prettyPrint();
            prettyWrite(":");
            dict.get(key).prettyPrint();
            if (n != dict.size() - 1)
                prettyWrite(", ");
            ++n;
        }

        prettyWrite("}");
    }

    @Override
    public RuntimeDictValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        HashMap<RuntimeStringValue, RuntimeValue> v = new HashMap<>();

        for (AspStringLiteral key : dict.keySet())
            v.put(key.eval(curScope), dict.get(key).eval(curScope));

        return new RuntimeDictValue(v);
    }
}
