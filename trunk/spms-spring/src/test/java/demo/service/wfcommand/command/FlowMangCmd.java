package demo.service.wfcommand.command;

import java.util.List;
import java.util.Locale;

import org.jbpm.api.Deployment;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.springframework.context.ApplicationContext;

import com.skynet.common.prop.PropEnum;
import com.skynet.common.prop.PropManager;
import com.skynet.common.prop.PropertyEntity;
import com.skynet.spms.jbpm.entity.FlowHistoryEntity;
import com.skynet.spms.jbpm.manager.WfTaskListManager;
import com.skynet.spms.jbpm.manager.WfTaskOperManager;

import demo.service.wfcommand.WfCmd;
import static demo.service.wfcommand.WfUtils.output;

public class FlowMangCmd implements WfCmd{


	private ExecutionService exeService;
	
	private RepositoryService repositoryService;
	
	private WfTaskListManager taskListMang;
	
	private PropManager messageProp;

	
	public FlowMangCmd(ApplicationContext context) {
		exeService = context.getBean(ExecutionService.class);
		repositoryService=context.getBean(RepositoryService.class);
		taskListMang=context.getBean(WfTaskListManager.class);
		messageProp=context.getBean(PropManager.class);
	}

	@Override
	public void doCommand(String[] input) {
		
		String oper="list";
		if(input.length>0){
			oper=input[0];
		}		
		
		if("list".equals(oper)){
			List<ProcessInstance> procList=exeService.createProcessInstanceQuery().list();
			
			output("proc list");
			for(ProcessInstance inst:procList){
				output("name:"+inst.getName()+" key:"+inst.getKey()+" define id:"+inst.getProcessDefinitionId());
				output("state:"+inst.getState()+ " id:"+inst.getId());
				output("=======================");
			}
		}else if("delete".equals(oper)){
			String procID=input[1];
			exeService.deleteProcessInstance(procID);
		}else if("listdef".equals(oper)){
			
			output("process define list");
			List<ProcessDefinition> procDefList=repositoryService.createProcessDefinitionQuery().list();
			for(ProcessDefinition proc:procDefList){
				output("proc key:"+proc.getKey()+" name:"+proc.getName()+" proc ver:"+proc.getVersion()+" deploy id:"+proc.getDeploymentId());
			}
			output("===================");
						
		}else if("deledef".equals(oper)){
			String deployID=input[1];
			repositoryService.deleteDeploymentCascade(deployID);
		}else if("history".equals(oper)){
			output("flow history");
			String procID=input[1];
			List<FlowHistoryEntity> taskList=taskListMang.getTaskHistoryByFlow(procID);
			PropertyEntity prop=messageProp.getPropEntity(Locale.SIMPLIFIED_CHINESE, PropEnum.WfTaskInfo);
			
			for(FlowHistoryEntity task:taskList){
				task.convertWithProp(prop);
				output("task info:"+task);
			}
			
		}
	}

	@Override
	public String getCmdName() {
		return "flowMang";
	}
	
	
	
}
