package com.mycompany.webservice.server.webservice.type;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 11-10-24
 * Time: 下午3:55
 * Description: a simple type return to client, tell if the operation is success.
 */
@XmlType(propOrder = {
    "success","responseText"
})
public class OperationOutputType {

//    private boolean success;
    private boolean success;
    private String responseText;

    public OperationOutputType() {
    }

    public OperationOutputType(boolean success, String responseText) {
        this.success = success;
        this.responseText = responseText;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
