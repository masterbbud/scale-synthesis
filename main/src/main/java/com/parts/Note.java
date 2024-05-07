package com.parts;

import java.util.Objects;

import com.display.CurveLibrary;
import com.display.VisualNote;
import com.display.VisualSettings;
import com.enums.NoteLength;

import processing.core.PGraphics;

public class Note {

    public NoteLength length;
    public float pitch;

    private Part parentPart;

    public VisualNote visualNote;
    public NoteDecoration noteDecoration;

    public Note(float pitch, NoteLength length, Part parentPart) {
        this.pitch = pitch;
        this.length = length;
        this.parentPart = parentPart;
        visualNote = new VisualNote(this);
        noteDecoration = new NoteDecoration();
    }

    public float getVisualWidth() {
        return VisualSettings.instance().noteWidth;
    }

    public void play() {

    }

    public float getCanonLength() {
        switch (length) {
            case SIXTEENTH:
                return (float)1/4;
            case EIGHTH_TRIPLET:
                return (float)1/3;
            case EIGHTH:
                return (float)1/2;
            case QUARTER_TRIPLET:
                return (float)2/3;
            case QUARTER:
                return 1;
            case HALF:
                return 2;
            case WHOLE:
                return 4;
        }
        return 0;
    }

    public void draw(PGraphics g, float x, float y) {
        g.fill(0);
        g.noStroke();
        CurveLibrary.getNoteByLength(length).draw(g, x, y, VisualSettings.instance().noteScale);
    }

    
}
