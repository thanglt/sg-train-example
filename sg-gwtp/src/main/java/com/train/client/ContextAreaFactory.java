package com.train.client;

import com.smartgwt.client.widgets.Canvas;

public interface ContextAreaFactory {

    Canvas create();

    String getID();

    String getDescription();
}
