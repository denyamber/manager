package com.example.demo.hr.manager.services;

import java.io.IOException;
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
	public ManagerService(@Qualifier("postgress") EmployeeDataManagementInterface managerInterface) {
		this.managerInterface = managerInterface;
	}

	public UUID addEmployee(EmployeeData employee) throws IOException {
		return managerInterface.addEmployee(employee);
	}

	public UUID editEmployee(UUID employeeID, EmployeeData employee) throws IOException {
		return managerInterface.editEmployee(employeeID, employee);
	}

	public void deleteEmployee(UUID employeeID) throws IOException {
		managerInterface.deleteEmployee(employeeID);
	}

	public List<UUID> bulkEdit(@NotEmpty List<EmployeeData> employees) throws IOException {
		List<UUID> updated = new ArrayList<>();
		String errorMessage = "";
		for (EmployeeData person : employees) {
			try {
				updated.add(editEmployee(person.getEmployeeID(), person));
			} catch (IOException e) {
				errorMessage += e.getMessage() + " ";
			}
		}
		if (!errorMessage.isEmpty()) {
			throw new IOException(errorMessage);
		}
		return updated;
	}

	public void bulkDelete(@NotEmpty List<UUID> ids) throws IOException {
		String errorMessage = "";
		for (UUID id : ids) {
			try {
				deleteEmployee(id);
			} catch (IOException e) {
				errorMessage += e.getMessage() + " ";
			}
		}
		if (!errorMessage.isEmpty()) {
			throw new IOException(errorMessage);
		}
	}

	public List<UUID> bulkAdd(@NotEmpty List<EmployeeData> employees) throws IOException {
		ArrayList<UUID> ids = new ArrayList<>();
		String errorMessage = "";
		for (EmployeeData person : employees) {
			try {
				ids.add(addEmployee(person));
			} catch (IOException e) {
				errorMessage += e.getMessage() + " ";
			}
		}
		if (!errorMessage.isEmpty()) {
			throw new IOException(errorMessage);
		}
		return ids;
	}

	public SortedSet<EmployeeData> listAlphabetically() {
		return managerInterface.listAlphabetically();
	}

	public List<EmployeeData> fetchLatestEmployees(int count) {
		return managerInterface.fetchLatestEmployees(count);
	}

	public EmployeeData findEmployee(UUID id) throws IOException {
		return managerInterface.findEmployee(id);
	}

	public void recalculateNetSalaries() throws IOException {
		managerInterface.calculateNetSalary();
	}
}
