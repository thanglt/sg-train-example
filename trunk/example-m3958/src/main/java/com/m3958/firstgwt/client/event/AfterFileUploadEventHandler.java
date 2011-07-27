package com.m3958.firstgwt.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AfterFileUploadEventHandler extends EventHandler {
  void onAfterUpload(AfterFileUploadEvent event);
}
