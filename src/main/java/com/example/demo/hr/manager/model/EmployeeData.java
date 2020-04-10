package com.example.demo.hr.manager.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
@EntityListeners(AuditingEntityListener.class)
@Table(appliesTo = "employees")
public class EmployeeData {
	@NotEmpty
	@JsonProperty("first_name")
	@Column(name = "first_name")
	private String firstName;
	@NotEmpty
	@JsonProperty("last_name")
	@Column(name = "last_name")
	private String lastName;
	@JsonProperty("address")
	@Column(name = "address")
	private String address;
	@JsonProperty("country")
	@Column(name = "country")
	private String country;
	@JsonProperty("postal_code")
	@Column(name = "postal_code")
	private String postalCode;
	@JsonProperty("phone_number")
	@Column(name = "phone_number")
	private String phoneNumber;
	@NotEmpty
	@JsonProperty("gross_salary")
	@Column(name = "gross_salary")
	private double grossSalary;
	@NotEmpty
	@JsonProperty("taxes")
	@Column(name = "taxes")
	private double percentOfTaxes;
	@JsonProperty("net_salary")
	@Column(name = "net_salary")
	private double netSalary;
	@JsonProperty("id")
	@Column(name = "id", nullable = false, unique = true)
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
