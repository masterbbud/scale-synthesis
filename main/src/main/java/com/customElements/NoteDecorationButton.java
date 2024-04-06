package com.customElements;

import com.Main;
import com.display.Curve;
import com.display.UIManager;
import com.enums.NoteDecoration;

public class NoteDecorationButton extends MenuButton {
    private Curve graphic;
    private NoteDecoration decoration;

    public NoteDecorationButton(Main launcher, float x, float y, float w, float h, Curve graphic, NoteDecoration decoration) {
        super(launcher, x, y, w, h);
        this.graphic = graphic;
        this.decoration = decoration;
    }
    
    public void draw() {
        isSelected = ( UIManager.isDecorationSelected(decoration) );
        super.draw();
        launcher.noStroke();
        launcher.fill(20);
        graphic.draw(launcher, x + w / 2, y + h / 2, w * 0.8f, h * 0.8f);
    }

    public void click() {
        UIManager.toggleDecoration(decoration);
    }
}
