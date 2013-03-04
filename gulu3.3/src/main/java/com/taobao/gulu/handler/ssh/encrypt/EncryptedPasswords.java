package com.taobao.gulu.handler.ssh.encrypt;


/**
 * <p>Title: EncryptedPasswords.java</p>
 * <p>Description: keep the user name and encrypted passwords</p>
 * @author: gongyuan.cz
 * @email:  gongyuan.cz@taobao.com
 * @blog:   100continue.iteye.com
 */
public class EncryptedPasswords {
	private String username = null;
	private String encryptedPasswords = null;

	public EncryptedPasswords() {
		super();
	}
	
	public EncryptedPasswords(String username, String encryptedPasswords) {
		this.username = username;
		this.encryptedPasswords = encryptedPasswords;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncryptedPasswords() {
		return encryptedPasswords;
	}

	public void setEncryptedPasswords(String encryptedPasswords) {
		this.encryptedPasswords = encryptedPasswords;
	}

	
}
