package com.playback;

import com.display.VisualSettings;

public class AudioSettings {
    
    private static AudioSettings INSTANCE = null;

    public float sampleRate = 44100f; // Hz

    public float tempo = 100;
    public float centralFrequency = 440;

    private AudioSettings() {

    }

    public static AudioSettings instance() {
        if (INSTANCE == null) {
            INSTANCE = new AudioSettings();
        }
        return INSTANCE;
    }

    public float timeForNote(float length) {
        return (60f / tempo) * length;
    }

    public float freqForNote(float pitch) {
        return centralFrequency * (float)Math.pow(2, pitch / VisualSettings.instance().notesPerOctave);
    }

    public float ticksToLength(int ticks) {
        return ticks / 12f;
    }
}
