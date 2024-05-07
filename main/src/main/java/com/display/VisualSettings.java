package com.display;

public class VisualSettings {
    
    private static VisualSettings INSTANCE = null;

    // DRAWING SIZE SETTINGS
    public float staffHeight = 150f;
    public float staffAreaPaddingTop = 200f;
    public float unfinishedMeasurePadding = 40f;
    public float measurePadding = 20f;

    public float notePadding = 0.5f;
    public float noteWidth = 30f;
    public float noteScale = 22f;
    public float noteHeight = 2f;
    
    public float flagHeight = 80f;

    public float staffTextSize = 16f;

    // GENERAL SETTINGS
    public float notesPerOctave = 9.5f;
    public float notesPerMeasure = 4;

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
