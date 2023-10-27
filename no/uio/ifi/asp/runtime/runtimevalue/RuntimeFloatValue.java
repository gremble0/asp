package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.parser.AspSyntax;

// TODO: maybe write RuntimeNumberValue abstract class
public class RuntimeFloatValue extends RuntimeValue {
    private double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    public String typeName() {
        return "float";
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
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue + v.getIntValue("+ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue + v.getFloatValue("+ operand", where));

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        // TODO: probably better to mutate and make method void
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue / v.getIntValue("/ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));

        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue == v.getIntValue("== operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
        else if (v instanceof RuntimeNoneValue)
            return new RuntimeBoolValue(false);
        else if (v instanceof RuntimeBoolValue)
            return v.evalEqual(this, where);

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue > v.getIntValue("> operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue > v.getFloatValue("> operand", where));
        else if (v instanceof RuntimeBoolValue)
            return v.evalLess(this, where);

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue >= v.getIntValue(">= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operand", where));
        else if (v instanceof RuntimeBoolValue)
            return v.evalLessEqual(this, where);

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue / v.getIntValue("// operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(Math.round(floatValue / v.getFloatValue("// operand", where)));

        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue < v.getIntValue("< operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue < v.getFloatValue("< operand", where));
        else if (v instanceof RuntimeBoolValue)
            return v.evalGreater(this, where);

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue <= v.getIntValue("<= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue <= v.getFloatValue("<= operand", where));
        else if (v instanceof RuntimeBoolValue)
            return v.evalGreaterEqual(this, where);

        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue % v.getIntValue("% operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue % v.getFloatValue("% operand", where));

        runtimeError("Type error for %.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue * v.getIntValue("* operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue * v.getFloatValue("* operand", where));

        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-floatValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (floatValue == 0.0)
            return new RuntimeBoolValue(true);
        
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue != v.getIntValue("!= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue != v.getFloatValue("!= operand", where));
        else if (v instanceof RuntimeNoneValue)
            return new RuntimeBoolValue(true);
        else if (v instanceof RuntimeBoolValue)
            return v.evalNotEqual(this, where);

        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(+floatValue);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue - v.getIntValue("+ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue - v.getFloatValue("+ operand", where));

        runtimeError("Type error for -.", where);
        return null;
    }
}
