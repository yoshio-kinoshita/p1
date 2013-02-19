package entity;

import java.util.Date;

public class Result {

	private String ip;
	private String url;
	private int count;
	private Date firstAccessDate;
	private Date lastAccessDate;
	private int fileNo;

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

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

}
