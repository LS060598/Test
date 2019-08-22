package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {
	
		 private static final int WIDTH = 120;
		 private static final int HEIGHT = 120;
	
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0); 

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {

	        /*for (String l : model.getLabels()) {
				Debug.print(l);
				Debug.print(",  ");
				Debug.println("");
			}
			for (Range range : model.getRanges()) {
				Debug.print(range.toString());
				Debug.print(",  ");
				Debug.println("");
			}
			for (Data d : model.getList()) {
				Debug.print(d.toString());
				Debug.println("");
			}*/
			
			Graphics2D g2D = (Graphics2D) g;
			g2D.clearRect(0, 0, getWidth(), getHeight());
			
			g2D.setColor(Color.RED);
			g2D.draw(markerRectangle);
			g2D.setColor(Color.BLACK);

			int amount_labels = model.getLabels().size();
			ArrayList<String> labels = model.getLabels(); //Arraylist mit einzelnen Labels

			ArrayList<Data> data = model.getList();
			ArrayList<Point2D> data_point_array = new ArrayList<Point2D>(); //Arraylist mit einzelnen Datenpunkten

			for(int i = 0; i < amount_labels; ++i) {
				for(int j = 0; j < amount_labels; ++j) {
					//Zeichnen der einzelen Labels
					if(i == 0) {
						Font font = new Font ("Arial", Font.BOLD, 10);
						AffineTransform trans = new AffineTransform();
						trans.rotate(-Math.toRadians(90), 0, 0);
						Font rotated_font = font.deriveFont(trans);
						g2D.setFont(rotated_font);
						g2D.drawString(labels.get(j), i * WIDTH + 25, (int) (j * HEIGHT + (85 + HEIGHT/2)));
					} if (j == 0) {
						g2D.setFont(new Font("Arial", Font.BOLD, 10));
						g2D.drawString(labels.get(i), (int) (i * WIDTH + (10 + WIDTH/2)), j * HEIGHT + 25);
					}

					int offset = model.getList().size();
					int count = 0;
					double min_width = 10000.0;
					double max_width = 0.01; 
					double min_height = 10000.0;
					double max_height = 0.01; 
					
					for(Data d: data) {
						// Berechnung der Grenzen der aktuellen Zelle
						if(count < offset) {
							if(d.getValue(i) < min_width) {
								min_width = d.getValue(i);
							} if(d.getValue(i) > max_width) {
								max_width = d.getValue(i);
							} if(d.getValue(j) < min_height) {
								min_height = d.getValue(j);
							} if(d.getValue(j) > max_height) {
								max_height = d.getValue(j);
							}
							min_width  -= min_width  * 0.1;
							min_height -= min_height * 0.1;
							max_width  += max_width  * 0.1;
							max_height += max_height * 0.1;
						}
						count++;
						// erstellen der einzelnen Datenpunkte
						Point2D data_point = new Point2D.Double((int) (((d.getValue(i) - min_width) * HEIGHT) / (max_width - min_width) + i * HEIGHT + 55),
								(int) (((d.getValue(j) - min_height) * WIDTH) / (max_height - min_height) + j * WIDTH + 55));

						data_point_array.add(data_point);
						
						//Einfärben der Punkte in Markierung
						if(markerRectangle.contains(data_point)) {
							d.setColor(Color.RED);
						}
					}
					g2D.setColor(Color.BLACK);
					g2D.draw(new Rectangle2D.Double(i * WIDTH + 50, j * HEIGHT + 50, WIDTH, HEIGHT)); // Zeichnen der einzelnen Rechtecke
					
				}
			}
			//Zeichnen der Datenpunkte
			for(int i = 0; i < data_point_array.size(); ++i) {
				g2D.setColor(data.get(i % data.size()).getColor());
				g2D.drawOval( (int) data_point_array.get(i).getX() - 3, (int) data_point_array.get(i).getY() - 3, 6, 6); 
	}
			
	        
			
		}
		
		public void setModel(Model model) {
			this.model = model;
		}
}
