package com.train.client;

import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.SC;

public class MyDebugKey extends KeyIdentifier {

        //Ctrl+Alt+Shift+S
	public MyDebugKey() {
		setCtrlKey(true);
		setShiftKey(true);
		setAltKey(true);
		setKeyName("s");
	}

	public KeyCallback getCb(){
		KeyCallback kc = new KeyCallback() {
		public void execute(String keyName) {
			SC.showConsole();
		}};
		return kc;
	}
}
