package demo.service.wfcommand.command;

import static demo.service.wfcommand.WfUtils.output;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.springframework.context.ApplicationContext;

import com.skynet.spms.jbpm.manager.WfTaskOperManager;

import demo.service.wfcommand.WfCmd;

public class TaskCommitCmd implements WfCmd {
	
	private WfTaskOperManager taskManager;

	public TaskCommitCmd(ApplicationContext context) {
		taskManager = context.getBean(WfTaskOperManager.class);
	}
	
	@Override
	public String getCmdName() {
		return "taskcommit";
	}

	@Override
	public void doCommand(String[] input) {
		
		String taskID=input[0];
		String outcoming=null;
		if(input.length>1){
			outcoming=input[1];
		}		
		taskManager.commitTask(taskID,outcoming,null);
		

		output("the task  been commit to "+outcoming );

	}
}
