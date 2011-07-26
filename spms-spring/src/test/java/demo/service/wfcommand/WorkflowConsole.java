package demo.service.wfcommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static demo.service.wfcommand.WfUtils.output;

import demo.service.wfcommand.command.ChangeTaskStatusCmd;
import demo.service.wfcommand.command.FlowMangCmd;
import demo.service.wfcommand.command.ListVariableCmd;
import demo.service.wfcommand.command.QuitCmd;
import demo.service.wfcommand.command.StartProcCmd;
import demo.service.wfcommand.command.TaskCommitCmd;
import demo.service.wfcommand.command.TaskDetailCmd;
import demo.service.wfcommand.command.TaskListCmd;
import demo.service.wfcommand.command.UserListCmd;
import edu.emory.mathcs.backport.java.util.Arrays;

public class WorkflowConsole {
	
	private Logger log=LoggerFactory.getLogger(WorkflowConsole.class);

	public static void main(String[] args) {

		WorkflowConsole wfcmd = new WorkflowConsole();
		
		wfcmd.messageCycle();
		
	}
	
	private void messageCycle(){
		while (true) {
			String cmd = readCmd();
			System.out.println("cmd:"+cmd);
			String[] params=StringUtils.split(cmd);
			String[] newParam=new String[params.length-1];
			System.arraycopy(params, 1, newParam, 0, params.length-1);

			WfCmd command=getCommand(params[0]);
			if(command==null){
				unknowCmd.doCommand(newParam);
			}
			try{
				command.doCommand(newParam);
			}catch(Exception e){
				log.error("command "+cmd+" error",e);
			}
		}
	}
	
	private static String readCmd() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					(System.in)));

			String cmd = reader.readLine();

			return StringUtils.trim(cmd);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
	}


	private WorkflowConsole() {

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"base_Context.xml", "service_Context.xml", "util_Context.xml",
				"jbpm_Context.xml" });
		generCmdMap(context);	
		output("cmd list:");
		for(String cmd:commandMap.keySet()){
			output(cmd);
		}
	}
	
	private WfCmd getCommand(String cmd){
		return commandMap.get(cmd);
	}	
	
	private WfCmd unknowCmd=new UnKnowCmd();
	
	private class UnKnowCmd implements WfCmd{
		
		@Override
		public void doCommand(String[] input) {
			output("unknow command:"+Arrays.asList(input));
			output("cmd list:");
			for(String cmd:commandMap.keySet()){
				output(cmd);
			}
		}

		@Override
		public String getCmdName() {
			return null;
		}		
	}
	
	private Map<String,WfCmd> commandMap=new HashMap<String,WfCmd>();
	
	
	private static Class[] clsArray={
			TaskDetailCmd.class,
			TaskCommitCmd.class,
			UserListCmd.class,
			TaskListCmd.class,
			StartProcCmd.class,
			QuitCmd.class,
			FlowMangCmd.class,
			ListVariableCmd.class,
			ChangeTaskStatusCmd.class};

	private void generCmdMap(ApplicationContext context){
		try{
			for(Class<WfCmd> cls:clsArray){
				try{
					WfCmd cmd=cls.newInstance();
					commandMap.put(cmd.getCmdName(), cmd);
				}catch(Exception e){
					log.warn("no default construct:"+cls,e.getMessage());
					Constructor<WfCmd> construct=cls.getConstructor(ApplicationContext.class);
					WfCmd cmd=construct.newInstance(context);
					commandMap.put(cmd.getCmdName(), cmd);
				}
			}
		}catch(Exception e){
			throw new IllegalArgumentException(e);
		}
	}
	
	
	

//	private final ApplicationContext context;
	//
//		private TaskService taskService;
	//
//		private ExecutionService executeService;
	//
//		private RepositoryService repositoryService;
	//
//		private ManagementService managerService;
	//
//		private IdentityService identityService;
	//	
//		private JbpmProcessService process;

}
