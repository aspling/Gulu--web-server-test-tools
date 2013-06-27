package User_Manual_Restful;
import java.util.List;

import org.junit.Test;

import Base.BaseCase;

import com.jcraft.jsch.SftpException;
import com.taobao.gulu.handler.OperationResult;
import com.taobao.gulu.handler.ssh.encrypt.EncryptedPasswords;
import com.taobao.gulu.handler.ssh.filehandler.FileHandlerSFTPImpl;
import com.taobao.gulu.handler.ssh.processhandler.ProcessHandlerExecImpl;
import com.taobao.gulu.tools.OperationException;

public class SSHTest extends BaseCase {

	
//	@Test
//	public void test_getEncryptedPasswords() {
//		System.out.println(Util.getEncryptedPasswords("password"));
//	}

	@Test
	public void test_Process() throws OperationException {
		
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		ProcessHandlerExecImpl process2 = new ProcessHandlerExecImpl(
				encryptedPasswords);
		OperationResult result = process2.executeCmd("10.232.4.29",
				"./sleep.sh ", true);
		System.out.println(result);
				
		int[] id = process2.getPidByProcName("gongyuan.cz", "10.232.4.29",
				"./sleep.sh");
		if (id != null)
			for (int i = 0; i < id.length; i++)
				System.out.println("id : " + id[i]);

		id = process2.getPidByProcName("admin", "10.232.4.29", "./sleep.sh");
		if (id != null)
			for (int i = 0; i < id.length; i++)
				System.out.println("id : " + id[i]);

		result = process2.killProcess("10.232.4.29", "sleep.sh");
		System.out.println("msg : " + result.getMsg());
		System.out.println("exit code : " + result.getReturnCode());
		System.out.println("is success : " + result.isSuccess());
	}
	
	@Test
	public void test_LocalProcess() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"", "");
		ProcessHandlerExecImpl process2 = new ProcessHandlerExecImpl(
				encryptedPasswords);
		OperationResult result = process2.executeCmd("",
				"ipconfig /all", false);
		System.out.println(result);
	}

	@Test
	public void test_Sudo() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		ProcessHandlerExecImpl process2 = new ProcessHandlerExecImpl(
				encryptedPasswords);
		OperationResult result = process2.executeCmdByRoot("10.232.4.29", "ls",
				false);

		System.out.println(result);

		result = process2.executeCmd("10.232.4.29", "./sleep.sh ", true);

		System.out.println(result);
	}

	@Test
	public void test_localhost() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		ProcessHandlerExecImpl process2 = new ProcessHandlerExecImpl(
				encryptedPasswords);
		OperationResult result = process2.executeCmdByRoot("10.232.4.29", "ls",
				false);
		System.out.println(result);
	}

	@Test
	public void test_error() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		ProcessHandlerExecImpl process2 = new ProcessHandlerExecImpl(
				encryptedPasswords);
		OperationResult result = process2.executeCmdByRoot("10.232.4.110",
				"ls", false);
		System.out.println(result);
	}
	
	@Test
	public void test_errorInfo() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		ProcessHandlerExecImpl process2 = new ProcessHandlerExecImpl(
				encryptedPasswords);
		System.out.println(process2.executeCmdByRoot("10.232.4.29",
				"/home/admin/nginx_LSB/sbin/nginx -c xx -s start", false));
	}

	@Test
	public void test_copyFile() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		OperationResult result = fileHandler.copyFile("10.232.4.29",
				"/home/gongyuan.cz/log.log", "10.232.4.31",
				"/home/gongyuan.cz/copyfile_log");
		System.out.println(result);
	}

	@Test
	public void test_copyFolder() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		OperationResult result = fileHandler.copyDirectory("10.232.4.29",
				"/home/gongyuan.cz/sql/", "10.232.4.31",
				"/home/gongyuan.cz/copyfolder");
		System.out.println(result);
	}

	@Test
	public void test_mkdir() {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		if (fileHandler.mkdir("10.232.4.29", "/home/gongyuan.cz/ttttdd")) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}

	}

	@Test
	public void test_listDirectory() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		@SuppressWarnings("rawtypes")
		List list = fileHandler.listDirectory("10.232.4.29",
				"/home/gongyuan.cz/sql/");
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}

	@Test
	public void test_isEntryExisted() throws OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		if (fileHandler.isEntryExisted("10.232.4.29",
				"/home/gongyuan.cz/ttttdd/t")) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}

	@Test
	public void test_deleteEntry() throws SftpException, OperationException {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		System.out.println(fileHandler.copyDirectory("10.232.4.29",
				"/home/gongyuan.cz/sql/", "10.232.4.29",
				"/home/gongyuan.cz/copyfolder"));
		System.out.println(fileHandler.copyDirectory("10.232.4.29",
				"/home/gongyuan.cz/sql/", "10.232.4.29",
				"/home/gongyuan.cz/copyfolder2"));

		System.out.println(fileHandler.deleteEntry("10.232.4.29",
				"/home/gongyuan.cz/sql/t", false));
		System.out.println(fileHandler.deleteEntry("10.232.4.29",
				"/home/gongyuan.cz/copyfolder/", false));
		System.out.println(fileHandler.deleteEntry("10.232.4.29",
				"/home/gongyuan.cz/copyfolder2/", true));

		System.out.println(fileHandler.deleteEntry("10.232.4.29",
				"/home/gongyuan.cz/test.log", true));
	}

	@Test
	public void test_rename1() {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		System.out.println(fileHandler.rename("10.232.4.29",
				"/home/gongyuan.cz/ttttdd/", "/home/gongyuan.cz/copyfolder"));
		System.out.println(fileHandler.rename("10.232.4.29",
				"/home/gongyuan.cz/update_logic_a_read.sql",
				"/home/gongyuan.cz/sql.sql.sql"));

	}

	@Test
	public void test_rename2() {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		System.out.println(fileHandler.rename("10.232.4.29",
				"/home/gongyuan.cz/copyfolder", "/home/gongyuan.cz/ttttdd/"));
		System.out.println(fileHandler.rename("10.232.4.29",
				"/home/gongyuan.cz/sql.sql.sql",
				"/home/gongyuan.cz/update_logic_a_read.sql"));

	}

	@Test
	public void test_setMode() {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		System.out.println(fileHandler.setMode("10.232.4.29",
				"/home/gongyuan.cz/xx.jpg", 7777, true));
		System.out.println(fileHandler.setMode("10.232.4.29",
				"/home/gongyuan.cz/ttttdd", 7000, true));

	}

	@Test
	public void test_setOwner() {
		EncryptedPasswords encryptedPasswords = new EncryptedPasswords(
				"gongyuan.cz", "ar6sA7Eo7InqWEnnnFFqw788FvvOCZ7H");
		FileHandlerSFTPImpl fileHandler = new FileHandlerSFTPImpl(
				encryptedPasswords);
		System.out.println(fileHandler.setOwner("10.232.4.29",
				"/home/gongyuan.cz/xx.jpg", 1, true));
		System.out.println(fileHandler.setOwner("10.232.4.29",
				"/home/gongyuan.cz/ttttdd", 0, true));

	}
}
