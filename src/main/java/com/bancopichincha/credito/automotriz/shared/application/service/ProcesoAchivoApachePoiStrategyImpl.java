package com.bancopichincha.credito.automotriz.shared.application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProcesoAchivoApachePoiStrategyImpl implements ProcesoArchivoStrategy {

	@Override
	public ArrayList<ArrayList<String>> procesamientoArchivoXlsx(String rutaArchivo) {
		try {
			var woorkBook = cargarXlsx(rutaArchivo);
			return construirMatrizDatos(woorkBook);
		} catch (EncryptedDocumentException | IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	

	private Workbook cargarXlsx(String rutaArchivo) throws EncryptedDocumentException, IOException {
		var file = new File(rutaArchivo);
		var fileInputStream = new FileInputStream(file);
		return WorkbookFactory.create(fileInputStream);
	}

	private ArrayList<ArrayList<String>> construirMatrizDatos(Workbook workBook) {
		var listaEntidades = new ArrayList<ArrayList<String>>();
		var sheet = workBook.getSheetAt(0);
		for (int fila = 1; fila < sheet.getLastRowNum(); fila++) {
			var entidad = new ArrayList<String>();
			var filaArc = sheet.getRow(fila);
			for (int columna = 0; columna < filaArc.getLastCellNum(); columna++) {
				var valor = filaArc.getCell(columna);
				try {
					entidad.add(!ObjectUtils.isEmpty(valor) ? valor.getStringCellValue() : "");
				} catch (IllegalStateException e) {
					entidad.add(!ObjectUtils.isEmpty(valor) ? String.valueOf(valor.getNumericCellValue()) : "");
				}
			}
			listaEntidades.add(entidad);
		}

		return listaEntidades;
	}

}
