package com.cwa.util.automsg;

import com.cwa.util.automsg.AutoMessage;
import com.cwa.util.automsg.RequestFields;
import com.cwa.util.automsg.ResponseFields;

@RequestFields( {"bool", "intVal->bool"})
@ResponseFields({"bool", "intVal->bool"})
public class DependencyMsg extends AutoMessage{

    private boolean bool;
    private int intVal;
    
    public DependencyMsg() {
        super(102);
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }
    
}
