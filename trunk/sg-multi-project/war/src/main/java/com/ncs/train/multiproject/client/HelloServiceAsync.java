package com.ncs.train.multiproject.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface HelloServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.ncs.train.multiproject.client.HelloService
     */
    void sayHello( java.lang.String message, AsyncCallback<java.lang.String> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static HelloServiceAsync instance;

        public static final HelloServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (HelloServiceAsync) GWT.create( HelloService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "Hello" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
