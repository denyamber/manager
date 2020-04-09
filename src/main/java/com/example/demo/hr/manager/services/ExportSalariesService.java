package com.example.demo.hr.manager.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.hr.manager.dao.export.ExportInterface;

@Service
public class ExportSalariesService {

	private final ManagerService managerService;
	private final ExportInterface exporter;

	@Autowired
	public ExportSalariesService(ManagerService managerService, @Qualifier("salaries") ExportInterface exporter) {
		super();
		this.managerService = managerService;
		this.exporter = exporter;
	}

	public File export() throws IOException {
		String convertedData = exporter.prepareDataForExport(managerService.listAlphabetically());
		File fileToDownload = new File(ExportInterface.DOWNLOAD_FILE_NAME);
		if (fileToDownload.exists()) {
			fileToDownload.delete();
		}

		FileWriter fileWriter = new FileWriter(fileToDownload);
		try {
			fileWriter.write(convertedData);
		} finally {
			fileWriter.flush();
			fileWriter.close();
		}

		return fileToDownload;
	}

}
