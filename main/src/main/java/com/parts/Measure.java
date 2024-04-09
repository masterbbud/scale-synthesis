package com.parts;

import java.util.ArrayList;

import com.display.VisualSettings;

public class Measure {
    public ArrayList<Note> notes;
    public int transposition;
    public int transpositionOctave;
    public int beats = 4;

    public Measure() {
        notes = new ArrayList<Note>();
    }

    public float getVisualWidth() {
        float total = 0;
        for (Note n : notes) {
            total += n.getVisualWidth();
        }
        total += VisualSettings.instance().measurePadding;
        return total;
    }

    public boolean isComplete() {
        float sum = 0;
        for (Note n : notes) {
            sum += n.getCanonLength();
        }
        return sum >= beats;
    }
}
