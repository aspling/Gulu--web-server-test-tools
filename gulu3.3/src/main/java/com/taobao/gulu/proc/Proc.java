//package com.taobao.gulu.proc;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import org.apache.log4j.Logger;
//import ch.ethz.ssh2.ChannelCondition;
//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.Session;
//import com.taobao.gulu.server.UserAuthentication;
//import com.taobao.gulu.tools.ComparisonFailureHandle;
//
///**
// * <p>
// * Title: HelpProc.java
// * </p>
// * <p>
// * Description: remote command execute tool
// * </p>
// * 
// * @author: gongyuan.cz
// * @email: gongyuan.cz@taobao.com
// * @blog: 100continue.iteye.com
// */
//public class Proc {
//	private static Logger logger = Logger.getLogger(Proc.class);
//
//	private static Connection conn;
//	private static Session session;
//
//	public static void executeRemoteCommand(UserAuthentication server, String cmd,
//			String expectStdout, String expectStderr) throws Exception {
//
//		doExecuteCommand(server, cmd);
//		verifySessionInfo(session, expectStdout, expectStderr);
//		doStopExecute();
//	}
//
//	public static String executeRemoteCommandWithReturn(UserAuthentication server,
//			String cmd, String expectStdout) throws Exception {
//
//		doExecuteCommand(server, cmd);
//		StringBuffer sb = verifySessionInfo(session, expectStdout);
//		doStopExecute();
//
//		return sb.toString();
//	}
//
//	public static void executeRemoteCommandWithDumbPTY(UserAuthentication server,
//			String cmd, String expectStdout, String expectStderr)
//			throws Exception {
//
//		doExecuteCommandWithPTY(server, cmd);
//		verifySessionInfo(session, expectStdout, expectStderr);
//		doStopExecute();
//	}
//
//	public static void executeRemoteCommandNotEQ(UserAuthentication server,
//			String cmd, String expectStdout) throws Exception {
//
//		doExecuteCommand(server, cmd);
//		verifySessionInfoNotEQ(session, expectStdout);
//		doStopExecute();
//	}
//
//	public static void executeRemoteCommandNotEQ(UserAuthentication server,
//			String cmd, String expectStdout, String expectStderr)
//			throws Exception {
//
//		doExecuteCommand(server, cmd);
//		verifySessionInfoNotEQ(session, expectStdout, expectStderr);
//		doStopExecute();
//	}
//
//	public static void executeCommand(String expectStdout,
//			String expectStderr, String...cmd) throws Exception {
//		ProcessBuilder builder = new ProcessBuilder(cmd);
//		Process p = builder.start();
//		verifySessionInfo(p, expectStdout, expectStderr);
//		logger.info(builder.command().toString() + " exit_code=" + p.waitFor());
//	}
//	
//
//	/**
//	 * <p>
//	 * Title: initSession
//	 * </p>
//	 * <p>
//	 * Description: init session from client addr to server addr
//	 * </p>
//	 * 
//	 * @param server
//	 * @throws Exception
//	 */
//	private static void initSession(UserAuthentication server) throws Exception {
//		conn = new Connection(server.getHost());
//		conn.connect();
//		boolean success = conn.authenticateWithPassword(server.getUsername(),
//				server.getPassword());
//		if (success) {
//			logger.info("initialize session SUCCESS");
//		} else {
//			logger.info("initialize session FAIL, Host: " + server.getHost());
//			throw new Exception("initialize session FAIL");
//		}
//		session = conn.openSession();
//	}
//
//	private static void doExecuteCommand(UserAuthentication server, String cmd)
//			throws Exception {
//		logger.info("execute remote command with result");
//		initSession(server);
//		logger.info("execute remote command : " + cmd);
//		session.execCommand(cmd);
//	}
//
//	private static void doExecuteCommandWithPTY(UserAuthentication server, String cmd)
//			throws Exception {
//		logger.info("execute remote command with dumb PTY");
//		initSession(server);
//		logger.info("execute remote command : " + cmd);
//		session.requestDumbPTY();
//		session.execCommand(cmd);
//	}
//
//	private static void doStopExecute() {
//		session.waitForCondition(ChannelCondition.EXIT_STATUS, 0);
//		int status = session.getExitStatus();
//		logger.info("exit_code : " + status);
//		session.close();
//		conn.close();
//	}
//
//	/**
//	 * <p>
//	 * Title: verifySessionInfo
//	 * </p>
//	 * <p>
//	 * Description: verify session output and error output with expect info
//	 * </p>
//	 * 
//	 * @param session
//	 * @param expect
//	 * @return
//	 * @throws Exception
//	 */
//	private static StringBuffer verifySessionInfo(Session session,
//			String... expect) throws Exception {
//
//		BufferedReader brStdout = new BufferedReader(new InputStreamReader(
//				session.getStdout()));
//
//		String readStdout = brStdout.readLine();
//		StringBuffer stdout = new StringBuffer();
//		while (readStdout != null) {
//			stdout.append(readStdout);
//			logger.info(readStdout);
//			readStdout = brStdout.readLine();
//		}
//		if (stdout.toString().contains(expect[0])) {
//			logger.info("output results are consistent with expectations");
//		} else {
//			logger.info("output results are inconsistent with expectations");
//
//			session.waitForCondition(ChannelCondition.EXIT_STATUS, 0);
//			int status = session.getExitStatus();
//			logger.info("exit_code : " + status);
//			session.close();
//			conn.close();
//			throw new ComparisonFailureHandle("verify command execute output",
//					expect[0], stdout.toString());
//		}
//
//		if (expect.length > 1) {
//			BufferedReader brStderr = new BufferedReader(new InputStreamReader(
//					session.getStderr()));
//			String readStderr = brStderr.readLine();
//			StringBuffer stderr = new StringBuffer();
//			while (readStderr != null) {
//				stderr.append(readStderr);
//				logger.info(readStderr);
//				readStderr = brStderr.readLine();
//			}
//			if (stderr.toString().contains(expect[1])) {
//				logger.info("output results are consistent with expectations");
//			} else {
//				logger.info("output results are inconsistent with expectations");
//
//				session.waitForCondition(ChannelCondition.EXIT_STATUS, 0);
//				int status = session.getExitStatus();
//				logger.info("exit_code : " + status);
//				session.close();
//				conn.close();
//				throw new ComparisonFailureHandle(
//						"verify command execute error output", expect[1],
//						stderr.toString());
//			}
//		}
//		return stdout;
//	}
//
//	private static StringBuffer verifySessionInfo(Process process,
//			String... expect) throws Exception {
//
//		BufferedReader brStdout = new BufferedReader(new InputStreamReader(
//				process.getInputStream()));
//
//		String readStdout = brStdout.readLine();
//		StringBuffer stdout = new StringBuffer();
//		while (readStdout != null) {
//			stdout.append(readStdout);
//			logger.info(readStdout);
//			readStdout = brStdout.readLine();
//		}
//		if (stdout.toString().contains(expect[0])) {
//			logger.info("output results are consistent with expectations");
//		} else {
//			logger.info("output results are inconsistent with expectations");
//			logger.info("exit_code : " + process.waitFor());
//			throw new ComparisonFailureHandle("verify command execute output",
//					expect[0], stdout.toString());
//		}
//
//		if (expect.length > 1) {
//			BufferedReader brStderr = new BufferedReader(new InputStreamReader(
//					process.getErrorStream()));
//			String readStderr = brStderr.readLine();
//			StringBuffer stderr = new StringBuffer();
//			while (readStderr != null) {
//				stderr.append(readStderr);
//				logger.info(readStderr);
//				readStderr = brStderr.readLine();
//			}
//			if (stderr.toString().contains(expect[1])) {
//				logger.info("output results are consistent with expectations");
//			} else {
//				logger.info("output results are inconsistent with expectations");
//				logger.info("exit_code : " + process.waitFor());
//				throw new ComparisonFailureHandle(
//						"verify command execute error output", expect[1],
//						stderr.toString());
//			}
//		}
//		return stdout;
//	}
//
//	private static void verifySessionInfoNotEQ(Session session,
//			String... expect) throws Exception {
//
//		BufferedReader brStdout = new BufferedReader(new InputStreamReader(
//				session.getStdout()));
//
//		String readStdout = brStdout.readLine();
//		StringBuffer stdout = new StringBuffer();
//		while (readStdout != null) {
//			stdout.append(readStdout);
//			logger.info(readStdout);
//			readStdout = brStdout.readLine();
//		}
//		if (!stdout.toString().contains(expect[0])) {
//			logger.info("output results are inconsistent with expectations");
//		} else {
//			logger.info("output results are consistent with expectations");
//
//			session.waitForCondition(ChannelCondition.EXIT_STATUS, 0);
//			int status = session.getExitStatus();
//			logger.info("exit_code : " + status);
//			session.close();
//			conn.close();
//			throw new ComparisonFailureHandle("verify command execute output",
//					expect[0], stdout.toString());
//		}
//
//		if (expect.length > 1) {
//			BufferedReader brStderr = new BufferedReader(new InputStreamReader(
//					session.getStderr()));
//			String readStderr = brStderr.readLine();
//			StringBuffer stderr = new StringBuffer();
//			while (readStderr != null) {
//				stderr.append(readStderr);
//				logger.info(readStderr);
//				readStderr = brStderr.readLine();
//			}
//			if (!stderr.toString().contains(expect[1])) {
//				logger.info("output error info are inconsistent with expectations");
//			} else {
//				logger.info("output error info are consistent with expectations");
//
//				session.waitForCondition(ChannelCondition.EXIT_STATUS, 0);
//				int status = session.getExitStatus();
//				logger.info("exit_code : " + status);
//				session.close();
//				conn.close();
//				throw new ComparisonFailureHandle(
//						"verify command execute error output", expect[1],
//						stderr.toString());
//			}
//		}
//	}
//}
