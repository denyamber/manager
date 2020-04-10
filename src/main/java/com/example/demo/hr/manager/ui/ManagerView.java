package com.example.demo.hr.manager.ui;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.olli.FileDownloadWrapper;

import com.example.demo.hr.manager.model.EmployeeData;
import com.example.demo.hr.manager.services.ExportSalariesService;
import com.example.demo.hr.manager.services.ManagerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class ManagerView extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6905060075233494333L;
	private final Grid<EmployeeData> grid;

	public ManagerView(@Autowired ManagerService managerService, @Autowired ExportSalariesService exportService) {
		H1 heading = new H1("Simple HR manager");
		add(heading);
		this.grid = new Grid<>(EmployeeData.class);
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.add(new Button("Recalculate NetSalary", event -> {
			try {
				managerService.recalculateNetSalaries();
			} catch (Exception e) {
				e.printStackTrace();
			}
			grid.setItems(managerService.listAlphabetically());
		}));
		Button button = new Button("Click to download Salaries as CSV");
		FileDownloadWrapper buttonWrapper;
		try {
			buttonWrapper = new FileDownloadWrapper("salariesList.csv", exportService.export());
			buttonWrapper.wrapComponent(button);
			buttonsLayout.add(buttonWrapper);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(buttonsLayout, grid);
		grid.setItems(managerService.listAlphabetically());
		grid.setColumns("firstName", "lastName", "phoneNumber", "country", "address", "postalCode", "grossSalary",
				"percentOfTaxes", "netSalary");
	}

}
