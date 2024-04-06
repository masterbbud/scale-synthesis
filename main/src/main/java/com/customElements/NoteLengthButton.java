package com.customElements;

import com.Main;
import com.display.Curve;
import com.display.UIManager;

public class NoteLengthButton extends MenuButton {

    private Curve graphic;
    private int noteIndex;

    public NoteLengthButton(Main launcher, float x, float y, float w, float h, Curve graphic, int noteIndex) {
        super(launcher, x, y, w, h);
        this.graphic = graphic;
        this.noteIndex = noteIndex;
        if (UIManager.selectedNoteLength == noteIndex) {
            isSelected = true;
        }
    }
    
    public void draw() {
        isSelected = ( UIManager.selectedNoteLength == noteIndex );
        super.draw();
        launcher.noStroke();
        launcher.fill(20);
        graphic.draw(launcher, x + w / 2, y + h / 2, w * 0.8f, h * 0.8f);
    }

    public void click() {
        UIManager.selectedNoteLength = noteIndex;
    }
}
