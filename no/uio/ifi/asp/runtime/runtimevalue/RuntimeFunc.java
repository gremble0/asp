package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt.AspFuncDef;
import no.uio.ifi.asp.runtime.RuntimeLibrary;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;

public class RuntimeFunc extends RuntimeValue {
    public AspFuncDef def;
    public RuntimeScope defScope;
    public String name;
    
    public RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String name) {
        this.def = def;
        this.defScope = defScope;
        this.name = name;
    }
    
    /**
     * Used by {@code RuntimeLibrary} anonymous classes
     */
    public RuntimeFunc(String name) {
        this.name = name;
    }
    
    @Override
    public String typeName() {
        return "function";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        // Python behavior
        return true;
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(this == v);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(!evalEqual(v, where).getBoolValue("!= operand", where));
    }
    
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
        // TODO: Make evalFuncCall accept a RuntimeListValue as param?
        // Some extra checks for friendlier error messages
        if (actualParams.size() < def.params.size())
            runtimeError(toString() + " missing " + (actualParams.size() - def.params.size()) + 
                        " positional argument(s)", where);
        else if (actualParams.size() > def.params.size())
            runtimeError(toString() + " takes " + def.params.size() + 
                        " positional arguments but " + actualParams.size() + " were given", where);

        for (int i = 0; i < actualParams.size(); i++)
            defScope.assign(def.params.get(i).name, actualParams.get(i));
        
        try {
            def.body.eval(defScope);
        } catch (RuntimeReturnValue ret) {
            return ret.value;
        }

        return new RuntimeNoneValue();
    }
}
