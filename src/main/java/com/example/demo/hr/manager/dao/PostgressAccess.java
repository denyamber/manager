package com.example.demo.hr.manager.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.hr.manager.model.EmployeeData;

@Repository("postgress")
public class PostgressAccess implements EmployeeDataManagementInterface {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PostgressAccess(JdbcTemplate template) {
		this.jdbcTemplate = template;
	}

	@Override
	public UUID addEmployee(@NotEmpty UUID id, @NotEmpty EmployeeData employee) throws IOException {
		final String sql = "INSERT INTO employees (id, first_name, last_name, address, postal_code, phone_number, gross_salary, taxes, net_salary)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		if (id == null) {
			id = addEmployee(employee);
		}
		jdbcTemplate.update(sql,
				new Object[] { id, employee.getFirstName(), employee.getLastName(), employee.getAddress(),
						employee.getPostalCode(), employee.getPhoneNumber(), employee.getGrossSalary(),
						employee.getPercentOfTaxes(), employee.getNetSalary() });
		return id;
	}

	@Override
	public UUID editEmployee(@NotEmpty UUID employeeID, @NotEmpty EmployeeData employee) throws IOException {
		if (findEmployee(employeeID) == null) {
			return null;
		}
		deleteEmployee(employeeID);
		addEmployee(employeeID, employee);
		return employeeID;
	}

	@Override
	public void deleteEmployee(@NotEmpty UUID employeeID) throws IOException {
		if (findEmployee(employeeID) == null) {
			return;
		}
		final String sql = "DELETE FROM employees WHERE id=? ";
		jdbcTemplate.update(sql, employeeID);
	}

	private RowMapper<EmployeeData> getRowMapper() {
		return new RowMapper<EmployeeData>() {
			@Override
			public EmployeeData mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmployeeData data = new EmployeeData();
				data.setEmployeeID(UUID.fromString(rs.getString("id")));
				data.setAddress(rs.getString("address"));
				data.setCountry(rs.getString("country"));
				data.setFirstName(rs.getString("first_name"));
				data.setLastName(rs.getString("last_name"));
				data.setGrossSalary(rs.getFloat("gross_salary"));
				data.setNetSalary(rs.getFloat("net_salary"));
				data.setPercentOfTaxes(rs.getFloat("taxes"));
				data.setPhoneNumber(rs.getString("phone_number"));
				data.setPostalCode(rs.getString("postal_code"));
				return data;
			}
		};
	}

	@Override
	public List<EmployeeData> fetchLatestEmployees(int count) {
		final String countSQL = "SELECT count(*) from employees";
		int size = jdbcTemplate.queryForObject(countSQL, Integer.class);
		List<EmployeeData> list = null;
		if (size <= count) {
			final String sql = "SELECT * from employees";
			list = jdbcTemplate.query(sql, getRowMapper());
		} else {
			final String sql = "SELECT * from employees OFFSET " + (size - count) + " FETCH first " + count
					+ " rows only";
			list = jdbcTemplate.query(sql, getRowMapper());
		}

		return list;
	}

	@Override
	public SortedSet<EmployeeData> listAlphabetically() {
		final String sql = "SELECT * from employees";
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
		sortedSet.addAll(jdbcTemplate.query(sql, getRowMapper()));
		return sortedSet;
	}

	@Override
	public EmployeeData findEmployee(UUID id) throws IOException {
		final String sql = "SELECT * from employees WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { id }, getRowMapper());
	}

}
