package epaw.lab4.model;

import java.sql.Timestamp;

public class Like implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int tweetId;
	private int userId;
	private Timestamp timestamp;

	public Like() {
	}

	public Like(int tweetId, int userId) {
		this.tweetId = tweetId;
		this.userId = userId;
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

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
