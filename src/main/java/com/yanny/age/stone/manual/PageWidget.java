package com.yanny.age.stone.manual;

import com.google.gson.JsonObject;

public class PageWidget extends VerticalLayoutWidget {

    public PageWidget(ManualWidget manual, JsonObject object, int page) {
        super(object, manual);
        this.width = manual.width;
        this.height = manual.height;

        String key = Utils.get(String.class, manual, object, "key", null, false);
        if (key != null) {
            manual.addLink(key, page);
        }
    }
}
