package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeNoneValue extends RuntimeValue {
    @Override
    public String typeName() {
        return "None";
    }

    @Override
    public String toString() {
        return "None";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return false;
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(v instanceof RuntimeNoneValue);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(!(v instanceof RuntimeNoneValue));
    }
}
