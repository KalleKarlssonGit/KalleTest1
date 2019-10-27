package se.atg.service.harrykart.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import se.atg.service.harrykart.domain.HarryResponse;
import se.atg.service.harrykart.exc.HarryEmptyException;
import se.atg.service.harrykart.exc.HarryServiceException;
import se.atg.service.harrykart.service.HarryKartService;

@RestController
@RequestMapping("/api")
public class HarryKartController {

	@Autowired
	private HarryKartService harryKartService;

	@RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
	public @ResponseBody HarryResponse playHarryKart(@RequestBody String xmlString) throws HarryEmptyException, HarryServiceException {
		return harryKartService.getHarryResponse(xmlString);
	}

}
