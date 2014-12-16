package com.cmsz.cloudplatform.utils;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.hp.core.model.ResponseObject;

public class SerializerHelper {
    public static final Logger s_logger = Logger.getLogger(SerializerHelper.class.getName());
    public static String token = "/";

    public static String toSerializedStringOld(Object result) {
        if (result != null) {
            Class<?> clz = result.getClass();
            Gson gson = GsonHelper.getBuilder().create();

            if (result instanceof ResponseObject) {
                return clz.getName() + token + ((ResponseObject) result).getObjectName() + token + gson.toJson(result);
            } else {
                return clz.getName() + token + gson.toJson(result);
            }
        }
        return null;
    }

    public static Object fromSerializedString(String result) {
        try {
            if (result != null && !result.isEmpty()) {

                String[] serializedParts = result.split(token);

                if (serializedParts.length < 2) {
                    return null;
                }
                String clzName = serializedParts[0];
                String nameField = null;
                String content = null;
                if (serializedParts.length == 2) {
                    content = serializedParts[1];
                } else {
                    nameField = serializedParts[1];
                    int index = result.indexOf(token + nameField + token);
                    content = result.substring(index + nameField.length() + 2);
                }

                Class<?> clz;
                try {
                    clz = Class.forName(clzName);
                } catch (ClassNotFoundException e) {
                    return null;
                }

                Gson gson = GsonHelper.getBuilder().create();
                Object obj = gson.fromJson(content, clz);
                if (nameField != null) {
                    ((ResponseObject) obj).setObjectName(nameField);
                }
                return obj;
            }
            return null;
        } catch (RuntimeException e) {
            s_logger.error("Caught runtime exception when doing GSON deserialization on: " + result);
            throw e;
        }
    }
}
