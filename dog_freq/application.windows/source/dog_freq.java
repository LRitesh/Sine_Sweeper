import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 
import ddf.minim.signals.*; 
import controlP5.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class dog_freq extends PApplet {



Minim minim;
AudioOutput out;
SineWave sine;


ControlP5 cp5;

int sliderValue = 100;

PFont font;
ControlFont font2;

public void setup()
{
  size(800, 600, P2D);
  font = loadFont("SegoeUI-Light-48.vlw"); 
  
  font2 = new ControlFont(font,241);
  
  cp5 = new ControlP5(this);
  
  // add a horizontal sliders, the value of this slider will be linked
  // to variable 'sliderValue' 
  cp5.addSlider("sliderValue")
     .setPosition(0,0)
     .setRange(0,45000)
     .setWidth(width)
     .setHeight(300)
     ;
  
  minim = new Minim(this);
  // get a line out from Minim, default bufferSize is 1024, default sample rate is 44100, bit depth is 16
  out = minim.getLineOut(Minim.STEREO);
  // create a sine wave Oscillator, set to 440 Hz, at 0.5 amplitude, sample rate from line out
  sine = new SineWave(440, 0.1f, out.sampleRate());
  // set the portamento speed on the oscillator to 200 milliseconds
  sine.portamento(200);
  // add the oscillator to the line out
  out.addSignal(sine);
}

public void draw()
{
  background(0);
  stroke(255);
  
  cp5.getController("sliderValue")
   .getValueLabel()
   .setFont(font2)
   .toUpperCase(false)
   .setSize(24)
   ;
  
  // draw the waveforms
  for(int i = 0; i < out.bufferSize() - 1; i++)
  {
    float x1 = map(i, 0, out.bufferSize(), 0, width);
    float x2 = map(i+1, 0, out.bufferSize(), 0, width);
    line(x1, 450 + out.right.get(i)*50, x2, 450 + out.right.get(i+1)*50);
  }
  
  sine.setFreq(sliderValue);
}


public void stop()
{
  out.close();
  minim.stop();
  
  super.stop();
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "dog_freq" });
  }
}
