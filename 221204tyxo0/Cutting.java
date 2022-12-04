package main;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class Cutting {
    public Cutting(String file_addr, Point inpPoint1, Point inpPoint2){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        System.out.println("cutting");
        try{
            Mat raw_img = new Mat();
            Mat cutted_img = new Mat();

            raw_img = Imgcodecs.imread(file_addr);
            // System.out.println(raw_img.size());

            
            Point startPoint = new Point();
            Point endPoint = new Point();

            startPoint.x = (inpPoint1.x <= inpPoint2.x) ? inpPoint1.x : inpPoint2.x; // start point에 최소 점들 할당
            startPoint.y= (inpPoint1.y <= inpPoint2.y) ? inpPoint1.y : inpPoint2.y; 
            endPoint.x = (inpPoint1.x >= inpPoint2.x) ? inpPoint1.x : inpPoint2.x; // endpoint에 최대 점들 할당
            endPoint.y= (inpPoint1.y >= inpPoint2.y) ? inpPoint1.y : inpPoint2.y; 
            // System.out.println(startPoint);
            // System.out.println(endPoint);

            cutted_img = raw_img.submat((int)startPoint.y, (int)endPoint.y,(int)startPoint.x, (int)endPoint.x); // 자른 결과
            
            Mat square_img = new Mat() ; //=raw_img; //사각형 그린 이미지
            raw_img.copyTo(square_img); //사각형 그린 이미지
            Imgproc.rectangle(square_img, inpPoint1, inpPoint2,new Scalar(255,255,255),5);

            // System.out.println(cutted_img.size());
            MenuBar.temp_imgPath = file_addr.substring(0, file_addr.length()-4) +
            "_cutted" +file_addr.substring(file_addr.length()-4, file_addr.length()); //파일명 변경
            
            
            // Imgcodecs.imwrite(MenuBar.temp_imgPath, square_img); // 사각형이미지 저장
            // MenuBar.setImage(MenuBar.temp_imgPath); // 사각형 그린 이미지 출력
            
            Imgcodecs.imwrite(MenuBar.temp_imgPath, cutted_img); //자른 이미지 저장
            MenuBar.setImage(MenuBar.temp_imgPath); // 자른 이미지 출력
            

            //ui로 확인 버튼 누르면 저장 되게
            
            
            

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    // public static void main(String[] args) {
    //     // new Cutting("KakaoTalk_20220628_163120775.jpg");
    //     new Cutting("hsv_cover.png");
    // }
}
