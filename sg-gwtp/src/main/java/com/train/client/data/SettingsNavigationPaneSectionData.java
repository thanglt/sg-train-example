package com.train.client.data;

import com.train.client.Accounts.AccountsView;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsNavigationPaneSectionData {

    private static NavigationPaneRecord[] records;

    public static NavigationPaneRecord[] getRecords() {
        if (records == null) {
            records = getNewRecords();
        }
        return records;
    }

    public static NavigationPaneRecord[] getNewRecords() {
        return new NavigationPaneRecord[]{
                new NavigationPaneRecord("administration", "Administration", new AccountsView.Factory(), null),
                new NavigationPaneRecord("templates", "Templates", new AccountsView.Factory(), null),
                new NavigationPaneRecord("datamanagement", "Data Management", new AccountsView.Factory(), null)
        };
    }
}
