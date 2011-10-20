package com.mycompany.gwt.sample.simplexml.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;


public class SimpleXML implements EntryPoint {
  private static final String XML_LABEL_STYLE = "xmlLabel";
  private static final String USER_TABLE_LABEL_STYLE = "userTableLabel";
  private static final String USER_TABLE_STYLE = "userTable";
  private static final String NOTES_STYLE = "notes";

  public void onModuleLoad() {
//    RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, "customerRecord.xml");
    RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, "simpleXml/customerRecord.xml");

    try {
      requestBuilder.sendRequest(null, new RequestCallback() {
        public void onError(Request request, Throwable exception) {
          requestFailed(exception);
        }

        public void onResponseReceived(Request request, Response response) {
          renderXML(response.getText());
        }
      });
    } catch (RequestException ex) {
      requestFailed(ex);
    }
  }

  private FlexTable createOrderTable(FlowPanel xmlParsed, String label) {
    HTML orderTableLabel = new HTML("<h2>" + label);
    xmlParsed.add(orderTableLabel);
    FlexTable orderTable = new FlexTable();
    orderTable.setStyleName(USER_TABLE_STYLE);
    orderTable.setBorderWidth(3);
    orderTable.getRowFormatter().setStyleName(0, USER_TABLE_LABEL_STYLE);
    orderTable.setText(0, 0, "Employee ID");
    orderTable.setText(0, 1, "Firstname");
    orderTable.setText(0, 2, "Lastname");
    orderTable.setText(0, 3, "City");
    orderTable.setText(0, 4, "State");
    orderTable.setText(0, 5, "Zip");
    xmlParsed.add(orderTable);
    return orderTable;
  }
  private void customerPane(String xmlText, FlowPanel xmlParsed) {
    Document customerDom = XMLParser.parse(xmlText);
    Element customerElement = customerDom.getDocumentElement();
    XMLParser.removeWhitespace(customerElement);

	String nameValue = getElementTextValue(customerElement, "name");
    String title = "<h1>" + nameValue + "</h1>";
    HTML titleHTML = new HTML(title);
    xmlParsed.add(titleHTML);

    String notesValue = getElementTextValue(customerElement, "notes");
    Label notesText = new Label();
    notesText.setStyleName(NOTES_STYLE);
    notesText.setText(notesValue);
    xmlParsed.add(notesText);


    FlexTable pendingTable = createOrderTable(xmlParsed, "Employee Details");

   NodeList orders = customerElement.getElementsByTagName("order");
    int pendingRowPos = 0;
    int completedRowPos = 0;

	System.out.println("orders.getLength()" + orders.getLength());
    for (int i = 0; i < orders.getLength(); i++) {
      Element order = (Element) orders.item(i);
      HTMLTable table;
      int rowPos;
      if (order.getAttribute("status").equals("pending")) {
        table = pendingTable;
        rowPos = ++pendingRowPos;
      } else {
        table = pendingTable;
        rowPos = ++pendingRowPos;
      }
      int columnPos = 0;
      fillInOrderTableRow(customerElement, order, table, rowPos, columnPos);
    }
  }

  private void fillInOrderTableRow(Element customerElement, Element order,
      HTMLTable table, int rowPos, int columnPos) {

    String orderId = order.getAttribute("id");
    table.setText(rowPos, columnPos++, orderId);

    Element item = (Element) order.getElementsByTagName("item").item(0);
    String itemUPC = item.getAttribute("Firstname");
    String itemName = item.getFirstChild().getNodeValue();
    Label itemLabel = new Label(itemUPC);
    itemLabel.setTitle(itemName);
    table.setWidget(rowPos, columnPos++, itemLabel);

    String orderedOnValue = getElementTextValue(customerElement, "Lastname");
    table.setText(rowPos, columnPos++, orderedOnValue);

    Element address = (Element) order.getElementsByTagName("address").item(0);
    XMLParser.removeWhitespace(address);
    NodeList lst = address.getChildNodes();
    for (int j = 0; j < lst.getLength(); j++) {
      Element next = (Element) lst.item(j);
      String addressPartText = next.getFirstChild().getNodeValue();
      table.setText(rowPos, columnPos++, addressPartText);
    }

   }
  private String getElementTextValue(Element parent, String elementTag) {

    return parent.getElementsByTagName(elementTag).item(0).getFirstChild().getNodeValue();
  }

  private void renderXML(String xmlText) {
    final TabPanel tab = new TabPanel();
    final FlowPanel xmlSource = new FlowPanel();
    final FlowPanel xmlParsed = new FlowPanel();
    tab.add(xmlParsed, "Employee");
    tab.add(xmlSource, "XML");
    tab.selectTab(0);
    RootPanel.get().add(tab);
    xmlPane(xmlText, xmlSource);
    customerPane(xmlText, xmlParsed);
  }

  private void requestFailed(Throwable exception) {
    Window.alert("Failed to send the message: "
        + exception.getMessage());
  }
  private void xmlPane(String xmlText, final FlowPanel xmlSource) {
    xmlText = xmlText.replaceAll("<", "&#60;");
    xmlText = xmlText.replaceAll(">", "&#62;");
    Label xml = new HTML("<pre>" + xmlText + "</pre>", false);
    xml.setStyleName(XML_LABEL_STYLE);
    xmlSource.add(xml);
  }
}
