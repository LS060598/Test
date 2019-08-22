package infovis.scatterplot;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;
	private int x_start = 0;// X-Koordinate Maus
	private int y_start = 0;// Y-Koordinate Maus

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
	//Maus ist gedrückt
	public void mousePressed(MouseEvent arg0) {
		x_start = arg0.getX();
		y_start = arg0.getY();
		
	}
	
	//Loslassen der Maus
	public void mouseReleased(MouseEvent arg0) {
		int x_end = arg0.getX();
		int y_end = arg0.getY();

		view.getMarkerRectangle().setRect(x_start, y_start, x_end-x_start, y_end-y_start); // Values für Markerrechteck geholt

		view.repaint();// neu gezeichnet
		for (Data d: model.getList()) {
			d.setColor(Color.BLACK);
		}
    }
	

	public void mouseDragged(MouseEvent arg0) {
		//view.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void setModel(Model model) {
		this.model  = model;	
	}

	public void setView(View view) {
		this.view  = view;
	}

}
