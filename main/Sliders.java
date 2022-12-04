package main;

import javax.swing.*;
import java.awt.*;

public class Sliders extends JSlider {
    int angle;

    public Sliders(int horizontal, int min, int max){
        // 슬라이더 설정
        setOrientation(horizontal); //수평 슬라이드
        setMinimum(min);  //최소값
        setMaximum(max);  //최대값
        setValue(0);  //초기값(=중앙값)

        JLabel label = new JLabel(Integer.toString(angle));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        setBackground(new Color(50, 50, 50));
        setBorder(BorderFactory.createEmptyBorder(0, 200, 70, 200)); //상, 좌, 하, 우 여백설정
        setMajorTickSpacing(60);
        setMinorTickSpacing(30);
        setPaintTicks(true);

        addChangeListener(e -> {
            JSlider s = (JSlider)e.getSource();
            angle = s.getValue();
            label.setText(Integer.toString(s.getValue()));
        });
    }

}
