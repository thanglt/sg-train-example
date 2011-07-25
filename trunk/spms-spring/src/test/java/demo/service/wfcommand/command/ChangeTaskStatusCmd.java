package demo.service.wfcommand.command;

import org.springframework.context.ApplicationContext;

import com.skynet.spms.jbpm.manager.WfTaskOperManager;

import demo.service.wfcommand.WfCmd;

public class ChangeTaskStatusCmd implements WfCmd {

	private WfTaskOperManager taskManager;

	public ChangeTaskStatusCmd(ApplicationContext context) {
		taskManager = context.getBean(WfTaskOperManager.class);
	}
	
	@Override
	public void doCommand(String[] input) {
		
		String taskID=input[1];
		String userID=input[0];
//		
//		if("take".equals(type)){
//			String userID=input[2];
		taskManager.takeTask(taskID,userID);
//		}else if("status".equals(type)){
//			int status=Integer.parseInt(input[2]);			
//			taskManager.changeTaskStatus(taskID, status);
//		}
	}

	@Override
	public String getCmdName() {
		return "taskTaken";
	}

}
