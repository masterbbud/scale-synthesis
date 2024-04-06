package com.playback;

import com.display.VisualSettings;

public class AudioSettings {
    
    private static AudioSettings INSTANCE = null;

    public float sampleRate = 44100f; // Hz

    private AudioSettings() {

    }

    public static AudioSettings instance() {
        if (INSTANCE == null) {
            INSTANCE = new AudioSettings();
        }
        return INSTANCE;
    }
}
