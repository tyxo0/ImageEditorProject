package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;
import java.util.Stack;

class MyMouseListener implements MouseListener, MouseMotionListener {

    // public void mousePressed(MouseEvent e) {

    // }

    public void mouseClicked(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e){

    }

    public void mouseMoved(MouseEvent e){
        // System.out.println("moved " + clickBool);

    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

};
public class ToolButtons implements MouseListener {
    JButton[] toolButton = new JButton[9];
    boolean clickBool;
    int resizeCNT = 0;
    Painter painter;
    Mat init_mat;  //resize 이미지 깨짐 방지를 위한 백업
    JLabel error_Label = new JLabel("불러온 이미지가 없습니다");
    JFileChooser combine_fileChooser = new JFileChooser();
    Font font = new Font("a도담도담 보통", Font.BOLD, 16);
    MyMouseListener mouselistener ; // buttons에서 쓸 mouselistener
    public ToolButtons(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MenuBar.imgLabel.addMouseListener(mouselistener);
        //툴바 버튼에 아이콘을 붙이고, 각 버튼에 리스너 등록
        for(int i=0; i<toolButton.length; i++){
            toolButton[i] = new JButton(new ImageIcon("img/icon/tool/toolIcon" + i + ".png"));
            toolButton[i].addMouseListener(this);
        }
        error_Label.setFont(font);

        UIManager.put("OptionPane.background",new ColorUIResource(255,255,255));
        UIManager.put("Panel.background",new ColorUIResource(255,255,255));
    }

    @Override
    public void mouseClicked(MouseEvent e){
        // 툴 버튼들을 클릭했을 때 각 이벤트 처리코드
        JButton btn = (JButton)e.getSource();
        if(btn == toolButton[0]){
            //크기변경 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Main.img_stack.clear();
                MenuBar.imgLabel.removeMouseListener(mouselistener);

                if(resizeCNT != 0){
                    new Resizing(init_mat);
                }
                else{
                    init_mat = Imgcodecs.imread(MenuBar.temp_imgPath);
                    new Resizing(init_mat);
                }
                resizeCNT ++;

                mouselistener = new MyMouseListener();
                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

        if(btn == toolButton[1]){
            //회전 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                Main.img_stack.clear();
                MenuBar.imgLabel.removeMouseListener(mouselistener);

                new Rotation(MenuBar.temp_imgPath);

                mouselistener = new MyMouseListener();
                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

        if(btn == toolButton[2]){
            //색상선택 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                Main.img_stack.clear();
                MenuBar.imgLabel.removeMouseListener(mouselistener);

                new ColorEditor(MenuBar.temp_imgPath);

                mouselistener = new MyMouseListener();
                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

        if(btn == toolButton[3]){
            //밝기변경 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                Main.img_stack.clear();
                MenuBar.imgLabel.removeMouseListener(mouselistener);

                new BrightnessEditor(MenuBar.temp_imgPath);

                mouselistener = new MyMouseListener();
                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

        if(btn == toolButton[4]){
            //채도변경 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                MenuBar.imgLabel.removeMouseListener(mouselistener);

                new SaturationEditor(MenuBar.temp_imgPath);

                mouselistener = new MyMouseListener();
                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

        if(btn == toolButton[5]){
            final Cutting[] cut = new Cutting[1];
            Cutting secondcut;
            final boolean[] cutBool = {true};
            //자르기 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                Main.img_stack.clear();
                MenuBar.imgLabel.removeMouseListener(mouselistener);
                Point starPoint = new Point();
                Point endPoint = new Point();

                JLabel cutting_Info = new JLabel("자를 부분을 드래그 하세요");
                cutting_Info.setFont(font);
                JOptionPane.showMessageDialog(null, "  자를 부분을 드래그 하세요", "자르기", JOptionPane.INFORMATION_MESSAGE);

                mouselistener = new MyMouseListener(){
                    public void mousePressed(MouseEvent e){
                        if(!cutBool[0]){
                            cut[0].dispose();
                            cutBool[0] = true;
                        }
                        clickBool= true;
                        starPoint.x = (e.getX() -((double)MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag;
                        starPoint.y =(e.getY() -((double)MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag;
                    }
                    public void mouseReleased(MouseEvent e){
                        if(clickBool){
                            if(cutBool[0]){
                                endPoint.x = (e.getX() -((double)MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag;
                                endPoint.y = (e.getY() -((double)MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag;
                                cut[0] = new Cutting(MenuBar.temp_imgPath, starPoint, endPoint);
                                cut[0].preview();
                                clickBool = false;
                                cutBool[0] = false;
                            }
                        }
                    }
                };

                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

        if(btn == toolButton[6]){
            //그리기 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                Main.img_stack.clear();

                MenuBar.imgLabel.removeMouseListener(mouselistener);
                Stack <Point> draggedPoints = new Stack<>(); //점 모아둔 stack
                painter = new Painter(MenuBar.temp_imgPath);

                mouselistener = new MyMouseListener(){
                    public void mouseReleased(MouseEvent e){ // 점 전달해서 이미지 수정
                        System.out.println("Realeased");

                        painter.pointDrawer(draggedPoints, painter.inp_Color, painter.angle);
                        MenuBar.setImage(MenuBar.temp_imgPath);  // 수정된 이미지 불러옴
                        System.out.println(painter.painterStack.size());
                    }
                    public void mouseDragged(MouseEvent e){  //점 저장

                        Point tempPoint = new Point((e.getX() -((double)MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag,
                                (e.getY() -((double)MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag);
                        if(!draggedPoints.contains(tempPoint))
                            draggedPoints.push(tempPoint);
                    }
                };

                // 언도 버튼 이벤트
                painter.undoBtn.addActionListener(e16 -> {
                    try{
                        System.out.println("뒤로가기 실행됨");
                        painter.backKey();
                        MenuBar.setImage(MenuBar.temp_imgPath); // 이전 이미지 불러옴
                        if(painter.painterStack.empty()){
                            painter.painterStack.push(painter.backgroundImg);
                        }
                    }catch(Exception e1){
                        e1.printStackTrace();
                    }
                });

                // 펜 색상 라디오버튼 이벤트
                painter.colorRBtn[0].addActionListener(e17 -> painter.inp_Color = "Red");
                painter.colorRBtn[1].addActionListener(e12 -> painter.inp_Color = "Orange");
                painter.colorRBtn[2].addActionListener(e13 -> painter.inp_Color = "Yellow");
                painter.colorRBtn[3].addActionListener(e14 -> painter.inp_Color = "Green");
                painter.colorRBtn[4].addActionListener(e18 -> painter.inp_Color = "Cyan");
                painter.colorRBtn[5].addActionListener(e15 -> painter.inp_Color = "Blue");
                painter.colorRBtn[6].addActionListener(e19 -> painter.inp_Color = "Purple");
                painter.colorRBtn[7].addActionListener(e110 -> painter.inp_Color = "Black");
                painter.colorRBtn[8].addActionListener(e111 -> painter.inp_Color = "White");

                // 펜 굵기 슬라이더 이벤트
                painter.Color_slider.addChangeListener(re -> {
                    JSlider s = (JSlider)re.getSource();
                    painter.angle = s.getValue();
                    painter.angle_Label.setText(Integer.toString(s.getValue()));
                });

                MenuBar.imgLabel.addMouseMotionListener(mouselistener);
                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

        if(btn == toolButton[7]) {
            //글자 삽입 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                MenuBar.imgLabel.removeMouseListener(mouselistener);

                if (Main.img_stack.empty()) {
                    Mat img = Imgcodecs.imread(MenuBar.temp_imgPath);
                    Main.img_stack.push(img);
                }
                PuttingText text = new PuttingText();
                mouselistener = new MyMouseListener() {
                    public void mouseClicked(MouseEvent e) {
                        text.puttext(MenuBar.temp_imgPath, (e.getX() - ((double) MenuBar.imgLabel.getWidth() - MenuBar.resizeWidth) / 2) * MenuBar.ui_imgMag, (e.getY() - ((double) MenuBar.imgLabel.getHeight() - MenuBar.resizeHeight) / 2) * MenuBar.ui_imgMag);
                        MenuBar.setImage(MenuBar.temp_imgPath);
                        MenuBar.imgLabel.removeMouseListener(this);
                    };
                };
                MenuBar.imgLabel.addMouseListener(mouselistener);
                text.deleteBtn.addActionListener(e112 -> {
                    text.dispose();
                    MenuBar.imgLabel.removeMouseListener(mouselistener);
                });
            }
        }

        if(btn == toolButton[8]){
            //사진 합성 구현
            if(Objects.equals(MenuBar.imgPath, "")){
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resizeCNT = 0;
                Main.img_stack.clear();
                MenuBar.imgLabel.removeMouseListener(mouselistener);

                Combiner combiner =  new Combiner(MenuBar.temp_imgPath);
                String sticker_Path = "";

                // 사진 선택
                combine_fileChooser.setDialogTitle("사진합성 불러오기");
                combine_fileChooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리를 지정
                combine_fileChooser.setAcceptAllFileFilterUsed(true);   // Fileter 모든 파일 적용
                combine_fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // 파일 선택 모드(단일 파일만 선택가능)

                // filter 확장자 추가
                FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter(".jpg", "jpg");
                FileNameExtensionFilter pngFilter = new FileNameExtensionFilter(".png", "png");
                combine_fileChooser.setFileFilter(jpgFilter);
                combine_fileChooser.setFileFilter(pngFilter);

                //열기용 창 만들기
                int retVal = combine_fileChooser.showOpenDialog(null);

                if(retVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭
                    sticker_Path = combine_fileChooser.getSelectedFile().getPath();
                    String finalSticker_Path = sticker_Path;

                    JLabel click_Info = new JLabel("위치를 클릭하세요");
                    click_Info.setFont(font);
                    JOptionPane.showMessageDialog(null, click_Info, "사진 합성", JOptionPane.INFORMATION_MESSAGE);

                    mouselistener = new MyMouseListener(){
                        public void mouseClicked(MouseEvent e){ // 점 전달해서 이미지 수정
                            combiner.replace_img(finalSticker_Path,
                                    (int)((e.getX() -(MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag)
                                    , (int)((e.getY() -(MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag)
                            );
                            MenuBar.setImage(MenuBar.temp_imgPath); // 수정된 이미지 불러옴
                        }

                    };
                }

                MenuBar.imgLabel.addMouseListener(mouselistener);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}