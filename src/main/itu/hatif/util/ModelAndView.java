package itu.hatif.util;

import java.util.Map;

public class ModelAndView {

    private String view;
    private Map<String, Object> data = new java.util.HashMap<>();
    
    public void addAttribute(String key, Object value) {
        data.put(key, value);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getAttribute() {
        return data;
    }

}
