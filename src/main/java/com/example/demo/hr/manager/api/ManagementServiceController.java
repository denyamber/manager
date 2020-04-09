package com.example.demo.hr.manager.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.hr.manager.model.EmployeeData;
import com.example.demo.hr.manager.services.ExportSalariesService;
import com.example.demo.hr.manager.services.ManagerService;

@RequestMapping("/api/v1/manager")
@RestController
public class ManagementServiceController {
	private final ManagerService managementService;
	private final ExportSalariesService exportService;

	@Autowired
	public ManagementServiceController(ManagerService service, ExportSalariesService exportService) {
		this.managementService = service;
		this.exportService = exportService;
	}

	@PostMapping(path = "/add")
	public UUID addEmployee(@RequestBody EmployeeData employee) throws IOException {
		return managementService.addEmployee(employee);
	}

	@PostMapping(path = "/bulkAdd")
	public List<UUID> bulkAdd(@RequestBody List<EmployeeData> employees) throws IOException {
		return managementService.bulkAdd(employees);
	}

	@PostMapping(path = "/edit/{id}")
	public UUID editEmployee(@PathVariable("id") UUID employeeID, @RequestBody EmployeeData employee) {
		return managementService.editEmployee(employeeID, employee);
	}

	@PostMapping(path = "/bulkEdit")
	public List<UUID> bulkEdit(@RequestBody List<EmployeeData> employees) {
		return managementService.bulkEdit(employees);
	}

	@DeleteMapping(path = "/delete/{id}")
	public void deleteEmployee(@PathVariable("id") UUID employeeID) throws IOException {
		managementService.deleteEmployee(employeeID);
	}

	@DeleteMapping(path = "/bulkDelete")
	public void bulkDelete(@RequestBody List<UUID> ids) throws IOException {
		managementService.bulkDelete(ids);
	}

	@GetMapping(path = "/sort")
	public SortedSet<EmployeeData> listEmployees() {
		return managementService.listAlphabetically();
	}

	@GetMapping(path = "/getlast/{count}")
	public List<EmployeeData> fetchLatestEmployees(@PathVariable("count") int count) {
		return managementService.fetchLatestEmployees(count);
	}

	@GetMapping(path = "/export")
	public ResponseEntity<Object> exportSalariesData() {
		FileInputStream resultFileInputStream = null;
		ResponseEntity<Object> responseEntity = null;
		try {
			File resultFile = exportService.export();
			resultFileInputStream = new FileInputStream(resultFile);
			InputStreamResource resource = new InputStreamResource(resultFileInputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", resultFile.getName()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			responseEntity = ResponseEntity.ok().headers(headers).contentLength(resultFile.length())
					.contentType(MediaType.parseMediaType("application/txt")).body(resource);
		} catch (Exception e) {
			return new ResponseEntity<>("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
