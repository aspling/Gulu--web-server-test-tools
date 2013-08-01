package com.taobao.gulu.server;

import java.util.ArrayList;

import com.taobao.gulu.handler.ssh.encrypt.EncryptedPasswords;
import com.taobao.gulu.handler.ssh.filehandler.FileHandlerSFTPImpl;
import com.taobao.gulu.handler.ssh.processhandler.ProcessHandlerExecImpl;

public class NginxServerDeploy {

	private String workspaces = "";
	private String projectName = "";
	private ArrayList<String> modulesInfo = new ArrayList<String>();
	private String nginxSrcCode;
	private String username = "";
	private String password = "";
//	private String svnUserName = "";
//	private String svnPassWord = "";
	private String host = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public String getSvnUserName() {
//		return svnUserName;
//	}
//
//	public void setSvnUserName(String svnUserName) {
//		this.svnUserName = svnUserName;
//	}
//
//	public String getSvnPassWord() {
//		return svnPassWord;
//	}
//
//	public void setSvnPassWord(String svnPassWord) {
//		this.svnPassWord = svnPassWord;
//	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getWorkspaces() {
		return workspaces;
	}

	public void setWorkspaces(String workspaces) {
		this.workspaces = workspaces;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ArrayList<String> getModulesInfo() {
		return modulesInfo;
	}

	public void setModulesInfo(ArrayList<String> modulesInfo) {
		this.modulesInfo = modulesInfo;
	}

	public String getNginxSrcCode() {
		return nginxSrcCode;
	}

	public void setNginxSrcCode(String nginxSrcCode) {
		this.nginxSrcCode = nginxSrcCode;
	}

	public void deployNginxServerFromSRC() {
		
	}

//	private void downloadSourceCodeFromSVN(String ) throws Exception {
//		//create workspace and project dir
//		FileHandlerSFTPImpl fileHandler = getFileHandler();
//		if(!fileHandler.isEntryExisted(host, workspaces))
//			fileHandler.mkdir(host, workspaces);
//		
//		if(!fileHandler.isEntryExisted(host, workspaces + "/" + projectName))
//			fileHandler.mkdir(host, workspaces + "/" + projectName);
//		
//		ProcessHandlerExecImpl processHandle = getProcessHandler();
//		// download nginx src code
//			String downloadNginxSrcCode = "svn co " + nginxSrcCode + " " + workspaces + "/" + projectName + "/nginxSrcCode --username " + svnUserName + " --password " + svnPassWord + " --non-interactive";
//			String execResult = processHandle.executeCmd(host, downloadNginxSrcCode, false).getMsg();
//			if(!execResult.contains("Checked out revision")){
//				throw new FailedHandle("check out nginx src code failed");
//			}
//	}

	private ProcessHandlerExecImpl getProcessHandler() {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				username, password);
		ProcessHandlerExecImpl process = new ProcessHandlerExecImpl(
				encryptedPasswords);
		return process;
	}

	private FileHandlerSFTPImpl getFileHandler() {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				username, password);
		FileHandlerSFTPImpl sftp = new FileHandlerSFTPImpl(encryptedPasswords);
		return sftp;
	}
}
