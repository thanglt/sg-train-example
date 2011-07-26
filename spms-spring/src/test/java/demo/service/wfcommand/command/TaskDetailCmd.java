package demo.service.wfcommand.command;

import static demo.service.wfcommand.WfUtils.output;

import java.util.List;
import java.util.Set;

import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.springframework.context.ApplicationContext;

import demo.service.wfcommand.WfCmd;

public class TaskDetailCmd implements WfCmd {
	private TaskService taskService;

	public TaskDetailCmd(ApplicationContext context) {
		taskService = context.getBean(TaskService.class);
	}

	@Override
	public String getCmdName() {
		return "taskdetail";
	}

	@Override
	public void doCommand(String[] input) {
		String taskID=input[0];
		
		Task task = taskService.getTask(taskID);
		output("task " + task.getName() );

		Set<String> valSet = taskService.getVariableNames(taskID);
		output("task " + task.getName() + " variables list:");
		for (String valName : valSet) {
			output("var name:" + valName + " value:"
					+ taskService.getVariable(taskID, valName));
		}
		

		Set<String> outcoming = taskService.getOutcomes(taskID);
		int i = 1;

		output("task outcoming list:");
		for (String out : outcoming) {
			output(i + "." + out);
			i++;
		}
		output("input taskcommit <taskid>:<outcoming> commit task");

	}
}
