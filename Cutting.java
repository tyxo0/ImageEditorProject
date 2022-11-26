import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Cutting {
    public Cutting(String file_addr){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try{
            Mat raw_img = new Mat();
            Mat cutted_img = new Mat();

            raw_img = Imgcodecs.imread(file_addr);
            System.out.println(raw_img.size());

            // 자르기 -> 시작점 끝점 입력 받고 그 영역 남기기
            Point inpPoint1 = new Point(1000,0); //사용자 지정한 점 1 
            Point inpPoint2 = new Point(300,200); // 점2
            // Point inpPoint1 = new Point(2500,1500); //사용자 지정한 점 1 
            // Point inpPoint2 = new Point(3500,2000); // 점2
            
            Point startPoint = new Point();
            Point endPoint = new Point();

            startPoint.x = (inpPoint1.x <= inpPoint2.x) ? inpPoint1.x : inpPoint2.x; // start point에 최소 점들 할당
            startPoint.y= (inpPoint1.y <= inpPoint2.y) ? inpPoint1.y : inpPoint2.y; 
            endPoint.x = (inpPoint1.x >= inpPoint2.x) ? inpPoint1.x : inpPoint2.x; // endpoint에 최대 점들 할당
            endPoint.y= (inpPoint1.y >= inpPoint2.y) ? inpPoint1.y : inpPoint2.y; 
            // System.out.println(startPoint);
            // System.out.println(endPoint);

            cutted_img = raw_img.submat((int)startPoint.y, (int)endPoint.y,(int)startPoint.x, (int)endPoint.x);
            System.out.println(cutted_img.size());
            Imgcodecs.imwrite("cutted.jpg", cutted_img);
            HighGui.imshow("initImg", cutted_img);
            HighGui.waitKey();


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // new Cutting("KakaoTalk_20220628_163120775.jpg");
        new Cutting("hsv_cover.png");
    }
}
