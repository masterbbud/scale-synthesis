package com.parts;

import java.util.ArrayList;

import com.display.VisualSettings;

public class Measure {
    public ArrayList<Beat> beats;

    public Measure() {
        beats = new ArrayList<Beat>();
        beats.add(new Beat());
        beats.add(new Beat());
        beats.add(new Beat());
        beats.add(new Beat());
    }

    public float getVisualWidth() {
        float total = 0;
        for (Beat b : beats) {
            total += b.getVisualWidth();
        }
        total += VisualSettings.instance().measurePadding;
        return total;
    }
}
