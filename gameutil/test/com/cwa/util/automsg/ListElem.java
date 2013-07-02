package com.cwa.util.automsg;

import com.cwa.util.automsg.RequestFields;
import com.cwa.util.automsg.ResponseFields;

@RequestFields ({"boolVal", "intVal"})
@ResponseFields({"boolVal", "intVal"})
public class ListElem {
    
    private boolean boolVal;
    private int intVal;

    public ListElem() {
        
    }

    public ListElem(boolean boolVal, int intVal) {
        this.boolVal = boolVal;
        this.intVal = intVal;
    }

    public boolean isBoolVal() {
        return boolVal;
    }

    public void setBoolVal(boolean boolVal) {
        this.boolVal = boolVal;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }
    
}
