package com.google.gwt.sample.contacts.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ContactsServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.google.gwt.sample.contacts.client.ContactsService
     */
    void addContact( com.google.gwt.sample.contacts.shared.Contact contact, AsyncCallback<com.google.gwt.sample.contacts.shared.Contact> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.google.gwt.sample.contacts.client.ContactsService
     */
    void deleteContact( java.lang.String id, AsyncCallback<java.lang.Boolean> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.google.gwt.sample.contacts.client.ContactsService
     */
    void deleteContacts( java.util.ArrayList<java.lang.String> ids, AsyncCallback<java.util.ArrayList<com.google.gwt.sample.contacts.shared.ContactDetails>> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.google.gwt.sample.contacts.client.ContactsService
     */
    void getContactDetails( AsyncCallback<java.util.ArrayList<com.google.gwt.sample.contacts.shared.ContactDetails>> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.google.gwt.sample.contacts.client.ContactsService
     */
    void getContact( java.lang.String id, AsyncCallback<com.google.gwt.sample.contacts.shared.Contact> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.google.gwt.sample.contacts.client.ContactsService
     */
    void updateContact( com.google.gwt.sample.contacts.shared.Contact contact, AsyncCallback<com.google.gwt.sample.contacts.shared.Contact> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static ContactsServiceAsync instance;

        public static final ContactsServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (ContactsServiceAsync) GWT.create( ContactsService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "contactsService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
