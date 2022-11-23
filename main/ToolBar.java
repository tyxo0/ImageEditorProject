package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ToolBar extends JToolBar {
    public ToolBar(){
        setOrientation(SwingConstants.VERTICAL);
        setBackground(new Color(50, 50, 50));

        //도구 버튼을 아이콘을 사용해 생성
        JButton[] toolButton = new JButton[9];
        for(int i=0; i<toolButton.length; i++){
            toolButton[i] = new JButton(new ImageIcon("img/icon/toolIcon" + i + ".png"));
            add(toolButton[i]);
        }
        //툴팁 설정
        toolButton[0].setToolTipText("크기 변경");
        toolButton[1].setToolTipText("회전");
        toolButton[2].setToolTipText("밝기 변경");
        toolButton[3].setToolTipText("색상 선택");
        toolButton[4].setToolTipText("채도 변경");
        toolButton[5].setToolTipText("자르기");
        toolButton[6].setToolTipText("그리기");
        toolButton[7].setToolTipText("텍스트 삽입");
        toolButton[8].setToolTipText("사진 추가");

    }
}
