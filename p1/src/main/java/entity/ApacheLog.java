package entity;
import java.util.Date;

public class ApacheLog {

	private ApacheLogId apacheLogId;
	private Date accessDate;

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public ApacheLogId getApacheLogId() {
		return apacheLogId;
	}

	public void setApacheLogId(ApacheLogId apacheLogId) {
		this.apacheLogId = apacheLogId;
	}
}
