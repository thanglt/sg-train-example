package com.train.client.data;

import com.train.client.Accounts.AccountsView;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceCentreNavigationPaneSectionData {
    private static NavigationPaneRecord[] records;

    public static NavigationPaneRecord[] getRecords() {
        if (records == null) {
            records = getNewRecords();
        }
        return records;
    }

    public static NavigationPaneRecord[] getNewRecords() {
        return new NavigationPaneRecord[]{
                new NavigationPaneRecord("highlights", "Highlights", new AccountsView.Factory(), null),
                new NavigationPaneRecord("sales", "Sales", new AccountsView.Factory(), null),
                new NavigationPaneRecord("settings", "Settings", new AccountsView.Factory(), null)
        };
    }
}
