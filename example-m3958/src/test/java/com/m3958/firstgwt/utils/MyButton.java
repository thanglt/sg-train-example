package com.m3958.firstgwt.utils;

public class MyButton implements Event
{
    private MangerHandler en; 
    
    public MyButton (){
    // Create the event notifier and pass ourself to it.
    	en = new MangerHandler (this);
    } 
    // Define the actual handler for the event.
    public void interestingEvent ()
    {
    	System.out.println("abc");
    } 
    //...
}