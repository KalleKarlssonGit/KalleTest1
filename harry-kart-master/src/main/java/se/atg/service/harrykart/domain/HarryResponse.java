package se.atg.service.harrykart.domain;

import java.util.List;

public class HarryResponse {

	private List<Ranking> ranking;

	public List<Ranking> getRanking() {
		return ranking;
	}

	public void setRanking(List<Ranking> ranking) {
		this.ranking = ranking;
	}

	public HarryResponse(List<Ranking> ranking) {
		super();
		this.ranking = ranking;
	}

	public HarryResponse() {
		super();
	}
}
