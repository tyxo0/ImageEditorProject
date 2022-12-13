package main;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JToolBar {
    ToolButtons buttons = new ToolButtons();
    public ToolBar(){
        setOrientation(SwingConstants.VERTICAL);
        setFloatable(false);
        setBackground(new Color(50, 50, 50));

        //도구 버튼을 생성
        for(int i=0; i<buttons.toolButton.length; i++){
            add(buttons.toolButton[i]);
        }
        //툴팁 설정
        buttons.toolButton[0].setToolTipText("크기 변경");
        buttons.toolButton[1].setToolTipText("회전");
        buttons.toolButton[2].setToolTipText("색상 선택");
        buttons.toolButton[3].setToolTipText("밝기 변경");
        buttons.toolButton[4].setToolTipText("채도 변경");
        buttons.toolButton[5].setToolTipText("자르기");
        buttons.toolButton[6].setToolTipText("그리기");
        buttons.toolButton[7].setToolTipText("텍스트 삽입");
        buttons.toolButton[8].setToolTipText("사진 추가");
    }
}
