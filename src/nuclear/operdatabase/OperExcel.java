package nuclear.operdatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nuclear.function.ExcelData;

public class OperExcel
{
	private static String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String dbURLHead = "jdbc:odbc:driver={Microsoft Excel Driver (*.xls)};DBQ="; // 不设置数据源
	// private static String dbURL="jdbc:odbc:ExcelTest"; //数据源连接方式 DSN：ExcelTest	
	private static Connection dbConn = null;
	private Statement smt = null;
	
	private String dbURL = null; 
	private static String REGEX = ".xls$";
	
	public static OperExcel CreateOperExcel(String url){
		OperExcel data = new OperExcel();
		data.dbURL = dbURLHead + url;
		System.out.println(data.dbURL);
		
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(data.dbURL);		
		if(!m.find()){
			System.out.println("不是2003格式的excel文件");
			return null;
		}
		
	   try {
		Class.forName(driverName);
		data.dbConn = DriverManager.getConnection(data.dbURL, "", "");
		data.smt = dbConn.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   return data;
	}

	 /**
	  * @param args
	  */
	 public ArrayList<ExcelData> readExcel ()
	 {
		  ArrayList<ExcelData> data = new ArrayList<ExcelData>();		 
		  try
		  {
			  
			  ResultSet set = smt.executeQuery("select * from [sheet1$]");
			  while (set.next())
			  {
				ExcelData excelData = new ExcelData();
				excelData.Z = set.getString(1);   
				excelData.nuclear_info[0] = set.getString(2);
				excelData.A = set.getString(3);
				
				for(int i=1; i<12; i++){
					excelData.nuclear_info[i] = set.getString(i+3);
				}
				
//				System.out.println("-------------------");
//				System.out.println(set.getString(7));
			    data.add(excelData);
			   }
		  } catch (SQLException e)
		  {
			  e.printStackTrace();
		  } finally
		  {
		   try
		   {
			   dbConn.close();
		   } catch (SQLException e)
		   {
			   e.printStackTrace();
		   }
		  }
	  
	  return data;
	
	 }
	 
	    public ArrayList<ExcelData> readExcel(String path) throws IOException {
	        System.out.println("Excel 文件地址: " + path);
	        InputStream is = new FileInputStream(path);
	        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
	        ExcelData excelData = null;
	        ArrayList<ExcelData> list = new ArrayList<ExcelData>();
	        // Read the Sheet
	        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
	            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
	            if (xssfSheet == null) {
	                continue;
	            }
	            // Read the Row
	            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
	                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	                if (xssfRow != null) {
	                	excelData = new ExcelData();
	                    
	    				excelData.Z = getValue(xssfRow.getCell(0));   
	    				excelData.nuclear_info[0] = getValue(xssfRow.getCell(1));
	    				excelData.A = getValue(xssfRow.getCell(2));
	    				
	    				for(int i=1; i<12; i++){
	    					excelData.nuclear_info[i] = getValue(xssfRow.getCell(i+2));
	    				}	                    
	    				list.add(excelData);
	                }
	            }
	        }
	        return list;
	    }
	    
	    private String getValue(XSSFCell xssfRow) {
	    	if (xssfRow == null){
	    		return null;
	    	}
	    	else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(xssfRow.getBooleanCellValue());
	        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
	            return String.valueOf(xssfRow.getNumericCellValue());
	        } else {
	            return String.valueOf(xssfRow.getStringCellValue());
	        }
	    }
	    
	 
}

