package com.display;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.Main;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Curve {

    private ArrayList<CurvePart> parts = new ArrayList<>();
    private float maxW; // max width
    private float maxH; // max height
    
    public Curve(String filename) {
        try {
            loadInData(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInData(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("curves/" + filename + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String content = br.readLine();
        br.close();
        String[] contentSplit = content.split("CURVE:");
        for (int i = 1; i < contentSplit.length; i++) {
            String s = contentSplit[i];
            float[] maxValues = new float[2];
            parts.add(new CurvePart(s, maxValues));
            if (maxValues[0] > maxW) {
                maxW = maxValues[0];
            }
            if (maxValues[1] > maxH) {
                maxH = maxValues[1];
            }
        }
    }

    public void draw(PApplet main, float x, float y, float w) {
        for (CurvePart part : parts) {
            part.draw(main, x, y, w / (maxW * 2));
        }
    }

    public void draw(PGraphics g, float x, float y, float w) {
        for (CurvePart part : parts) {
            part.draw(g, x, y, w / (maxW * 2));
        }
    }

    public void draw(PApplet main, float x, float y, float w, float h) {
        float scale;
        if (maxW / maxH > w / h) {
            scale = w / (maxW * 2);
        }
        else {
            scale = h / (maxH * 2);
        }
        for (CurvePart part : parts) {
            part.draw(main, x, y, scale);
        }
    }

    public void scale(float scale) {
        maxH /= scale;
        maxW /= scale;
    }
}
