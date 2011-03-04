package com.train.client.data;

import com.train.client.Accounts.AccountsView;
import com.train.client.Calendar.CalendarView;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SalesNavigationPaneSectionData {

    private static NavigationPaneRecord[] records;

    public static NavigationPaneRecord[] getRecords() {
        if (records == null) {
            records = getNewRecords();
        }
        return records;
    }

    public static NavigationPaneRecord[] getNewRecords() {
        return new NavigationPaneRecord[]{
                new NavigationPaneRecord("activities", "Activities", new AccountsView.Factory(), null),
                new NavigationPaneRecord("calendar", "Calendar", new CalendarView.Factory(), null),
                new NavigationPaneRecord("leads", "Leads", new AccountsView.Factory(), null),
                new NavigationPaneRecord("opportunities", "Opportunities", new AccountsView.Factory(), null),
                new NavigationPaneRecord("accounts", "Accounts", new AccountsView.Factory(), null),
                new NavigationPaneRecord("contacts", "Contacts", new AccountsView.Factory(), null)
        };
    }
}
