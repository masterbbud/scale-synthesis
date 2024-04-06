package com.datatypes;

import com.Main;
import processing.core.PApplet;

public class Color {

    public static final Color BLACK = new Color(0);
    public static final Color WHITE = new Color(255);

    public int r;
    public int g;
    public int b;
    public int a;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(int grey, int a) {
        this(grey, grey, grey, a);
    }

    public Color(int grey) {
        this(grey, grey, grey);
    }

    public void setFill(PApplet launcher) {
        launcher.fill(r, g, b, a);
    }

    public void setStroke(PApplet launcher) {
        launcher.stroke(r, g, b, a);
    }

    public Color darker() {
        return new Color(darkenFunction(r), darkenFunction(g), darkenFunction(b), a);
    }

    public Color lighter() {
        return new Color(lightenFunction(r), lightenFunction(g), lightenFunction(b), a);
    }

    private int lightenFunction(int c) {
        return (int)Math.min(255, (c*1.05f)+10);
    }

    private int darkenFunction(int c) {
        return (int)Math.max(0, (c-10)/1.05f);
    }

    public Color getColoredTextColor() {
        int MAX = Math.max(r, Math.max(g, b));
        if (MAX == 0) {
            return WHITE;
        }
        int MIN = Math.min(r, Math.min(g, b));
        int baseline = 3 * (MAX - MIN) / MAX;
        float scalar;
        if (MAX > 127) {
            scalar = (float) baseline / MAX;
        }
        else {
            scalar = (float) (255 - baseline) / MAX;
        }
        return new Color((int)(r * scalar), (int)(g * scalar), (int)(b * scalar), a);
    }

    public Color getBWTextColor() {
        int MAX = Math.max(r, Math.max(g, b));
        if (MAX > 127) {
            return BLACK;
        }
        else {
            return WHITE;
        }
    }

    public String toString() {
        return "COLOR RGBA["+Integer.toString(r)+", "+Integer.toString(g)+", "+Integer.toString(b)+", "+Integer.toString(a)+"]";
    }

    public Color lerpToColor(Color other, float amt) {
        return new Color(
            (int)lerp(r, other.r, amt),
            (int)lerp(g, other.g, amt),
            (int)lerp(b, other.b, amt),
            (int)lerp(a, other.a, amt)
        );
    }

    private float lerp(float start, float end, float amt) {
        return start * (1f - amt) + end * amt;
    }
}
