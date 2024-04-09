package com;

import java.util.ArrayList;
import java.util.HashMap;

import com.comparators.LayerClickComparator;
import com.comparators.LayerDrawComparator;
import com.customElements.NoteDecorationButton;
import com.customElements.NoteLengthButton;
import com.display.Curve;
import com.display.CurveLibrary;
import com.display.StaffVisualizer;
import com.enums.NoteDecoration;
import com.enums.NoteLength;
import com.parts.Measure;
import com.parts.Note;
import com.parts.Part;
import com.playback.Instrument;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

public class Main extends PApplet {

    protected static final int WIDTH = 2000;
    protected static final int HEIGHT = 1000;
    protected static final int FRAME_RATE = 30;
    protected ArrayList<Element> elements;
    protected boolean isMousePressed = false;

    private ArrayList<Part> staffs;

    private StaffVisualizer staffVisualizer;
    
    public static void main(String[] args) {
        PApplet.main(new String[] {"com.Main"});
    }

    public void settings() {
        size(WIDTH, HEIGHT);
        
        elements = new ArrayList<Element>();
    }

    public void setup() {
        frameRate(FRAME_RATE);
        ellipseMode(CENTER);

        staffs = new ArrayList<>();
        staffs.add(new Part(0, null));
        staffs.get(0).getMeasures().add(new Measure());
        staffs.get(0).getMeasures().get(0).notes.add(new Note(0, NoteLength.EIGHTH_TRIPLET, null));
        staffs.get(0).getMeasures().get(0).notes.add(new Note(1, NoteLength.EIGHTH_TRIPLET, null));
        staffs.get(0).getMeasures().get(0).notes.add(new Note(2, NoteLength.EIGHTH_TRIPLET, null));
        staffs.get(0).getMeasures().get(0).notes.add(new Note(1.5f, NoteLength.QUARTER, null));
        staffs.get(0).getMeasures().get(0).notes.add(new Note(0, NoteLength.QUARTER, null));
        staffs.get(0).getMeasures().add(new Measure());
        staffs.get(0).getMeasures().get(1).notes.add(new Note(0, NoteLength.QUARTER, null));
        staffs.get(0).getMeasures().get(1).notes.add(new Note(3, NoteLength.QUARTER, null));
        staffs.get(0).getMeasures().get(1).notes.add(new Note(0, NoteLength.QUARTER, null));
        staffs.get(0).getMeasures().get(1).transposition = 3;
        staffs.get(0).getMeasures().add(new Measure());
        staffs.get(0).getMeasures().add(new Measure());

        staffs.add(new Part(0, null));
        staffs.get(1).getMeasures().add(new Measure());
        staffs.get(1).getMeasures().get(0).notes.add(new Note(0, NoteLength.EIGHTH, null));
        staffs.get(1).getMeasures().get(0).notes.add(new Note(0, NoteLength.EIGHTH, null));
        staffs.get(1).getMeasures().get(0).notes.add(new Note(0, NoteLength.EIGHTH, null));
        staffs.get(1).getMeasures().get(0).notes.add(new Note(0, NoteLength.QUARTER, null));
        staffs.get(1).getMeasures().add(new Measure());
        staffs.get(1).getMeasures().get(1).transposition = 3;
        staffs.get(1).getMeasures().add(new Measure());
        staffs.get(1).getMeasures().add(new Measure());
        staffVisualizer = new StaffVisualizer(this, 100, 100, 400, 800, staffs);
        elements.add(staffVisualizer);
        initUI();
        
        // try {
        //     //playScale();
        //     playAltScale();
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }

    private void initUI() {
        CurveLibrary.init();
        PVector uiCursor = new PVector(40, 40);
        initButtons(uiCursor);
        initStaffs(uiCursor);
    }

    private void initButtons(PVector uiCursor) {

        float buttonSize = 40;

        PVector buttonPosition = new PVector(uiCursor.x, uiCursor.y);

        elements.add(new NoteLengthButton(this, buttonPosition.x, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_WHOLE, 0));
        elements.add(new NoteLengthButton(this, buttonPosition.x + buttonSize * 1, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_HALF, 1));
        elements.add(new NoteLengthButton(this, buttonPosition.x + buttonSize * 2, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_QUARTER, 2));
        elements.add(new NoteLengthButton(this, buttonPosition.x + buttonSize * 3, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_EIGHTH, 3));
        elements.add(new NoteLengthButton(this, buttonPosition.x + buttonSize * 4, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_SIXTEENTH, 4));
        elements.add(new NoteLengthButton(this, buttonPosition.x + buttonSize * 5, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_QUARTER_TRIPLET, 5));
        elements.add(new NoteLengthButton(this, buttonPosition.x + buttonSize * 6, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_EIGHTH_TRIPLET, 6));

        buttonPosition = new PVector(buttonPosition.x + buttonSize * 8, buttonPosition.y);

        elements.add(new NoteDecorationButton(this, buttonPosition.x + buttonSize * 0, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_TIE, NoteDecoration.TIE));
        elements.add(new NoteDecorationButton(this, buttonPosition.x + buttonSize * 1, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_TIE, NoteDecoration.STACCATO));
        elements.add(new NoteDecorationButton(this, buttonPosition.x + buttonSize * 2, buttonPosition.y, buttonSize, buttonSize, CurveLibrary.ICON_TIE, NoteDecoration.ACCENT));

        uiCursor.y = uiCursor.y + buttonSize * 2;
    }

    private void initStaffs(PVector uiCursor) {
        float staffHeight = 200;

        PVector staffPosition = new PVector(uiCursor.x, uiCursor.y);
    }

    public void playAltScale() throws InterruptedException {
        Instrument instrument = new Instrument(new float[]{0.064f, 0.0213f, 0.0285f, 0.014f, 0.013f, 0.0025f, 0.003f, 0.0023f, 0.002f, 0.0018f, 0.0015f, 0.001f, 0.0005f});

        instrument.cacheNotes(2, 12, 440);

        // TODO next play notes on a part, and add ability to stop the note of a part
        
        for (int i = 0; i < 12; i++) {
            instrument.startPlayingNote(0, i);
            Thread.sleep(4000);
        }
    }

    public void playScale() throws InterruptedException {
        Sound sound = new Sound();

        double freq = 440;
        double notesPerOctave = 7;
        //getBestFitForNote(8, 10.5f);

        // make something that will play the closest fraction instead of the actual float (equivalent of old school tuning in 12-tone)

        for (float f = 1; f < 20; f += 0.5) {
            System.out.println("Scale with " + Float.toString(f) + " steps");
            float totalDist = 0;
            for (int i = 0; i < f; i++) {
                totalDist += getBestFitForNote(i, f);
            }
            System.out.println("Results: Average dist: " + Float.toString((float)(totalDist / Math.ceil(f))));
        }

        Waveform w = new Waveform();
        sound.playBytes(w.getBytes(440));
        sound.playBytes(w.getBytes((float)(440 * Math.pow(2, (double)6/9.5))));
        sound.playBytes(w.getBytes((float)(440 * Math.pow(2, (double)4/9.5))));
        sound.playBytes(w.getBytes((float)(440 * Math.pow(2, (double)6/9.5))));
        sound.playBytes(w.getBytes((float)(440 * Math.pow(2, (double)4/9.5))));
        sound.playBytes(w.getBytes((float)(440 * Math.pow(2, (double)12/9.5))));
        
        // for (int i = 0; i < notesPerOctave * 2 + 1; i++) {
        //     sound.playSine(freq);
        //     sound.playSine(freq * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)));
        //     sound.playSine(freq * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave))* pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)) * pow(2, (float)(1/notesPerOctave)));
        //     freq *= pow(2, (float)(1/notesPerOctave));
        //     Thread.sleep(1000);
        // }
    }

    public float getBestFitForNote(int index, float notesPerOctave) {
        float c = pow(2, (float)(1/notesPerOctave));
        float goal = pow(c, index);
        float bestDistance = 9999;
        int bestNum = 0;
        int bestDenom = 0;
        for (int num = 1; num < 100; num++) {
            for (int denom = 1; denom < 100; denom++) {
                if (abs(goal - (float)num/denom) * pow(denom, 2) < bestDistance) {
                    bestDistance = abs(goal - (float)num/denom) * pow(denom, 2);
                    bestNum = num;
                    bestDenom = denom;
                }
            }
        }
        System.out.println("bestDistance: " + Float.toString(bestDistance) + " frac: " + Integer.toString(bestNum) + "/" + Integer.toString(bestDenom));
        return bestDistance;
    }

    public void draw() {
        background(64);

        elements.sort(new LayerDrawComparator());
        for (Element e : elements) {
            if (e.isHidden()) {
                continue;
            }
            e.draw();
        }
        elements.sort(new LayerClickComparator());
        boolean isSomethingHovered = false;
        for (Element e : elements) {
            if (e.isHidden()) {
                continue;
            }
            if (e.tryHover(isSomethingHovered)) {
                isSomethingHovered = true;
            }
            if (e.tryClick()) {
                isMousePressed = true;
            }
        }
    }

    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        if (staffVisualizer.mouseInBounds()) {
            staffVisualizer.scroll(e);
        }
    }

    public void mouseReleased() {
        isMousePressed = false;
    }

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public float fixedDeltaTime() {
        return 1f / FRAME_RATE;
    }
}