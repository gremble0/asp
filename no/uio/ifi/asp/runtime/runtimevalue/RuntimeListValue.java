package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;

// TODO: make superclass RuntimeCollectionValue
public class RuntimeListValue extends RuntimeValue {
    private ArrayList<RuntimeValue> rtValues = new ArrayList<>();
    // This is mostly unnecessary for this class but i'll add it for extensibility
    private Map<String, List<Class<? extends RuntimeValue>>> supportedTypes = new HashMap<>() {{
        // Math operations
        put("evalAdd", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalMultiply", new ArrayList<>(List.of(
            RuntimeIntValue.class
        )));

        // Comparisons
        // NOTE: these are not implemented for lists in the reference interpreter, but it
        // is in python, so i have implemented it with the slightly weird python behavior.
        put("evalGreater", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalLess", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalGreaterEqual", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalLessEqual", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));

        // Suffixes
        put("evalSubscription", new ArrayList<>(List.of(
            RuntimeIntValue.class
        )));
    }};

    public RuntimeListValue(ArrayList<RuntimeValue> vs) {
        rtValues = vs;
    }

    @Override
    public String typeName() {
        return "list";
    }

    @Override
    public String toString() {
        String res = "[";
        for (int i = 0; i < rtValues.size(); i++) {
            RuntimeValue v = rtValues.get(i);
            if (v instanceof RuntimeStringValue)
                res += v.showInfo();
            else
                res += v.toString();

            if (i != rtValues.size() - 1)
                res += ", ";
        }
        res += "]";
            
        return res;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return rtValues.size() != 0;
    }

    @Override
    public ArrayList<RuntimeValue> getListValue(String what, AspSyntax where) {
        return rtValues;
    }

    @Override
    public RuntimeIntValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(rtValues.size());
    }

    @Override
    public RuntimeListValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalAdd").contains(v.getClass()))
            runtimeError("+", typeName(), v.typeName(), where);

        RuntimeListValue newRtValues = new RuntimeListValue(rtValues);
        newRtValues.rtValues.addAll(v.getListValue("+ operand", where));

        return newRtValues;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalMultiply").contains(v.getClass()))
            runtimeError("*", typeName(), v.typeName(), where);

       ArrayList<RuntimeValue> newRtValues = new ArrayList<>();

       for (int i = 0; i < v.getIntValue("* operand", where); i++)
           newRtValues.addAll(rtValues);

       return new RuntimeListValue(newRtValues);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(rtValues.size() == 0);
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        // We don't raise error if none of the supported types, just return false (like in python)
        if (!(v instanceof RuntimeListValue) || rtValues.size() != v.getListValue("==", where).size())
            return new RuntimeBoolValue(false);
        
        ArrayList<RuntimeValue> vRtValues = v.getListValue("==", where);
        for (int i = 0; i < rtValues.size(); i++) {
            RuntimeValue rtValue = rtValues.get(i);
            RuntimeValue vRtValue = vRtValues.get(i);

            if (!rtValue.evalEqual(vRtValue, where).getBoolValue("==", where))
                return new RuntimeBoolValue(false);
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeBoolValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalGreater").contains(v.getClass()))
            runtimeError(">", typeName(), v.typeName(), where);

        ArrayList<RuntimeValue> vRtValues = v.getListValue("> operand", where);
            
        if (rtValues.size() != vRtValues.size())
            return new RuntimeBoolValue(rtValues.size() > vRtValues.size());
        
        for (int i = 0; i < rtValues.size(); i++) {
            if (rtValues.get(i) != rtValues.get(i)) {
                return rtValues.get(i).evalGreater(vRtValues.get(i), where);
            }
        }

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

        return rtValues.get((int)v.getIntValue("subscription", where));
    }
    
    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        rtValues.set((int)inx.getIntValue("= operand", where), val);
    }
}
