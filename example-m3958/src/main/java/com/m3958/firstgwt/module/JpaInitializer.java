package com.m3958.firstgwt.module;

import com.google.inject.Inject;
import com.wideplay.warp.persist.PersistenceService;

public class JpaInitializer {
	
	@Inject
	JpaInitializer(PersistenceService  service){
		service.start();
	}
}
