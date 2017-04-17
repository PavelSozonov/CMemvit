/**
 * 
 */
package ru.innopolis.lips.memvit.service;

/**
 * Class for communication with Bugzilla
 * 
 * @author Pavel Sozonov
 */
public class BugReporter {

	private String token;
	private String userName;
	private String password;
	private String serverAddress;

	public BugReporter(String userName, String password, String serverAddress) {
		this.userName = userName;
		this.password = password;
		this.serverAddress = serverAddress;
	}

	/**
	 * Function main is used for check class functions without plugin running
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public String requestToken() {

		return null;
	}

}
