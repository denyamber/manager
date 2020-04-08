package com.example.demo.hr.manager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.hr.manager.dao.EmployeeDataManagementInterface;
import com.example.demo.hr.manager.model.EmployeeData;

@Service
public class ManagerService {

	private final EmployeeDataManagementInterface managerInterface;

	@Autowired
	public ManagerService(@Qualifier("inMemory") EmployeeDataManagementInterface managerInterface) {
		this.managerInterface = managerInterface;
	}

	public UUID addEmployee(EmployeeData employee) {
		return managerInterface.addEmployee(employee);
	}

	public UUID editEmployee(UUID employeeID, EmployeeData employee) {
		return managerInterface.editEmployee(employeeID, employee);
	}

	public void deleteEmployee(UUID employeeID) {
		managerInterface.deleteEmployee(employeeID);
	}

	public List<UUID> bulkEdit(@NotEmpty List<EmployeeData> employees) {
		List<UUID> updated = new ArrayList<>();
		for (EmployeeData person : employees) {
			updated.add(editEmployee(person.getEmployeeID(), person));
		}
		return updated;
	}

	public void bulkDelete(@NotEmpty List<UUID> ids) {
		for (UUID id : ids) {
			deleteEmployee(id);
		}
	}

	public List<UUID> bulkAdd(@NotEmpty List<EmployeeData> employees) {
		ArrayList<UUID> ids = new ArrayList<>();
		for (EmployeeData person : employees) {
			ids.add(addEmployee(person));
		}
		return ids;
	}

	public SortedSet<EmployeeData> listAlphabetically() {
		return managerInterface.listAlphabetically();
	}

	public List<EmployeeData> fetchLatestEmployees(int count) {
		return managerInterface.fetchLatestEmployees(count);
	}
}
