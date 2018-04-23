import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable
{
	//runnable allows a thread to be run
	
	private static final long serialVersionUID = 1L;
	public int mapWidth = 16;
	public int mapHeight = 16;
	private Thread thread; //allows multiple things to be done at once
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public ArrayList<Texture> textures;
	public Camera camera;
	public Screen screen;
	
	//0 represents no wall, all other numbers represent textures on walls
	//1 is wood
	//2 is greystone
	//3 is redbrick
	//4 is stonewall
	public static int[][] map = 
		{
	       //1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16
			{4,2,2,2,2,2,2,4,4,4,4,2,4,2,2,2},//1
			{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},//2
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},//3
			{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},//4
			{2,0,0,3,0,0,0,0,0,0,3,0,0,0,0,4},//5
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},//5
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},//7
			{4,3,0,0,3,0,0,1,0,0,3,0,0,0,3,2},//8
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},//9
			{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},//10
			{4,0,0,0,3,0,0,0,0,0,3,0,0,0,0,4},//11
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},//12
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},//13
			{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},//14
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},//15
			{4,2,2,2,2,2,2,4,4,4,4,4,4,4,4,2}//16
		};
	
	public Game() 
	{
		thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //connects pixels + image, any time the data values in pixels are changed, corresponding changes appear on image 
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.greystone);
		textures.add(Texture.redbrick);
		textures.add(Texture.stone);
		camera = new Camera(4.5, 4.5, 1, 0, 0, -0.80);
		screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
		addKeyListener(camera);
		setSize(640, 480);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		start();
	}
	
	private synchronized void start() 
	{
		running = true;
		thread.start(); //run the thread
	}
	
	public synchronized void stop() 
	{
		running = false;
		try 
		{
			thread.join(); //wait for the thread to die
		} 
		catch(InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	public void render() 
	{
		BufferStrategy bs = getBufferStrategy(); //used when rendering so that screen updates are smoother
		if(bs == null) 
		{
			createBufferStrategy(3); //3D
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		bs.show();
	}
	
	public void run() 
	{
		long lastTime = System.nanoTime(); //current time
		final double ns = 1000000000.0 / 40.0;//40 times per second
		double delta = 0;
		requestFocus();
		while(running) 
		{
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				//handles all of the logic restricted time
				screen.update(camera, pixels);
				camera.update(map);
				delta--;
			}
			render();//displays to the screen unrestricted time
		}
	}
	
	public static void main(String [] args) {
		Game game = new Game();
	}
}
