package se.atg.service.harrykart.service;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import se.atg.service.harrykart.domain.HarryResponse;
import se.atg.service.harrykart.exception.HarryEmptyException;
import se.atg.service.harrykart.exception.HarryServiceException;
import se.atg.service.harrykart.generated.HarryKartType;
import se.atg.service.harrykart.generated.LoopType;
import se.atg.service.harrykart.generated.ParticipantType;

public interface HarryKartService {

	public HarryResponse getHarryResponse(String xmlString)  throws HarryServiceException, HarryEmptyException;

	/* Added for testing purposes. */
	public HarryResponse getHarryResponseFromXmlAsJava(JAXBElement<HarryKartType> hkt) throws HarryEmptyException;

	/* Added for testing purposes. */
	public Double getHorseTotalTime(BigInteger numberOfLoops, ParticipantType participantType, List<LoopType> loopTypeList);

}
