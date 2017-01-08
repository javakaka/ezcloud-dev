package com.ezcloud.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ezcloud.framework.vo.DataSet;
import com.ezcloud.framework.vo.Row;
/**
 * 
 * 
 * Excel工具类
 * @author JianBoTong
 *
 */
public class ExcelUtil {

	
	 private static Logger logger = Logger.getLogger(ExcelUtil.class);

	    
	 public static List<Map<String ,Object>> parseExcel(String filePath) throws FileNotFoundException, IOException
	 {
	    	List<Map<String ,Object>>sheetList =new ArrayList<Map<String,Object>>();
	    	List<Object> sheetData =null;
	    	HSSFWorkbook  workbook = new HSSFWorkbook(new FileInputStream(filePath));
	    	HSSFSheet sheet=null;
	    	Map <String ,Object>sheetMap=null;
	    	int sheetNum = workbook.getNumberOfSheets();
	    	System.out.println("\n");
	    	System.out.println("\n=============sheet num====>>"+sheetNum);
	    	for(int i=0; i< sheetNum; i++)
	    	{
	    		sheet = workbook.getSheetAt(i);
	    		String sheetName =sheet.getSheetName();
	    		sheetData =getDatasInSheet(workbook,i);
	    		sheetMap =new HashMap<String ,Object>();
	    		sheetMap.put("index", String.valueOf(i));
	    		sheetMap.put("name", sheetName);
	    		sheetMap.put("data", sheetData);
	    		sheetMap.put("rows", sheetData.size());
	    		sheetList.add(sheetMap);
	    	}
	    	return sheetList;
	    }
	    

	    /**
	     * 获得表中的数据
	     * 
	     * @param sheetNumber
	     *            表格索引(EXCEL 是多表文档,所以需要输入表索引号)
	     * @return 由LIST构成的行和表
	     * @throws FileNotFoundException
	     * @throws IOException
	     * 
	     */
	    public static List<Object> getDatasInSheet(HSSFWorkbook workbook,int sheetNumber)
	            throws FileNotFoundException, IOException {
	        List<Object> result = new ArrayList<Object>();
	        // 获得指定的表
	        HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
	        // 获得数据总行数
	        int rowCount = sheet.getLastRowNum();
	        logger.info("found excel rows count: " + rowCount);
	        if (rowCount < 1) {
	            return result;
	        }

	        // 逐行读取数据
	        for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
	            // 获得行对象
	            HSSFRow row = sheet.getRow(rowIndex);
	            if (row != null) {
	                List<Object> rowData = new ArrayList<Object>();
	                // 获得本行中单元格的个数
	                int columnCount = row.getLastCellNum();
	                // 获得本行中各单元格中的数据
	                for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
	                    @SuppressWarnings("deprecation")
						HSSFCell cell = row.getCell(columnIndex);
	                    // 获得指定单元格中数据
	                    Object cellStr = getCellString(cell);
	                    rowData.add(cellStr);
	                }
	                result.add(rowData);
	            }
	        }
	        return result;
	    }

	    /**
	     * 获得单元格中的内容
	     * 
	     * @param cell
	     * @return
	     */
	    protected static Object getCellString(HSSFCell cell) {
	        Object result = null;
	        if (cell != null) {
	            int cellType = cell.getCellType();
	            switch (cellType) {
	            case HSSFCell.CELL_TYPE_STRING:
	                result = cell.getRichStringCellValue().getString();
	                break;
	            case HSSFCell.CELL_TYPE_NUMERIC:
	            {
	            	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//	                result = cell.getNumericCellValue();
	            	 result = cell.getRichStringCellValue().getString();
	                break;
	            }
	            	
	            case HSSFCell.CELL_TYPE_FORMULA:
	            {
	            	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//	            	result = cell.getNumericCellValue();
	            	result = cell.getRichStringCellValue().getString();
	                break;
	            }
	            case HSSFCell.CELL_TYPE_ERROR:
	                result = null;
	                break;
	            case HSSFCell.CELL_TYPE_BOOLEAN:
	                result = cell.getBooleanCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_BLANK:
	                result = null;
	                break;
	            }
	        }
	        return result;
	    }

	    @SuppressWarnings("unchecked")
		public static void main(String[] args) throws Exception {
	    	String filePath="/users/JianBoTong/Desktop/test.xls";
	       List<Map<String,Object>>sheetlist =ExcelUtil.parseExcel(filePath);
	       String index ="";
	       String sheetName="";
	       List<Object> sheetData=null;
	        for (int i = 0; i < sheetlist.size(); i++) {// 显示数据
	            Map<String,Object> map=  (Map<String,Object>)sheetlist.get(i);
	            index=(String)map.get("index");
	            sheetName=(String)map.get("name");
	            sheetData=(List<Object>)map.get("data");
	            System.out.println("=============sheet:"+index+" sheet name:"+sheetName);
	            for (short n = 0; n < sheetData.size(); n++) {
	            	List<Object> rowData =(List<Object>)sheetData.get(n);
//	            	System.out.println("row----------------------->> "+n);
	            	for(int m=0;m<rowData.size();m++)
	            	{
	            		Object value = rowData.get(m);
	 	                String data = String.valueOf(value);
	 	                System.out.print(data + "\t");
	            	}
	            	System.out.println();
	            }
	            System.out.println();
	        }
	    }
	    
	    /**
	     * 
	     * @param titleDs 中文标题列表
	     * @param keyDs   数据字段key列表
	     * @param dataDs  数据列表
	     * @param out_path 文件保存路径
	     * @throws IOException
	     */
	    public static void writeExcel(DataSet titleDs,DataSet keyDs,DataSet dataDs,
	    		String out_path,String fileName,String sheetName,int rowHeight) throws IOException
	    {
	        String exportPath = out_path+"/"+fileName;
	        FileUtil.mkdir(out_path);
	        OutputStream out = new FileOutputStream(new File(exportPath));  
	  
	        // 声明一个工作薄  
	        HSSFWorkbook workbook = new HSSFWorkbook();  
	        // 生成一个表格  
	        if(StringUtils.isEmptyOrNull(sheetName))
	        {
	        	sheetName ="SHEET_"+DateUtil.getCurrentDateTime().replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
	        }
	        HSSFSheet sheet = workbook.createSheet(sheetName);  
	        // 设置表格默认列宽度为15个字节  
	        sheet.setDefaultColumnWidth(15);  
	        // 设置标题  
	        HSSFCellStyle titleStyle = workbook.createCellStyle();  
	        // 居中显示  
	        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	       
	        // 标题字体  
	        HSSFFont titleFont = workbook.createFont();  
	        // 字体大小  
//	        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
	        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        titleStyle.setFont(titleFont);  
	  
	        HSSFCellStyle contentStyle = workbook.createCellStyle();  
	        contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
	        HSSFFont contentFont = workbook.createFont();  
	        contentFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
	        contentStyle.setFont(contentFont);  
	  
	        // 产生表格标题行  
	        HSSFRow row = sheet.createRow(0);
	        for (int i = 0; i < titleDs.size(); i++) 
	        {  
	            HSSFCell cell = row.createCell(i);  
	            HSSFRichTextString text = new HSSFRichTextString(String.valueOf(titleDs.get(i)));  
	            cell.setCellValue(text);  
	            cell.setCellStyle(titleStyle);  
	        }  
	        int rowCount = 1;  
	        for (int i = 0; i < dataDs.size(); i++, rowCount++) 
	        {  
	            HSSFRow dataRow = sheet.createRow(rowCount);  
	            if(rowHeight > 0)
	            {
	            	dataRow.setHeight((short)rowHeight);
	            }
	            Row data_row = (Row)dataDs.get(i);  
	            for (int j = 0; j < keyDs.size(); j++) 
	            {  
	            	HSSFCell cell = dataRow.createCell(j);
	            	String value =data_row.getString(String.valueOf(keyDs.get(j)));
	            	if(StringUtils.isEmptyOrNull(value))
	            	{
	            		value ="";
	            	}
	            	cell.setCellValue(value);  
		            cell.setCellStyle(contentStyle);  
	            }
	        }  
	        // 自动调整列宽
	        for (int i = 0; i < titleDs.size(); i++) 
	        {  
	        	sheet.autoSizeColumn((short)i);
	        	int colWidth =sheet.getColumnWidth(i);
	        	if(colWidth>15000)
	        	{
	        		colWidth =50*256;
	        		sheet.setColumnWidth(i, 50*256);
	        	}
	        }  
	        workbook.write(out);  
	        out.close();
	    }  

}
