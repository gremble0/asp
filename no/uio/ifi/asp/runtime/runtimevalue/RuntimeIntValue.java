package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }

    @Override
    String typeName() {
        return "boolean";
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return intValue != 0;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double)intValue;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        // TODO: write function for int
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (intValue == 0)
            return new RuntimeBoolValue(false);
        
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        // TODO: write function for int
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }
}
