package com.example.demo.hr.manager.dao;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.hr.manager.model.EmployeeData;

public interface EmployeeDataManagementInterface {

	default public UUID addEmployee(@NotEmpty EmployeeData employee) throws IOException {
		UUID id = UUID.randomUUID();
		employee.setEmployeeID(id);
		return addEmployee(id, employee);
	}

	// returns the employeeID of the added employee;
	public UUID addEmployee(@NotEmpty UUID id, @NotEmpty EmployeeData employee) throws IOException;

	// returns the employeeID of the added employee;
	public UUID editEmployee(@NotEmpty UUID employeeID, @NotEmpty EmployeeData employee);

	default public UUID editEmployee(@NotNull EmployeeData employee) throws IOException {
		UUID id = employee.getEmployeeID();
		if (id != null && findEmployee(id) != null) {
			return editEmployee(id, employee);
		} else {
			return addEmployee(employee);
		}
	}

	// returns the employeeID of the added employee;
	public void deleteEmployee(@NotEmpty UUID employeeID) throws IOException;

	// returns a list with the latest employees
	public List<EmployeeData> fetchLatestEmployees(int count);

	// returns all employees sorted
	public SortedSet<EmployeeData> listAlphabetically();

	public EmployeeData findEmployee(UUID id);

}
