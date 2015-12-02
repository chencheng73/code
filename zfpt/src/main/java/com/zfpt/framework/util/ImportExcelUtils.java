package com.zfpt.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
/**
 * 项目名称：PPSPV1.0   
 * 类名称：ExcelHelper   
 * 类描述：导入Excel   
 * 创建人：chens
 * 创建时间：Nov 14, 2014 10:57:27 AM      
 * @version
 */
public class ImportExcelUtils {

	/** 区分20003、20007格式区别 **/
	private final static String EXCEL_2003_FIX = ".xls",
			EXCEL_2007_FIX = ".xlsx";

	/** 97~2003格式 **/
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;

	/** 2007~2010格式 **/
	private XSSFWorkbook xWorkbook = null;
	private XSSFSheet xSheet = null;
	private Integer lastNumber;

	private boolean excelType_2003;
	private InputStream inputStream;
	private String fileName;

	public ImportExcelUtils(File file) {
		this.fileName = file.getName();
		try {
			inputStream = new FileInputStream(file);
			readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ImportExcelUtils(MultipartFile multipartFile) {
		this.fileName = multipartFile.getOriginalFilename();
		try {
			inputStream = multipartFile.getInputStream();
			//readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法名称: readFile 
	 * 方法描述: 读取excel: 
	 * 返回类型: void
	 * @throws
	 */
	public List<String[]> readFile() throws IOException {
		List<String[]> sList =new ArrayList<String[]>();
		try {
			String _fileFix = fileName.substring(fileName.lastIndexOf("."));
			excelType_2003 = EXCEL_2003_FIX.equals(_fileFix) ? true : false;
			if (excelType_2003) {
				workbook = new HSSFWorkbook(inputStream);
				sheet = workbook.getSheetAt(0);
				lastNumber = sheet.getLastRowNum();
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++)   
	               {   
	                   HSSFRow row = sheet.getRow(i);   
	                   String[] strRow = new String[row.getLastCellNum()];
                       for (int j = 0; j < row.getLastCellNum(); j++)   
	                    {   
	                        HSSFCell cell = row.getCell(j);   
	                        String cellStr ="";
	                        if (null==cell) {
	                        	 strRow[j]= cellStr;
							}else {
								 cellStr = cell.toString();   
			                     strRow[j]= cellStr;
							} 
	                     }  
                          sList.add(strRow);
	              }   
			} else {
				xWorkbook = new XSSFWorkbook(inputStream);
				xSheet = xWorkbook.getSheetAt(0);
				lastNumber = xSheet.getLastRowNum();
				for (int i = 1; i < xSheet.getPhysicalNumberOfRows(); i++)   
	               {   
	                   XSSFRow row = xSheet.getRow(i);   
	                   String[] strRow = new String[row.getLastCellNum()];
                   for (int j = 0; j < row.getLastCellNum(); j++)   
	                    {   
                           XSSFCell cell = row.getCell(j);   
                           String cellStr ="";
                           if (null==cell) {
                           	 strRow[j]= cellStr;
   						}else {
   							 cellStr = cell.toString();   
   		                     strRow[j]= cellStr;
   						} 
	                     }     
                        sList.add(strRow); 
	              }   
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return sList;
	}
	/**
	 * 
	 * @Description: 导入方法
	 * @author LBB   
	 * @date 2015-4-7 上午9:26:06
	 * @param startIndex 导入开始行数
	 * @return
	 * @throws IOException
	 */
	public List<String[]> readFile2(Integer startIndex) throws IOException {
		List<String[]> sList =new ArrayList<String[]>();
		try {
			String _fileFix = fileName.substring(fileName.lastIndexOf("."));
			excelType_2003 = EXCEL_2003_FIX.equals(_fileFix) ? true : false;
			if (excelType_2003) {
				workbook = new HSSFWorkbook(inputStream);
				sheet = workbook.getSheetAt(0);
				lastNumber = sheet.getLastRowNum();
				for (int i = startIndex; i < sheet.getPhysicalNumberOfRows(); i++)   
	               {   
	                   HSSFRow row = sheet.getRow(i);   
	                   String[] strRow = new String[sheet.getRow(startIndex-1).getLastCellNum()];//获取导入列数 初始化数组
                    for (int j = 0; j < row.getLastCellNum(); j++)   
	                    {   
	                        HSSFCell cell = row.getCell(j);   
	                        String cellStr ="";
	                        if (null==cell) {
	                        	 strRow[j]= cellStr;
							}else {
								 cellStr = cell.toString();   
			                     strRow[j]= cellStr;
							} 
	                     }  
                       sList.add(strRow);
	              }   
			} else {
				xWorkbook = new XSSFWorkbook(inputStream);
				xSheet = xWorkbook.getSheetAt(0);
				lastNumber = xSheet.getLastRowNum();
				for (int i = startIndex; i < xSheet.getPhysicalNumberOfRows(); i++)   
	               {   
	                   XSSFRow row = xSheet.getRow(i);   
	                   String[] strRow = new String[xSheet.getRow(startIndex-1).getLastCellNum()];//获取导入列数 初始化数组
                   for (int j = 0; j < row.getLastCellNum(); j++) {   
                        XSSFCell cell = row.getCell(j);  
                        String cellStr ="";
                        if (null==cell) {
                        	 strRow[j]= cellStr;
						}else {
							 cellStr = cell.toString();   
		                     strRow[j]= cellStr;
						} 
	                   }     
                     sList.add(strRow); 
	              }   
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return sList;
	}
	/**
	 * 方法名称: readFile 
	 * 方法描述: 读取excel: 
	 * 返回类型: void
	 * @throws
	 */
	public void readFile1() throws IOException {
		try {
			String _fileFix = fileName.substring(fileName.lastIndexOf("."));
			excelType_2003 = EXCEL_2003_FIX.equals(_fileFix) ? true : false;
			if (excelType_2003) {
				workbook = new HSSFWorkbook(inputStream);
				sheet = workbook.getSheetAt(0);
				lastNumber = sheet.getLastRowNum();
			} else {
				xWorkbook = new XSSFWorkbook(inputStream);
				xSheet = xWorkbook.getSheetAt(0);
				lastNumber = xSheet.getLastRowNum();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
	}

	
	
	/**
	 * 方法名称: getCurrentContent 
	 * 方法描述: 返回指定列内容: 
	 * 返回类型: String
	 * @throws
	 */
	public String getCurrentContent(Integer currentRow, Integer cellNum) {
		String strExcelCell = "";
		if (currentRow < 0)
			return null;
		if (currentRow > lastNumber)
			return null;
		if (excelType_2003) {
			HSSFRow row = sheet.getRow(currentRow);
			if (row != null) {
				HSSFCell cell = row.getCell(cellNum);
				if (cell != null) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						strExcelCell = "FORMULA ";
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						strExcelCell = String.valueOf(cell
								.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:
						strExcelCell = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						strExcelCell = "";
						break;
					default:
						strExcelCell = "";
						break;
					}
				}
			}
		} else {
			XSSFRow row = xSheet.getRow(currentRow);
			if (row != null) {
				XSSFCell cell = row.getCell(cellNum);
				if (cell != null) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						strExcelCell = "FORMULA ";
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						strExcelCell = String.valueOf(cell
								.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:
						strExcelCell = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						strExcelCell = "";
						break;
					default:
						strExcelCell = "";
						break;
					}
				}
			}
		}
		return strExcelCell;
	}

	/**
	 * 方法名称: getExcelRows 
	 * 方法描述:  获取excel总行数:   
	 * 返回类型: Integer 
	 * @throws
	 */
	public Integer getExcelRows() throws IOException {
		return lastNumber;
	}

}
