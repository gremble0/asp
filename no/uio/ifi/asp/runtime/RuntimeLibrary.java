package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeFunc;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeListValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeNoneValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeStringValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeFloatValue;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, name, where);
                return new RuntimeFloatValue(actualParams.get(0).getFloatValue(name, where));
            }
        });

        assign("input", new RuntimeFunc("input") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, name, where);
                System.out.print(actualParams.get(0));
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });

        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, name, where);
                return new RuntimeIntValue(actualParams.get(0).getIntValue(name, where));
            }
        });

        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, name, where);
                return actualParams.get(0).evalLen(where);
            }
        });
        
        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                for (int i = 0; i < actualParams.size(); ++i) {
                    if (i > 0) System.out.print(" ");
                    System.out.print(actualParams.get(i).toString());
                }
                System.out.println();
                return new RuntimeNoneValue();
            }
        });
        
        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 2, name, where);
                ArrayList<RuntimeValue> ret = new ArrayList<>();
                RuntimeValue lower = actualParams.get(0);
                RuntimeValue higher = actualParams.get(1);
                
                // Type check parameters
                if (!(lower instanceof RuntimeIntValue) && !(higher instanceof RuntimeIntValue))
                    RuntimeValue.runtimeError("range() function requires two positional arguments of type: 'int' and 'int'", where);

                for (long i = lower.getIntValue(name, where); i < higher.getIntValue(name, where); i++) {
                    ret.add(new RuntimeIntValue(i));
                }

                return new RuntimeListValue(ret);
            }
        });
        
        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, name, where);
                return new RuntimeStringValue(actualParams.get(0).toString());
            }
        });
    }

    private void checkNumParams(
        ArrayList<RuntimeValue> actArgs,
        int nCorrect,
        String id,
        AspSyntax where
    ) {
        if (actArgs.size() != nCorrect)
            RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!", where);
    }
}
