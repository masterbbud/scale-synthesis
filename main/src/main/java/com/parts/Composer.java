package com.parts;

import java.beans.Visibility;
import java.util.ArrayList;

import com.display.VisualSettings;
import com.enums.NoteLength;
import com.playback.AudioSettings;
import com.playback.Instrument;

public class Composer {
    
    ArrayList<Part> staffs = new ArrayList<>();

    Instrument globalInstrument;
    Instrument highInstrument;

    public Composer() {
        globalInstrument = new Instrument(new float[]{0.064f, 0.0213f, 0.0285f, 0.014f, 0.013f, 0.0025f, 0.003f, 0.0023f, 0.002f, 0.0018f, 0.0015f, 0.001f, 0.0005f});
        highInstrument = new Instrument(new float[]{0.034f, 0.044f, 0.0525f, 0.064f, 0.023f, 0.0025f, 0.003f, 0.0023f, 0.002f, 0.0018f, 0.0015f, 0.001f, 0.0005f});
    }

    public ArrayList<Part> compose() {


        //setParts(3);

        staffs = new ArrayList<>();
        staffs.add(new Part(0, globalInstrument));
        staffs.get(0).getMeasures().add(new Measure());
        staffs.add(new Part(0, highInstrument));
        staffs.get(1).getMeasures().add(new Measure());
        staffs.add(new Part(0, globalInstrument));
        staffs.get(2).getMeasures().add(new Measure());

        NoteDecoration rest = new NoteDecoration();
        rest.rest = true;

        // for (int i = 2; i < 18; i++) {
        //     for (int b = 1; b < i; b++) {
        //         System.out.println("0 " + Integer.toString(b) + " " + Integer.toString(i));
        //         addNote(0, 0, NoteLength.EIGHTH);
        //         addNote(0, b, NoteLength.EIGHTH);
        //         addNote(0, i, NoteLength.EIGHTH);
        //         addNote(0, i, NoteLength.HALF, rest);
        //     }
        // }

        for (int i = 0; i < 0; i++) {
            addNote(0, 0, NoteLength.EIGHTH, rest);
        }

        for (int i = 0; i < 290; i++) {
            addNote(0, 0, NoteLength.EIGHTH);
            addNote(0, 4, NoteLength.EIGHTH);
            addNote(0, 7, NoteLength.EIGHTH);
            // addNote(0, 6, NoteLength.EIGHTH);
            // addNote(0, 0, NoteLength.EIGHTH);
            // addNote(0, 3, NoteLength.EIGHTH);
            // addNote(0, 0, NoteLength.EIGHTH);
            // addNote(0, 7, NoteLength.EIGHTH);
        }
        addNote(0, 0, NoteLength.EIGHTH);
        addNote(0, 4, NoteLength.EIGHTH);
        addNote(0, 7, NoteLength.QUARTER);
        addNote(0, 4, NoteLength.QUARTER, rest);
        addNote(0, 4, NoteLength.QUARTER, rest);
        addNote(0, 4, NoteLength.QUARTER, rest);
        

        pitchPart(0, -19);

        // for (int i = 0; i < 32; i++) {
        //     addNote(1, 0, NoteLength.EIGHTH, rest);
        // }

        // addNote(1, 14, NoteLength.EIGHTH);


        // addNote(1, 14, NoteLength.HALF);
        // addNote(1, 14, NoteLength.QUARTER);
        // addNote(1, 16, NoteLength.QUARTER);
        // addNote(1, 16, NoteLength.QUARTER);
        // addNote(1, 16, NoteLength.QUARTER);
        // addNote(1, 16, NoteLength.QUARTER);
        // addNote(1, 14, NoteLength.SIXTEENTH);
        // addNote(1, 15, NoteLength.SIXTEENTH);
        // addNote(1, 16, NoteLength.EIGHTH);
        // addNote(1, 14, NoteLength.EIGHTH);

        staffs.get(1).getMeasures().get(staffs.get(1).getMeasures().size() - 1).transpositionOctave = -1;
        
        for (int i = 0; i < 12; i++) {
            addNote(1, 4, NoteLength.QUARTER, rest);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 0, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 19, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
            addNote(1, 13, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 10, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET, rest);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET);
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 13, NoteLength.QUARTER_TRIPLET);
        }
        
        for (int i = 0; i < 4; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 13, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
            addNote(1, 13, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
            addNote(1, 19, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 19, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 12, NoteLength.QUARTER_TRIPLET);
            addNote(1, 19, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 12, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
            addNote(1, 10, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
            addNote(1, 10, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 10, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 10, NoteLength.QUARTER_TRIPLET, rest);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 9, NoteLength.QUARTER);
            addNote(1, 9, NoteLength.QUARTER);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 5, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 5, NoteLength.EIGHTH, rest);
            addNote(1, 2, NoteLength.EIGHTH);
            addNote(1, 9, NoteLength.EIGHTH, rest);
            addNote(1, 2, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 5, NoteLength.EIGHTH, rest);
            addNote(1, 2, NoteLength.EIGHTH);
            addNote(1, 9, NoteLength.EIGHTH, rest);
            addNote(1, 2, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 9, NoteLength.EIGHTH);
            addNote(1, 2, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 7, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 1, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 6, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 0, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 8, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 1, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 6, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 0, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 8, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 8, NoteLength.EIGHTH);
            addNote(1, 11, NoteLength.EIGHTH);
            addNote(1, 8, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 8, NoteLength.EIGHTH);
            addNote(1, 12, NoteLength.EIGHTH);
            addNote(1, 11, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 8, NoteLength.EIGHTH);
            addNote(1, 11, NoteLength.EIGHTH);
            addNote(1, 8, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 11, NoteLength.EIGHTH);
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 11, NoteLength.EIGHTH);
        }

        
        for (int i = 0; i < 4; i++) {
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 12, NoteLength.EIGHTH);
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 12, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 13, NoteLength.EIGHTH);
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 13, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
            addNote(1, 19, NoteLength.EIGHTH);
            addNote(1, 4, NoteLength.EIGHTH);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 10, NoteLength.QUARTER_TRIPLET, rest);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 4, NoteLength.QUARTER, rest);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
            addNote(1, 12, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 4, NoteLength.QUARTER, rest);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
            addNote(1, 12, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 12, NoteLength.QUARTER_TRIPLET, rest);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 9, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET, rest);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 10, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 2; i++) {
            addNote(1, 10, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET, rest);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET, rest);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 19, NoteLength.QUARTER_TRIPLET);
            addNote(1, 13, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 4; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
            addNote(1, 15, NoteLength.QUARTER_TRIPLET);
        }
        
        for (int i = 0; i < 8; i++) {
            addNote(1, 11, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 8; i++) {
            addNote(1, 0, NoteLength.QUARTER_TRIPLET);
            addNote(1, 4, NoteLength.QUARTER_TRIPLET);
            addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        }

        for (int i = 0; i < 20; i++) {
            addNote(1, 4, NoteLength.QUARTER, rest);
        }
        

        


        // for (int i = 0; i < 2; i++) {
        //     addNote(1, 4, NoteLength.QUARTER_TRIPLET);
        //     addNote(1, 7, NoteLength.QUARTER_TRIPLET);
        //     addNote(1, 12, NoteLength.QUARTER_TRIPLET);
        // }

        // addNote(1, 7, NoteLength.HALF);
        // addNote(1, 11, NoteLength.QUARTER);
        // addNote(1, 8, NoteLength.QUARTER);
        // addNote(1, 11, NoteLength.QUARTER);
        // addNote(1, 14, NoteLength.QUARTER);
        // addNote(1, 8, NoteLength.QUARTER);
        // addNote(1, 3, NoteLength.QUARTER);
        
        for (int i = 0; i < 16; i++) {
            // addNote(1, 14, NoteLength.EIGHTH);
            // addNote(1, 14, NoteLength.HALF);
            // addNote(1, 14, NoteLength.QUARTER);
            // addNote(1, 11, NoteLength.QUARTER);
            // addNote(1, 8, NoteLength.QUARTER);
            // addNote(1, 11, NoteLength.QUARTER);
            // addNote(1, 14, NoteLength.QUARTER);
            // addNote(1, 8, NoteLength.QUARTER);
            // addNote(1, 3, NoteLength.QUARTER);
            for (int b = 0; b < 3; b++) {
                // addNote(1, 8, NoteLength.QUARTER_TRIPLET);
                // addNote(1, 11, NoteLength.QUARTER_TRIPLET);
                // addNote(1, 14, NoteLength.QUARTER_TRIPLET);
                // addNote(1, 16, NoteLength.QUARTER_TRIPLET);
                // addNote(1, 0, NoteLength.EIGHTH, rest);

            }
            // for (int b = 0; b < 3; b++) {
            //     addNote(1, 14, NoteLength.QUARTER_TRIPLET);
            //     addNote(1, 8, NoteLength.QUARTER_TRIPLET);
            //     addNote(1, 14, NoteLength.QUARTER_TRIPLET);
            //     addNote(1, 8, NoteLength.QUARTER_TRIPLET);

            // }
        }
        pitchPart(1, -19);

        for (int i = 0; i < 160; i++) {
            // addNote(2, 0, NoteLength.SIXTEENTH);
            // addNote(2, 3, NoteLength.SIXTEENTH);
            // addNote(2, 0, NoteLength.SIXTEENTH);
            // addNote(2, 9, NoteLength.SIXTEENTH, rest);
        }
        pitchPart(2, -38);

        finishMeasures();
        return staffs;
    }

    private void finishMeasures() {
        int maxMeasures = 0;
        for (Part p : staffs) {
            if (p.getMeasures().size() > maxMeasures) {
                maxMeasures = p.getMeasures().size();
            }
        }
        for (Part p : staffs) {
            while (p.getMeasures().size() < maxMeasures) {
                p.getMeasures().add(new Measure());
            }
        }
    }

    private void setParts(int parts) {
        staffs = new ArrayList<>();
        for (int i = 0; i < parts; i++) {
            staffs.add(new Part(0, globalInstrument));
            staffs.get(i).getMeasures().add(new Measure());
        }
    }

    private Note addNote(int part, float pitch, NoteLength length) {
        int lastMeasure = staffs.get(part).getMeasures().size();
        Note newNote = new Note(pitch, length, staffs.get(part));
        staffs.get(part).getMeasures().get(lastMeasure - 1).notes.add(newNote);
        if (staffs.get(part).getMeasures().get(lastMeasure - 1).totalLength() >= VisualSettings.instance().notesPerMeasure) {
            staffs.get(part).getMeasures().add(new Measure());
        }
        return newNote;
    }

    private Note addNote(int part, float pitch, NoteLength length, NoteDecoration decoration) {
        Note result = addNote(part, pitch, length);
        result.noteDecoration = new NoteDecoration(decoration);
        return result;
    }

    private void pitchPart(int part, float by) {
        for (Measure m : staffs.get(part).getMeasures()) {
            for (Note n : m.notes) {
                n.pitch += by;
            }
        }
    }
}
