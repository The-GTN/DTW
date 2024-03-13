package dtw;

/**
 * @author <a href="mailto:gery.casiez@univ-lille.fr">Gery Casiez</a>
 */

import java.util.Vector;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.image.Image;

public class DTWGUI extends Application{
	GraphicsContext gc;
	Canvas canvas;
	Vector<Point2D> userGesture = new Vector<Point2D>();

	DTW dtw = new DTW();
	TemplateManager manager = new TemplateManager();
	Vector<Template> templates;


	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage stage) {

		manager.loadFile("media/data/gestures.xml");
		templates = manager.getTemplates();

		VBox root = new VBox();
		canvas = new Canvas (600, 700);
		gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		canvas.setOnMousePressed(e -> {
			userGesture.clear();
			redrawMyCanvas();			
		});
		
		canvas.setOnMouseDragged(e -> {
			userGesture.add(new Point2D(e.getX(), e.getY()));
			redrawMyCanvas();
		});
		
		canvas.setOnMouseReleased(e -> {
			
			if (userGesture.size() > 0) {
				Vector<Point2D> candidate = templates.elementAt(0).getPoints();
				String name = templates.elementAt(0).getName();
				Matrix D = dtw.D(userGesture,candidate);
				double theMin = D.items[D.nRows-1][D.nCols-1];

				for(int i = 1; i< templates.size(); i++){
					Vector<Point2D> candidate2 = templates.elementAt(i).getPoints();
					String name2 = templates.elementAt(i).getName();
					Matrix D2 = dtw.D(userGesture,candidate2);
					double min = D2.items[D2.nRows-1][D2.nCols-1];
					if (min < theMin) {
						candidate = candidate2;
						name = name2;
						theMin = min;
						D = D2;
					}
				}

				redrawMyCanvas();
				redrawTemplate(candidate,D,name);
			}

		});

		Scene scene = new Scene(root);
		stage.setTitle("Universite de Lille - M2 RVA - IHMA - Dynamic Time Warping - G. Casiez");
		//stage.getIcons().add(new Image("../media/pictures/logo.jpg"));
		stage.setScene(scene);
		stage.show();
	}
	
	public void redrawMyCanvas() {
		double r = 5.0;
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for (int i=1; i<userGesture.size(); i++) {
			gc.setStroke(Color.BLACK);
			gc.strokeLine(userGesture.elementAt(i-1).getX(), userGesture.elementAt(i-1).getY(),
					userGesture.elementAt(i).getX(), userGesture.elementAt(i).getY());
			gc.strokeOval(userGesture.elementAt(i-1).getX() - r, userGesture.elementAt(i-1).getY() - r, 2*r, 2*r);
		}
	}

	public void redrawTemplate(Vector<Point2D> template,Matrix D, String name) {
		double r = 5.0;
		System.out.println(name);

		for (int i=1; i<template.size(); i++) {
			gc.setStroke(Color.ORANGE);
			gc.strokeLine(template.elementAt(i-1).getX(), template.elementAt(i-1).getY(),
					template.elementAt(i).getX(), template.elementAt(i).getY());
			gc.strokeOval(template.elementAt(i-1).getX() - r, template.elementAt(i-1).getY() - r, 2*r, 2*r);
		}

		Couple c = D.couple[userGesture.size()-1][template.size()-1];
		while (c != null) {
			gc.setStroke(Color.ORANGE);
			gc.strokeLine(userGesture.elementAt(c.x).getX(), userGesture.elementAt(c.x).getY(), 
				template.elementAt(c.y).getX(), template.elementAt(c.y).getY());
			c = D.couple[c.x][c.y];
		}

	}




}
