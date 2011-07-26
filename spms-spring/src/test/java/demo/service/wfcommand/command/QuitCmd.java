package demo.service.wfcommand.command;

import static demo.service.wfcommand.WfUtils.output;
import demo.service.wfcommand.WfCmd;

public class QuitCmd implements WfCmd{
	
	public QuitCmd(){
		
	}

	@Override
	public void doCommand(String[] input) {
		output("quit system");
		System.exit(0);
	}

	@Override
	public String getCmdName() {
		return "quit";
	}
	
}

