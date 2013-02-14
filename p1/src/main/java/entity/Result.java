package entity;

import java.util.Date;

public class Result {

	private ResultId resultId;

	private Integer count;
	private Date firstAccessDate;

	public ResultId getResultId() {
		return resultId;
	}

	public void setResultId(ResultId resultId) {
		this.resultId = resultId;
	}

	public Date getFirstAccessDate() {
		return firstAccessDate;
	}

	public void setFirstAccessDate(Date firstAccessDate) {
		this.firstAccessDate = firstAccessDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
