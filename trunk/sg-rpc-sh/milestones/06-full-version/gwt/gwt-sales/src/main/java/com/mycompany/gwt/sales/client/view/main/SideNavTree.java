package com.mycompany.gwt.sales.client.view.main;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.datasource.MenuNodeDataSource;
import com.mycompany.gwt.sales.client.dto.MenuNodeDto;
import com.mycompany.gwt.sales.client.event.ModuleSelectedEvent;
import com.mycompany.gwt.sales.client.event.ModuleSelectedEventHandler;
import com.mycompany.gwt.sales.client.event.TreeNodeSelectedEvent;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.DataArrivedEvent;
import com.smartgwt.client.widgets.tree.events.DataArrivedHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;

/*
   左侧导航来树状目录控制类
*/
@Singleton
public class SideNavTree extends TreeGrid {

    @Inject
    public SideNavTree(MenuNodeDataSource menuNodeDS, final EventBus eventBus) {
        setWidth100();
        setHeight100();
        setLeaveScrollbarGap(false);
        setAnimateFolders(true);
        setSelectionType(SelectionStyle.SINGLE);
        setShowRoot(false);
        setShowHeader(false);
        //disable D&D
        setCanDrop(false);
        setCanDrag(false);
        setCanAcceptDrop(false);
        setCanAcceptDroppedRecords(false);
        setShowConnectors(false);
        setCanReparentNodes(false);
        setShowOpener(false);
        setCustomIconProperty("icon");

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setIdField("id");
        tree.setNameProperty("name");
        tree.setParentIdField("parentId");
        tree.setIsFolderProperty("isFolder");
        tree.setReportCollisions(false);
        setData(tree);
        menuNodeDS.setClientOnly(true);
        setDataSource(menuNodeDS);
//        setAutoFetchData(true);

        //展开所有节点
        addDataArrivedHandler(new DataArrivedHandler() {
            public void onDataArrived(DataArrivedEvent dataArrivedEvent) {
                getData().openAll();
            }
        });

        //添加目录树最终节点的鼠标点击事件处理器
        addLeafClickHandler(new LeafClickHandler() {
            public void onLeafClick(LeafClickEvent leafClickEvent) {
                String nodeName = getSelectedRecord().getAttributeAsString("name");
//                SC.say("nodeName : " + nodeName);
                String viewId = getSelectedRecord().getAttributeAsString("viewId");
//                SC.say("viewId : " + viewId);
                String icon = getSelectedRecord().getAttributeAsString("icon");
//                SC.say("icon : " + icon);
                if (null != viewId && !viewId.equals("")) {
                    eventBus.fireEvent(new TreeNodeSelectedEvent(nodeName, viewId, icon));
                }
            }
        });

        eventBus.addHandler(ModuleSelectedEvent.TYPE, new ModuleSelectedEventHandler() {
            public void onEvent(ModuleSelectedEvent event) {
//                String moduleName = event.moduleName;
//                fetchData(new Criteria("category", moduleName));

                TreeNode[] data = new TreeNode[2];
                TreeNode record1 = new TreeNode();
                record1.setAttribute("id", "test1");
                record1.setAttribute("isFolder", "1");
                record1.setAttribute("icon", "icons/bullet_go.png");
                record1.setAttribute("name", "Test 1");
                record1.setAttribute("parentId", "root");
                data[0] = record1;

                TreeNode record2 = new TreeNode();
                record2.setAttribute("id", "test2");
                record2.setAttribute("isFolder", "0");
                record2.setAttribute("icon", "icons/key.png");
                record2.setAttribute("name", "Test 2");
                record2.setAttribute("parentId", "test1");
                data[1] = record2;

                Record record3 = new Record();
                record3.setAttribute("id", "test2");
                record3.setAttribute("isFolder", true);
                record3.setAttribute("icon", "icons/key.png");
                record3.setAttribute("name", "Test 2");
                record3.setAttribute("parentId", "root");

//                getData().setData(data);
                getDataSource().invalidateCache();
//                getDataSource().addData(record3);
                MenuNodeDto dto = new MenuNodeDto();
                dto.setId("test1");
                dto.setFolder(true);
                dto.setIcon("icons/key.png");
                dto.setName("test 1");
                dto.setParentId("root");

                getDataSource().setCacheData(record3);

            }
        });

    }

}
