package com.smartgwt.sample.showcase.client.dataintegration.java.transactions;

import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.QueueSentCallback;
import com.google.gwt.user.client.Timer;

public class MultiRowDragSaveSample extends ShowcasePanel {

    private static final String DESCRIPTION = "<p>Drag employee records into the Project Team Members list. SmartGWT recognizes that the two dataSources are linked by a " +
            "foreign key relationship, and automatically uses that relationship to populate values in the record that is added when you drop. " +
            "SmartGWT also populates fields based on current criteria and maps explicit titleFields as necessary.</p>" +
            "<p>In this example, note that SmartGWT is automatically populating all three of the fields in the teamMembers dataSource, even though none of " +
            "those fields is present in the employees dataSource we are dragging from. Change the \"Team for Project\" " +
            "select box, then try dragging employees across; note that the Project Code column is being correctly populated for the dropped records.</p>" +
            "<p>Multi-row selection is enabled on the Employees grid, so you can select multiple employees and drag them to the Teams grid in one go. Because the grids are databound, " +
            "this drag and drop action will send data operations to the server automatically, using SmartGWT queuing to ensure all the updates arrive on the server together.<p>" +
            "<p>All of this just works, there is no coding needed to enable it.</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public Canvas create() {
            MultiRowDragSaveSample panel = new MultiRowDragSaveSample();
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

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {

        String[] projects = new String[] {
                "New Costing System", "Warehousing Improvements", "Evaluate AJAX Frameworks", "Upgrade Postgres", "Online Billing"
        };

        DataSource employeesDS = DataSource.get("employees");

        final ListGrid employeesGrid = new ListGrid();
        employeesGrid.setWidth(300);
        employeesGrid.setHeight(224);
        employeesGrid.setDataSource(employeesDS);
        employeesGrid.setCanDragRecordsOut(true);
        employeesGrid.setDragDataAction(DragDataAction.COPY);
        employeesGrid.setAlternateRecordStyles(true);
        employeesGrid.setAutoFetchData(false);

        ListGridField employeeIdField = new ListGridField("EmployeeId");
        employeeIdField.setWidth("25%");

        ListGridField nameField = new ListGridField("Name");

        employeesGrid.setFields(employeeIdField, nameField);


        DataSource teamMembersDS = DataSource.get("teamMembers");

        final ListGrid projectGrid = new ListGrid();
        projectGrid.setWidth(300);
        projectGrid.setHeight(264);
        projectGrid.setDataSource(teamMembersDS);
        projectGrid.setCanAcceptDroppedRecords(true);
        projectGrid.setCanRemoveRecords(true);
        projectGrid.setAutoFetchData(false);
        projectGrid.setPreventDuplicates(true);

        ListGridField employeeIdField2 = new ListGridField("employeeId");
        employeeIdField2.setWidth("20%");

        ListGridField employeeNameField2 = new ListGridField("employeeName");
        employeeNameField2.setWidth("40%");
        ListGridField projectCodeField2 = new ListGridField("projectCode");

        projectGrid.setFields(employeeIdField2, employeeNameField2, projectCodeField2);

        HStack hStack = new HStack(10);
        hStack.setHeight(160);

        VStack vStack = new VStack();
        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setHeight(30);
        vStack.addMember(spacer);
        vStack.addMember(employeesGrid);

        hStack.addMember(vStack);

        Img arrowImg = new Img("icons/32/arrow_right.png", 32, 32);
        arrowImg.setLayoutAlign(Alignment.CENTER);
        arrowImg.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                projectGrid.transferSelectedData(employeesGrid);
            }
        });
        hStack.addMember(arrowImg);

        VStack vStack2 = new VStack();

        final DynamicForm projectSelectorForm = new DynamicForm();
        projectSelectorForm.setWidth(300);
        projectSelectorForm.setHeight(30);

        SelectItem selectItem = new SelectItem("projectCode", "Team for Project");
        selectItem.setDefaultValue(projects[0]);
        selectItem.setValueMap(projects);
        selectItem.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                Criteria criteria = projectSelectorForm.getValuesAsCriteria();
                projectGrid.fetchData(criteria);
            }
        });
        projectSelectorForm.setFields(selectItem);

        vStack2.addMember(projectSelectorForm);
        vStack2.addMember(projectGrid);



        final ServerCountLabel serverCountLabel = new ServerCountLabel();
        serverCountLabel.setTop(315);
        serverCountLabel.setLeft(220);

        hStack.addChild(serverCountLabel);

        hStack.addMember(vStack2);

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

        projectGrid.fetchData(projectSelectorForm.getValuesAsCriteria());
        employeesGrid.fetchData();

        return hStack;
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
