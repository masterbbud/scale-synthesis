package com;

import java.io.File;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;

import javax.swing.JFileChooser;

import com.comparators.LayerClickComparator;
import com.comparators.LayerDrawComparator;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class BezierOverlay extends PApplet {
    
    protected static final int WIDTH = 2000;
    protected static final int HEIGHT = 1500;
    protected static final int FRAME_RATE = 30;
    protected ArrayList<Element> elements;
    protected boolean isMousePressed = false;

    private PImage chosenImage = null;

    protected static final float pointRadius = 10;
    private int[] pointColor = new int[]{ 255, 0, 0 };
    private int[] oldPointColor = new int[]{ 255, 100, 100 };

    private int[] lastLineColor = new int[]{ 200, 200, 255 };
    private int[] nextLineColor = new int[]{0, 255, 0 };

    private int bounceDistance = 50;

    private int gridMode = 0; // off, light, medium, fine

    private PVector centerPoint = new PVector(0,0);

    // last point
    // previous angle
    // moving point
    // next angle
    private ArrayList<PVector> points = new ArrayList<>();

    // i hope nobody sees this ever
    private ArrayList<ArrayList<ArrayList<PVector>>> savedPoints = new ArrayList<>();

    private int currentCurve = 0;

    private int selectedPoint = 2; // -1 specifies center, -2 specifies none
    
    public static void main(String[] args) {
        PApplet.main(new String[] {"com.BezierOverlay"});
    }

    public void settings() {
        size(WIDTH, HEIGHT);
        
        elements = new ArrayList<Element>();
    }

    public void setup() {
        frameRate(FRAME_RATE);
        ellipseMode(CENTER);

        for (int i = 0; i < 4; i++) {
            points.add(new PVector(0,0));
        }

        savedPoints.add(new ArrayList<>());
    }

    public void draw() {
        background(64);
        elements.sort(new LayerDrawComparator());
        for (Element e : elements) {
            if (e.isHidden()) {
                continue;
            }
            e.draw();
        }
        elements.sort(new LayerClickComparator());
        boolean isSomethingHovered = false;
        for (Element e : elements) {
            if (e.isHidden()) {
                continue;
            }
            if (e.tryHover(isSomethingHovered)) {
                isSomethingHovered = true;
            }
            if (e.tryClick()) {
                isMousePressed = true;
            }
        }

        if (mousePressed) {

            mousePressedHandler();
        }
        else {
            if (selectedPoint >= 0) {
                selectedPoint = -2;
            }
        }

        if (chosenImage != null) {
            imageMode(CENTER);
            image(chosenImage, WIDTH / 2, HEIGHT / 2, (float)(HEIGHT * 0.5) * ((float)chosenImage.width / chosenImage.height), (float)(HEIGHT * 0.5));
        }

        strokeWeight(2);

        for (int lst = 0; lst < savedPoints.size() - 1; lst ++) {
            for (ArrayList<PVector> l : savedPoints.get(lst)) {
                drawBezier(l, oldPointColor);
            }
        }
        for (ArrayList<PVector> lst : savedPoints.get(currentCurve)) {
            drawBezier(lst, pointColor);
        }

        drawLine(points.get(0), points.get(1), pointColor);
        drawLine(points.get(2), points.get(3), pointColor);

        strokeWeight(2);

        drawPoint(points.get(0), true, true);
        drawPoint(points.get(1), false, false);
        drawPoint(points.get(2), true, false);
        drawPoint(points.get(3), false, false);

        drawBezier(points, pointColor);

        drawOverlayGrid();

        drawCenterPoint();
        drawGuideLine();
    }

    private void mousePressedHandler() {
        if (selectedPoint == -1) { // selecting center
            centerPoint.x = mouseX - WIDTH / 2;
            centerPoint.y = mouseY - HEIGHT / 2;
        }
        else {

            // ignore last point, which you can't move
            int startIndex = 1;
            // unless we are on the first point, in which case you can move it
            if (savedPoints.get(currentCurve).size() == 0) {
                startIndex = 0;
            }
            if (selectedPoint == -2) {
                if (pointClicked(points.get(2)) && 2 != selectedPoint) {
                    selectedPoint = 2;
                    return;
                }
                for (int p = startIndex; p < 4; p++) {
                    if (pointClicked(points.get(p)) && p != selectedPoint) {
                        selectedPoint = p;
                    }
                }
            }
            else {
                points.get(selectedPoint).x = mouseX - WIDTH / 2;
                points.get(selectedPoint).y = mouseY - HEIGHT / 2;
            }
        }
    }

    private void drawGuideLine() {
        stroke(pointColor[0], pointColor[1], pointColor[2], 70);
        strokeWeight(2);

        if (savedPoints.get(currentCurve).size() == 0) {
            return;
        }

        ArrayList<PVector> lastSet = savedPoints.get(currentCurve).get(savedPoints.get(currentCurve).size() - 1);

        PVector diff = PVector.sub(lastSet.get(3), lastSet.get(2));
        diff.normalize();

        line(points.get(0).x - WIDTH * diff.x + WIDTH / 2, points.get(0).y - WIDTH * diff.y + HEIGHT / 2, points.get(0).x + WIDTH * diff.x + WIDTH / 2, points.get(0).y + WIDTH * diff.y + HEIGHT / 2);
    }

    private void drawCenterPoint() {
        if (selectedPoint == -1) {
            stroke(lastLineColor[0], lastLineColor[1], lastLineColor[2], 200);
        }
        else {
            stroke(lastLineColor[0], lastLineColor[1], lastLineColor[2], 70);
        }
        strokeWeight(2);
        line(centerPoint.x - 10 + WIDTH / 2, centerPoint.y + HEIGHT / 2, centerPoint.x + 10 + WIDTH / 2, centerPoint.y + HEIGHT / 2);
        line(centerPoint.x + WIDTH / 2, centerPoint.y - 10 + HEIGHT / 2, centerPoint.x + WIDTH / 2, centerPoint.y + 10 + HEIGHT / 2);
    }

    private void drawOverlayGrid() {
        stroke(200, 200, 200, 50);
        strokeWeight(1);
        int width = 0;
        switch (gridMode) {
            case 0:
                return;
            case 1:
                width = 200;
                break;
            case 2:
                width = 80;
                break;
            case 3:
                width = 20;
                break;
        }
        for (int y = 0; y < HEIGHT; y += width) {
            line(0, y, WIDTH, y);
        }
        for (int x = 0; x < WIDTH; x += width) {
            line(x, 0, x, HEIGHT);
        }
    }

    private void drawBezier(ArrayList<PVector> points, int[] color) {
        stroke(color[0], color[1], color[2]);
        noFill();
        bezier(points.get(0).x + WIDTH / 2, points.get(0).y + HEIGHT / 2, points.get(1).x + WIDTH / 2, points.get(1).y + HEIGHT / 2, points.get(3).x + WIDTH / 2, points.get(3).y + HEIGHT / 2, points.get(2).x + WIDTH / 2, points.get(2).y + HEIGHT / 2);
    }

    private void drawLine(PVector point1, PVector point2, int[] color) {
        stroke(color[0], color[1], color[2]);
        line(point1.x + WIDTH / 2, point1.y + HEIGHT / 2, point2.x + WIDTH / 2, point2.y + HEIGHT / 2);
    }

    private void drawPoint(PVector point, boolean filled, boolean first) {
        
        if (first) {
            fill(255, 255, 255);
            stroke(0, 0, 0);
        }
        else if (filled) {
            fill(pointColor[0], pointColor[1], pointColor[2]);
            stroke(pointColor[0], pointColor[1], pointColor[2]);
        }
        else {
            noFill();
            stroke(pointColor[0], pointColor[1], pointColor[2]);
        }
        ellipseMode(CENTER);
        ellipse(point.x + WIDTH / 2, point.y + HEIGHT / 2, pointRadius, pointRadius);
    }

    private boolean pointClicked(PVector point) {
        if (Math.pow((point.x + WIDTH / 2 - mouseX), 2) + Math.pow((point.y + HEIGHT / 2 - mouseY), 2) < Math.pow(pointRadius, 2)) {
            return true;
        }
        return false;
    }

    public void mouseReleased() {
        isMousePressed = false;
    }

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public float fixedDeltaTime() {
        return 1f / FRAME_RATE;
    }

    private void nextPoint() {
        ArrayList<PVector> savedList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PVector n = new PVector(points.get(i).x, points.get(i).y);
            savedList.add(n);
        }
        savedPoints.get(currentCurve).add(savedList);

        points.get(1).x = points.get(2).x;
        points.get(1).y = points.get(2).y;

        points.get(0).x = points.get(2).x;
        points.get(0).y = points.get(2).y;
        points.get(3).x = points.get(2).x;
        points.get(3).y = points.get(2).y;

        selectedPoint = 2;
    }

    public void keyPressed() {
        if (key == 'o') { // open file
            chooseFile();
        }
        else if (key == ENTER) { // next point
            nextPoint();
        }
        else if (key == 'r') { // reset selected point
            if (selectedPoint >= -1) {
                points.get(selectedPoint).x = points.get(0).x;
                points.get(selectedPoint).y = points.get(0).y;
                selectedPoint = -2;
            }
        }
        else if (key == 'f') { // finish the current loop
            if (savedPoints.get(currentCurve).size() == 0) {
                return;
            }
            points.get(2).x = savedPoints.get(currentCurve).get(0).get(0).x;
            points.get(2).y = savedPoints.get(currentCurve).get(0).get(0).y;
            nextPoint();
            points.get(1).x = points.get(0).x;
            points.get(1).y = points.get(0).y;
            currentCurve ++;
            savedPoints.add(new ArrayList<>());
        }
        else if (key == 'e') { // export to terminal
            export();
            
        }
        else if (key == 'h') { // hole / jump

        }
        else if (key == 'g') { // toggle grid
            gridMode = (gridMode + 1 ) % 4;
        }
        else if (key == 'c') { // select center of graphic
            if (selectedPoint == -1) {
                selectedPoint = 2;
            }
            else {
                selectedPoint = -1;
            }
        }
        else if (key == BACKSPACE) { // delete last point
            handleDelete();
        }
    }

    public void handleDelete() {
        int currentIndex = savedPoints.get(currentCurve).size();
        if (currentIndex == 0) {
            for (int i = 1; i < 4; i++) {
                points.get(i).x = points.get(0).x;
                points.get(i).y = points.get(0).y;
            }
        }
        else {
            for (int i = 0; i < 4; i++) {
                points.get(i).x = savedPoints.get(currentCurve).get(currentIndex - 1).get(i).x;
                points.get(i).y = savedPoints.get(currentCurve).get(currentIndex - 1).get(i).y;
            }
            savedPoints.get(currentCurve).remove(currentIndex - 1);
        }
    }

        
    public void export() {
        String exportString = "";
        for (ArrayList<ArrayList<PVector>> curve : savedPoints.subList(0, savedPoints.size()-1)) {
            exportString += "CURVE:";
            exportString += "START:";
            exportString += Float.toString(curve.get(0).get(0).x - centerPoint.x) + "," + Float.toString(curve.get(0).get(0).y - centerPoint.y);
            for (ArrayList<PVector> vertex : curve) {
                exportString += "BEZIER:";
                exportString += Float.toString(vertex.get(1).x - centerPoint.x) + "," + Float.toString(vertex.get(1).y - centerPoint.y) + ",";
                exportString += Float.toString(vertex.get(3).x - centerPoint.x) + "," + Float.toString(vertex.get(3).y - centerPoint.y) + ",";
                exportString += Float.toString(vertex.get(2).x - centerPoint.x) + "," + Float.toString(vertex.get(2).y - centerPoint.y) + ",";
                exportString = exportString.substring(0, exportString.length() - 1);
            }
        }
        System.out.println(exportString);
    }

    public void chooseFile() {
        selectInput("Select an Image File", "fileSelected");
    }

    public void fileSelected(File selection) {
        System.out.println("Results " + selection.getAbsolutePath());
        chosenImage = loadImage(selection.getAbsolutePath());
    }
}