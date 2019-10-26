package se.atg.service.harrykart.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBElement;

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

	private static final Logger logger = Logger.getLogger(HarryKartServiceImpl.class);

	@Override
	public HarryResponse getHarryResponse(JAXBElement<HarryKartType> hkt) throws HarryServiceException, HarryEmptyException {

		validate(hkt);

		TreeMap<Double, List<ParticipantType>> calculatedTimes = calculate(hkt);

		List<Ranking> rankingList = getTop3RankingList(calculatedTimes);

		return new HarryResponse(rankingList);
	}

	/**
	 * Get top 3
	 * @param calculatedTimes
	 * @return
	 */
	private List<Ranking> getTop3RankingList(TreeMap<Double, List<ParticipantType>> calculatedTimes) throws HarryServiceException {
		List<Ranking> rankingList = new ArrayList<>();

		for (Map.Entry<Double, List<ParticipantType>> entry : calculatedTimes.entrySet()) {
			for (ParticipantType participantType : entry.getValue()) {
				if (rankingList.size() < 3) {
					rankingList.add(new Ranking((rankingList.size() + 1), participantType.getName()));
				}
			}
		}

		if (rankingList.size() < 1) {
			throw new HarryServiceException("Tom returlista");
		}

		return rankingList;
	}

	/**
	 *
	 * @param hkt
	 * @return Treemap where
	 * 					key: time to complete race.
	 * 					value: list of participants for this key
	 * @throws RuntimeException
	 */
	private TreeMap<Double, List<ParticipantType>> calculate(JAXBElement<HarryKartType> hkt) {

		BigInteger numberOfLoops = hkt.getValue().getNumberOfLoops();

		List<ParticipantType> participantTypeList = hkt.getValue().getStartList().getParticipant();

		List<LoopType> loopTypeList = hkt.getValue().getPowerUps().getLoop();

		TreeMap<Double, List<ParticipantType>> result = new TreeMap<>();

		try {

			for (ParticipantType participantType : participantTypeList) {

				int speed = participantType.getBaseSpeed().intValue();
				if (speed <= 0) {
					continue;
				}

				/*If a horse gets speed 0 or negative, it will never finish the race and is disqualified. */
				boolean disqualifyHorse = false;

				double timeToCompleteRace = 0.0;

				for (int k = 0; k < numberOfLoops.intValue(); k++) {
					for (LoopType loopType : loopTypeList) {
						if ((loopType.getNumber() != null) && (loopType.getNumber().intValue() == k)) {

							List<LaneType> laneTypeList = loopType.getLane();

							for (LaneType laneType : laneTypeList) {
								if (laneType.getNumber() != null && laneType.getNumber().equals(participantType.getLane())) {
									speed += laneType.getValue().intValue();

									if (speed <= 0) {
										disqualifyHorse = true;
									}

									break;
								}

							}

							if (disqualifyHorse) {
								break;
							}

						}

					}

					if (!disqualifyHorse) {
						timeToCompleteRace += (1000 / (double)speed);
					}

				}

				if (!disqualifyHorse) {
					if (result.get(timeToCompleteRace) != null) {
						/* More than one participant can have the same total time. */
						List<ParticipantType> value = result.get(timeToCompleteRace);
						value.add(participantType);
						result.put(timeToCompleteRace, value);
					} else {
						List<ParticipantType> value = new ArrayList<ParticipantType>();
						value.add(participantType);
						result.put(timeToCompleteRace, value);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("HarryKartServiceImpl calculate, exception. ts:" + System.currentTimeMillis() + ", message:" + e.getMessage(), e);
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * Ensure all data exists.
	 * @param hkt
	 * @return
	 */
	private void validate(JAXBElement<HarryKartType> hkt) throws HarryServiceException {
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
			// throws HarryServiceException, ResourceNotFoundException
//			if () {
				logger.error("HarryKartServiceImpl getHarryResponse, Wrong input data. ts:" + System.currentTimeMillis());
				throw new HarryServiceException("Invalid input data.");
//			}
		}

	}

}
