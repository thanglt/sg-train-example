package com.smartgwt.sample.showcase.client.dataintegration.java.transactions;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.QueueSentCallback;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class GridMassUpdateSample extends ShowcasePanel {

    private static final String DESCRIPTION = "<p>Click on any cell to start editing, then <b>Tab</b> or <b>Down Arrow</b> past the last " +
            "row in the grid to create a new row.</p>" +
            "<p>Alternatively, click the Edit New button to create a new data-entry row at the end of the grid. Unlike the other editing examples, " +
            "none of your changes are being automatically saved to the server. Note how SmartGWT highlights changed values, and new rows.</p>" +
            "<p>Click the \"Save\" button to save all your changes at once, or click the \"Discard\" button to discard all your changes " +
            "(including any new rows) and revert to the data as it was before you started editing.</p>" +
            "<p>When you click the \"Save\" button all your changes - changed rows and new ones - are sent to the server in a queue, as a single HTTP request.</p>" +
            "<p>Because all your changes arrive on the server at once, committing them as a single transaction becomes possible. And because SmartGWT's queuing support " +
            "is completely unobtrusive and requires no extra code on either client or server, as soon as you have an operation that can update a single record, you automatically " +
            "have an operation that can participate in SmartGWT queued updates and potentially transactional commits.</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public Canvas create() {
            GridMassUpdateSample panel = new GridMassUpdateSample();
            id = panel.getID();
            return panel;
        }

        public String getID() {
            return id;
        }

        public String getDescription() {
            return DESCRIPTION;
        }
    }

    public Canvas getViewPanel() {

        Layout layout = new VLayout(15);
        layout.setAutoHeight();

        DataSource countryDS = DataSource.get("countryDS");

        final ListGrid countryGrid = new ListGrid();
        countryGrid.setWidth(500);
        countryGrid.setHeight(224);
        countryGrid.setAlternateRecordStyles(true);
        countryGrid.setCellHeight(22);
        countryGrid.setDataSource(countryDS);

        ListGridField nameField = new ListGridField("countryName", "Country");
        ListGridField continentField = new ListGridField("continent", "Continent");
        ListGridField memberG8Field = new ListGridField("member_g8", "Member G8");
        ListGridField populationField = new ListGridField("population", "Population");
        populationField.setType(ListGridFieldType.INTEGER);
        populationField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(value == null) {
                    return null;
                }
                NumberFormat nf = NumberFormat.getFormat("0,000");
                try {
                    return nf.format(((Number) value).longValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        ListGridField independenceField = new ListGridField("independence", "Independence");
        countryGrid.setFields(nameField,continentField, memberG8Field, populationField, independenceField);

        countryGrid.setAutoFetchData(true);
        countryGrid.setCanEdit(true);
        countryGrid.setModalEditing(false);
        countryGrid.setEditEvent(ListGridEditEvent.CLICK);
        countryGrid.setListEndEditAction(RowEndEditAction.NEXT);
        countryGrid.setAutoSaveEdits(false);
        layout.addMember(countryGrid);

        HLayout hLayout = new HLayout(15);
        IButton editButton = new IButton("Edit New");
        editButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                countryGrid.startEditingNew();
            }
        });
        hLayout.addMember(editButton);

        IButton saveButton = new IButton("Save");
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                countryGrid.saveAllEdits();
            }
        });
        hLayout.addMember(saveButton);

        IButton discardButton = new IButton("Discard");
        discardButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                countryGrid.discardAllEdits();
            }
        });
        hLayout.addMember(discardButton);

        layout.addMember(hLayout);

        final ServerCountLabel serverCountLabel = new ServerCountLabel();

        layout.addMember(serverCountLabel);

        RPCManager.setQueueSentCallback(new QueueSentCallback() {
            public void queueSent(RPCRequest[] requests) {
                serverCountLabel.incrementAndUpdate(requests);
                //flash the label
                serverCountLabel.setBackgroundColor("ffff77");
                new Timer() {
                    public void run() {
                        serverCountLabel.setBackgroundColor("ffffff");
                    }
                }.schedule(500);
            }
        });
        
        return layout;
    }

    class ServerCountLabel extends Label {
        private int count = 0;

        ServerCountLabel() {
            setPadding(10);
            setWidth(300);
            setHeight(40);
            setBorder("1px solid grey");
            setContents("<b>Number of server trips: 0<br>No queues sent</b>");
        }

        public void incrementAndUpdate(RPCRequest[] requests) {
            count++;
            setContents("<b>Number of server trips: " + count +
                    "<br/>Last queue contained: " + requests.length + " request(s)</b>");
        }
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}