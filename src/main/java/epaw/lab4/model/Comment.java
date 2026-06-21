package epaw.lab4.model;

import java.sql.Timestamp;

public class Comment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int tweetId;
	private int userId;
	private String userName;
	private String userPicture;
	private String content;
	private Timestamp timestamp;

	public Comment() {
	}

	public Comment(int tweetId, int userId, String userName, String content) {
		this.tweetId = tweetId;
		this.userId = userId;
		this.userName = userName;
		this.content = content;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTweetId() {
		return this.tweetId;
	}

	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPicture() {
		return this.userPicture;
	}

	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
