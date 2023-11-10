package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspatom.AspAtom;
import no.uio.ifi.asp.parser.aspprimarysuffix.AspPrimarySuffix;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspPrimary extends AspSyntax {
    public AspAtom atom;
    public ArrayList<AspPrimarySuffix> suffixes = new ArrayList<>();
    
    public AspPrimary(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspPrimary}
      * @return  {@code AspPrimary} with parsed information about its {@code AspAtom}
      *          and any potential {@code AspPrimarySuffix}es
      */
    public static AspPrimary parse(Scanner s) {
        enterParser("primary");
        AspPrimary primary = new AspPrimary(s.curLineNum());

        primary.atom = AspAtom.parse(s);
        while (s.curToken().kind == leftBracketToken || s.curToken().kind == leftParToken)
            primary.suffixes.add(AspPrimarySuffix.parse(s));
        
        leaveParser("primary");
        return primary;
    }

    @Override
    public void prettyPrint() {
        atom.prettyPrint();

        for (AspPrimarySuffix suffix : suffixes)
            suffix.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = atom.eval(curScope);

        for (AspPrimarySuffix suffix : suffixes)
            v = v.evalSubscription(suffix.eval(curScope), this);
        
        return v;
    }
}
