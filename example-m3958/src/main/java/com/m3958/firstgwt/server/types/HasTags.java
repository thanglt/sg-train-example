package com.m3958.firstgwt.server.types;

import java.util.List;

import com.m3958.firstgwt.model.Tag;

public interface HasTags {
	
	boolean addTag(Tag tag);
	
	boolean dropTag(Tag tag);
	
	List<Tag> getTags();
	
	void setTags(List<Tag> tags);

}
