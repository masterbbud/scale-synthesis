package com.display;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class CurvePart {

    private PVector firstVertex;
    private ArrayList<PVector[]> vertices = new ArrayList<>();
    
    public CurvePart(String serialized, float[] maxValues) {
        String[] splits = serialized.split("BEZIER:");

        // handle start
        String[] startPoint = splits[0].split("START:")[1].split(",");
        System.out.println(startPoint[0]);
        firstVertex = new PVector(Float.parseFloat(startPoint[0]), Float.parseFloat(startPoint[1]));

        float maxW = 0;
        float maxH = 0;
        for (int i = 1; i < splits.length; i++) {
            PVector[] vectors = new PVector[3];
            String[] points = splits[i].split(",");
            for (int p = 0; p < 3; p++) {
                vectors[p] = new PVector(Float.parseFloat(points[2*p]), Float.parseFloat(points[2*p + 1]));
                if (p == 2 && Math.abs(Float.parseFloat(points[2*p])) > maxW) {
                    maxW = Math.abs(Float.parseFloat(points[2*p]));
                }
                if (p == 2 && Math.abs(Float.parseFloat(points[2*p + 1])) > maxH) {
                    maxH = Math.abs(Float.parseFloat(points[2*p + 1]));
                }
            }
            vertices.add(vectors);
        }
        maxValues[0] = maxW;
        maxValues[1] = maxH;
    }

    public void draw(PApplet main, float x, float y, float scale) {
        main.beginShape();
        main.vertex(x + firstVertex.x * scale, y + firstVertex.y * scale);
        for (PVector[] vertex : vertices) {
            main.bezierVertex(x + vertex[0].x * scale, y + vertex[0].y * scale, x + vertex[1].x * scale, y + vertex[1].y * scale, x + vertex[2].x * scale, y + vertex[2].y * scale);
        }

        main.endShape();
    }

    public void draw(PGraphics g, float x, float y, float scale) {
        g.beginShape();
        g.vertex(x + firstVertex.x * scale, y + firstVertex.y * scale);
        for (PVector[] vertex : vertices) {
            g.bezierVertex(x + vertex[0].x * scale, y + vertex[0].y * scale, x + vertex[1].x * scale, y + vertex[1].y * scale, x + vertex[2].x * scale, y + vertex[2].y * scale);
        }

        g.endShape();
    }
}
