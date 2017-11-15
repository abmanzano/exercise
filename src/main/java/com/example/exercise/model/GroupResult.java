package com.example.exercise.model;

public class GroupResult implements Comparable<GroupResult>{
	private String lastname; 
	private Integer adult;
	private Integer children;
	
	public GroupResult() {
		
	}
	
	public GroupResult(final String lastname, final Integer adult, final Integer children) {
		this.lastname = lastname;
		this.adult = adult;
		this.children = children;
	}
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Integer getAdult() {
		return adult;
	}
	public void setAdult(Integer adult) {
		this.adult = adult;
	}
	public Integer getChildren() {
		return children;
	}
	public void setChildren(Integer children) {
		this.children = children;
	}

	@Override
	public int compareTo(GroupResult o) {
		return this.lastname.compareTo(o.getLastname());
	}
}
