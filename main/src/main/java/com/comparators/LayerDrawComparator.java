package com.comparators;

import java.util.Comparator;

import com.Element;

public class LayerDrawComparator implements Comparator<Element> {
    public int compare(Element a, Element b) {
        return a.layer - b.layer;
    }
}
