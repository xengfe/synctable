package com.yeecare.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


import com.yeecare.master.service.ICrmBloodglucoseService;
import com.yeecare.slave.service.IBloodglucoseService;


public class Client {
	private static final int SCREEN_HEIGHT = ScreenSize.getScreenHeight();
	private static final int SCREEN_WIDTH = ScreenSize.getScreenWidth();
	private static final int FRAME_HEIGHT = SCREEN_HEIGHT / 2;
	private static final int FRAME_WIDTH = SCREEN_WIDTH / 2;

	private static Timer timer ;
	private static JFrame frame;
	private static int windowWidth, windowHeight, locationX, locationY;
	private static boolean isbegin = false;
	private static boolean isRunable = false;
	private static ToastDialog toastDialog;
	private static int period ;
	private static JButton startButton;
	private static JButton stopButton;
	private static JTextField periodTextField;
	
	@Resource
	private  ICrmBloodglucoseService masterService;
	
	@Resource
	private  IBloodglucoseService slaveService;

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			frame = new JFrame("数据库同步管理终端");
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			windowWidth = frame.getWidth();
			windowHeight = frame.getHeight();
			locationX = (SCREEN_WIDTH - windowWidth) / 2;
			locationY = (SCREEN_HEIGHT - windowHeight) / 2;
			frame.setLocation(locationX, locationY);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBackground(Color.white);
			JTextArea ta = new JTextArea();
			ta.setEditable(false);
			TextAreaOutputStream2 taos = new TextAreaOutputStream2(ta, DateStrUtl.getFormatDate(new Date()) + "  log");
			PrintStream ps = new PrintStream(taos);
			System.setOut(ps);
			System.setErr(ps);
			frame.add(new JScrollPane(ta));
			
			frame.setVisible(true);
			System.out.println("application start run");
			initMenuBar();
			initMenuListener();
			System.out.println("application component init successfully");
		}
	});
		
		
	}

	/**
	 * @param frame
	 * @param windowWidth
	 * @param windowHeight
	 */
	public static void initMenuBar() {
		JMenuBar menubar = new JMenuBar();
		periodTextField = new JTextField();
		
		periodTextField.setToolTipText("请输入整数...");
		periodTextField.setDisabledTextColor(Color.lightGray);
		periodTextField.setEnabled(!isRunable);
		menubar.add(periodTextField);

		startButton = new JButton("开始");
		stopButton = new JButton("暂停");

		startButton.setEnabled(!isRunable);
		stopButton.setEnabled(isRunable);
		
		menubar.add(startButton);
		menubar.add(stopButton);

		frame.setJMenuBar(menubar);
	}

	private static void initMenuListener() {
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startSyncDataBase();
			}
		});

		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopSyncDataBase();
			}
		});

	}
	
	



	protected  static void stopSyncDataBase() {
		if (isbegin ) {
			timer.cancel();
			isbegin = false;
		}else {
			toastDialog = new ToastDialog(frame, "please start application before stop !");
			toastDialog.show();
		}
		
		isRunable = false;
		
		periodTextField.setEnabled(!isRunable);
		
		startButton.setEnabled(!isRunable);

		stopButton.setEnabled(isRunable);
		
	}

	protected  static void startSyncDataBase() {
		
		if (!isbegin) {
			int delay = 0;// 毫秒
			int time = (period == 0 ? 10: period )* 1000;// 1s

			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					
				}

			};
			timer = new Timer();
			timer.schedule(task, delay, time);
			isbegin = true;
			
		}else {
			System.out.println("application is running...");
		}

		isRunable = true;
		
		periodTextField.setEnabled(!isRunable);
		
		startButton.setEnabled(!isRunable);

		stopButton.setEnabled(isRunable);
	}
	
	private static boolean isValid(String str){
		boolean flag = false;
		if (!str.equals("") && str != null) {
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 正则表达式：判断是否数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 }
	
	static class ToastDialog extends JDialog {
		private String msg;

		private static final long serialVersionUID = 1L;

		public ToastDialog(JFrame frame) {
			super(frame);
			initToastDialogComponents(frame);
		}
		
		public ToastDialog (JFrame frame,String showMsg){
			super(frame);
			this.msg = showMsg;
			initToastDialogComponents(frame);
			
		}

		/**
		 * @param frame
		 */
		private void initToastDialogComponents(JFrame frame) {
			this.setSize(300, 150);
			this.setLocationRelativeTo(frame);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			Container c = this.getContentPane();
			c.setLayout(new BorderLayout());
			
			JPanel titlePanel = new JPanel();
			titlePanel.setLayout(new FlowLayout());
			titlePanel.add(new JLabel("操作提醒"));
			c.add(titlePanel,"North");
			
			JPanel fieldPanel = new JPanel();
			fieldPanel.setLayout(null);
			
			JLabel hintLabel = new JLabel(msg);
			hintLabel.setBounds(40, 5, 200,30);
			hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
			hintLabel.setVerticalAlignment(SwingConstants.CENTER);
			
			fieldPanel.add(hintLabel);
			c.add(fieldPanel,"Center");
			
			JButton ok = new JButton("确定");
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.add(ok);
			c.add(buttonPanel,"South");
		}
		
	}

}
