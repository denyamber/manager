package com.example.demo.hr.manager.dao.export;

import java.util.Iterator;
import java.util.SortedSet;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.example.demo.hr.manager.model.EmployeeData;

@Component("salaries")
public class ExportSalariesData implements com.example.demo.hr.manager.dao.export.ExportInterface {

	@Override
	public String prepareDataForExport(@NotEmpty SortedSet<EmployeeData> employees) {
		StringBuilder scvFileFormatBuilder = new StringBuilder();
		scvFileFormatBuilder.append("FirstName LastName GrossSalary PercentOfTaxes NetSalary");
		scvFileFormatBuilder.append(END_OF_LINE);
		Iterator<EmployeeData> employeesIter = employees.iterator();
		while (employeesIter.hasNext()) {
			EmployeeData currentEmployee = employeesIter.next();
			addColumnValueInScvFileFormat(currentEmployee.getFirstName(), scvFileFormatBuilder, false);
			addColumnValueInScvFileFormat(currentEmployee.getLastName(), scvFileFormatBuilder, false);
			addColumnValueInScvFileFormat("" + currentEmployee.getGrossSalary(), scvFileFormatBuilder, false);
			addColumnValueInScvFileFormat("" + currentEmployee.getPercentOfTaxes() + "%", scvFileFormatBuilder, false);
			addColumnValueInScvFileFormat("" + currentEmployee.getNetSalary(), scvFileFormatBuilder, true);
		}
		return scvFileFormatBuilder.toString();
	}

	private void addColumnValueInScvFileFormat(String value, StringBuilder builder, boolean endOfRow) {
		builder.append(value);
		if (endOfRow) {
			builder.append(END_OF_LINE);
		} else {
			builder.append(COLUMN_DELIMITER);
		}
	}

}
