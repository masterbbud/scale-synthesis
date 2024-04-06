package com.display;

import java.util.ArrayList;

import com.Element;
import com.Main;

import processing.core.PGraphics;
import processing.core.PVector;

public class StaffVisualizer extends Element {
    
    private ArrayList<Staff> staffs;
    private float scrollX;

    public StaffVisualizer(Main launcher, float x, float y, float w, float h, ArrayList<Staff> staffs) {
        super(launcher, x, y, w, h);
        this.staffs = staffs;
    }

    public void draw() {
        drawScrollable();
    }

    public void drawScrollable() {
        PGraphics g = launcher.createGraphics((int)Math.ceil(w), (int)Math.ceil(h));
        g.beginDraw();

        // draw all staffs

        g.endDraw();
        launcher.image(g, x, y);
    }

    public void scroll(float x) {
        scrollX += x * VisualSettings.instance().scrollSpeed;
    }
}
