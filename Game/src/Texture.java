import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture 
{
	public int[] pixels;
	private String loc;
	public final int SIZE;
	
	public Texture(String location, int size) //for creating objects of textures
	{
		loc = location;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() 
	{
		try 
		{
			BufferedImage image = ImageIO.read(new File(loc)); //read image from location
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static Texture wood = new Texture("res/wood.bmp", 64);
	public static Texture greystone = new Texture("res/greystone.bmp", 64);
	public static Texture redbrick = new Texture("res/redbrick.bmp", 64);
	public static Texture stone = new Texture("res/stonewall.bmp", 64);
}
