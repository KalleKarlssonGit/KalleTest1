package se.atg.service.harrykart.service;

import se.atg.service.harrykart.domain.HarryResponse;
import se.atg.service.harrykart.exception.HarryEmptyException;
import se.atg.service.harrykart.exception.HarryServiceException;

public interface HarryKartService {
	public HarryResponse getHarryResponse(String xmlString)  throws HarryServiceException, HarryEmptyException;
}
