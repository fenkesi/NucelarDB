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
	static final int HEIGHT = 150;
	
	String head[] = {"Zԭ������",
	"Ԫ��",
	"A������",
	"J��ԭ�Ӻ˻�̬",
	"Mԭ������",
	"��(nm)",
	"Q(b)",
	"T(1/2)��˥��",
	"�����ط��",
	"D%Q",
	"R<R>",
	"E��",
	"P�ã�%��",
	"��(ny)"};
	
	
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
		final JFrame jf = new JFrame("���ݶԱ�");
		JPanel jp = new JPanel(new GridLayout(2,1));
		String info [][] = new String[2][14];
		JTable jt = null;
		JTable libJt = null;
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen_size = kit.getScreenSize();
		int width = screen_size.width;
		int height = screen_size.height;
		//����Ļ�м���ʾ��ˮƽ����
		jf.setSize(width, HEIGHT);
		
		int x = 0;
//		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		jf.setLocation(x,y);
		jf.setVisible(true);
		
		info[0][0] = "ʵ���";
		
		info[0][0] = data.Z;
		info[0][1] = data.nuclear_info[0];
		info[0][2] = data.A;
		
		for(int i=3; i<head.length; i++){
			info[0][i] = data.nuclear_info[i-2];
		}

		info[1][0] = "��׼��";
		info[1][0] = data.Z;
		info[1][1] = data.nuclear_info[0];
		info[1][2] = data.A;
		
		for(int i=3; i<head.length; i++){
			info[1][i] = data.nuclear_info[i-2];
		}		
				
		jt = new JTable(info, head);
		
		jp.add(new JScrollPane(jt));
	
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// ����table���ݾ���
		// tcr.setHorizontalAlignment(JLabel.CENTER);
		 tcr.setHorizontalAlignment(SwingConstants.CENTER);// �����Ͼ�����һ��
		 jt.setDefaultRenderer(Object.class, tcr);		
		
		jf.add(jp);
		
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.out.println("�ҹ���");
				jf.setVisible(false);
			}			
		});
	}
	
}
