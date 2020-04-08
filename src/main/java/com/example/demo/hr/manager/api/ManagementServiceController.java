package com.example.demo.hr.manager.api;

import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.hr.manager.model.EmployeeData;
import com.example.demo.hr.manager.services.ManagerService;

@RequestMapping("/api/v1/manager")
@RestController
public class ManagementServiceController {
	private final ManagerService service;

	@Autowired
	public ManagementServiceController(ManagerService service) {
		this.service = service;
	}

	@PostMapping(path = "/add")
	public UUID addEmployee(@RequestBody EmployeeData employee) {
		return service.addEmployee(employee);
	}

	@PostMapping(path = "/bulkAdd")
	public List<UUID> bulkAdd(@RequestBody List<EmployeeData> employees) {
		return service.bulkAdd(employees);
	}

	@PostMapping(path = "/edit/{id}")
	public UUID editEmployee(@PathVariable("id") UUID employeeID, @RequestBody EmployeeData employee) {
		return service.editEmployee(employeeID, employee);
	}

	@PostMapping(path = "/bulkEdit")
	public List<UUID> bulkEdit(@RequestBody List<EmployeeData> employees) {
		return service.bulkEdit(employees);
	}

	@DeleteMapping(path = "/delete/{id}")
	public void deleteEmployee(@PathVariable("id") UUID employeeID) {
		service.deleteEmployee(employeeID);
	}

	@DeleteMapping(path = "/bulkDelete")
	public void bulkDelete(@RequestBody List<UUID> ids) {
		service.bulkDelete(ids);
	}

	@GetMapping(path = "/sort")
	public SortedSet<EmployeeData> listEmployees() {
		return service.listAlphabetically();
	}

	@GetMapping(path = "/getlast/{count}")
	public List<EmployeeData> fetchLatestEmployees(@PathVariable("count") int count) {
		return service.fetchLatestEmployees(count);
	}
}
