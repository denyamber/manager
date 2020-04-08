package com.example.demo.hr.manager.dao;

import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.hr.manager.model.EmployeeData;

public interface EmployeeDataManagementInterface {

	default public UUID addEmployee(@NotEmpty EmployeeData employee) {
		UUID id = UUID.randomUUID();
		employee.setEmployeeID(id);
		return addEmployee(id, employee);
	}

	// returns the employeeID of the added employee;
	public UUID addEmployee(@NotEmpty UUID id, @NotEmpty EmployeeData employee);

	// returns the employeeID of the added employee;
	public UUID editEmployee(@NotEmpty UUID employeeID, @NotEmpty EmployeeData employee);

	public default UUID editEmployee(@NotNull EmployeeData employee) {
		if (employee.getEmployeeID() != null) {
			return editEmployee(employee.getEmployeeID(), employee);
		} else {
			return addEmployee(employee);
		}
	}

	// returns the employeeID of the added employee;
	public void deleteEmployee(@NotEmpty UUID employeeID);

	// returns a list with the latest employees
	public List<EmployeeData> fetchLatestEmployees(int count);

	// returns all employees sorted
	public SortedSet<EmployeeData> listAlphabetically();
}
