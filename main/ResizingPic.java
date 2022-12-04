package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;

public class ResizingPic extends JFrame {
	int resize_width = 0;
	int resize_height = 0;

	// 최대 공약수 구하기
//	int gcd(int a, int b){
//		if(b == 0)	return a;
//		else return gcd(b, a%b);
//	}
	public ResizingPic(Mat raw_img){ // 매개변수: 실행 전 이미지 원본
        try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			// 크기변경 프레임 설정
			this.setTitle("크기 변경");
			this.setSize(400, 300);
			this.setLocationRelativeTo(null);
			this.setBackground(new Color(50, 50, 50));
			this.setLayout(new GridLayout(2, 2));
			JButton setBtn = new JButton(new ImageIcon("img/changeIcon.png"));

			// 텍스트필드의 정수값 범위지정
			NumberFormatter nfm = new NumberFormatter();
			nfm.setValueClass(Integer.class);
			nfm.setMaximum(1);
			nfm.setMinimum(3000);

			// 포멧이 정해진 텍스트필드 선언
			JFormattedTextField ftf_width = new JFormattedTextField(nfm);
			ftf_width.setColumns(10);
			ftf_width.setFocusLostBehavior(JFormattedTextField.COMMIT);
			JFormattedTextField ftf_height = new JFormattedTextField(nfm);
			ftf_height.setColumns(10);
			ftf_height.setFocusLostBehavior(JFormattedTextField.COMMIT);

			// openCV 라이브러리 설정
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			// 리사이즈 너비, 높이 입력받기
			this.add(new JLabel("너비"));
			this.add(ftf_width);
			this.add(new JLabel("높이"));
			this.add(ftf_height);
			this.add(setBtn);
			this.setVisible(true);
					
			// raw image gcd
//			var raw_w = raw_img.width();
//			var raw_h = raw_img.height();
//			int raw_gcd = gcd(raw_w, raw_h);
//			System.out.print(raw_w/raw_gcd +" " +raw_h/raw_gcd); // 사용자에게 이미지 비율 출력

			setBtn.addActionListener(e -> {
				try{
					Mat resize_img = new Mat();

					resize_width = Integer.parseInt(ftf_width.getText());
					resize_height = Integer.parseInt(ftf_height.getText());

					//입력확인
					System.out.println("입력한 너비: " + Integer.parseInt(ftf_width.getText()));
					System.out.println("입력한 높이: " + Integer.parseInt(ftf_height.getText()));

					Size sizePic = new Size(resize_width, resize_height); //resize 크기 입력받기
					Imgproc.resize(raw_img, resize_img, sizePic);
					Imgcodecs.imwrite(MenuBar.temp_imgPath, resize_img); //저장

					MenuBar.setImage(MenuBar.temp_imgPath);

					//해당 프레임 하나만 종료
					this.dispose();

				}catch (NumberFormatException n){
					JOptionPane.showMessageDialog(null, "1~3000의 수를 입력하세요");
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
