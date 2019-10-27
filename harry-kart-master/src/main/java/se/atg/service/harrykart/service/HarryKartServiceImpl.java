package se.atg.service.harrykart.service;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import se.atg.service.harrykart.domain.HarryResponse;
import se.atg.service.harrykart.domain.Ranking;
import se.atg.service.harrykart.exc.HarryEmptyException;
import se.atg.service.harrykart.exc.HarryServiceException;
import se.atg.service.harrykart.generated.HarryKartType;
import se.atg.service.harrykart.generated.LaneType;
import se.atg.service.harrykart.generated.LoopType;
import se.atg.service.harrykart.generated.ParticipantType;

@Service
public class HarryKartServiceImpl implements HarryKartService {

	private static final int TOP = 3;

	private static final Logger logger = Logger.getLogger(HarryKartServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public HarryResponse getHarryResponse(String xmlString) throws HarryServiceException, HarryEmptyException {
		try {
			JAXBElement<HarryKartType> hkt = getXmlAsJavaObjectAndValidate(xmlString);
			return getHarryResponseFromXmlAsJava(hkt);
		}  catch (HarryEmptyException e) {
			e.printStackTrace();
			throw e;
		} catch (HarryServiceException e) {
			e.printStackTrace();
			throw e;
		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unknown exception", e);
		}
	}

	private HarryResponse getHarryResponseFromXmlAsJava(JAXBElement<HarryKartType> hkt) throws HarryEmptyException {

		List<Ranking> allRanking = getAllRanking(
				hkt.getValue().getNumberOfLoops(),
				hkt.getValue().getStartList().getParticipant(),
				hkt.getValue().getPowerUps().getLoop());

		List<Ranking> topXRankingList = getTopX(allRanking);

		if (topXRankingList.size() == 0) {
			logger.error("HarryKartServiceImpl getHarryResponseFromXmlAsJava, no finishers. ts:" + System.currentTimeMillis());
			throw new HarryEmptyException("No finishers.");
		}

		return new HarryResponse(topXRankingList);
	}

	//Java8
	private List<Ranking> getAllRanking(BigInteger numberOfLoops, List<ParticipantType> participantTypeList, List<LoopType> loopTypeList) {
		return
				participantTypeList.stream()
				.map(x -> createRanking(numberOfLoops, x, loopTypeList))
				.sorted(
						(o1, o2)->
							o1 != null && o2 != null?
							o1.getTotalTime().compareTo(o2.getTotalTime())
							:
							o1 != null ? 1 : -1)
				.collect(Collectors.toList());
	}

	public Ranking createRanking(BigInteger numberOfLoops, ParticipantType pt, List<LoopType> loopTypeList) {
		Ranking rank = new Ranking(pt.getName());
		Double horseTotalTime = getHorseTotalTime(numberOfLoops, pt, loopTypeList);
		rank.setTotalTime(horseTotalTime);
		return horseTotalTime != null ? rank : null;
	}

	private List<Ranking> getTopX(List<Ranking> allRanking) {
		List<Ranking> rankingList = new ArrayList<>();
		for (int i = 0; i < allRanking.size(); i++) {
			Ranking tmpRank = allRanking.get(i);
			if (tmpRank == null) {
				continue;
			}
			tmpRank.setPosition(rankingList.size() + 1);
			rankingList.add(tmpRank);
			if (rankingList.size() == TOP) {
				return rankingList;
			}
		}
		return rankingList;
	}

	public Double getHorseTotalTime(BigInteger numberOfLoops, ParticipantType participantType, List<LoopType> loopTypeList) {

		Double timeToCompleteRace = 0.0;

		int speed = participantType.getBaseSpeed().intValue();

		if (speed <= 0) {
			return null;
		}

		for (int k = 1; k <= numberOfLoops.intValue(); k++) {
			for (LoopType loopType : loopTypeList) {
				if ((loopType.getNumber() != null) && (loopType.getNumber().intValue() == k)) {
					List<LaneType> laneTypeList = loopType.getLane();
					for (LaneType laneType : laneTypeList) {
						if (laneType.getNumber() != null && laneType.getNumber().equals(participantType.getLane())) {
							speed += laneType.getValue().intValue();
							if (speed <= 0) {
								return null;
							}
							break;
						}
					}
				}
			}
			timeToCompleteRace += (1000 / (double) speed);
		}

		return timeToCompleteRace;
	}

	@SuppressWarnings("unchecked")
	private JAXBElement<HarryKartType> getXmlAsJavaObjectAndValidate(String xmlString) throws HarryServiceException {

		JAXBElement<HarryKartType> hkt = null;

		try {
			JAXBContext jc = JAXBContext.newInstance("se.atg.service.harrykart.rest");
			Unmarshaller um = jc.createUnmarshaller();
			hkt = (JAXBElement<HarryKartType>) um.unmarshal(new StringReader(xmlString));
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new HarryServiceException("Can not convert xml.");
		}


		if (
				(hkt != null)
				&&
				(hkt.getValue() != null)
				&&
				(hkt.getValue().getNumberOfLoops() != null)
				&&
				(hkt.getValue().getNumberOfLoops().intValue() > 0)
				&&
				(hkt.getValue().getStartList() != null)
				&&
				(hkt.getValue().getStartList().getParticipant() != null)
				&&
				(hkt.getValue().getStartList().getParticipant().size() > 0)
				&&
				(hkt.getValue().getPowerUps() != null)
				&&
				(hkt.getValue().getPowerUps().getLoop() != null)
				&&
				(hkt.getValue().getPowerUps().getLoop().size() > 0)
				) {
		} else {
			logger.error("HarryKartServiceImpl getHarryResponse, Wrong input data. ts:" + System.currentTimeMillis());
			throw new HarryServiceException("Invalid input data. Found inconsistent data");
		}

		return hkt;
	}

}
