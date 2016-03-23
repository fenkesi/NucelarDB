package nuclear.operdatabase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import nuclear.function.ExcelData;

public class OperSQL {
	private Connection conn = null;
	private Statement stmt = null;
	private String sql;
	private String tableName = null;
	private String dataDBs[]= {"nucleardb", "nuclearlibdb"};
	
	public static final int DB = 0;
	public static final int LIBDB = 1;
	private String url_head = "jdbc:mysql://localhost:3306/";
	private String url_tail = "?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
	private String url;
	public OperSQL(int db, String tableName){
		this(db);	
		this.tableName = tableName;		
	}
	
	public OperSQL(int db){
		url = url_head + dataDBs[db] + url_tail;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Loda database faile");
			e.printStackTrace();
		}	
	}
	
	public ArrayList<String> showTables(){
		DatabaseMetaData md;
		ArrayList<String> tablesName = new ArrayList<String>();
		try {
			md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
			  tablesName.add(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tablesName;
	}
	
	public void createTable(){
		
		String nInfo="";
		
		for(int i=0; i<12; i++){
			nInfo+="nInfo"+i+" char(50), ";
		}
		
		
		sql = "create table "+tableName+" (id int(9) NOT NULL AUTO_INCREMENT, Z varchar(20), "+nInfo+"A char(30), "+"primary key(id) )";
		System.out.println(sql);
		try {
			ResultSet rs  = conn.getMetaData().getTables(null, null,  tableName, null );
			if(rs.next()){
				System.out.println("表已经存在了！");
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			System.out.println(sql);			
			int result = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println("create Table successfully");
	}
	
	public  void insert(ExcelData data){	
			int result;	
			
			createTable();
			String nInfo = "";	
			String values = "";
			
			for(int i=0; i<12; i++){
				nInfo+="nInfo"+i+", ";
			}
			for(int i=0; i<12; i++){
				values+="'"+data.nuclear_info[i]+"', ";
			}
			
			sql = "insert into "+tableName+"(Z, "+nInfo+"A"+") values('"+data.Z+"', "+values+"'"+data.A+"')";
			System.out.println(sql);
			try {
				result = stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("插入表格失败!" + data.Z + ' ' + data.A);
				e.printStackTrace();
			}
			
	}
	
	public ExcelData query(String Z, String A){
		ExcelData data = new ExcelData();
		
		//修改Z为字母  对应数据库nInfo0
		sql = "select * from "+ tableName + " where " + "nInfo0=" + "'" + Z + "'" + " AND " + "A=" + "'" + A + "'";
		
		System.out.println(sql);
		
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
			System.out.println("No\t name");
			while(rs.next()){
				System.out.println("-----------");
				data.Z = rs.getString(2);
				
				for(int i=0; i<12; i++){
					data.nuclear_info[i] = (rs.getString(i+3).equals("null"))?" ":rs.getString(i+3);
				}
				data.A = rs.getString(15);
				System.out.println(data.Z + " " + data.A);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	

}

