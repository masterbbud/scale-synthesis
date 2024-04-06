package com.parts;

import java.util.ArrayList;

import com.display.VisualSettings;

public class Beat {
    
    public ArrayList<Note> notes = new ArrayList<Note>();
    
    public Beat() {

    }

    public float getVisualWidth() {
        float total = 0;
        for (Note n : notes) {
            total += n.getVisualWidth();
            total += VisualSettings.instance().notePadding;
        }
        return total;
    }
}
