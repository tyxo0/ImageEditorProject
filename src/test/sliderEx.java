package test;

import javax.swing.*;
import java.awt.*;

public class sliderEx extends JFrame{
    JSlider slider = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);
    Font myfont = new Font("a도담도담 보통", Font.PLAIN, 12);

    public sliderEx(){
        //프레임 설정
        setTitle("사진 편집기");
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        slider.setBackground(new Color(50, 50, 50));
        slider.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100)); //상, 좌, 하, 우 여백설정
        slider.setValue(0);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        
        //폰트 설정 //필요없을듯
        slider.setFont(myfont);
        slider.setForeground(Color.WHITE);

        add(slider);

        setVisible(true);
    }

    public static void main(String[] args) {
        new sliderEx();
    }
}
