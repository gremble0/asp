package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.nameToken;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspName extends AspAtom {
    public String name;
    
    public AspName(int n) {
        super(n);
    }

    /**
      * Parses and stores a variable or function name
      *
      * @param s {@code Scanner} used and mutated to parse the {@code AspName}
      * @return  {@code AspName} with the literal value of the scanners current token
      */
    public static AspName parse(Scanner s) {
        enterParser("name");
        AspName name = new AspName(s.curLineNum());

        name.name = s.curToken().name;
        skip(s, nameToken);
        
        leaveParser("name");
        return name;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(name);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return curScope.find(name, this);
    }
}
