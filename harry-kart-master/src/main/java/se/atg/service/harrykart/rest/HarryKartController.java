package se.atg.service.harrykart.rest;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import se.atg.service.harrykart.domain.HarryResponse;
import se.atg.service.harrykart.exc.HarryServiceException;
import se.atg.service.harrykart.exc.ResourceNotFoundException;
import se.atg.service.harrykart.generated.HarryKartType;
import se.atg.service.harrykart.service.HarryKartService;

@RestController
@RequestMapping("/api")
public class HarryKartController {

	@Autowired
	private HarryKartService harryKartService;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
	public @ResponseBody HarryResponse playHarryKart(@RequestBody String xmlString) throws ResourceNotFoundException, HarryServiceException {

		try {
			JAXBContext jc = JAXBContext.newInstance("se.atg.service.harrykart.rest");
			Unmarshaller um = jc.createUnmarshaller();
			JAXBElement<HarryKartType> hkt = (JAXBElement<HarryKartType>) um.unmarshal(new StringReader(xmlString));
			return harryKartService.getHarryResponse(hkt);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new HarryServiceException("Kan ej konvertera xmlen. Jaxb error.");
		}  catch (ResourceNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (HarryServiceException e) {
			throw e;
		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Annat exception i controller controller", e);
		}

		//också runtime här
	}

}
