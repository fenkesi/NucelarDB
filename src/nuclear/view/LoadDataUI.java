package nuclear.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nuclear.function.StoreExcelData;
import nuclear.operdatabase.OperExcel;
import nuclear.operdatabase.OperSQL;

public class LoadDataUI{
	private JFrame data_ui = null;
	private JLabel load_info = null;
	private JTextField exper_name = null;
//	private JButton auto_load = null;
	private JLabel dir = null;
	private JButton browser = null;
	private JButton file_load = null;
	private JLabel show_time = null;
	private JButton back = null;
	
	private String head_info[] = {"实验数据录入", "标准库据录入"};
	private String text_info[] = {"输入实验数据名称", "输入标准库名称"};
	private JFrame main_ui = null;
	
	private int operation;
	public static final int OPERATION_DATA = 0;
	public static final int OPERATION_LIB = 1;
	
	static final int WIDTH = 220;
	static final int HEIGHT = 300;
	
	public LoadDataUI(int operation, JFrame main_ui) {
		
		this.main_ui = main_ui;
		
		this.operation = operation;
		
		// TODO Auto-generated constructor stub
		init();
		addEvent();
		
	}
	
	private void init(){
		
		containerInit();
		contentInit();
		main_ui.setVisible(false);
	}
	
	private void contentInit(){
		JPanel container = new JPanel(new BorderLayout());
		Box box = Box.createVerticalBox();
		JPanel jp_file = new JPanel(new FlowLayout());
		JPanel jp_down =  new JPanel(new GridLayout(1,2));
		
		load_info = new JLabel(head_info[operation]);
		box.add(load_info);		
		exper_name = new JTextField(text_info[operation]);
		box.add(exper_name);		
//		auto_load = new JButton("自动录入");
//		box.add(auto_load);
		
		dir = new JLabel();
		dir.setText("文件目录");
		browser = new JButton(" ... ");
		jp_file.add(dir);
		jp_file.add(browser);		
		box.add(jp_file);
		
		file_load = new JButton("文件录入");
		box.add(file_load);
		container.add(box, BorderLayout.NORTH);
		
		String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		show_time = new JLabel(time);
		back = new JButton("返回");
		jp_down.add(show_time);
		jp_down.add(back);		

		
		container.add(jp_down, BorderLayout.SOUTH);
		data_ui.add(container);
	}
	
	private void containerInit(){
		data_ui = new JFrame(head_info[operation]);
		data_ui.setSize(WIDTH, HEIGHT);
		data_ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen_size = kit.getScreenSize();
		int width = screen_size.width;
		int height = screen_size.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		data_ui.setLocation(x,y);
		data_ui.setVisible(true);	
		
	}
	
	private void addEvent(){
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				data_ui.setVisible(false);
				main_ui.setVisible(true);
			}
		});
		
		browser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showDialog(new JLabel(), "选择文件");
				
				try{
					File file = jfc.getSelectedFile();				
					dir.setText(file.getAbsolutePath());
					System.out.println("Directory:" + dir.getText());
					
				}catch(Exception e1){
					System.out.println("Wrong!");
				}
				finally{}												
			}
		});
		
		file_load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new StoreExcelData(dir.getText(), operation, exper_name.getText()).execute();
			}
		});
	}
	
}
