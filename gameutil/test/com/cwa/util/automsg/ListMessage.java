package com.cwa.util.automsg;

import com.cwa.util.automsg.AutoMessage;
import com.cwa.util.automsg.RequestFields;
import com.cwa.util.automsg.ResponseFields;
import java.util.List;

@RequestFields ({"bool", "list"})
@ResponseFields({"bool", "list"})
public class ListMessage extends AutoMessage {

    private boolean bool;
    private List<ListElem> list;
    
    public ListMessage() {
        super(103);
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public List<ListElem> getList() {
        return list;
    }

    public void setList(List<ListElem> list) {
        this.list = list;
    }
    
}
