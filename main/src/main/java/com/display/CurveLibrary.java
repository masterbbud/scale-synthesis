package com.display;

public class CurveLibrary {

    public static Curve ICON_WHOLE;
    public static Curve ICON_HALF;
    public static Curve ICON_QUARTER;
    public static Curve ICON_EIGHTH;
    public static Curve ICON_SIXTEENTH;
    public static Curve ICON_QUARTER_TRIPLET;
    public static Curve ICON_EIGHTH_TRIPLET;

    public static Curve ICON_TIE;
    public static Curve ICON_REPEAT_START;
    public static Curve ICON_REPEAT_END;
    
    public static void init() {
        ICON_WHOLE = new Curve("icons/whole");
        ICON_WHOLE.scale(0.7f);
        ICON_HALF = new Curve("icons/half");
        ICON_QUARTER = new Curve("icons/quarter");
        ICON_EIGHTH = new Curve("icons/eighth");
        ICON_SIXTEENTH = new Curve("icons/sixteenth");
        ICON_QUARTER_TRIPLET = new Curve("icons/quarter-triplet");
        ICON_EIGHTH_TRIPLET = new Curve("icons/eighth-triplet");

        ICON_TIE = new Curve("icons/tie");
        ICON_REPEAT_START = new Curve("icons/repeat-start");
        ICON_REPEAT_END = new Curve("icons/repeat");
    }
}
