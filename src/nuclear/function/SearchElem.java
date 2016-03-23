package nuclear.function;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
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
	
	String head[] = {
	"���ݿ�",
	"Zԭ������",
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
		String info [][] = new String[2][15];
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
		
		JMenu jMenu = new JMenu("����");
		JMenuBar MenuBar = new JMenuBar();
		jf.setJMenuBar(MenuBar);
		MenuBar.add(jMenu);		
		
		info[0][0] = "ʵ���";
		
		info[0][1] = data.Z;
		info[0][2] = data.nuclear_info[0];
		info[0][3] = data.A;
		
		for(int i=4; i<head.length; i++){
			info[0][i] = data.nuclear_info[i-3];
		}

		info[1][0] = "��׼��";
		info[1][1] = data.Z;
		info[1][2] = data.nuclear_info[0];
		info[1][3] = data.A;
		
		for(int i=4; i<head.length; i++){
			info[1][i] = data.nuclear_info[i-3];
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

		jMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				File file = new File("����.CHM");
//				try{
//					bf = new BufferedReader(new FileReader(file));
//					String tempString = null;
//					while((tempString = bf.readLine()) != null)
//					{
//						System.out.println(tempString);
//					}
//				}catch(Exception e1){
//					
//				}
//				
				//����ϵͳĬ�ϳ���򿪰����ĵ�
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
}
