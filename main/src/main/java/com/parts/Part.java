package com.parts;

import java.util.ArrayList;

import com.Waveform;
import com.playback.Instrument;

public class Part {
    
    private ArrayList<Measure> measures = new ArrayList<Measure>();
    private Instrument instrument;
    public int key;
    
    public Part(int key, Instrument instrument) {
        this.key = key;
        this.instrument = instrument;
    }

    public ArrayList<Measure> getMeasures() {
        return measures;
    }

    public Measure getMeasure(int index) {
        return measures.get(index);
    }
}
