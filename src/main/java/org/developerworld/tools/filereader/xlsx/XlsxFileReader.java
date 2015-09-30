package org.developerworld.tools.filereader.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.developerworld.tools.filereader.FileReader;

/**
 * xlsx文件内容读取器
 * 
 * @author Roy Huang
 * @version 20130417
 * 
 * @deprecated
 * @see org.developerworld.commons.filereader project
 */
public class XlsxFileReader implements FileReader {

	public String readFileToString(File file) throws IOException {
		String rst = null;
		InputStream inputStream = null;
		XSSFWorkbook workbook = null;
		XSSFExcelExtractor extractor = null;
		try {
			inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputStream);
			extractor = new XSSFExcelExtractor(workbook);
			extractor.setFormulasNotResults(true);
			extractor.setIncludeSheetNames(false);
			rst = extractor.getText();
		} finally {
			if (extractor != null)
				extractor.close();
			if (workbook != null)
				workbook.close();
			if (inputStream != null)
				inputStream.close();
		}
		return rst;
	}

}
