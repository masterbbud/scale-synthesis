package com.parts;

import com.display.VisualSettings;

public class Note {

    private float length;
    private float pitch;

    private Part parentPart;

    public Note(float pitch, float length, Part parentPart) {
        this.pitch = pitch;
        this.length = length;
        this.parentPart = parentPart;
    }

    public float getVisualWidth() {
        return VisualSettings.instance().noteWidth;
    }

    public void play() {

    }
}
