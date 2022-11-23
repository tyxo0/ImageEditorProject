package main;

import javax.swing.*;
import java.awt.*;

public class ImgLabel extends JLabel {
    int resizeHeight = 700;
    int resizeWidth;

    public ImgLabel(){
        setHorizontalAlignment(CENTER);
        //높이에 대한 너비의 비율을 구하여 이미지 리사이즈
        ImageIcon imgIcon = new ImageIcon("img/sample.png");
        double ratio = (double)imgIcon.getIconWidth() / (double)imgIcon.getIconHeight();
//        System.out.println("이미지 비율:" + ratio);
        ratio = Math.round(ratio * 1000) / 1000.0;  //넷째자리에서 반올림
//        System.out.println("반올림 후:" + ratio);
        resizeWidth = (int)((double)resizeHeight*ratio);
//        System.out.println("바뀔 너비" + resizeWidth);
//        System.out.println("바뀔 높이" + resizeHeight);

        //이미지라벨 설정
        Image img = imgIcon.getImage();
        Image resized_Img = img.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_SMOOTH);
        ImageIcon resized_imgIcon = new ImageIcon(resized_Img);
        setIcon(resized_imgIcon);
    }
}
//높이를 기준으로 이미지의 리사이즈를 할 때는 높이가 고정되니까 높이가 너무 큰 이미지를 처리할 수 있다.
//반대로 너비가 큰 이미지는 너비를 기준으로 높이를 변경하면 이미지를 처리할 수 있다.
//이미지 비율에 따라 크기 조절

//이미지 이동
//이미지 위에 마우스 커서가 눌린 지점을 기준으로 이미지를 이동

//이미지가 확대/축소되는 기능
//확대를 하지 않았을 때는 축소가 불가능, 최대로 축소할 수 있는 크기의 기준은 이미지의 리사이즈 크기로
//최대 확대의 기준은 일정크기 만큼