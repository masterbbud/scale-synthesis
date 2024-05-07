package com.parts;

public class NoteDecoration {
    
    public boolean staccato;
    public boolean tie;
    public boolean accent;
    public boolean rest;

    public NoteDecoration() {
        staccato = false;
        tie = false;
        accent = false;
        rest = false;
    }

    public NoteDecoration(NoteDecoration from) {
        staccato = from.staccato;
        tie = from.tie;
        accent = from.accent;
        rest = from.rest;
    }
}
