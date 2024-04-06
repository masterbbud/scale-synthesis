package com;

import com.datatypes.Color;

public class Element {
    
    protected Main launcher;
    protected float x;
    protected float y;
    protected float w;
    protected float h;
    public int layer = 1;
    protected boolean isHovered = false;
    protected Color color;
    protected Color stroke = null;
    protected boolean hidden = false;

    public Element(Main launcher, float x, float y, float w, float h, Color color, int layer) {
        this.launcher = launcher;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        this.layer = layer;
    }

    public Element(Main launcher, float x, float y, float w, float h) {
        this(launcher, x, y, w, h, Color.BLACK, 1);
    }

    public Element(Main launcher, float x, float y, float w, float h, int layer) {
        this(launcher, x, y, w, h, Color.BLACK, layer);
    }

    public Element(Main launcher, float x, float y, float w, float h, Color color) {
        this(launcher, x, y, w, h, color, 1);
    }

    public void setStroke(Color stroke) {
        this.stroke = stroke;
    }

    public void draw() {
        setupDraw();
        launcher.rect(x, y, w, h);
    }

    protected void setupDraw() {
        if (stroke == null) {
            launcher.noStroke();
        }
        else {
            stroke.setStroke(launcher);
        }
        color.setFill(launcher);
    }

    protected void click() {}
    protected void startHover() {}
    protected void endHover() {}

    /**
     * Checks if this element was clicked.
     * 
     * @return if this element was clicked
     */
    public boolean tryClick() {
        if (launcher.mousePressed && ! launcher.isMousePressed() && mouseInBounds()) {
            click();
            return true;
        }
        return false;
    }

    public boolean tryHover(boolean isSomethingHovered) {
        if (!isSomethingHovered && mouseInBounds()) {
            if (!isHovered) {
                startHover();
            }
            isHovered = true;
            return true;
        }
        isHovered = false;
        return false;
    }

    protected boolean mouseInBounds() {
        return (launcher.mouseX >= x && launcher.mouseY >= y && launcher.mouseX <= x + w && launcher.mouseY <= y + h);
    }

    public boolean isHidden() {
        return hidden;
    }

    public void hide() {
        hidden = true;
    }

    public void show() {
        hidden = false;
    }
}
