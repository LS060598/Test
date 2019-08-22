package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

import java.util.Iterator;


public class Fisheye implements Layout{
	
	private double fishEyeCenterX = 250;// x-Koordinate der Mitte
	private double fishEyeCenterY = 240;//y-Koordinate der Mitte
	private double d = 5;// Grad der Verzerrung    //5 vll besser

	public void setMouseCoords(int x, int y, View view) {
		fishEyeCenterX = x;// x-Koordinate wird gesetzt
		fishEyeCenterY = y;// y-Koordinate wird gesetzt
		transform(view.getModel(), view);//Transformation view-models
	}

	public Model transform(Model model, View view) {//Modelltransformation
		for(int i = 0; i < model.getVertices().size(); ++i) {
			Vertex current = model.getVertices().get(i); //Jetziger Knoten bekommen
			double pFishX = calcFish(view.getWidth(), fishEyeCenterX, current.getX()); // x-Koordinate fisheye berechnen
			double pFishY = calcFish(view.getHeight(), fishEyeCenterY, current.getY());// y-Koordinate fisheye berechnen

			double qNormX = current.getX() + current.getWidth()/2; // Normierung der Q-Koordinaten 
			double qNormY = current.getY() + current.getHeight()/2;// qNorm = Pnorm + Snorm/2

			double qFishX = calcFish(view.getWidth(), fishEyeCenterX, qNormX); // Fisheye koordinaten für q berechnen
			double qFishY = calcFish(view.getHeight(), fishEyeCenterY, qNormY);

			double sGeom = 2 * Math.min(Math.abs(qFishX - pFishX), Math.abs(qFishY - pFishY)) * 0.05; // Geometriche Größe berrechnen

			current.setFrame(pFishX, pFishY, current.getWidth() * sGeom, current.getHeight() * sGeom); //Setz frame mit fisheye 
			model.getVertices().set(i, current);
		}
		view.setModel(model);
		view.repaint();

		return model;
		//return null;
	}
	
	public double calcFish(double pBorder, double pFocus, double pNorm) {// von normalaisierten zu fisheye-Koordinaten
		double dMax = calcDMax(pBorder, pFocus, pNorm);
		double dNorm = pNorm - pFocus; // Horizontale Distanz zwischen transformiertem Punkt und Fokus (alles normalisiert)
		double g = calcG(dNorm / dMax);

		return pFocus + g * dMax; // dMax unnormalisiert die fisheye-Koordinaten 
	}
	
	public double calcDMax(double pBorder, double pFocus, double pNorm) {// Horizontale Distanz zwischen Bildschirmgrenze
																		//und normalisierte Focuskoordinaten
		double dMax = 0;
		if (pNorm > pFocus) {
			dMax = pBorder - pFocus;
		}	
		else {
			dMax = 0 - pFocus;
		}
		
		return dMax;
	}
	
	public double calcG(double x) { // Berechnet G
		return ((d + 1) * (x)) / ((d * x) + 1);
	}
	
	
}
