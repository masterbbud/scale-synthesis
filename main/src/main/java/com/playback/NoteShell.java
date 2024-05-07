package com.playback;

import java.util.ArrayList;
import java.util.Objects;

import com.enums.NoteLength;
import com.parts.Note;
import com.parts.NoteDecoration;

public class NoteShell {
    
    public float pitch;
    public int length;

    public NoteDecoration noteDecoration;

    public NoteShell(ArrayList<Note> notes) {
        pitch = notes.get(0).pitch;
        length = 0;
        for (Note n : notes) {
            switch (n.length) {
                case SIXTEENTH:
                    length += 3;
                    break;
                case EIGHTH_TRIPLET:
                    length += 4;
                    break;
                case EIGHTH:
                    length += 6;
                    break;
                case QUARTER_TRIPLET:
                    length += 8;
                    break;
                case QUARTER:
                    length += 12;
                    break;
                case HALF:
                    length += 24;
                    break;
                case WHOLE:
                    length += 48;
                    break;
            }

        }

        noteDecoration = new NoteDecoration(notes.get(0).noteDecoration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NoteShell other = (NoteShell) o;
        return length == other.length && pitch == other.pitch && noteDecoration.accent == other.noteDecoration.accent && noteDecoration.staccato == other.noteDecoration.staccato && noteDecoration.rest == other.noteDecoration.rest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, pitch, noteDecoration.accent, noteDecoration.staccato, noteDecoration.rest);
    }

    public float getCanonLength() {
        return length / 12f;
    }
}
