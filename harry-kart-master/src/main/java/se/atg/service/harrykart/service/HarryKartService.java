package se.atg.service.harrykart.service;

import javax.xml.bind.JAXBElement;

import se.atg.service.harrykart.domain.HarryResponse;
import se.atg.service.harrykart.generated.HarryKartType;

public interface HarryKartService {

	public HarryResponse getHarryResponse(JAXBElement<HarryKartType> hkt) throws RuntimeException;

}
