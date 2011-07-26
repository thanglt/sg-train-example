package demo.service.wfcommand.command;

import static demo.service.wfcommand.WfUtils.output;
import demo.service.wfcommand.WfCmd;

public class UnKnowCmd implements WfCmd{
	
	

	@Override
	public void doCommand(String[] input) {
		output("unknow command");
	}

	@Override
	public String getCmdName() {
		return null;
	}
	
}

