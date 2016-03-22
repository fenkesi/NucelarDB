package nuclear.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainUI {
	private JFrame main_ui = null;
	private JButton buttons[] = null;
	private JLabel show_time = null;
	
	static final int WIDTH = 440;
	static final int HEIGHT = 300;
	
	MainUI(){
		//绘制主界面
		init();
	}
	
	private void init(){
		containerInit();
		buttonInit();
		addEvent();
	}
	
	private void containerInit(){
		main_ui = new JFrame("核数据库系统");
		main_ui.setSize(WIDTH, HEIGHT);
		main_ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen_size = kit.getScreenSize();
		int width = screen_size.width;
		int height = screen_size.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		main_ui.setLocation(x,y);
		main_ui.setVisible(true);
		
	}
		
	public void buttonInit(){
		String strs[] = {"实验数据录入", "数据检索","对比绘图", "标准库录入", "退出"};
		buttons = new JButton[5];
		JPanel jp_up = new JPanel(new GridLayout(4,1));
		JPanel jp_down = new JPanel(new GridLayout(1,2));
		
		for(int i=0; i<strs.length - 1; i++){
			buttons[i] = new JButton(strs[i]);
			jp_up.add(buttons[i]);
		}
		
		main_ui.add(jp_up, BorderLayout.NORTH);
		
		show_time = new JLabel();		
		new Thread(new Runnable() {
			public void run() {
				while(true){
				show_time.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));	
				}
			}
		}).start();
		
		
		jp_down.add(show_time);		
		buttons[4] = new JButton(strs[4]);
		jp_down.add(buttons[4]);
		
		main_ui.add(jp_down, BorderLayout.SOUTH);
		
	}	
	
	private void addEvent(){
		buttons[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new LoadDataUI(LoadDataUI.OPERATION_DATA, main_ui);
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				new SearchUI(main_ui);
			}
		});
		
		buttons[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new LoadDataUI(LoadDataUI.OPERATION_LIB, main_ui);
			}
		});
		
		buttons[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainUI();
	}

}
