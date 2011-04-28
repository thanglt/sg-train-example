package org.codehaus.mojo.gwt.test.client;

import org.codehaus.mojo.gwt.test.domain.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Hello
    implements EntryPoint
{
    final HelloServiceAsync service = GWT.create(HelloService.class);

    public void onModuleLoad()
    {
        User user = new User();
        final Label l = new Label( "GWT says : " + user.sayHello() );
        RootPanel.get().add( l );

        Button b = new Button( "click me !" );
        RootPanel.get().add( b );
        b.addClickHandler( new ClickHandler()
        {
            public void onClick( ClickEvent event )
            {
                service.sayHello( "hello", new AsyncCallback<String>()
                {

                    public void onFailure( Throwable caught )
                    {
                        l.setText( "RPC failure " + caught.getMessage() );
                        GWT.log( "RPC failure", caught );
                    }

                    public void onSuccess( String result )
                    {
                        l.setText( result );
                    }
                } );
            }
        } );

    }
}
