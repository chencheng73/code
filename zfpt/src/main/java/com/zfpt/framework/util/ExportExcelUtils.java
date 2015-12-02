package com.zfpt.framework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 项目名称：PPSPV1.0   
 * 类名称：ExportExcelTemplate   
 * 类描述：导出Excel帮助类
 * 创建人：chens
 * 创建时间：Nov 14, 2014 2:23:45 PM      
 * @version
 */
public class ExportExcelUtils {
    /**模板格式 **/
	private final static String EXCEL_TEMPLATE_TYPE=".xls";
	/**判断是否为2003 **/
	private boolean  excelType_2003;
	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";
	// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy";  
	/** 97~2003格式 **/
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row = null;
	/** 2007~2010格式 **/
	private XSSFWorkbook xWorkbook = null;
	private XSSFSheet xSheet = null;
	private XSSFRow xRow=null;
	/**文件名 **/
	private String fileName=null;
	/**
	 * 初始化Excel
	 * 
	 */
	public ExportExcelUtils(OutputStream out) {
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}
	
   /**
    * ExportExcelTemplate.   
    * templatePath:为导出Excel模板物理路径 
    */
	public ExportExcelUtils(String templatePath) throws Exception {
		InputStream inputStream=null;
		if(templatePath != null) {
		   try{ 
			    inputStream=new FileInputStream(templatePath);
			    String _fileFix = templatePath.substring(templatePath.lastIndexOf("."));
			    fileName=templatePath.substring(templatePath.lastIndexOf("\\")+1);
				excelType_2003 = EXCEL_TEMPLATE_TYPE.equals(_fileFix) ? true : false;
				if (excelType_2003) {
					workbook = new HSSFWorkbook(inputStream);
					sheet = workbook.getSheetAt(0);
				} else {
					xWorkbook = new XSSFWorkbook(inputStream);
					xSheet = xWorkbook.getSheetAt(0);
				}
		   } catch (FileNotFoundException e) {
			    throw new  FileNotFoundException(" 到不到模板路径! ");
		   } catch (IOException e) {
			  e.printStackTrace();
		   }finally{
			   if(inputStream!=null){
				  inputStream.close(); 
			   }
		   }
		}
	}

	 
	
	/**
	 * 导出Excel文件
	 * @throws IOException
	 */
	public void export(HttpServletResponse response,String _fileName) throws FileNotFoundException, IOException {
		OutputStream outputStream=null;
		try{
			if(response!=null){
			   outputStream= response.getOutputStream();
			   response.setContentType("application/x-download;charset=UTF-8");
			}
			/**自定义文件名 **/
			if(StringUtils.isEmpty(_fileName)){
			   response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));  
			}else{
			   response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(_fileName, "UTF-8")+fileName.substring(fileName.lastIndexOf(".")));  	
			}
			if(excelType_2003){
			   workbook.write(outputStream);
			}else{
			   xWorkbook.write(outputStream);
			}
		} catch (FileNotFoundException e) {
			throw new IOException(" 生成导出Excel文件出错! ");
		} catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ");
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	/**
	 * 增加一行
	 * @param index
	 * 行号
	 */
	public void createRow(int index) {
	 if(excelType_2003){
	     this.row = this.sheet.createRow(index);;
	     this.row.setHeightInPoints(21);
	  }else{
		this.xRow=this.xSheet.createRow(index);
		  this.xRow.setHeightInPoints(21);
	  }
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, int value) {
		 if(excelType_2003){
			HSSFCell cell = this.row.createCell((short) index);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(value);
		  }else{
			 XSSFCell cell = this.xRow.createCell((short) index);
			 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			 cell.setCellValue(value);
		  }
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	/**
	 * 合并单元格
	 * @Description: TODO(用一句话描述该方法做什么) 
	 * @author LBB   
	 * @date 2015-2-5 上午11:30:25
	 * @param firstRow 开始行
	 * @param lastRow  结束行
	 * @param firstCell 开始列
	 * @param lastCell 结束列
	 */
	public void mergeRegion(int firstRow,int lastRow,int firstCell,int lastCell){
		if (excelType_2003) {
			this.sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCell, lastCell));
		}else {
			this.xSheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCell, lastCell));
		}
	}
	
	/**
	 * 设置单元格
	 * 
	 * @param index
	 *   列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, String value) {
		if(excelType_2003){
		   HSSFCell cell = this.row.createCell(index);
		   cell.setCellType(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue(value);
		 }else{
			XSSFCellStyle xCellStyle = xWorkbook.createCellStyle();
			/*xCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			xCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			xCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			xCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
*/			xCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
			xCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
		   XSSFCell cell = this.xRow.createCell((short) index);
		   cell.setCellType(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue(value);
		   cell.setCellStyle(xCellStyle);
		 }
	}
  
	
	public void setCell(int index, String value,Short font) {
		if(excelType_2003){
		   HSSFCell cell = this.row.createCell(index);
		   cell.setCellType(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue(value);
		   if(font!=null){
			   HSSFFont hSSFFont = workbook.createFont(); 
			   hSSFFont.setBoldweight(font);
			   HSSFCellStyle cellStyle= workbook.createCellStyle();
			   cellStyle.setFont(hSSFFont);
			   cell.setCellStyle(cellStyle);
		   }
		 }else{
			XSSFCellStyle xCellStyle = xWorkbook.createCellStyle();
			xCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			xCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			xCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			xCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			xCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
			xCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
		    XSSFCell cell = this.xRow.createCell((short) index);
		    cell.setCellType(HSSFCell.ENCODING_UTF_16);
		    cell.setCellValue(value);
		    if(font!=null){
			   XSSFFont xssfFont = xWorkbook.createFont();
			   xssfFont.setBoldweight(font);
			   xCellStyle.setFont(xssfFont);
		   }
		    cell.setCellStyle(xCellStyle);
		 }
	}
	
	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell((short) index);
		// cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

}