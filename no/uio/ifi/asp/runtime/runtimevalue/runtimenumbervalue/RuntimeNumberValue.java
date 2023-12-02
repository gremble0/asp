package no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;

abstract public class RuntimeNumberValue extends RuntimeValue {
    protected long pythonModulo(long a, long n) {
        return ((a % n) + n) % n;
    }

    protected double pythonModulo(double a, double n) {
        return ((a % n) + n) % n;
    }

    protected Number getNumberValue(String what, AspSyntax where) {
        if (this instanceof RuntimeIntValue) {
            return getIntValue(what, where);
        } else if (this instanceof RuntimeFloatValue) {
            return getFloatValue(what, where);
        }

        runtimeError("cannot get number for variable of type: " + this.getClass(), where);
        return null;
    }
}
