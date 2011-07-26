package demo.service.wfcommand.command;

import org.springframework.context.ApplicationContext;

import com.skynet.spms.jbpm.manager.WfTaskOperManager;

import demo.service.wfcommand.WfCmd;

public class ListVariableCmd implements WfCmd{
	private WfTaskOperManager taskManager;

	public ListVariableCmd(ApplicationContext context) {
		taskManager = context.getBean(WfTaskOperManager.class);
	}

	@Override
	public void doCommand(String[] input) {

	}

	@Override
	public String getCmdName() {
		return "listVar";
	}
	
}
