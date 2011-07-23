package streammz.map.maptest.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.bukkit.maps.*;

public class ServerStaticBase implements MapRenderer {
	int tick = 0;
	
	int count = 0;
	
	public void initialize(MapView map) {
	}

	public void render(MapView map, MapCanvas canvas) {
		System.out.println("render...");
		
		map.setRate(128);
		map.setPrintOrder(MapPrintOrder.Sequential);
		
		if ((tick++ % 60) != 0) return;
		else tick = 0;
		
		count++;
		
		BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		g.setColor(Color.WHITE);
		g.drawString(""+count, (int)(145-(g.getFontMetrics().getStringBounds(""+count, g).getWidth())), 20);
		
		g.dispose();
		
		map.getDrawer().drawImage(img);
		
		return;
	}

}
