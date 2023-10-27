package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

// TODO: superclass RuntimeCollectionValue?
public class RuntimeListValue extends RuntimeValue {
    private ArrayList<RuntimeValue> rtValues = new ArrayList<>();

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
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        RuntimeListValue newRtValues = new RuntimeListValue(rtValues);
        newRtValues.rtValues.addAll(v.getListValue("+ operand", where));

        return newRtValues;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeListValue))
            return new RuntimeBoolValue(false);

        return new RuntimeBoolValue(rtValues.equals(v.getListValue("== operand", where)));
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeListValue)) {
            runtimeError("Type error for >.", where);
            return null; // Required by the compiler
        }

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
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeListValue)) {
            runtimeError("Type error for >=.", where);
            return null; // Required by the compiler
        }

        ArrayList<RuntimeValue> vRtValues = v.getListValue(">= operand", where);
            
        if (rtValues.size() != vRtValues.size())
            return new RuntimeBoolValue(rtValues.size() > vRtValues.size());
        
        for (int i = 0; i < rtValues.size(); i++) {
            if (rtValues.get(i) != rtValues.get(i)) {
                return rtValues.get(i).evalGreaterEqual(vRtValues.get(i), where);
            }
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeListValue)) {
            runtimeError("Type error for <.", where);
            return null; // Required by the compiler
        }

        ArrayList<RuntimeValue> vRtValues = v.getListValue("< operand", where);
            
        if (rtValues.size() != vRtValues.size())
            return new RuntimeBoolValue(rtValues.size() < vRtValues.size());
        
        for (int i = 0; i < rtValues.size(); i++) {
            if (rtValues.get(i) != rtValues.get(i)) {
                return rtValues.get(i).evalLess(vRtValues.get(i), where);
            }
        }

        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeListValue)) {
            runtimeError("Type error for <=.", where);
            return null; // Required by the compiler
        }

        ArrayList<RuntimeValue> vRtValues = v.getListValue("<= operand", where);
            
        if (rtValues.size() != vRtValues.size())
            return new RuntimeBoolValue(rtValues.size() < vRtValues.size());
        
        for (int i = 0; i < rtValues.size(); i++) {
            if (rtValues.get(i) != rtValues.get(i)) {
                return rtValues.get(i).evalLessEqual(vRtValues.get(i), where);
            }
        }

        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            ArrayList<RuntimeValue> newRtValues = new ArrayList<>(); // TODO: (rtValues) in arraylist constructor

            for (int i = 0; i < v.getIntValue("* operand", where); i++)
                newRtValues.addAll(rtValues);

            return new RuntimeListValue(newRtValues);
        }

        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(rtValues.size() == 0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeListValue))
            return new RuntimeBoolValue(true);

        return new RuntimeBoolValue(!rtValues.equals(v.getListValue("!= operand", where)));
    }
}
