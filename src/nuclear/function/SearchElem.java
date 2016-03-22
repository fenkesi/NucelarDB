package nuclear.function;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import nuclear.operdatabase.OperSQL;

public class SearchElem {
	private String Z;
	private String A = null;
	private OperSQL experSQL = null;
	private OperSQL libSQL = null;
	private ExcelData experData;
	private ExcelData libData;
	
	static final int WIDTH = 220;
	static final int HEIGHT = 300;
	
	public SearchElem(String info[]){
		this.Z = info[0];
		this.A = info[1];
		experSQL = new OperSQL(OperSQL.DB, info[2]);
		libSQL = new OperSQL(OperSQL.LIBDB, info[3]);
	}
	
	public void execute(){
		experData = experSQL.query(Z, A);
		libData = libSQL.query(Z, A);
		
		System.out.println(libData.Z + " " + libData.A);
		
		showResult(experData, libData);
	}
	
	public void showResult(ExcelData data, ExcelData libdata){
		final JFrame jf = new JFrame("数据对比");
		JPanel jp = new JPanel(new GridLayout(2,1));
		String head [] = new String[6];
		String info [][] = new String[2][6];
		JTable jt = null;
		JTable libJt = null;
		
		jf.setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen_size = kit.getScreenSize();
		int width = screen_size.width;
		int height = screen_size.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		jf.setLocation(x,y);
		jf.setVisible(true);
		
		head[0] = "数据库";
		head[1] = "核素";
		head[2] = "原子量";
		head[3] = "原子序数";
		head[4] = "半衰期";
		head[5] = "衰变方式";
		
		info[0][0] = "实验库";
		info[0][1] = data.nuclear_info[0];
		info[0][2] = data.nuclear_info[2];
		info[0][3] = data.Z;
		info[0][4] = data.nuclear_info[5];
		info[0][5] = data.nuclear_info[7];

		info[1][0] = "标准库";
		info[1][1] = libdata.nuclear_info[0];
		info[1][2] = libdata.nuclear_info[2];
		info[1][3] = libdata.Z;
		info[1][4] = libdata.nuclear_info[5];
		info[1][5] = libdata.nuclear_info[7];
		
		jt = new JTable(info, head);
		
		jp.add(new JScrollPane(jt));
	
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		// tcr.setHorizontalAlignment(JLabel.CENTER);
		 tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		 jt.setDefaultRenderer(Object.class, tcr);		
		
		jf.add(jp);
		
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.out.println("我关了");
				jf.setVisible(false);
			}			
		});
	}
	
}
