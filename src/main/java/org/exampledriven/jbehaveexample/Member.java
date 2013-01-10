package org.exampledriven.jbehaveexample;

import java.util.Date;

public class Member {
	
	public enum AGE_BAND { CHILD("child"), TEEN("teen"), ADULT("adult");
		
		String name;
		AGE_BAND(String s) {
			name = s;
		}

		public static AGE_BAND fromString(String s) {
			for (AGE_BAND a : AGE_BAND.values()) {
				if (a.name.equals(s)) {
					return a;
				}
			}
			
			return null;
		}
		
	} 
	
	private String firstName;
	private int id;
	private Date dob;
	private String originatingCountry;

	public String getFirstName() {
		
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}
	

}
