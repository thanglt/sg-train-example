package demo.service.wfcommand.command;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;
import org.springframework.context.ApplicationContext;

import com.skynet.spms.client.entity.CommonApproveParam;
import com.skynet.spms.jbpm.WorkflowEnum;
import com.skynet.spms.jbpm.entity.WfInstParamCols;
import com.skynet.spms.jbpm.manager.WfTaskOperManager;
import com.skynet.spms.service.UUIDGeneral;

import demo.service.wfcommand.WfCmd;
import static demo.service.wfcommand.WfUtils.output;

public class StartProcCmd implements WfCmd {

	private WfTaskOperManager taskManager;
	
	private UUIDGeneral uuidGeneral;

	public StartProcCmd(ApplicationContext context) {
		taskManager = context.getBean(WfTaskOperManager.class);
		uuidGeneral=context.getBean(UUIDGeneral.class);
	}

	@Override
	public String getCmdName() {
		return "startCommApproval";
	}
	
	/*
	 * startCommApproval amount=4000 user=sale_3 manager=SalesSupervisor

	 */
	
	/*
	 * (non-Javadoc)
	 * @see demo.service.wfcommand.WfCmd#doCommand(java.lang.String[])
	 */

	@Override
	public void doCommand(String[] input) {
		WorkflowEnum type=WorkflowEnum.common_approval;

		WfInstParamCols paramCols=new WfInstParamCols();
		CommonApproveParam wfParam=new CommonApproveParam();
		for(String param:input){
			String key=StringUtils.substringBefore(param, "=");
			String val=StringUtils.substringAfter(param,"=");
			
			if("amount".equals(key)){
				wfParam.setAmount(NumberUtils.toFloat(val));
			}else if("user".equals(key)){
				wfParam.setBusinessUser(val);
			}else if("manager".equals(key)){
				wfParam.setManagerForType(val);
			}
		}
		paramCols.addInstWfParam("param", wfParam);
				
		String businessKey=uuidGeneral.getSequence("wfTest");
		
		ProcessInstance proc = taskManager.
			startProcessInstanceWithKeyAndVariables(type, businessKey, paramCols);

		output("process " + proc.getName() + " been start,inst id:"
				+ proc.getId());
		output("input tasklist <userid> query user's tasklist");

	}
	


}
