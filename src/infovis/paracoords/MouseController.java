package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.diagram.elements.Element;
import infovis.diagram.elements.None;
import infovis.scatterplot.Data;
import java.awt.*;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.Iterator;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model= null;
	Shape currentShape = null;
	private int x_start = 0; // x-Position der Maus
	private int y_start = 0; // y-Position der Maus
	
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	//Maus gedrückt
	public void mousePressed(MouseEvent e) { 
		x_start = e.getX(); //Positionsdaten des Zeigers bekommen (x,y)
		y_start = e.getY();
		
	}
    
	// Maus wird losgelassen
	public void mouseReleased(MouseEvent e) {
		int x_end = e.getX(); // Positionsdaten des Zeigers bekommen
		int y_end = e.getY();

		view.getMarkerRectangle().setRect(x_start, y_start, x_end-x_start, y_end-y_start); // Markerrechteck mit gesammelten Zeigerdaten
		view.repaint(); // neu gezeichnet
		
		// Farbsetzung
		for (Data d: model.getList()) {
			d.setColor(Color.BLACK);
		}
	}

	public void mouseDragged(MouseEvent e) {
		//view.repaint();

	}

	public void mouseMoved(MouseEvent e) {

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	

}
