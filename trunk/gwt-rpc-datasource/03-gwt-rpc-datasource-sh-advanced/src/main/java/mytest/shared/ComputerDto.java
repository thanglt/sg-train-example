package mytest.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ComputerDto implements IsSerializable {

    private String Id; //主键
    private String type;
    private String code;

    public ComputerDto() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
