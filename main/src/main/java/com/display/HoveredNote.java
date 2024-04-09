package com.display;

import com.parts.Note;

public class HoveredNote {

    public Note note = null;
    public int part = 0;
    public int measure = 0;
    public int measureIndex = 0;

    public HoveredNote() {

    }

    public void set(Note note, int part, int measure, int measureIndex) {
        if (this.note != null) {
            System.out.println(this.note.pitch);

        }
        this.note = note;
        this.part = part;
        this.measure = measure;
        this.measureIndex = measureIndex;
    }
}
