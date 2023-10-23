package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }

    @Override
    String typeName() {
        return "integer";
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
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        // TODO: probably better to mutate and make method void
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue + v.getIntValue("+ operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue + v.getFloatValue("+ operand", where));
        }

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        // TODO: probably better to mutate and make method void
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue / v.getIntValue("/ operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue / v.getFloatValue("/ operand", where));
        }

        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue == v.getIntValue("== operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue == v.getFloatValue("== operand", where));
        }

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue > v.getIntValue("> operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue > v.getFloatValue("> operand", where));
        }

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue >= v.getIntValue(">= operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue >= v.getFloatValue(">= operand", where));
        }

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue / v.getIntValue("// operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeIntValue(Math.round(intValue / v.getFloatValue("// operand", where)));
        }

        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue < v.getIntValue("< operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue < v.getFloatValue("< operand", where));
        }

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue <= v.getIntValue("<= operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue <= v.getFloatValue("<= operand", where));
        }

        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue % v.getIntValue("% operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue % v.getFloatValue("% operand", where));
        }

        runtimeError("Type error for %.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue * v.getIntValue("* operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue * v.getFloatValue("* operand", where));
        }

        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntValue(-intValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (intValue == 0)
            return new RuntimeBoolValue(false);
        
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue != v.getIntValue("!= operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue != v.getFloatValue("!= operand", where));
        }

        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntValue(+intValue);
    }
}
