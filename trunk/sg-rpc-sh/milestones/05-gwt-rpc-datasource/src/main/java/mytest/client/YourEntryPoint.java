//package mytest.client;
//
//import com.google.gwt.core.client.EntryPoint;
//import com.google.gwt.core.client.GWT;
//import com.smartgwt.client.core.KeyIdentifier;
//import com.smartgwt.client.types.FetchMode;
//import com.smartgwt.client.types.ListGridEditEvent;
//import com.smartgwt.client.util.KeyCallback;
//import com.smartgwt.client.util.Page;
//import com.smartgwt.client.util.SC;
//import com.smartgwt.client.widgets.Canvas;
//import com.smartgwt.client.widgets.IButton;
//import com.smartgwt.client.widgets.events.ClickEvent;
//import com.smartgwt.client.widgets.events.ClickHandler;
//import com.smartgwt.client.widgets.grid.ListGrid;
//import com.smartgwt.client.widgets.grid.ListGridField;
//import com.smartgwt.client.widgets.grid.ListGridRecord;
//import mytest.client.datasource.UserDataSource;
//
//public class YourEntryPoint implements EntryPoint {
//
//    public void onModuleLoad() {
//
//        Canvas canvas = new Canvas();
//
//        final ListGrid yourGrid = new ListGrid();
//        yourGrid.setWidth(550);
//        yourGrid.setHeight(200);
//        yourGrid.setCellHeight(22);
//        yourGrid.setDataSource(UserDataSource.getInstance());
//
//        ListGridField nameField = new ListGridField("name", "Name");
//        ListGridField locationField = new ListGridField("location", "Location");
//        yourGrid.setFields(nameField, locationField);
//
//        yourGrid.setAutoFetchData(true);
////		yourGrid.setDataFetchMode(FetchMode.BASIC);
//        yourGrid.setDataFetchMode(FetchMode.PAGED);
//        yourGrid.setShowAllRecords(false);
//        yourGrid.setDataPageSize(20);
//        yourGrid.setCanEdit(true);
//        yourGrid.setShowFilterEditor(true);
//        yourGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
//
//        IButton addRow = new IButton("Add new row");
//        addRow.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
//                ListGridRecord rec = new ListGridRecord();
//                rec.setAttribute("name", "yourName");
//                rec.setAttribute("location", "yourLocation");
//                yourGrid.addData(rec);
//            }
//        });
//        addRow.setLeft(0);
//        addRow.setTop(240);
//        addRow.setWidth(140);
//
//        IButton removeAll = new IButton("Remove All Selected");
//        removeAll.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
//                ListGridRecord[] selectedRecords = yourGrid.getSelection();
//                for (ListGridRecord rec : selectedRecords) {
//                    yourGrid.removeData(rec);
//                }
//            }
//        });
//        removeAll.setLeft(320);
//        removeAll.setTop(240);
//        removeAll.setWidth(140);
//
//        canvas.addChild(yourGrid);
//        canvas.addChild(addRow);
//        canvas.addChild(removeAll);
//
//        canvas.draw();
//
//        // for debugging only, shows the developer console when hitting CTRL-D.
//        if (!GWT.isScript()) {
//            KeyIdentifier debugKey = new KeyIdentifier();
//            debugKey.setCtrlKey(true);
//            debugKey.setKeyName("D");
//            Page.registerKey(debugKey, new KeyCallback() {
//                public void execute(String keyName) {
//                    SC.showConsole();
//                }
//            });
//        }
//    }
//
//}
