package entity;

import java.util.Date;

public class Result {

	private String ip;
	private String url;
	private int count;
	private Date firstAccessDate;

	public Date getFirstAccessDate() {
		return firstAccessDate;
	}

	public void setFirstAccessDate(Date firstAccessDate) {
		this.firstAccessDate = firstAccessDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
