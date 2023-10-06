package no.uio.ifi.asp.parser.aspprimarysuffix;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSubscription extends AspPrimarySuffix {
    public AspSubscription(int n) {
        super(n);
    }

    public static AspSubscription parse(Scanner s) {
        // TODO write method
        enterParser("assignment");

        AspSubscription subscription = null;

        leaveParser("assignment");
        return subscription;
    }

    @Override
    public void prettyPrint() {
        // TODO write method
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
