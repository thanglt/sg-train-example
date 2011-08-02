/**
 * (C) Copyright 2010, 2011 upTick Pty Ltd
 *
 * Licensed under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation. You may obtain a copy of the
 * License at: http://www.gnu.org/copyleft/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package au.com.uptick.serendipity.client;

public interface SerendipityConstants extends com.google.gwt.i18n.client.Constants {

  // Menus

  @DefaultStringValue("<u>N</u>ew Activity")
  String newActivityMenuName();

  @DefaultStringValue("Task, Fax, Phone Call, Email, Letter, Appointment")
  String newActivityMenuItemNames();

  @DefaultStringValue("New Re<u>c</u>ord")
  String newRecordMenuName();

  // @DefaultStringValue("Account, Contact, separator, Lead, Opportunity")
  // newRecordMenuItemNames: Konto, Kontakt, separator, Blei, Gelegenheit
  @DefaultStringValue("Account, Contact")
  String newRecordMenuItemNames();

  @DefaultStringValue("<u>G</u>o To")
  String goToMenuName();

  @DefaultStringValue("Sales")
  String salesMenuItemName();

  @DefaultStringValue("Activities, Calendar, Dashboards, Imports, Accounts, Contacts, Queues, Reports")
  String salesMenuItemNames();
  @DefaultStringValue("Activities")
  String activitiesMenuItemName();
  @DefaultStringValue("Calendar")
  String calendarMenuItemName();
  @DefaultStringValue("Dashboards")
  String dashboardsMenuItemName();
  @DefaultStringValue("Imports")
  String importsMenuItemName();
  @DefaultStringValue("Accounts")
  String accountsMenuItemName();
  @DefaultStringValue("Contacts")
  String contactsMenuItemName();
  @DefaultStringValue("Queues")
  String queuesMenuItemName();
  @DefaultStringValue("Reports")
  String reportsMenuItemName();

  @DefaultStringValue("Settings")
  String settingsMenuItemName();

  @DefaultStringValue("Administration, Templates, Data Management")
  String settingsMenuItemNames();
  @DefaultStringValue("Administration")
  String administrationMenuItemName();

  @DefaultStringValue("Resource Centre")
  String resourceCentreMenuItemName();

  @DefaultStringValue("Highlights, Sales, Settings")
  String resourceCentreMenuItemNames();
  @DefaultStringValue("Highlights")
  String highlightsMenuItemName();

  @DefaultStringValue("<u>T</u>ools")
  String toolsMenuName();

  @DefaultStringValue("Import Data, Duplicate Detection, Advanced Find, Options")
  String toolsMenuItemNames();

  @DefaultStringValue("<u>H</u>elp")
  String helpMenuName();

  @DefaultStringValue("Help on this Page, Contents, Serendipity Online, About Serendipity")
  String helpMenuItemNames();

  // Navigation Pane Header

  @DefaultStringValue("Workplace")
  String workplace();

  @DefaultStringValue("Activities")
  String activities();

  // Navigation Pane

  @DefaultStringValue("Sales")
  String salesStackSectionName();

  @DefaultStringValue("Settings")
  String settingsStackSectionName();

  @DefaultStringValue("Resource Centre")
  String resourceCentreStackSectionName();

  // Entity Navigation Pane

  @DefaultStringValue("Details")
  String accountDetailsStackSectionName();

  // ToolBar

  @DefaultStringValue("New")
  String newButton();
  @DefaultStringValue("New")
  String newButtonTooltip();

  @DefaultStringValue("Run Workflow...")
  String workflowButton();
  @DefaultStringValue("Run Workflow")
  String workflowButtonTooltip();

  @DefaultStringValue("Reports")
  String reportsButton();
  @DefaultStringValue("Reports")
  String reportsButtonTooltip();

  @DefaultStringValue("Print Preview")
  String printPreviewButtonTooltip();
  @DefaultStringValue("Export")
  String exportButtonTooltip();
  @DefaultStringValue("Mail Merge")
  String mailMergeButtonTooltip();
  @DefaultStringValue("Assign")
  String assignButtonTooltip();
  @DefaultStringValue("Delete")
  String deleteButtonTooltip();
  @DefaultStringValue("Email")
  String emailButtonTooltip();
  @DefaultStringValue("Attach")
  String attachButtonTooltip();
  @DefaultStringValue("Refresh")
  String refreshButtonTooltip();

  // Form ToolBar

  @DefaultStringValue("Save and Close")
  String saveAndCloseButton();

  @DefaultStringValue("Help")
  String helpButton();

  @DefaultStringValue("Save")
  String saveButtonTooltip();
  @DefaultStringValue("Save and Close")
  String saveAndCloseButtonTooltip();
  @DefaultStringValue("Help")
  String helpButtonTooltip();

  //
  // Account Form tabs
  //

  @DefaultStringValue("Account: ")
  String accountWindowTitle();

  @DefaultStringValue("General")
  String generalTab();

  @DefaultStringValue("General Information")
  String generalInformationSectionItem();

  @DefaultStringValue("Account Name")
  String accountNameLabel();
  @DefaultStringValue("Account Number")
  String accountNumberLabel();
  @DefaultStringValue("Parent Account")
  String parentAccountLabel();
  @DefaultStringValue("Primary Contact")
  String primaryContactLabel();
  @DefaultStringValue("Relationship Type")
  String relationshipTypeLabel();
  @DefaultStringValue("Main Phone")
  String mainPhoneLabel();
  @DefaultStringValue("Other Phone")
  String otherPhoneLabel();
  @DefaultStringValue("Fax")
  String faxLabel();
  @DefaultStringValue("Web Site")
  String webSiteLabel();
  @DefaultStringValue("Email")
  String emailLabel();

  @DefaultStringValue("Address Information")
  String addressInformationSectionItem();

  @DefaultStringValue("Address Name")
  String addressNameLabel();
  @DefaultStringValue("Street 1")
  String addressLine1Label();
  @DefaultStringValue("Street 2")
  String addressLine2Label();
  @DefaultStringValue("Street 3")
  String addressLine3Label();
  @DefaultStringValue("City")
  String cityLabel();
  @DefaultStringValue("State/Province")
  String stateLabel();
  @DefaultStringValue("ZIP/Postal Code")
  String postalCodeLabel();
  @DefaultStringValue("Country/Region")
  String countryLabel();
  @DefaultStringValue("Address Type")
  String addressTypeLabel();

  @DefaultStringValue("Administration")
  String administrationTab();

  @DefaultStringValue("Notes")
  String notesTab();

  // ActivitiesRecord
  @DefaultStringValue("Activity Type")
  String activityType();
  @DefaultStringValue("Subject")
  String subject();
  @DefaultStringValue("Regarding")
  String regarding();
  @DefaultStringValue("Priority")
  String priority();
  @DefaultStringValue("Start Date")
  String startDate();
  @DefaultStringValue("Due Date")
  String dueDate();

  // AccountsRecord
  @DefaultStringValue("Account Name")
  String accountName();
  @DefaultStringValue("Main Phone")
  String mainPhone();
  @DefaultStringValue("Location")
  String location();
  @DefaultStringValue("Primary Contact")
  String primaryContact();
  @DefaultStringValue("Email (Primary Contact)")
  String emailPrimaryContact();

  // ReportsRecord
  @DefaultStringValue("Report Name")
  String reportName();
  @DefaultStringValue("Report Type")
  String reportType();
  @DefaultStringValue("Modified On")
  String modifiedOn();
  @DefaultStringValue("Description")
  String description();

  // ImportsRecord
  @DefaultStringValue("Import Name")
  String importName();
  @DefaultStringValue("Status")
  String status();
  @DefaultStringValue("Successes")
  String successes();
  @DefaultStringValue("Errors")
  String errors();
  @DefaultStringValue("Total")
  String total();
  @DefaultStringValue("Created On")
  String createdOn();

}
