package com.example.demo.hr.manager.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.hr.manager.model.EmployeeData;

@Repository("inMemory")
public class InMemoryImplementation implements EmployeeDataManagementInterface {

	private final Map<UUID, EmployeeData> inMemoryDB = Collections.synchronizedMap(new LinkedHashMap<>());

	@Override
	public UUID addEmployee(UUID id, EmployeeData employee) {
		if (id == null) {
			id = addEmployee(employee);
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
	public void deleteEmployee(UUID employeeID) {
		inMemoryDB.remove(employeeID);
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
				return o1.getFullName().compareTo(o2.getFullName());
			}

		});
		sortedSet.addAll(inMemoryDB.values());
		return sortedSet;
	}

}
