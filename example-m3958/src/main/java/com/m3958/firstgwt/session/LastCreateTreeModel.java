package com.m3958.firstgwt.session;

import com.google.inject.servlet.SessionScoped;
import com.m3958.firstgwt.server.types.TreeModel;

@SessionScoped
public class LastCreateTreeModel {
	private TreeModel tm;

	public void setTm(TreeModel tm) {
		this.tm = tm;
	}

	public TreeModel getTm() {
		return tm;
	}
}
