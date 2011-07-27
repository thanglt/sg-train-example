package com.m3958.firstgwt.utils;

import java.io.IOException;
import java.util.logging.*;

public class Nose {

	    private static Logger logger = Logger.getLogger("com.wombat.nose");
	    private static FileHandler fh;
	    
	    
	    public static void main(String argv[]) throws SecurityException, IOException {
	        // Send logger output to our FileHandler.
	    	fh = new FileHandler("e:/mylog.txt");
	        logger.addHandler(fh);
	        // Request that every detail gets logged.
	        logger.setLevel(Level.ALL);
	        // Log a simple INFO message.
	        logger.info("doing stuff");
	        try {
	            
	        } catch (Error ex) {
	            logger.log(Level.WARNING, "trouble sneezing", ex);
	        }
	        logger.fine("done");
	    }
}
