package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;

public class RuntimeStringValue extends RuntimeValue {
    private String stringValue;
    private Map<String, List<Class<? extends RuntimeValue>>> supportedTypes = new HashMap<>() {{
        // Math operations
        put("evalAdd", new ArrayList<>(List.of(
            RuntimeStringValue.class
        )));
        put("evalMultiply", new ArrayList<>(List.of(
            RuntimeIntValue.class
        )));

        // Comparisons
        put("evalEqual", new ArrayList<>(List.of(
            RuntimeStringValue.class, RuntimeNoneValue.class
        )));
        put("evalNotEqual", new ArrayList<>(List.of(
            RuntimeStringValue.class, RuntimeNoneValue.class
        )));
        put("evalGreater", new ArrayList<>(List.of(
            RuntimeStringValue.class
        )));
        put("evalLess", new ArrayList<>(List.of(
            RuntimeStringValue.class
        )));
        put("evalGreaterEqual", new ArrayList<>(List.of(
            RuntimeStringValue.class
        )));
        put("evalLessEqual", new ArrayList<>(List.of(
            RuntimeStringValue.class
        )));
        
        // Suffixes
        put("evalSubscription", new ArrayList<>(List.of(
            RuntimeIntValue.class
        )));
    }};

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    public String typeName() {
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
    public int hashCode() {
        // We override the hashcode of RuntimeStringValues so that we can .get() them
        // in RuntimeDictValue by the RuntimeStringValues stringValue
        return Objects.hash(stringValue);
    }

    @Override
    public boolean equals(Object obj) {
        // The HashMap in the RuntimeDictValue implementation depends on
        // RuntimeStringValue's equals() method, and we need it to compare
        // the values of the strings, not just the memory address
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        RuntimeStringValue other = (RuntimeStringValue)obj;
        return stringValue.equals(other.stringValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return stringValue != "";
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        try {
            return Long.parseLong(stringValue);
        } catch(NumberFormatException e) {
            runtimeError("invalid literal for int(): '" + stringValue + "'", where);
            return 0; // Unreachable, required by the compiler
        }
    }
    
    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        try {
            return Double.parseDouble(stringValue);
        } catch(NumberFormatException e) {
            runtimeError("invalid literal for float(): '" + stringValue + "'", where);
            return 0.0; // Unreachable, required by the compiler
        }
    }

    @Override
    public RuntimeIntValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(stringValue.length());
    }

    @Override
    public RuntimeStringValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalAdd").contains(v.getClass()))
            runtimeError("+", typeName(), v.typeName(), where);

        return new RuntimeStringValue(stringValue + v.getStringValue("+ operand", where));
    }

    @Override
    public RuntimeStringValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalMultiply").contains(v.getClass()))
            runtimeError("*", typeName(), v.typeName(), where);

        // Could overflow for really big numbers, but why would you ever
        // need to repeat a string more than Integer.MAX_VALUE times
        return new RuntimeStringValue(stringValue.repeat((int)v.getIntValue("* operand", where)));
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(stringValue == "");
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalEqual").contains(v.getClass()))
            runtimeError("==", typeName(), v.typeName(), where);

        if (v instanceof RuntimeStringValue)
            return new RuntimeBoolValue(v.getStringValue("== operand", where).equals(stringValue));
        else // v is RuntimeNoneValue
            return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalNotEqual").contains(v.getClass()))
            runtimeError("!=", typeName(), v.typeName(), where);

        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeBoolValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalGreater").contains(v.getClass()))
            runtimeError(">", typeName(), v.typeName(), where);

        String vStringValue = v.getStringValue("> operand", where);

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
    public RuntimeBoolValue evalLess(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalLess").contains(v.getClass()))
            runtimeError("<", typeName(), v.typeName(), where);

        RuntimeBoolValue notGreater = evalGreater(v, where).evalNot(where);
        boolean notGreaterBool = notGreater.getBoolValue("< operand", where);
        
        RuntimeBoolValue notEqual = evalEqual(v, where).evalNot(where);
        boolean notEqualBool = notEqual.getBoolValue("< operand", where);

        return new RuntimeBoolValue(notGreaterBool && notEqualBool);
    }

    @Override
    public RuntimeBoolValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalGreaterEqual").contains(v.getClass()))
            runtimeError(">=", typeName(), v.typeName(), where);

        RuntimeBoolValue notGreater = evalLess(v, where).evalNot(where);
        boolean notGreaterBool = notGreater.getBoolValue("> operand", where);

        return new RuntimeBoolValue(notGreaterBool);
    }

    @Override
    public RuntimeBoolValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalLessEqual").contains(v.getClass()))
            runtimeError("<=", typeName(), v.typeName(), where);

        RuntimeBoolValue notGreater = evalGreater(v, where).evalNot(where);
        boolean notGreaterBool = notGreater.getBoolValue("> operand", where);

        return new RuntimeBoolValue(notGreaterBool);
    }
    
    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalSubscription").contains(v.getClass()))
            runtimeError("subscription", typeName(), v.typeName(), where);

        int index = (int)v.getIntValue("subscription", where);
        return new RuntimeStringValue(stringValue.charAt(index) + "");
    }
}
