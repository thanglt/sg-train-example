package com.m3958.firstgwt.session;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.inject.servlet.RequestScoped;
import com.m3958.firstgwt.server.types.Error;

@RequestScoped
public class ErrorMessages {
	
	private List<Error> errors = new ArrayList<Error>();
	
	public boolean isEmpty(){
		return errors.size() == 0;
	}
	
	
	public void addError(Error error){
		errors.add(error);
	}
	
	public void removeError(Error error){
		errors.remove(error);
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public JSONArray getJsArray() {
		JSONArray ja = new JSONArray();
		for(Error e:errors){
			ja.add(JSONObject.fromObject(e));
		}
		return ja;
	}
	
	
	
}
