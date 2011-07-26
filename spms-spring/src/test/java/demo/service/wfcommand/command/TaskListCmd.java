package demo.service.wfcommand.command;

import static demo.service.wfcommand.WfUtils.output;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.IdentityService;
import org.jbpm.api.TaskService;
import org.jbpm.api.identity.User;
import org.jbpm.api.task.Task;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.context.ApplicationContext;

import com.skynet.common.prop.PropEnum;
import com.skynet.common.prop.PropManager;
import com.skynet.common.prop.PropertyEntity;
import com.skynet.spms.jbpm.entity.TaskInfoEntity;
import com.skynet.spms.jbpm.entity.WfTaskInfo;
import com.skynet.spms.jbpm.manager.WfTaskListManager;
import com.skynet.spms.jbpm.manager.WfTaskOperManager;

import demo.service.wfcommand.WfCmd;

public class TaskListCmd implements WfCmd{

	private WfTaskListManager taskManager;

	private PropManager messageProp;
	public TaskListCmd(ApplicationContext context) {
		taskManager = context.getBean(WfTaskListManager.class);
		messageProp=context.getBean(PropManager.class);
	}
	
	@Override
	public String getCmdName(){
		return "tasklist";
	}

	@Override
	public void doCommand(String[] input) {
		String user=input[0];
		
		List<TaskInfoEntity> taskList=null;
		
		String oper=input[1];
		
		if("preview".equals(oper)){
			taskList=taskManager.getPreviewTaskList(user);
		}else if("taken".equals(oper)){
			taskList=taskManager.getTakenTaskList(user);
		}
				
		PropertyEntity prop=messageProp.getPropEntity(Locale.SIMPLIFIED_CHINESE, PropEnum.WfTaskInfo);
		
		output("user "+user+ "'s task list");
		for(TaskInfoEntity entity:taskList){
			
			WfTaskInfo task=entity.getTaskInfo(prop);
			output("proc inst id:"+task.getProcInstID());
			output("task name:"+task.getTaskName()+" active name:"+task.getFlowName());
			output("task id:"+task.getTaskID()+" business key:"+task.getBusKey());
			output("task status:"+task.getStatus());
			
			output("outcoming:");
			String outcoming=task.getOutcoming();
			
			List<Map<String,Object>> list=(List<Map<String, Object>>) JSONValue.parse(outcoming);			
			for(Map<String,Object> map:list){
				String out=(String) map.get("out");
				String desc=(String) map.get("desc");
				output("\t==>"+out+":"+desc);
			}
			output("variable:");
//			Map<String,Object> varMap=task.getVariableMap();
//			if(varMap!=null){
//				for(Map.Entry<String, Object> entry:varMap.entrySet()){
//					output("\tkey:"+entry.getKey()+"=>val:"+entry.getValue());				
//				}
//			}
			output("=====================");
				
		}		
		output("input taskcommit <taskid> <outcoming> do task commit" );

	}
}
