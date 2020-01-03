package com.yanny.age.stone.manual;

import com.google.gson.JsonElement;

public interface IManual {
    void changePage(String key);
    void addLink(String key, int page);
    JsonElement getConstant(String key);
}
