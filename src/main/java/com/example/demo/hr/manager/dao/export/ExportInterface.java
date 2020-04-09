package com.example.demo.hr.manager.dao.export;

import java.util.SortedSet;

import javax.validation.constraints.NotEmpty;

import com.example.demo.hr.manager.model.EmployeeData;

public interface ExportInterface {
	public static final String COLUMN_DELIMITER = " ";
	public static final String END_OF_LINE = "\r\n";
	public static final String DOWNLOAD_FILE_NAME = "./salariesExport.csv";
	
	
	/**
	 * export list of employees in CSV format with following format Firstname,
	 * Lastname, GrossSalary, Taxes%, NetSalary
	 */
	public String prepareDataForExport(@NotEmpty SortedSet<EmployeeData> employees);
}
