package demo.service.wfcommand.command;

import static demo.service.wfcommand.WfUtils.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.identity.User;
import org.springframework.context.ApplicationContext;


import demo.service.wfcommand.WfCmd;

public class UserListCmd implements WfCmd{

	private final IdentityService identityService;
	
	public UserListCmd(ApplicationContext context){
		identityService = context.getBean(IdentityService.class);
	}
	
	@Override
	public String getCmdName(){
		return "userlist";
	}

	@Override
	public void doCommand(String[] input) {
		
		List<User> userList=identityService.findUsers();
		output("workflow user list:");
		for(User user:userList){
			output("user id:"+user.getId());
		}
	}

}
