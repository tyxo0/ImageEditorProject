import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ResizingPic {
	int gcd(int a, int b){// 최대 공약수 구하기
		if(b ==0 )	return a;
		else return gcd(b, a%b);
	}
	public ResizingPic(String file_adrr){
		
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        try {
			Mat raw_img = Imgcodecs.imread(file_adrr); //이미지 읽기
			
            
			// Mat imageLenna = Imgcodecs.imread(locLenna);
			Mat resize_img = new Mat();
					
			// Resize
			var raw_w = raw_img.width();
			var raw_h = raw_img.height();
			int raw_gcd = gcd(raw_w, raw_h);

			System.out.print(raw_w/raw_gcd +" " +raw_h/raw_gcd); // 사용자에게 이미지 비율 출력

			Size sizePic = new Size(100, 100); //resize 크기 입력받기 
            Imgproc.resize(raw_img, resize_img, sizePic);
			
			// HighGui.imshow("raw", raw_img);
			// HighGui.imshow("resize", resize_img);
            Imgcodecs.imwrite(file_adrr.substring(0, file_adrr.length()-4) + 
            "_resize" +".png", resize_img); //저장
			
			// HighGui.waitKey();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public static void main(String[] args) {
		// new ResizingPic("KakaoTalk_20220628_163120775.jpg");
		new ResizingPic("C:\\Users\\roxno\\Desktop\\cartoon-city-landscape-night-view_52683-69417.png");
		// String locLenna = "C:\\Users\\roxno\\Desktop\\cartoon-city-landscape-night-view_52683-69417.png";
			// String locLenna = "cartoon-city-landscape-night-view_52683-69417.png";
    }
}
