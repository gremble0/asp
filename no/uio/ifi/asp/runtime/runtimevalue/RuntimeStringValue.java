package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    String typeName() {
        return "str";
    }

    @Override
    public String showInfo() {
        if (stringValue.indexOf('\'') >= 0)
            return "\"" + stringValue + "\"";
        else
            return "'" + stringValue + "'";
    }

    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return stringValue != "";
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        // Can raise error
        return Long.parseLong(stringValue);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        // Can raise error
        return Double.parseDouble(stringValue);
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        return new RuntimeStringValue(stringValue + v.toString());
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        // TODO: make return type RuntimeBoolValue
        if (v instanceof RuntimeStringValue)
            return new RuntimeBoolValue(v.toString() == stringValue);

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeStringValue)) {
            runtimeError("Type error for >.", where);
            return null; // Required by the compiler
        }
        String vStringValue = v.toString();

        // Simple optimization, if the strings are different lengths they cant be equal
        if (vStringValue.length() != stringValue.length())
            return new RuntimeBoolValue(stringValue.length() > vStringValue.length());

        // In python the string "b" is greater than "a" so we account for this.
        // The string "ba" is also greater than "ab" which indicates to me that pythons
        // evalGreater equivalent for strings is comparing every char in the string like this:
        for (int i = 0; i < stringValue.length(); i++) {
            if (vStringValue.charAt(i) != stringValue.charAt(i))
                return new RuntimeBoolValue(stringValue.charAt(i) > vStringValue.charAt(i));
        }

        // If we reach this it means the strings are equal
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeStringValue)) {
            runtimeError("Type error for >=.", where);
            return null; // Required by the compiler
        }
        String vStringValue = v.toString();

        if (vStringValue.length() != stringValue.length())
            return new RuntimeBoolValue(stringValue.length() > vStringValue.length());

        for (int i = 0; i < stringValue.length(); i++) {
            if (vStringValue.charAt(i) != stringValue.charAt(i))
                return new RuntimeBoolValue(stringValue.charAt(i) > vStringValue.charAt(i));
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeStringValue)) {
            runtimeError("Type error for <.", where);
            return null; // Required by the compiler
        }
        String vStringValue = v.toString();

        if (vStringValue.length() != stringValue.length())
            return new RuntimeBoolValue(stringValue.length() < vStringValue.length());

        for (int i = 0; i < stringValue.length(); i++) {
            if (vStringValue.charAt(i) != stringValue.charAt(i))
                return new RuntimeBoolValue(stringValue.charAt(i) < vStringValue.charAt(i));
        }

        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeStringValue)) {
            runtimeError("Type error for <=.", where);
            return null; // Required by the compiler
        }
        String vStringValue = v.toString();

        if (vStringValue.length() != stringValue.length())
            return new RuntimeBoolValue(stringValue.length() < vStringValue.length());

        for (int i = 0; i < stringValue.length(); i++) {
            if (vStringValue.charAt(i) != stringValue.charAt(i))
                return new RuntimeBoolValue(stringValue.charAt(i) < vStringValue.charAt(i));
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            // Could overflow for really big numbers, but why would you ever
            // need to repeat a string more than Integer.MAX_VALUE times
            return new RuntimeStringValue(stringValue.repeat((int)v.getIntValue("* operand", where)));

        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(stringValue == "");
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue)
            return new RuntimeBoolValue(v.toString() != stringValue);

        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }
}
