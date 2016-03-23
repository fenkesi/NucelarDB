package nuclear.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import nuclear.function.SearchElem;
import nuclear.operdatabase.OperSQL;

public class ModifyDBUI {
	private JFrame modify_ui = null;
	JLabel items[];
	private JComboBox<String> jc[] = new JComboBox[2];
	private JButton delete = null;
	private JLabel show_time = null;
	private JButton back = null;
	private JFrame main_ui = null;
	private JLabel sheet_info;
	
	static final String strs[] = {
		"ʵ���",
		"��׼��"
	};
	static final int WIDTH = 440;
	static final int HEIGHT = 300;
	
	
	BufferedReader bf = null;
	
	ModifyDBUI(JFrame main_ui){
		this.main_ui = main_ui;
		init();
	}
	
	private void init(){
		containerInit();
		contentInit();
		addEvent();
		main_ui.setVisible(false);
		
	}
	private void contentInit(){
		Box box1 = Box.createVerticalBox();
		JPanel jp1 = new JPanel(new GridLayout(2,2));
		JPanel jp2 = new JPanel(new GridLayout(1,2));
		items = new JLabel[4];
		
		ArrayList<String> tablesName;
		
		//�����
		tablesName = new OperSQL(OperSQL.DB).showTables();
		
		jc[0] = new JComboBox<String>();
		jc[0].addItem("");
		for(String list:tablesName){
			jc[0].addItem(list);
		}
		
		//��׼��
		tablesName = new OperSQL(OperSQL.LIBDB).showTables();
		jc[1] = new JComboBox<String>();
		jc[1].addItem("");
		for(String list:tablesName){
			jc[1].addItem(list);
		}		
		
		sheet_info = new JLabel("���ݿ�ɾ��");
		box1.add(sheet_info);
		
		for(int i=0; i<strs.length; i++){
			jp1.add(new JLabel(strs[i]));
			jp1.add(jc[i]);
		}	
		
		box1.add(jp1);
		
		delete = new JButton("ɾ��");
		box1.add(delete);
		modify_ui.add(box1, BorderLayout.NORTH);
		
		show_time = new JLabel();		
		new Thread(new Runnable() {
			public void run() {
				while(true){
				show_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));	
				}
			}
		}).start();
		back = new JButton("����");
		jp2.add(show_time);
		jp2.add(back);
		
		modify_ui.add(jp2, BorderLayout.SOUTH);
	}

	private void containerInit(){
		modify_ui = new JFrame("�����ݿ�ϵͳ");
		modify_ui.setSize(WIDTH, HEIGHT);
		modify_ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen_size = kit.getScreenSize();
		int width = screen_size.width;
		int height = screen_size.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		modify_ui.setLocation(x,y);
		modify_ui.setVisible(true);	
				
	}	
	
	private void addEvent(){
		
		delete.addActionListener(new ActionListener() {
			String str_info[][] = {{"���ݿ�ɾ��ʧ��", "���ݿ�ɾ���ɹ�"},{"�޸�ʵ�����ݿ�", "�޸ı�׼��"}};
			
			int result;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String textInfo[] = new String[2];
				for(int i=0; i<strs.length; i++){
					textInfo[i] = jc[i].getSelectedItem().toString();
					if(textInfo[i].equals("")) continue;
					result = new OperSQL(i).deleteTable(textInfo[i]);
					JOptionPane.showMessageDialog(null, str_info[0][result], str_info[1][i],
				              JOptionPane.YES_NO_OPTION);
				}
				init();
			}
		});
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modify_ui.setVisible(false);
				main_ui.setVisible(true);
			}
		});
		
	}
}
