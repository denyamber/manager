package com.example.demo.hr.manager.dao;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.hr.manager.model.EmployeeData;

@Repository("inMemory")
public class InMemoryImplementation implements EmployeeDataManagementInterface {

	private final Map<UUID, EmployeeData> inMemoryDB = Collections.synchronizedMap(new LinkedHashMap<>());

	private final Set<String> employeeNames = new HashSet<>();

	@Override
	public UUID addEmployee(UUID id, EmployeeData employee) throws IOException {
		if (id == null) {
			id = addEmployee(employee);
		}
		String fullName = employee.getFullName();
		if (employeeNames.contains(fullName)) {
			throw new IOException("Employee with name " + employee.getFullName() + " already exists in the system.");
		} else {
			employeeNames.add(fullName);
		}
		employee.setEmployeeID(id);
		inMemoryDB.put(id, employee);
		return id;
	}

	@Override
	public UUID editEmployee(UUID employeeID, EmployeeData employee) {
		inMemoryDB.replace(employeeID, employee);
		return employeeID;
	}

	@Override
	public void deleteEmployee(UUID employeeID) throws IOException {
		String fullName = inMemoryDB.get(employeeID).getFullName();
		inMemoryDB.remove(employeeID);
		employeeNames.remove(fullName);
	}

	@Override
	public List<EmployeeData> fetchLatestEmployees(int count) {
		int size = inMemoryDB.size();
		List<EmployeeData> result = new LinkedList<>();
		Iterator<EmployeeData> dbIterator = inMemoryDB.values().iterator();
		if (size > count) {
			LinkedList<EmployeeData> values = new LinkedList<>();
			values.addAll(inMemoryDB.values());
			values = (LinkedList<EmployeeData>) values.subList(size - count - 1, size - 1);
			dbIterator = values.iterator();
		}
		while (dbIterator.hasNext()) {
			result.add(dbIterator.next());
		}
		return result;
	}

	@Override
	public SortedSet<EmployeeData> listAlphabetically() {
		SortedSet<EmployeeData> sortedSet = new TreeSet<EmployeeData>(new Comparator<EmployeeData>() {
			@Override
			public int compare(EmployeeData o1, EmployeeData o2) {
				int result = o1.getFullName().compareTo(o2.getFullName());
				if (result == 0) {
					result = o1.getEmployeeID().compareTo(o2.getEmployeeID());
				}
				return result;
			}

		});
		sortedSet.addAll(inMemoryDB.values());
		return sortedSet;
	}

	@Override
	public EmployeeData findEmployee(UUID id) {
		return inMemoryDB.get(id);
	}

}
