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
			System.out.println("��ȡexcel����ʧ��");
			return;
		}
		
		try {
			excelData = handleExcel.readExcel(excelPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("��excel �ļ�ʧ��");
			e.printStackTrace();
		}
	
		for(ExcelData data : excelData){
			System.out.println(data.Z + "-" + data.A);
			handleSQL.insert(data);
		}
		
		JOptionPane.showMessageDialog(null, "����¼�����", "�����ݿ�ϵͳ",
	              JOptionPane.YES_NO_OPTION);		
	}
}
