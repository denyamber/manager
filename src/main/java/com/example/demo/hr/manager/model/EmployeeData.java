package com.example.demo.hr.manager.model;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class EmployeeData {
	@NotEmpty
	@JsonProperty("first_name")
	private String firstName;
	@NotEmpty
	@JsonProperty("last_name")
	private String lastName;
	@NotEmpty
	@JsonProperty("address")
	private String address;
	@JsonProperty("country")
	private String country;
	@JsonProperty("postal_code")
	private String postalCode;
	@JsonProperty("phone")
	private String phoneNumber;
	@NotEmpty
	@JsonProperty("gross")
	private double grossSalary;
	@NotEmpty
	@JsonProperty("taxes")
	private double percentOfTaxes;
	@JsonProperty("net_salary")
	private double netSalary;
	@JsonProperty("id")
	private UUID employeeID;

	public String getFirstName() {
		return firstName;
	}

	public UUID getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(UUID employeeID) {
		this.employeeID = employeeID;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public double getPercentOfTaxes() {
		return percentOfTaxes;
	}

	public void setPercentOfTaxes(double percentOfTaxes) {
		this.percentOfTaxes = percentOfTaxes;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public double getNetSalary() {
		return this.netSalary;
	}

	public void recalculateNetSalary() {
		this.netSalary = calculateNetSalaryFromGrossSalary(this.grossSalary, this.percentOfTaxes);
	}

	public static double calculateNetSalaryFromGrossSalary(double fromGrossAmount, double withTaxPercentige) {
		double moneyForTaxes = fromGrossAmount * withTaxPercentige / 100d;
		return fromGrossAmount - moneyForTaxes;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
}
