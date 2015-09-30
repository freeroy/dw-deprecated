package org.developerworld.tools.filereader.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.developerworld.tools.filereader.FileReader;

/**
 * xls文件内容读取器
 * 
 * @author Roy Huang
 * @version 20130417
 *
 *@deprecated
 *@see org.developerworld.commons.filereader project 
 */
public class XlsFileReader implements FileReader {

	public String readFileToString(File file) throws IOException {
		String rst = null;
		InputStream inputStream = null;
		ExcelExtractor extractor=null;
		HSSFWorkbook workbook=null;
		try {
			inputStream = new FileInputStream(file);
			workbook = new HSSFWorkbook(inputStream);
			extractor = new ExcelExtractor(workbook);
			extractor.setFormulasNotResults(true);
			extractor.setIncludeSheetNames(false);
			rst = extractor.getText();
		} finally {
			if(extractor!=null)
				extractor.close();
			if(workbook!=null)
				workbook.close();
			if (inputStream != null)
				inputStream.close();
		}
		return rst;
	}

}
