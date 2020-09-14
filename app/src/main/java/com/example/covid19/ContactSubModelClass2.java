package com.example.covid19;

import java.util.ArrayList;
import java.util.Map;

public class ContactSubModelClass2 {
    Map primary;
    ArrayList<Map> regional;

    public ContactSubModelClass2(Map primary, ArrayList<Map> regional) {
        this.primary = primary;
        this.regional = regional;
    }

    public Map getPrimary() {
        return primary;
    }

    public void setPrimary(Map primary) {
        this.primary = primary;
    }

    public ArrayList<Map> getRegional() {
        return regional;
    }

    public void setRegional(ArrayList<Map> regional) {
        this.regional = regional;
    }
}
