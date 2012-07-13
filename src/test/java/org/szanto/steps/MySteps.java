package org.szanto.steps;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.szanto.registration.Member;
import org.szanto.registration.Member.AGE_BAND;
import org.szanto.registration.RegistrationException;
import org.szanto.registration.RegistrationManager;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@Component
public class MySteps {

	@Autowired
	RegistrationManager registrationManager;
	
	String country;
	
	@Given("I am in <country>")
	public void setCountry(@Named("country")String country) {
		this.country = country;
	}

	@AsParameterConverter
	public AGE_BAND stringToAgeBand(String s) {
		return AGE_BAND.fromString(s);
	}
	
	private Member member = null;
	private String errorMessage = null; 
	
	@When("2 I register $country with age <age>")
	public void test(@Named("country")String country, @Named("age")int age) {
		
	}
	
	@When("I register {a|an} $ageBand with age <age>")
	public void registerMember(@Named("ageBand")AGE_BAND ageBand, @Named("age")int age) {
		Member member = new Member();
		
		GregorianCalendar date = new GregorianCalendar();
        date.add(Calendar.YEAR, - age);
		
		member.setDob(date.getTime());
		
		try {
			this.member = registrationManager.register(ageBand, member, country);
			errorMessage = null; 
		} catch (RegistrationException e) {
			errorMessage = e.getMessage();
			this.member = null;
		}
		
	}
	
	@Then("I get an error message $message")
	public void mustBeChild(String message) {
		assertThat(errorMessage, equalTo(message));
	}
	
	@Then("I get a valid ID")
	public void thenIGetAValidID() {
		assertThat(member.getId(), Matchers.greaterThan(0));
	}	
	
}