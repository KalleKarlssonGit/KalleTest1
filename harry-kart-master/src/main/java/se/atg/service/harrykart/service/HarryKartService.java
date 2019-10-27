package se.atg.service.harrykart.service;

import se.atg.service.harrykart.domain.HarryResponse;
import se.atg.service.harrykart.exc.HarryEmptyException;
import se.atg.service.harrykart.exc.HarryServiceException;

public interface HarryKartService {
	public HarryResponse getHarryResponse(String xmlString)  throws HarryServiceException, HarryEmptyException;
}
