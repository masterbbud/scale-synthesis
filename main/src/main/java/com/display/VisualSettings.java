package com.display;

public class VisualSettings {
    
    private static VisualSettings INSTANCE = null;

    // DRAWING SIZE SETTINGS
    public float notePadding = 0.5f;
    public float measurePadding = 2f;
    public float noteWidth = 2f;
    public float noteHeight = 2f;


    // UI SETTINGS
    public float scrollSpeed = 10f;

    private VisualSettings() {

    }

    public static VisualSettings instance() {
        if (INSTANCE == null) {
            INSTANCE = new VisualSettings();
        }
        return INSTANCE;
    }
}
