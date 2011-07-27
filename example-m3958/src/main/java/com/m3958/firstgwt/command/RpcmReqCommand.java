package com.m3958.firstgwt.command;

import java.io.IOException;

import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.service.LgbExcelExport;
import com.m3958.firstgwt.service.LgbJasperExport;

public class RpcmReqCommand extends BaseCommand  implements Command{
	
	
	
	@Override
	public void execute() throws SmartJpaException {
		SmartSubOperationName sson = SmartSubOperationName.NO_SUB_OPERATION;
		if(reqPs.getSubOpType() != null)
			sson = SmartSubOperationName.valueOf(reqPs.getSubOpType());
		
		switch (sson) {
		case EXPORT_LGB_TO_EXCEL:
			exportLgbToExcel();
			break;
		case EXPORT_LGB_TO_PDF:
			exportLgbToPdf();
			break;
		default:
			;
		}
	}

	private void exportLgbToPdf() {
		LgbJasperExport lje = injector.getInstance(LgbJasperExport.class);
		lje.execute(reqPs,res);
	}

	private void exportLgbToExcel() {
		LgbExcelExport lec = injector.getInstance(LgbExcelExport.class);
		lec.createExcelToBaos(reqPs,res);
	}

	@Override
	public String getResult() {
		return null;
	}

	@Override
	public void execute(boolean writeResponse) throws SmartJpaException,
			IOException {
		// TODO Auto-generated method stub
		
	}

}
