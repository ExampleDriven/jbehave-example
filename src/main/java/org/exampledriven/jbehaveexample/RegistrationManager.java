package org.exampledriven.jbehaveexample;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.exampledriven.jbehaveexample.Member.AGE_BAND;
import org.springframework.stereotype.Service;

@Service
public class RegistrationManager {

	int id = 1;
	
	public Member register (AGE_BAND ageBand, Member member, String country) throws RegistrationException {
		
		if (AGE_BAND.TEEN.equals(ageBand)) {
			
			int limit = 99;
			
			if ("UK".equals(country)) {
				limit = 18;
			} else if ("FR".equals(country)) {
				limit = 19;
			} 

			GregorianCalendar teenagerBirthDate = new GregorianCalendar();
            teenagerBirthDate.add(Calendar.YEAR, - limit);
			
			if (member.getDob().before(teenagerBirthDate.getTime())) {
				throw new RegistrationException("You must be a teen");
			}
		}
		
		member.setOriginatingCountry(country);

		member.setId(id++);
		
		return member;
	} 
}
