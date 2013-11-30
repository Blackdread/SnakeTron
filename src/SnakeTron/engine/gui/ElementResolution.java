package SnakeTron.engine.gui;

import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

import SnakeTron.engine.Game;
import SnakeTron.utils.Configuration;
import SnakeTron.utils.Resolution;
import SnakeTron.views.OptionsView;

public class ElementResolution extends Elements{

	private Resolution resolution;
	
	public ElementResolution(GUIContext container, Image image, Shape shape, int withReso, int heightReso) {
		super(container, image, shape);
		// TODO Auto-generated constructor stub
		resolution = new Resolution(withReso,heightReso);
	}

	public ElementResolution(GUIContext container, Image image, int x, int y, int withReso, int heightReso) {
		super(container, image, x, y);
		// TODO Auto-generated constructor stub
		resolution = new Resolution(withReso,heightReso);
	}

	public ElementResolution(GUIContext container, Image image, int x, int y, int withReso, int heightReso,
			ComponentListener listener) {
		super(container, image, x, y, listener);
		// TODO Auto-generated constructor stub
		resolution = new Resolution(withReso,heightReso);
	}
	// ********** CELUI que j'utiliserai le plus souvent ***********
	public ElementResolution(GUIContext container, Image image, int x, int y, int withReso, int heightReso,
			int width, int height) {
		super(container, image, x, y, width, height);
		// TODO Auto-generated constructor stub
		resolution = new Resolution(withReso,heightReso);
	}
	
	public ElementResolution(GUIContext container, Image image, int x, int y, int withReso, int heightReso,
			int width, int height, ComponentListener listener) {
		super(container, image, x, y, width, height, listener);
		// TODO Auto-generated constructor stub
		resolution = new Resolution(withReso,heightReso);
	}
	
	@Override
	public boolean isMouseOver(){
		if(super.isMouseOver()){
			if(!resolution.equals(new Resolution(container.getWidth(),container.getHeight())) && resolution.getHeight() >= Game.MINIMUM_SCREEN_HAUTEUR){
				try {
				/*	try {
						Display.setDisplayMode(new DisplayMode(resolution.getWidth(), resolution.getHeight()));
					} catch (LWJGLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					Game.changeResolution(resolution.getWidth(), resolution.getHeight());
					Game.rechargerToutesLesRessources();
					Configuration.setWidth(resolution.getWidth());
					Configuration.setHeight(resolution.getHeight());
					try {
						Configuration.saveNewConfig();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					OptionsView.setErrorChangeScreenTrue();
				}
			}
			
			return true;
		}
		return false;
		
	}
	
	public boolean isElementUsed(){
		return (container.getWidth() == resolution.getWidth() && container.getHeight() == resolution.getHeight());
	}
	
	@Override
	public void render(GUIContext container, Graphics g){
		super.render(container, g);
		g.drawString(""+resolution.toString(), super.getX()+25, super.getY() + super.getHeight()/4);
	}
	public void render(GUIContext container, Graphics g, int x, int y){
		super.render(container, g);
		g.drawString(""+resolution.toString(), x+25, y + super.getHeight()/4);
	}
	@Override
	public void renderString(GUIContext container, Graphics g, int x, int y) {
		g.drawString(""+resolution.toString(), x+25, y + super.getHeight()/4 + 6);
	}
}
