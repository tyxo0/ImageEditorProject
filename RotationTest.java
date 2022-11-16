import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class RotationTest extends JFrame {
	private static int angle;
	private static String name;
	private JSlider slider = new JSlider(JSlider.HORIZONTAL, -360, 360, 0);
	private JButton btn1 = new JButton("Save");
	private JButton btn2 = new JButton("Preview");
	private JLabel label = new JLabel(Integer.toString(angle));
	private JTextField text = new JTextField("rot");
	
	public RotationTest() {
		setTitle("Image Rotation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new GridLayout(5, 1));
		
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider s = (JSlider)e.getSource();
				angle = s.getValue();
				label.setText(Integer.toString(s.getValue()));
			}
		});
		c.add(slider);
		
		label.setHorizontalAlignment(JLabel.CENTER);
		c.add(label);
		c.add(text);
		
		btn1.addActionListener(new Save());
		c.add(btn1);
		
		btn2.addActionListener(new Preview());
		c.add(btn2);
		
		setSize(500, 200);
		setVisible(true);
	}
	private class Save implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				System.load("C:\\Users\\gyumc\\Desktop\\opencv\\build\\java\\x64\\opencv_java460.dll");
				
				String file = "C:\\Users\\gyumc\\Desktop\\OpenCV_Test\\img.jpg";
				Mat src = Imgcodecs.imread(file);
				Mat dst = new Mat();
				
				if (angle == 90 || angle == -270)
					Core.rotate(src, dst, Core.ROTATE_90_CLOCKWISE);
				else if (angle == 180 || angle == -180)
					Core.rotate(src, dst, Core.ROTATE_180);
				else if (angle == 270 || angle == -90)
					Core.rotate(src, dst, Core.ROTATE_90_COUNTERCLOCKWISE);
				else {
					Point rotPoint = new Point(src.cols() / 2.0, src.rows() / 2.0);
					
					Mat rotMat = Imgproc.getRotationMatrix2D(rotPoint, angle, 1);
					Imgproc.warpAffine(src, dst, rotMat, src.size(), Imgproc.WARP_INVERSE_MAP);
				}
				
				name = text.getText();
				Imgcodecs.imwrite("C:\\Users\\gyumc\\Desktop\\OpenCV_Test\\" + name + ".jpg", dst);
				
				JOptionPane.showMessageDialog(null, "Image Rotated Successfully.", "Image Rotation", JOptionPane.INFORMATION_MESSAGE);
			} catch(Exception ex) {
				System.out.println(ex);
			}
		}
	}
	private class Preview implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				System.load("C:\\Users\\gyumc\\Desktop\\opencv\\build\\java\\x64\\opencv_java460.dll");
				
				String file = "C:\\Users\\gyumc\\Desktop\\OpenCV_Test\\img.jpg";
				Mat src = Imgcodecs.imread(file);
				Mat dst = new Mat();
				
				if (angle == 90 || angle == -270)
					Core.rotate(src, dst, Core.ROTATE_90_CLOCKWISE);
				else if (angle == 180 || angle == -180)
					Core.rotate(src, dst, Core.ROTATE_180);
				else if (angle == 270 || angle == -90)
					Core.rotate(src, dst, Core.ROTATE_90_COUNTERCLOCKWISE);
				else {
					Point rotPoint = new Point(src.cols() / 2.0, src.rows() / 2.0);
					
					Mat rotMat = Imgproc.getRotationMatrix2D(rotPoint, angle, 1);
					Imgproc.warpAffine(src, dst, rotMat, src.size(), Imgproc.WARP_INVERSE_MAP);
				}
				
				JFrame pre = new JFrame();
				pre.setTitle("Preview");
				pre.setSize(1920, 1920);
				pre.setVisible(true);
				pre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JPanel panel = new JPanel();
				
			} catch(Exception ex) {
				System.out.println(ex);
			}
		}
	} 

	public static void main(String[] args) {
		new RotationTest();
	}

}
