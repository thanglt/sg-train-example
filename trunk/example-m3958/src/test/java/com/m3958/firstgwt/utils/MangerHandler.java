package com.m3958.firstgwt.utils;


public class MangerHandler {
    private Event ie;
    private boolean somethingHappened; 
    public MangerHandler (Event event)
    {
    // Save the event object for later use.
    ie = event; 
    // Nothing to report yet.
    somethingHappened = false;
    } 
    //...  
    public void doWork ()
    {
    // Check the predicate, which is set elsewhere.
    if (somethingHappened)
        {
        // Signal the even by invoking the interface's method.
        ie.interestingEvent ();
        }
    //...
    }
    
    
    //event和handler紧密相关。handler绑定在event里面。所以通过event就可以找到handler，那么event到底有谁发出的呢？
    // ...
}
