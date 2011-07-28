package com.train.client;

import com.google.inject.Singleton;
import com.smartgwt.client.widgets.IButton;

@Singleton
public class MyButton extends IButton {

    public MyButton() {

        setTitle("test btn");
    }
}
