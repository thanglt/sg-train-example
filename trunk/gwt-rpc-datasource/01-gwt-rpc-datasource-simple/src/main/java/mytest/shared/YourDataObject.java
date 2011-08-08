package mytest.shared;
import com.google.gwt.user.client.rpc.IsSerializable;

public class YourDataObject implements IsSerializable {

	private Integer id;
	
	private String name;
	
	private String location;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}