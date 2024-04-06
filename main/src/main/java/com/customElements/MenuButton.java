package com.customElements;

import com.Element;
import com.Main;

public abstract class MenuButton extends Element {

    protected boolean isSelected = false;
    
    public MenuButton(Main launcher, float x, float y, float w, float h) {
        super(launcher, x, y, w, h);
    }
    
    public void draw() {
        launcher.fill(200);
        if (isHovered || isSelected) {
            launcher.fill(255);
        }
        launcher.stroke(0);
        launcher.strokeWeight(2);
        launcher.rect(x, y, w, h);
    }
}
