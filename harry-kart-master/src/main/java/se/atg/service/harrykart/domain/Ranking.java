package se.atg.service.harrykart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ranking {

	private Integer position;
	private String horseName;

	@JsonIgnore
	private Double totalTime;

	public Ranking() {
		super();
	}

	public Ranking(String horseName) {
		super();
		this.horseName = horseName;
	}

	public Ranking(Integer position, String horseName) {
		super();
		this.position = position;
		this.horseName = horseName;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getHorseName() {
		return horseName;
	}

	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}

	public Double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public String toString() {
		return "Ranking [position=" + position + ", horseName=" + horseName + ", totalTime=" + totalTime + "]";
	}

}
