package org.exampledriven.jbehaveexample.steps;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.exampledriven.jbehaveexample.Member;
import org.exampledriven.jbehaveexample.RegistrationException;
import org.exampledriven.jbehaveexample.RegistrationManager;
import org.exampledriven.jbehaveexample.Member.AGE_BAND;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.OutcomesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
	private List<Member> memberList = new LinkedList<Member>();
	private String errorMessage = null; 
	
	@When("I register {a|an} $ageBand with age <age>")
	public void registerMember(@Named("ageBand")AGE_BAND ageBand, @Named("age")int age) {
		Member member = new Member();
		
		GregorianCalendar date = new GregorianCalendar();
        date.add(Calendar.YEAR, - age);
		
		member.setDob(date.getTime());
		
		try {
			this.member = registrationManager.register(ageBand, member, country);
			memberList.add(member);
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
	
	@Then("The registered members will have the following ID and originating country $table")
	public void checkMemberId(ExamplesTable inputTable) {
		
		OutcomesTable outcomes = new OutcomesTable();
		
		for (int i = 0; i < memberList.size(); i++) {
			Map<String, String> row = inputTable.getRow(i);
			
			outcomes.addOutcome("Member ID " + i, ""+memberList.get(i).getId(), equalTo(row.get("ID")));
			outcomes.addOutcome("Originating country" + i, ""+memberList.get(i).getOriginatingCountry(), equalTo(row.get("country")));
			
		}
		
		outcomes.verify();
	}
	
}