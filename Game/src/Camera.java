import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Camera implements KeyListener
{
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left, right, forward, back;
	public double MOVE_SPEED = .08;
	public double ROTATION_SPEED = .045;
	
	public Camera(double x, double y, double xd, double yd, double xp, double yp) 
	{
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
	}
	
	public void keyPressed(KeyEvent key) 
	{
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = true;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = true;
		if((key.getKeyCode() == KeyEvent.VK_UP))
			forward = true;
		if((key.getKeyCode() == KeyEvent.VK_DOWN))
			back = true;
		if((key.getKeyCode() == KeyEvent.VK_CAPS_LOCK))
		{
			//capslock = true;
			System.out.println("SPEED BOOST");
			MOVE_SPEED = .99;
			ROTATION_SPEED = .060;
		}
	}
	
	public void keyReleased(KeyEvent key) 
	{
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = false;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = false;
		if((key.getKeyCode() == KeyEvent.VK_UP))
			forward = false;
		if((key.getKeyCode() == KeyEvent.VK_DOWN))
			back = false;
		if((key.getKeyCode() == KeyEvent.VK_CAPS_LOCK))
		{
			//capslock = false;
			System.out.println("SPEED BOOST GONE");
			MOVE_SPEED = .08;
			ROTATION_SPEED = .045;
		}
	}
	
	public void update(int[][] map) 
	{
		if(forward) 
		{
			if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) 
			{
				xPos+=xDir*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
				yPos+=yDir*MOVE_SPEED;
		}
		if(back) 
		{
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
				xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
				yPos-=yDir*MOVE_SPEED;
		}
		if(right) 
		{
			double oldxDir=xDir;
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
		}
		if(left) 
		{
			double oldxDir=xDir;
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
		}
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
