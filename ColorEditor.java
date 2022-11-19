import java.util.HashMap;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
public class ColorEditor {


//hashmap으로 만들기
public ColorEditor(String str)  {// 색상 변경
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat raw_img = new Mat();
    Mat imgHSV = new Mat();
    Mat result_img = new Mat();
    Mat out_img = new Mat();

    HashMap <String,Color_Range> color_Map = new HashMap<>();
    // hue(색상): 0~180, saturation(채도), value(밝기):0~255
    // 초록색 계열 hue: 30~83
    color_Map.put("Red",new Color_Range(170, 10)); // 빨간색: 170~10
    color_Map.put("Orange",new Color_Range(10, 20));// 주황색: 10~20
    color_Map.put("Yellow",new Color_Range(20, 40));// 노란색: 20~40 /
    color_Map.put("Green",new Color_Range(40, 80));// 초록색: 40~ 80 // 30~83
    color_Map.put("Cyan",new Color_Range(80, 110));// 하늘색: 80 110 // 70~ 110
    color_Map.put("Blue",new Color_Range(110, 130));// 파란색: 110 130
    color_Map.put("Purple",new Color_Range(130, 150));// 보라색: 130 150
    color_Map.put("Pink",new Color_Range(150, 170));// 핑크색: 150 170

    //색상: color_Map.mid를 중앙값으로 놓고 스크롤로 조절
    //밝기: 0을 부터 최대값 255
    //채도: 0부터 255 
    try{
            // raw_img = Imgcodecs.imread("hsl-cover-2.png");
            raw_img = Imgcodecs.imread("KakaoTalk_20220628_163120775.jpg");
            Imgproc.cvtColor(raw_img, imgHSV, Imgproc.COLOR_BGR2HSV); //HSV로 이미지 변환
            //Imgcodecs.imwrite("Output_0.jpg", imgHSV);
            imgHSV.copyTo(result_img);
            //imgHSV = Imgcodecs.imread("KakaoTalk_20220628_163120775.jpg");
            
            int r =raw_img.rows();
            int c =raw_img.cols();
            
            //**** 변경 값 ****//
            String color_Key = "Green"; // 변경할 색상
            int delta_val = 40; // 0~180 //스크롤로 색상 조절

            int min_Val = color_Map.get(color_Key).min;
            int max_Val =color_Map.get(color_Key).max;
            // int mid_Val =color_Map.get(color_Key).mid;

            if(color_Key.equals("Red")){
                for(int i =0; i<r; i++){
                    for(int j =0; j<c; j++) {
                        double[] data = imgHSV.get(i, j); //Stores element in an array
                    // System.out.print(data[0] +" "); 
                    if(data[0]>=min_Val || data[0]<max_Val){
                        var color_val =data[0]+delta_val;
                        if(color_val>=180) data[0] =color_val -180;
                        else data[0] =color_val;
                    }
                    result_img.put(i, j, data); //Puts element back into matrix
                    }
                }
            }
            else{
                for(int i =0; i<r; i++){
                    for(int j =0; j<c; j++) {
                        double[] data = imgHSV.get(i, j); //Stores element in an array 
                        if(data[0]>=min_Val && data[0]<max_Val){
                            var color_val =data[0]+delta_val;
                            if(color_val>=180) data[0] =color_val -180;
                            else data[0] =color_val;
                        }
                    result_img.put(i, j, data); //Puts element back into matrix
                    }
                }
            }
            Imgproc.cvtColor(result_img, out_img, Imgproc.COLOR_HSV2BGR);
            Imgcodecs.imwrite("Output.jpg", out_img);
            
            //HighGui.waitKey();
            

    }catch (Exception e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        new ColorEditor("green");
    }

}
