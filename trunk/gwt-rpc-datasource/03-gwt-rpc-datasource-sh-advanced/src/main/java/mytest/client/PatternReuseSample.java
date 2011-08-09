//package mytest.client;
//
//import com.google.gwt.core.client.EntryPoint;
//import com.smartgwt.client.data.DataSource;
//import com.smartgwt.client.types.Alignment;
//import com.smartgwt.client.widgets.IButton;
//import com.smartgwt.client.widgets.events.ClickEvent;
//import com.smartgwt.client.widgets.events.ClickHandler;
//import com.smartgwt.client.widgets.form.DynamicForm;
//import com.smartgwt.client.widgets.form.fields.SelectItem;
//import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
//import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
//import com.smartgwt.client.widgets.grid.ListGrid;
//import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
//import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
//import com.smartgwt.client.widgets.layout.HLayout;
//import com.smartgwt.client.widgets.layout.VLayout;
//import mytest.client.datasource.ComputerDataSource;
//import mytest.client.datasource.UserDataSource;
//
//public class PatternReuseSample implements EntryPoint {
//
//    protected boolean isTopIntro() {
//        return true;
//    }
//
//    public void onModuleLoad() {
//        final DataSource userDS = UserDataSource.getInstance();
//        final DataSource computerDS = ComputerDataSource.getInstance();
//
//        final CompoundEditor cEditor = new CompoundEditor(userDS);
//
//        SelectItem dsSelect = new SelectItem();
//        dsSelect.setName("datasource");
//        dsSelect.setShowTitle(false);
//        dsSelect.setValueMap("user", "computer");
//        dsSelect.addChangedHandler(new ChangedHandler() {
//            public void onChanged(ChangedEvent event) {
//                String ds = (String) event.getValue();
//                if (ds.equalsIgnoreCase("user")) {
//                    cEditor.setDatasource(userDS);
//                } else {
//                    cEditor.setDatasource(computerDS);
//                }
//            }
//        });
//        DynamicForm form = new DynamicForm();
//        form.setValue("datasource", "Select a DataSource");
//        form.setItems(dsSelect);
//
//        VLayout layout = new VLayout(15);
//        layout.setWidth100();
//        layout.setHeight("80%");
//
//        layout.addMember(form);
//        layout.addMember(cEditor);
//
//        layout.draw();
//    }
//
//    private static class CompoundEditor extends HLayout {
//        private DataSource datasource;
//        private DynamicForm form;
//        private ListGrid grid;
//        private IButton saveButton;
//
//        private CompoundEditor(DataSource datasource) {
//            this.datasource = datasource;
//        }
//
//        @Override
//        protected void onInit() {
//            super.onInit();
//            this.form = new DynamicForm();
//
//            form.setDataSource(datasource);
//
//            saveButton = new IButton("Save");
//            saveButton.setLayoutAlign(Alignment.CENTER);
//            saveButton.addClickHandler(new ClickHandler() {
//
//                public void onClick(ClickEvent event) {
//                    form.saveData();
//                }
//            });
//
//            VLayout editorLayout = new VLayout(5);
//            editorLayout.addMember(form);
//            editorLayout.addMember(saveButton);
//            editorLayout.setWidth(280);
//
//            grid = new ListGrid();
//            grid.setWidth(500);
//            grid.setHeight(350);
//            grid.setDataSource(datasource);
//            grid.setAutoFetchData(true);
//            grid.addRecordClickHandler(new RecordClickHandler() {
//                public void onRecordClick(RecordClickEvent event) {
//                    form.clearErrors(true);
//                    form.editRecord(event.getRecord());
//                    saveButton.enable();
//                }
//
//            });
//
//            addMember(grid);
//            addMember(editorLayout);
//        }
//
//        public DataSource getDatasource() {
//            return datasource;
//        }
//
//        public void setDatasource(DataSource datasource) {
//            this.datasource = datasource;
//            grid.setDataSource(datasource);
//            form.setDataSource(datasource);
//            saveButton.disable();
//            grid.fetchData();
//        }
//    }
//
//}