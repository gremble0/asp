package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<AspNotTest>();
    
    public AspAndTest(int n) {
        super(n);
    }

    public static AspAndTest parse(Scanner s) {
        return null;
    }

    @Override
    public void prettyPrint() {
        int n = 0;

        for (AspNotTest ant: notTests) {
            if (n > 0)
                prettyWrite(" and ");
            ant.prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
