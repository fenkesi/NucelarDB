package nuclear.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nuclear.function.SearchElem;
import nuclear.operdatabase.OperSQL;

public class SearchUI {
	private JFrame search_ui = null;
	private JLabel sheet_info = null;
	private JLabel items[] = null;
	private JTextField values[] = new JTextField[2];
	private JComboBox<String> jc[] = new JComboBox[2];
	private JButton search = null;
	private JLabel show_time = null;
	private JButton back = null;	
	
	private JFrame main_ui = null;
	
	static final String strs[] = {
		"Z元素",
		"A质量数",
		"实验库",
		"标准库"
	};
	static final String values_info[] = {"例：Fe","例：56"};
	static final int WIDTH = 440;
	static final int HEIGHT = 300;
	
	SearchUI(JFrame main_ui){
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
		JPanel jp1 = new JPanel(new GridLayout(4,2));
		JPanel jp2 = new JPanel(new GridLayout(1,2));
		values = new JTextField[4];
		items = new JLabel[4];
		
		ArrayList<String> tablesName;
		
		tablesName = new OperSQL(OperSQL.DB).showTables();
		
		jc[0] = new JComboBox<String>();
		for(String list:tablesName){
			jc[0].addItem(list);
		}
		
		tablesName = new OperSQL(OperSQL.LIBDB).showTables();
		jc[1] = new JComboBox<String>();
		for(String list:tablesName){
			jc[1].addItem(list);
		}		
		
		sheet_info = new JLabel("数据检索");
		box1.add(sheet_info);
		
		for(int i=0; i<strs.length; i++){
			jp1.add(new JLabel(strs[i]));
			if (i<2){
				values[i] = new JTextField();
				values[i].setText(values_info[i]);
				jp1.add(values[i]);
			}else{
				jp1.add(jc[i-2]);
			}
		}	
		
		box1.add(jp1);
		
		search = new JButton("检索");
		box1.add(search);
		search_ui.add(box1, BorderLayout.NORTH);
		
		show_time = new JLabel();		
		new Thread(new Runnable() {
			public void run() {
				while(true){
				show_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));	
				}
			}
		}).start();
		back = new JButton("返回");
		jp2.add(show_time);
		jp2.add(back);
		
		search_ui.add(jp2, BorderLayout.SOUTH);
	}

	private void containerInit(){
		search_ui = new JFrame("核数据库系统");
		search_ui.setSize(WIDTH, HEIGHT);
		search_ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen_size = kit.getScreenSize();
		int width = screen_size.width;
		int height = screen_size.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		search_ui.setLocation(x,y);
		search_ui.setVisible(true);	
		
	}	
	
	private void addEvent(){
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String textInfo[] = new String[4];
				for(int i=0; i<strs.length; i++){
					if (i<2){
						textInfo[i] = values[i].getText();
					}
					else{
						textInfo[i] = jc[i-2].getSelectedItem().toString();
					}
					
				}
				new SearchElem(textInfo).execute();
			}
		});
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				search_ui.setVisible(false);
				main_ui.setVisible(true);
			}
		});
	}
}
