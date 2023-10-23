package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    String typeName() {
        return "boolean";
    }

    @Override
    public String toString() {
        return String.valueOf(floatValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return floatValue != 0;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long)floatValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        // TODO: probably better to mutate and make method void
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(floatValue + v.getIntValue(null, where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeIntValue(floatValue + v.getFloatValue(null, where));
        }

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        // TODO: probably better to mutate and make method void
        return new RuntimeIntValue(-floatValue);
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
        if (floatValue == 0)
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
