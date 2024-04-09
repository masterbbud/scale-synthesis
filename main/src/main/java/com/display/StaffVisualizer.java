package com.display;

import java.util.ArrayList;

import com.Element;
import com.Main;
import com.enums.NoteLength;
import com.parts.Measure;
import com.parts.Note;
import com.parts.Part;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class StaffVisualizer extends Element {
    
    private final float scrollbarHeight = 20;
    private final float measurePadding = 40;

    private ArrayList<Part> parts;
    private float scrollX = 0;

    private float lastMaxW = 0;

    private HoveredNote hoveredNote = new HoveredNote();

    public StaffVisualizer(Main launcher, float x, float y, float w, float h, ArrayList<Part> parts) {
        super(launcher, x, y, w, h);
        this.parts = parts;
    }

    public void draw() {
        drawScrollable();
    }

    public void drawScrollable() {
        PGraphics g = launcher.createGraphics((int)Math.ceil(w), (int)Math.ceil(h));
        g.beginDraw();

        g.background(230);

        float paddingTop = 200f;

        // draw all staffs

        // TODO improve algorithm for drawing notes (with respect to xPerBeat)

        int measure = 0;
        boolean anyFound = false;
        int[] lastTransposition = new int[parts.size()];
        int[] lastTranspositionOctave = new int[parts.size()];
        float xCursor = -1 * scrollX;
        float measureHeight = VisualSettings.instance().staffHeight;

        while (true) {
            anyFound = false;
            for (Part p : parts) {
                if (p.getMeasures().size() > measure) {
                    anyFound = true;
                }
            }
            if (!anyFound) {
                break;
            }
            float measureWidth = 200;
            g.strokeWeight(1);
            float notesPerOctave = VisualSettings.instance().notesPerOctave;

            // determine measure positions
            float[] lastXPosition = new float[parts.size()];
            float[] leftInNote = new float[parts.size()];
            int[] partIndices = new int[parts.size()];
            Measure[] measures = new Measure[parts.size()];
            boolean[] partsActive = new boolean[parts.size()];

            float tempCursor = 0;
            tempCursor += VisualSettings.instance().measurePadding;

            for (int p = 0; p < parts.size(); p++) {
                lastXPosition[p] = tempCursor;
                leftInNote[p] = 0;
                partIndices[p] = 0;
                measures[p] = parts.get(p).getMeasure(measure);
                partsActive[p] = true;
                if (measures[p].notes.size() == 0) {
                    partsActive[p] = false;
                }
            }

            float xPerBeat = 0;

            while (true) {

                float minLeft = 100;
                for (int p = 0; p < parts.size(); p++) {
                    if (leftInNote[p] < minLeft && partsActive[p]) {
                        minLeft = leftInNote[p];
                    }
                }

                if (minLeft == 100) {
                    float minBuffer = 0;
                    for (int p = 0; p < parts.size(); p++) {
                        if (measures[p].notes.size() > 0 && lastXPosition[p] == tempCursor && measures[p].notes.get(partIndices[p]-1).getVisualWidth() > minBuffer) {
                            minBuffer = measures[p].notes.get(partIndices[p]-1).getVisualWidth();
                        }
                    }
                    tempCursor += minBuffer;
                    break;
                }

                float deltaX = 1000;
                for (int p = 0; p < parts.size(); p++) {
                    Part part = parts.get(p);
                    if (leftInNote[p] == minLeft && partsActive[p]) {
                        Note n = measures[p].notes.get(partIndices[p]);
                        float dx;
                        if (partIndices[p] > 0) {
                            dx = Math.max(minLeft * xPerBeat, lastXPosition[p] + measures[p].notes.get(partIndices[p]-1).getVisualWidth() - tempCursor);
                        }
                        else {
                            dx = minLeft * xPerBeat;
                        }
                        if (dx < deltaX) {
                            deltaX = dx;
                        }
                        
                        // not sure about this one
                        if (xPerBeat == 0 || n.getVisualWidth() / n.getCanonLength() > xPerBeat) {
                            xPerBeat = n.getVisualWidth() / n.getCanonLength();
                        }

                        leftInNote[p] = 0;
                    }
                    else {
                        leftInNote[p] -= minLeft;
                    }
                }
                tempCursor += deltaX;
                for (int p = 0; p < parts.size(); p++) {
                    Part part = parts.get(p);
                    if (leftInNote[p] == 0 && partsActive[p]) {
                        Note n = measures[p].notes.get(partIndices[p]);

                        float noteX = xCursor + tempCursor;
                        n.visualNote.x = noteX;
                        leftInNote[p] = measures[p].notes.get(partIndices[p]).getCanonLength();
                        partIndices[p] ++;
                        if (partIndices[p] >= measures[p].notes.size()) {
                            partsActive[p] = false;
                        }

                        lastXPosition[p] = tempCursor;
                    }
                }
            }

            tempCursor += VisualSettings.instance().measurePadding;
            for (int p = 0; p < parts.size(); p++) {
                if (!measures[p].isComplete()) {
                    tempCursor += VisualSettings.instance().unfinishedMeasurePadding;
                    break;
                }
            }
            
            measureWidth = tempCursor;

            for (int p = 0; p < parts.size(); p++) {
                Part part = parts.get(p);
                // draw staff
                if (part.getMeasures().size() > measure) {
                    if (measure == 0 || lastTransposition[p] != part.getMeasure(measure).transposition) {
                        lastTransposition[p] = part.getMeasure(measure).transposition;
                        lastTranspositionOctave[p] = part.getMeasure(measure).transpositionOctave;
                        drawTransposition(g, xCursor, p * (measureHeight + measurePadding) + paddingTop + measureHeight, lastTransposition[p], lastTranspositionOctave[p]);
                    }
                }
                int transposition = lastTransposition[p];
                drawEmptyStaffMeasure(g, xCursor, p * (measureHeight + measurePadding) + paddingTop, measureWidth, measureHeight, notesPerOctave, transposition);
            }

            System.out.println("per measure");

            drawOneMeasure(g, notesPerOctave, measures, measure, xCursor, measureWidth);

            measure ++;
            xCursor += measureWidth;
        }

        lastMaxW = xCursor + scrollX;

        drawScrollbar(g);
        // TODO Flags

        g.endDraw();
        launcher.image(g, x, y);
    }

    private void drawOneMeasure(PGraphics g, float notesPerOctave, Measure[] measures, int measure, float xCursor, float measureWidth) {
        float measureHeight = VisualSettings.instance().staffHeight;
        float paddingTop = VisualSettings.instance().staffAreaPaddingTop;

        for (int p = 0; p < parts.size(); p++) {
            float measureTop = p * (measureHeight + measurePadding) + paddingTop;
            float lastX = -1;

            ArrayList<Note> barredNotes = new ArrayList<>();
            float positionInBeat = 0;

            for (int n = 0; n < measures[p].notes.size(); n++) {
                Note note = measures[p].notes.get(n);

                positionInBeat += note.getCanonLength();
                barredNotes.add(note);

                float noteY = measureTop + measureHeight - ((note.pitch - measures[p].transposition + notesPerOctave) % notesPerOctave) * (measureHeight / notesPerOctave);
                note.visualNote.y = noteY;

                if (positionInBeat >= 1 || n + 1 >= measures[p].notes.size() || note.length != measures[p].notes.get(n + 1).length) {
                    positionInBeat = positionInBeat % 1;
                    if (barredNotes.size() == 1) {
                        if (note.length != NoteLength.WHOLE) {
                            drawFlag(g, note, note.visualNote.x, noteY, noteY > measureTop + measureHeight / 2);
                        }
                    }
                    else {
                        drawBar(g, barredNotes, measureTop, measures[p].transposition, notesPerOctave, note.length);
                    }
                    barredNotes.clear();
                }
                
                drawNote(g, note, note.visualNote.x, noteY);

                float left = note.visualNote.x - VisualSettings.instance().noteScale / 2;
                float right;
                if (measures[p].notes.size() > n+1) {
                    right = measures[p].notes.get(n+1).visualNote.x - VisualSettings.instance().noteScale / 2;
                }
                else {
                    right = xCursor + measureWidth - VisualSettings.instance().measurePadding;
                    if (!measures[p].isComplete()) {
                        right -= VisualSettings.instance().unfinishedMeasurePadding + VisualSettings.instance().noteScale / 2;
                    }
                }
                    
                checkAndDrawHighlight(g, note, left, right, p, measure, n);
            }
            
            if (measures[p].notes.size() == 0) {

                float left = xCursor + VisualSettings.instance().measurePadding;
                float right = left + measureWidth - 2 * VisualSettings.instance().measurePadding;

                checkAndDrawHighlight(g, null, left, right, p, measure, 0);
            }
            else if (!measures[p].isComplete()) {
                float left = xCursor + measureWidth - VisualSettings.instance().measurePadding - VisualSettings.instance().unfinishedMeasurePadding - VisualSettings.instance().noteScale / 2;
                float right = xCursor + measureWidth - VisualSettings.instance().measurePadding;

                checkAndDrawHighlight(g, null, left, right, p, measure, measures[p].notes.size());
            }
        }
    }

    private void drawBar(PGraphics g, ArrayList<Note> notes, float top, int transposition, float notesPerOctave, NoteLength length) {

        g.stroke(0);
        g.strokeWeight(3);
        g.strokeJoin(PApplet.MITER);
        g.fill(0);

        float measureHeight = VisualSettings.instance().staffHeight;
        //calculate if it's up or down
        int ctUp = 0;
        float totalHeight = 0;
        for (Note n : notes) {
            if (n.visualNote.y > top + measureHeight / 2) {
                ctUp ++;
            }
        }

        boolean up = ctUp >= notes.size() / 2;
        float avgHeight = totalHeight / notes.size();
        float slope = (notes.get(notes.size() - 1).visualNote.y - notes.get(0).visualNote.y) / ((notes.get(notes.size() - 1).visualNote.x - notes.get(0).visualNote.x));

        if (Math.abs(slope) > 1) {
            slope = (float)Math.sqrt(Math.abs(slope)) * slope / Math.abs(slope);
        }

        int upScalar = up ? 1 : -1;

        float flagThickness = 6;

        float minStartY = 0;
        for (int n = 0; n < notes.size(); n++) {
            Note note = notes.get(n);
            float flagHeight = VisualSettings.instance().flagHeight;
            if (note.visualNote.y - upScalar * flagHeight < top - measureHeight / 3) {
                flagHeight = note.visualNote.y - top + measureHeight / 3;
            }
            else if (note.visualNote.y - upScalar * flagHeight > top + (4 * measureHeight) / 3) {
                flagHeight = top + (4*measureHeight) / 3 - note.visualNote.y;
            }
            note.visualNote.flagHeight = flagHeight;
            float minHeight = Math.abs( (note.visualNote.y - notes.get(0).visualNote.y) - upScalar * flagHeight - slope * (note.visualNote.x - notes.get(0).visualNote.x));
            if (minHeight > minStartY) {
                minStartY = minHeight;
            }
        }
        //System.out.println(minStartY);

        float endY = notes.get(0).visualNote.y - upScalar * minStartY + slope * (notes.get(notes.size() - 1).visualNote.x - notes.get(0).visualNote.x);
        float xOffset = upScalar * VisualSettings.instance().noteScale * 0.45f;
        g.quad(notes.get(0).visualNote.x + xOffset, notes.get(0).visualNote.y - upScalar * (minStartY - flagThickness), notes.get(0).visualNote.x + xOffset, notes.get(0).visualNote.y - upScalar * (minStartY), notes.get(notes.size() -1).visualNote.x + xOffset, endY, notes.get(notes.size() -1).visualNote.x + xOffset, endY + upScalar * flagThickness);
        if (length == NoteLength.SIXTEENTH) {
            g.quad(notes.get(0).visualNote.x + xOffset, notes.get(0).visualNote.y - upScalar * (minStartY - flagThickness * 3.5f), notes.get(0).visualNote.x + xOffset, notes.get(0).visualNote.y - upScalar * (minStartY - flagThickness * 2.5f), notes.get(notes.size() -1).visualNote.x + xOffset, endY + upScalar * flagThickness * 2.5f, notes.get(notes.size() -1).visualNote.x + xOffset, endY + upScalar * flagThickness * 3.5f);
        }

        if (length == NoteLength.EIGHTH_TRIPLET) {
            float textX = (notes.get(notes.size() - 1).visualNote.x - notes.get(0).visualNote.x)/2 + notes.get(0).visualNote.x;
            g.textAlign(PApplet.CENTER, PApplet.CENTER);
            System.out.println(textX);
            g.noStroke();
            CurveLibrary.TEXT_3.draw(g, textX, notes.get(0).visualNote.y - upScalar * minStartY + slope * (textX - notes.get(0).visualNote.x) - upScalar * 12, 12);
        }

        g.stroke(0);
        g.strokeWeight(3);
        for (Note n : notes) {
            g.line(n.visualNote.x + xOffset, n.visualNote.y, n.visualNote.x + xOffset, notes.get(0).visualNote.y - upScalar * minStartY + slope * (n.visualNote.x - notes.get(0).visualNote.x));
        }

        // float startY = top + measureHeight - ((notes.get(0).pitch - transposition + notesPerOctave) % notesPerOctave) * (measureHeight / notesPerOctave);
        // boolean startFlagUp = startY > top + measureHeight / 2;
        // float leftY;
        // if (startFlagUp) {
        //     leftY = y - VisualSettings.instance().flagHeight;
        // }
        // else {
        //     leftY = y + VisualSettings.instance().flagHeight;
        // }
        // if (notes.size() == 1) {
        //     drawFlag(g, notes.get(0), notes.get(0).visualNote.x, startY, startFlagUp);
        //     return;
        // }
        // float endY = top + measureHeight - ((notes.get(notes.size()-1).pitch - transposition + notesPerOctave) % notesPerOctave) * (measureHeight / notesPerOctave);
        // boolean endFlagUp = startY > top + measureHeight / 2;
        // float rightY;
    }

    private void drawFlag(PGraphics g, Note n, float x, float y, boolean flagUp) {
        g.stroke(0);
        g.strokeWeight(3);
        if (flagUp) {
            float drawX = x + VisualSettings.instance().noteScale * 0.45f;
            g.line(drawX, y, drawX, y - VisualSettings.instance().flagHeight);
        }
        else {
            float drawX = x - VisualSettings.instance().noteScale * 0.45f;
            g.line(drawX, y, drawX, y + VisualSettings.instance().flagHeight);
        }
    }

    private void checkAndDrawHighlight(PGraphics g, Note n, float left, float right, int p, int measure, int noteIndex) {
        float measureHeight = VisualSettings.instance().staffHeight;
        float paddingTop = VisualSettings.instance().staffAreaPaddingTop;
        float top = p * (measureHeight + measurePadding) + paddingTop;
        if (isHovered && launcher.mouseY - y > top && launcher.mouseY - y < top + measureHeight && launcher.mouseX - x > left && launcher.mouseX - x < right) {
            hoveredNote.set(n, p, measure, noteIndex);
            drawHighlight(g, left, top, right - left, measureHeight);
        }
    }

    public void scroll(float x) {
        scrollX += x * VisualSettings.instance().scrollSpeed;
        
        scrollX = Math.max(0, Math.min(scrollX, lastMaxW - w));
    }

    public void click(float xPos, float yPos) {
        if (yPos > h - scrollbarHeight) {
            scrollX = (xPos / w) * (lastMaxW - w);
        }
    }

    private void drawEmptyStaffMeasure(PGraphics g, float x, float y, float w, float h, float notesPerOctave, int transposition) {
        // TODO implement transposition of key
        g.stroke(0);
        for (int i = 0; i < notesPerOctave - transposition; i++) {
            g.line(x, y + h * (notesPerOctave - i) / notesPerOctave, x + w, y + h * (notesPerOctave - i) / notesPerOctave);
        }
        for (float i = notesPerOctave - transposition; i <= notesPerOctave; i++) {
            g.line(x, y + h * (notesPerOctave - i) / notesPerOctave, x + w, y + h * (notesPerOctave - i) / notesPerOctave);
        }
        g.line(x + w, y, x + w, y + h);
    }

    private void drawNote(PGraphics g, Note n, float x, float y) {
        n.draw(g, x, y);
    }

    private void drawTransposition(PGraphics g, float x, float y, int transposition, int transpositionOctave) {
        char note = (char) (transposition + 'A');
        g.fill(0);
        g.textSize(VisualSettings.instance().staffTextSize);
        g.textAlign(PApplet.LEFT, PApplet.BASELINE);
        g.text(note + Integer.toString(transpositionOctave) + ":", x, y + 2 + VisualSettings.instance().staffTextSize);
    }

    private void drawHighlight(PGraphics g, float x, float y, float w, float h) {
        g.fill(100, 100, 255, 50);
        g.noStroke();
        g.rect(x, y, w, h);
    }

    private void drawScrollbar(PGraphics g) {
        g.stroke(0);
        g.line(0, h - scrollbarHeight, w, h - scrollbarHeight);
        g.noStroke();
        g.fill(150);
        g.ellipse((scrollX / (lastMaxW - w)) * w, h - scrollbarHeight / 2, scrollbarHeight * 0.8f, scrollbarHeight * 0.8f);

    }
}
