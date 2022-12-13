package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Resizing extends JFrame {
	int resize_width = 0;
	int resize_height = 0;

	public Resizing(Mat raw_img){ // 매개변수: 실행 전 이미지 원본
        try {
			// openCV 라이브러리 설정
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			// 폰트 설정
			Font font = new Font("맑은 고딕", Font.BOLD, 14);
			JLabel resizing_Info = new JLabel("1~3000의 정수를 입력하세요");
			JLabel resizing_Info2 = new JLabel("정수를 입력하세요");
			resizing_Info.setFont(font);
			resizing_Info2.setFont(font);

			// 수정 전 너비와 높이
			float raw_w = raw_img.width();
			float raw_h = raw_img.height();

			// 배경 이미지 설정
			ImageIcon background_img = new ImageIcon("img/background/bg03.jpg");
			Image bg_img = background_img.getImage();
			Image temp_img = bg_img.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
			background_img = new ImageIcon(temp_img);
			JLabel back = new JLabel(background_img);
			back.setLayout(null);
			back.setBounds(0, 0, 400, 300);

			// 크기변경 프레임 설정
			this.setTitle("크기 변경");
			this.setUndecorated(true);
			this.setSize(400, 300);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			getRootPane().setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));  //테두리 설정
			this.setLayout(null);

			// 프레임 드래그 이동 설정
			FrameDragListener frameDragListener = new FrameDragListener(this);
			addMouseListener(frameDragListener);
			addMouseMotionListener(frameDragListener);
			setLocationRelativeTo(null);
			setVisible(true);

			// 텍스트필드의 포멧지정
			NumberFormatter formatter = new NumberFormatter();
			formatter.setValueClass(Integer.class);

			// 포멧이 정해진 텍스트필드 선언
			JFormattedTextField ftf_width = new JFormattedTextField(formatter);
			ftf_width.setSize(150, 50);
			ftf_width.setLocation(150, 30);
			ftf_width.setColumns(5);
			ftf_width.setFocusLostBehavior(JFormattedTextField.COMMIT);

			JFormattedTextField ftf_height = new JFormattedTextField(formatter);
			ftf_height.setSize(150, 50);
			ftf_height.setLocation(150, 90);
			ftf_height.setColumns(5);
			ftf_height.setFocusLostBehavior(JFormattedTextField.COMMIT);

			// 레이블 설정
			JLabel width_Label = new JLabel("너비");
			width_Label.setFont(font);
			width_Label.setForeground(Color.WHITE);
			width_Label.setSize(50, 50);
			width_Label.setLocation(75, 30);

			JLabel height_Label = new JLabel("높이");
			height_Label.setFont(font);
			height_Label.setForeground(Color.WHITE);
			height_Label.setSize(50, 50);
			height_Label.setLocation(75, 90);

			final JLabel beforeWidth_Label = new JLabel("원본 너비: " + raw_w);
			beforeWidth_Label.setFont(font);
			beforeWidth_Label.setForeground(Color.WHITE);
			beforeWidth_Label.setSize(200, 50);
			beforeWidth_Label.setLocation(120, 140);

			final JLabel beforeheight_Label = new JLabel("원본 높이: " + raw_h);
			beforeheight_Label.setFont(font);
			beforeheight_Label.setForeground(Color.WHITE);
			beforeheight_Label.setSize(200, 50);
			beforeheight_Label.setLocation(120, 160);

			// 확인버튼 설정
			JButton setBtn = new JButton("변경");
			setBtn.setFont(font);
			setBtn.setBackground(Color.LIGHT_GRAY);
			setBtn.setSize(120, 40);
			setBtn.setLocation(70, 220);
			setBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			// 취소버튼 설정
			JButton deleteBtn = new JButton("취소");
			deleteBtn.setFont(font);
			deleteBtn.setBackground(Color.LIGHT_GRAY);
			deleteBtn.setSize(120, 40);
			deleteBtn.setLocation(211, 220);
			deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			this.add(width_Label);
			this.add(ftf_width);
			this.add(height_Label);
			this.add(ftf_height);
			this.add(beforeWidth_Label);
			this.add(beforeheight_Label);
			this.add(setBtn);
			this.add(deleteBtn);
			this.add(back);  //항상 다른 컴포넌트 add후에 이것을 add

			this.setVisible(true);

			setBtn.addActionListener(e -> {
				// 숫자가 아닌 값이 들어오면 예외처리
				try{
					// 천의 자리 이상 숫자 입력 시 붙는 콤마(,)를 제거
					String width_onlyNum = ftf_width.getText().replaceAll(",", "");
					String heigth_onlyNum = ftf_height.getText().replaceAll(",", "");
					resize_width = Integer.parseInt(width_onlyNum);
					resize_height = Integer.parseInt(heigth_onlyNum);

					// 숫자 크기 검사
					if(resize_width <= 3000 && resize_height <= 3000){
						if(resize_width >= 1 && resize_height >= 1){
							System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
							Mat resize_img = new Mat();

							// 값 확인용
//							System.out.println("ftf_width.getText(): " + ftf_width.getText());
//							System.out.println("ftf_height.getText(): " + ftf_height.getText());
//							System.out.println("width_onlyNum: " + width_onlyNum);
//							System.out.println("heigth_onlyNum: " + heigth_onlyNum);

							//입력확인
							System.out.println("입력한 너비: " + resize_width);
							System.out.println("입력한 높이: " + resize_height);

							Size sizePic = new Size(resize_width, resize_height); //resize 크기 입력받기
							Imgproc.resize(raw_img, resize_img, sizePic);
							Imgcodecs.imwrite(MenuBar.temp_imgPath, resize_img); //저장

							MenuBar.setImage(MenuBar.temp_imgPath);

							//해당 프레임 하나만 종료
							this.dispose();
						}else JOptionPane.showMessageDialog(null, resizing_Info);
					} else JOptionPane.showMessageDialog(null, resizing_Info);
				}catch (NumberFormatException n){
					JOptionPane.showMessageDialog(null, resizing_Info2);
				}
			});
			deleteBtn.addActionListener(e -> {
				try{
					//해당 프레임 하나만 종료
					this.dispose();

				}catch (NumberFormatException n){
					JOptionPane.showMessageDialog(null, "알 수 없는 오류!");
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 타이틀바 없어도 프레임의 드래그 이동이 가능하도록 이벤트 설정
	public static class FrameDragListener extends MouseAdapter {

		private final JFrame frame;
		private Point mouseDownCompCoords = null;

		public FrameDragListener(JFrame frame) {
			this.frame = frame;
		}

		public void mouseReleased(MouseEvent e) {
			mouseDownCompCoords = null;
		}

		public void mousePressed(MouseEvent e) {
			mouseDownCompCoords = e.getPoint();
		}

		public void mouseDragged(MouseEvent e) {
			Point currCoords = e.getLocationOnScreen();
			frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
		}
	}
}
