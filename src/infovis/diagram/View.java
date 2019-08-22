package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import javax.swing.JPanel;



public class View extends JPanel{
	private Model model = null;
	private Color color = Color.BLUE;
	private double scale = 1;
	private double translateX = 0;
	private double translateY = 0;
	private double overviewX = 0;
	private double overviewY = 0;
	private double markerX = 0;
	private double markerY = 0;
	private Rectangle2D marker = new Rectangle2D.Double();
	private Rectangle2D overviewRect = new Rectangle2D.Double();
	private double overviewScale = 5;
	private Graphics2D graphics;

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		graphics = g2D;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		//clear background
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		//Sclae image
		//setScale(2);
		g2D.scale(getScale(), getScale());
		
		//translate image and paint image
		g2D.translate(-getTranslateX(), -getTranslateY());
		
		paintDiagram(g2D);
		
		//translate back so overview stays in place
		g2D.translate(getTranslateX(), getTranslateY());
		
		//scale to overview size
		g2D.scale((1/getScale())/overviewScale, (1/getScale())/overviewScale);

		g2D.clearRect((int)overviewX, (int)overviewY, (int)getDrawingWidth(), (int)getDrawingHeight());
		
		//draw marker
		marker = new Rectangle2D.Double(markerX+overviewX, markerY+overviewY, getWidth()/scale, getHeight()/scale);
		g2D.setColor(Color.GREEN);
		g2D.fill(marker);
		
		g2D.setStroke(new BasicStroke(1));
		overviewRect = new Rectangle2D.Double(overviewX, overviewY, getDrawingWidth(), getDrawingHeight());
		
		g2D.setColor(Color.BLACK);
		g2D.draw(overviewRect);
		
		g2D.translate(overviewX, overviewY);
		paintDiagram(g2D);
	}
	private void paintDiagram(Graphics2D g2D){
		for (Element element: model.getElements()){
			element.paint(g2D);
		}
	}
	
	//Transform methods
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale(){
		return scale;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double translateY) {
		this.translateY = translateY;
	}
	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}
	
	//Marker methods
	public double getMarkerX() {
		return markerX;
	}
	public void setMarkerX(double overviewX) {
		this.markerX = overviewX;
	}
	public double getMarkerY() {
		return markerY;
	}
	public void setMarkerY(double markerY) {
		this.markerY = markerY;
	}
	public void updateMarker(int x, int y){
		setMarkerX(x);
		setMarkerY(y);
	}
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}
	
	//Overview methods
	public double getOverviewScale(){
		return overviewScale;
	}
	public boolean overviewContains(int x, int y){
		return overviewRect.contains(x, y);
	}	
	public double getOverviewX() {
		return overviewX;
	}
	public void setOverviewX(double overviewX) {
		this.overviewX = overviewX;
	}
	public double getOverviewY() {
		return overviewY;
	}
	public void setOverviewY(double overviewY) {
		this.overviewY = overviewY;
	}
	public void updateOverview(double x, double y){
		setOverviewX(x);
		setOverviewY(y);
	}
	
	public double getDrawingHeight() {
		double y = 0;
		for (Element element: model.getElements()){
			if(element.getY() >= y) {
				y = element.getY();
			}
		}
		y += 100;
		return y;
	}
	public double getDrawingWidth() {
		double x = 0;
		for (Element element: model.getElements()){
			if(element.getX() >= x) {
				x = element.getX();
			}
		}
		x+= 100;
		return x;
	}
}
 