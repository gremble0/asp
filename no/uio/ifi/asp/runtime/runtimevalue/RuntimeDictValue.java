package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;

public class RuntimeDictValue extends RuntimeValue {
    private HashMap<RuntimeStringValue, RuntimeValue> dict = new HashMap<>();
    // This is mostly unnecessary for this class but i'll add it for extensibility
    private Map<String, List<Class<? extends RuntimeValue>>> supportedTypes = new HashMap<>() {{
        // Suffixes
        put("evalSubscription", new ArrayList<>(List.of(
            RuntimeStringValue.class
        )));
    }};


    public RuntimeDictValue(HashMap<RuntimeStringValue, RuntimeValue> dict) {
        this.dict = dict;
    }

    @Override
    public String typeName() {
        return "dict";
    }

    @Override
    public String toString() {
        String res = "{";

        int n = 0;
        for (RuntimeValue key : dict.keySet()) {
            res += "'" + key + "'";
            res += ": ";
            res += dict.get(key);
            if (n != dict.size() - 1)
                res += ",";
            ++n;
        }
        res += "}";

        return res;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return dict.size() != 0;
    }

    @Override
    public HashMap<RuntimeStringValue, RuntimeValue> getDictValue(String what, AspSyntax where) {
        return dict;
    }

    @Override
    public ArrayList<RuntimeStringValue> getDictKeys(String what, AspSyntax where) {
        ArrayList<RuntimeStringValue> keys = new ArrayList<>();
        keys.addAll(dict.keySet());
        return keys;
    }

    @Override
    public ArrayList<RuntimeValue> getDictValues(String what, AspSyntax where) {
        ArrayList<RuntimeValue> values = new ArrayList<>();
        values.addAll(dict.values());
        return values;
    }

    @Override
    public RuntimeIntValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(dict.size());
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(dict.size() == 0);
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        // NOTE: == is not implemented for dicts in the reference interpreter, but it
        // is in python, so i have implemented it with the python behavior
        if (!(v instanceof RuntimeDictValue) || dict.size() != v.getDictValue("==", where).size())
            return new RuntimeBoolValue(false);

        HashMap<RuntimeStringValue, RuntimeValue> vDict = v.getDictValue("==", where);
        for (RuntimeStringValue key : dict.keySet()) {
            RuntimeValue rtValue = dict.get(key);
            RuntimeValue vRtValue = vDict.get(key); // null if the key does not exist

            if (vRtValue == null || !rtValue.evalEqual(vRtValue, where).getBoolValue("==", where))
                return new RuntimeBoolValue(false);
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalSubscription").contains(v.getClass()))
            runtimeError("subscription", typeName(), v.typeName(), where);

        return dict.get(v);
    }
}
