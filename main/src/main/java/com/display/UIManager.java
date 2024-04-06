package com.display;

import com.enums.NoteDecoration;

public class UIManager {
    
    public static int selectedNoteLength = 0;
    public static boolean tieSelected = false;
    public static boolean staccatoSelected = false;
    public static boolean accentSelected = false;

    public static boolean isDecorationSelected(NoteDecoration decoration) {
        switch (decoration) {
            case TIE:
                return tieSelected;
            case STACCATO:
                return staccatoSelected;
            case ACCENT:
                return accentSelected;
        }
        return false;
    }

    public static void toggleDecoration(NoteDecoration decoration) {
        switch (decoration) {
            case TIE:
                tieSelected = ! tieSelected;
                return;
            case STACCATO:
                staccatoSelected = ! staccatoSelected;
                return;
            case ACCENT:
                accentSelected = ! accentSelected;
                return;
        }
    }
}
