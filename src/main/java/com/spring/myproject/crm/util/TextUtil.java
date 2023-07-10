package com.spring.myproject.crm.util;

import org.springframework.stereotype.Component;

@Component
public class TextUtil {
    
    public String fullNameUtil(String fullName) {
        fullName = fullName.trim();
        String[] components = fullName.split("[ ]+");
        String result = "";
        for (String component : components) {
            result += component.substring(0, 1).toUpperCase() + component.substring(1).toLowerCase() + " ";
        }
        return result.trim();
    }
}
