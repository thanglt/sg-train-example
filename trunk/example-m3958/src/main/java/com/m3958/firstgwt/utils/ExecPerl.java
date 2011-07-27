package com.m3958.firstgwt.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.inject.Inject;
import com.m3958.firstgwt.service.SiteConfigService;


public class ExecPerl {
	
	@Inject
	private SiteConfigService scs;
	
	private StringBuffer results;

	private int exitValue = 0;
	
	private String[] cmd;

	
	public void exec(String perlFileName,String assetPath,String imgFileName,String...paras){
		try {
			results = new StringBuffer();
			cmd = new String[paras.length + 4];
			cmd[0] = scs.getPerlExec();
			cmd[1] = perlFileName;
			cmd[2] = assetPath;
			cmd[3] = imgFileName;
			for(int i = 0;i < paras.length;i++){
				cmd[i+4] = paras[i];
			}
			
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = proc.getInputStream();
			BufferedReader bufferedRreader = new BufferedReader(
					new InputStreamReader(inputStream));
			// save next lines
			String outputLine;
			while ((outputLine = bufferedRreader.readLine()) != null) {
				results.append(outputLine);
			}
			// Always reading STDOUT first, then STDERR, exitValue last
			proc.waitFor(); // wait for reading STDOUT and STDERR over
			setExitValue(proc.exitValue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	public int getExitValue() {
		return exitValue;
	}
	
	public StringBuffer getResults() {
		return results;
	}

	public void setResults(StringBuffer results) {
		this.results = results;
	}
}