package nuclear.function;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import nuclear.operdatabase.OperExcel;
import nuclear.operdatabase.OperSQL;

public class StoreExcelData {
	private String excelPath;
	private ArrayList<ExcelData> excelData;
	private OperExcel handleExcel;
	private OperSQL  handleSQL;
	
	public StoreExcelData(String path, int option, String tableName){
		this.excelPath = path;
		handleExcel = new OperExcel();

		handleSQL = new OperSQL(option, tableName);
	}
	
	public void execute(){
		
		if(handleExcel == null){
			System.out.println("获取excel数据失败");
			return;
		}
		
		try {
			excelData = handleExcel.readExcel(excelPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("打开excel 文件失败");
			e.printStackTrace();
		}
	
		for(ExcelData data : excelData){
			System.out.println(data.Z + "-" + data.A);
			handleSQL.insert(data);
		}
		
		JOptionPane.showMessageDialog(null, "数据录入完成", "核数据库系统",
	              JOptionPane.YES_NO_OPTION);		
	}
}
