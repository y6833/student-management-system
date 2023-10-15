package com.yangy.util;

import java.util.HashMap;
import java.util.Map;

public class Pagetool {

    public static Map<String, String> parseParams(String input) {
        String[] params = input.split("&");
        Map<String, String> keyValueMap = new HashMap<>();
        for (String param : params) {
            if (!param.isEmpty()) {
                String[] keyValue = param.split("=");
                if(keyValue.length == 2){
                    keyValueMap.put(keyValue[0],keyValue[1]);
                }
            }
        }
        return keyValueMap;
    }

    public static String parseParamString(String input) {
        Map<String, String> stringStringMap = parseParams(input);
        String sqlif = " ";
        for (Map.Entry<String, String> entry : stringStringMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sqlif += key + "="+value + " and ";
        }
        // 去掉最后一个" and "
        if (sqlif.endsWith(" and ")) {
            sqlif = sqlif.substring(0, sqlif.length() - 5);
        }
        return sqlif;
    }
}

