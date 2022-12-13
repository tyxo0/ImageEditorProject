package test;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class colorsetting extends JFrame implements ChangeListener {
    JColorChooser colorChooser = new JColorChooser();
    JPanel panel = new JPanel();
    colorsetting(){
        setTitle("색상 예제");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1400, 800);

        colorChooser.getSelectionModel().addChangeListener(this);
        colorChooser.setBorder(BorderFactory.createTitledBorder("색상 선택"));

        panel.add(colorChooser);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new colorsetting();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Color selectedColor = colorChooser.getColor(); //색상 선택기의 현재 컬러값 반환
    }
}
