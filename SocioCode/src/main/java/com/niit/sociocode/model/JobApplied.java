package com.niit.sociocode.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class JobApplied extends BaseDomain {
	@Id
	private int jopAppliedId;
	private String userId, jobId, remarks, status;
	private Date dateApplied;

	public int getJopAppliedId() {
		return jopAppliedId;
	}

	public void setJopAppliedId(int jopAppliedId) {
		this.jopAppliedId = jopAppliedId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

}
