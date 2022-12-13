package copy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

class cColor
{
	static public Color mColor = new Color(255, 255, 255);
}

class cRect
{
	Point mPoint = new Point();
	Point mOld = new Point();
	Point mDest = new Point();
	
	Color mColor;
	
	cRect(Point _pos)
	{
		mPoint = _pos;
		mOld.x = _pos.x;
		mOld.y = _pos.y;
		
		mColor = cColor.mColor;
	}
	
	void setDest(Point _dest)
	{
		if (mPoint.x > _dest.x)
		{
			mOld.x = _dest.x;
			mDest.x = Math.abs(mPoint.x - _dest.x);
		}
		else
			mDest.x = Math.abs(mPoint.x - _dest.x);
			
		if (mPoint.y > _dest.y)
		{
			mOld.y = _dest.y;
			mDest.y = Math.abs(mPoint.y - _dest.y);
		}
		else
			mDest.y = Math.abs(mPoint.y - _dest.y);
	}
	
	void draw(Graphics2D g)
	{
		g.setColor(mColor);
		g.drawRect(mOld.x, mOld.y, mDest.x, mDest.y);
	}
}


class cPanel extends JPanel
{	
	LinkedList<cRect> mList = new LinkedList<cRect>();
	
	cPanel()
	{
		addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e) 
			{
				// TODO Auto-generated method stub
				super.mousePressed(e);
				mList.add(new cRect(e.getPoint()));
				repaint();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				// TODO Auto-generated method stub
				super.mouseReleased(e);
				repaint();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() 
		{
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				// TODO Auto-generated method stub
				super.mouseDragged(e);
				mList.getLast().setDest(e.getPoint());
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
		Graphics2D g2 = (Graphics2D)arg0.create();
		for(cRect row : mList)
		{
			row.draw(g2);
		}
	}
}

public class Example03 {
	static JMenuBar mMenuBar = new JMenuBar();
	static JMenu mMenu = new JMenu("Color");
	
	static ButtonGroup mGroup = new ButtonGroup();
	static JRadioButtonMenuItem mRed = new JRadioButtonMenuItem("Red");
	static JRadioButtonMenuItem mGreen = new JRadioButtonMenuItem("Green");
	static JRadioButtonMenuItem mBlue = new JRadioButtonMenuItem("Blue");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("test");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(new cPanel());
		
		mMenuBar.add(mMenu);
		mMenu.add(mRed);
		mMenu.add(mGreen);
		mMenu.add(mBlue);
		mGroup.add(mRed);
		mGroup.add(mGreen);
		mGroup.add(mBlue);
		
		mRed.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				cColor.mColor = Color.RED;
			}
		});
		
		mGreen.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				cColor.mColor = Color.GREEN;
			}
		});

		mBlue.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				cColor.mColor = Color.BLUE;
			}
		});

		frame.setJMenuBar(mMenuBar);
		
		frame.setVisible(true);
	}

}
