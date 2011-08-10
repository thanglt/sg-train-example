package mytest.client.view.sample2;

import com.google.inject.Singleton;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.events.DataArrivedEvent;
import com.smartgwt.client.widgets.tree.events.DataArrivedHandler;

@Singleton
public class MyTreeGrid extends TreeGrid {

    public MyTreeGrid() {
        setWidth(300);
        setHeight(400);
        setLeaveScrollbarGap(false);
        setAnimateFolders(true);
        setSelectionType(SelectionStyle.SINGLE);
//        setExportAll(true);
        setShowRoot(false);
        setShowHeader(false);
        //disable D&D
        setCanDrop(false);
        setCanDrag(false);
        setCanAcceptDrop(false);
        setCanAcceptDroppedRecords(false);
        setShowConnectors(false);
        setCanReparentNodes(false);
        //icons
        setFolderIcon("icons/bullet_go.png");
        setNodeIcon("icons/application.png");
//        setCustomIconProperty("icon");
//        setTreeRootValue("root");
//        setExpansionFieldTrueImage("");
//        setExpansionFieldFalseImage("");

        TreeGridField idField = new TreeGridField("id", "主键");
        idField.setHidden(true);

        TreeGridField nameField = new TreeGridField("name", "显示名");
        nameField.setCanSort(false);

        TreeGridField parentIdField = new TreeGridField("parentId", "父键");
        parentIdField.setHidden(true);

        TreeGridField categoryField = new TreeGridField("category", "分类");
        categoryField.setHidden(true);

        TreeGridField isFolder = new TreeGridField("isFolder", "节点类型");
        isFolder.setHidden(true);

        setFields(idField, nameField, parentIdField, categoryField , isFolder);

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setIdField("id");
        tree.setNameProperty("name");
        tree.setParentIdField("parentId");
        tree.setIsFolderProperty("isFolder");
        tree.setReportCollisions(false);

        setData(tree);

        addDataArrivedHandler(new DataArrivedHandler() {
            public void onDataArrived(DataArrivedEvent dataArrivedEvent) {
                getData().openAll();
            }
        });
    }
}
