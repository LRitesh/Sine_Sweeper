import ddf.minim.*;
import ddf.minim.signals.*;
Minim minim;
AudioOutput out;
SineWave sine;

import controlP5.*;
ControlP5 cp5;

int sliderValue = 100;

PFont font;
ControlFont font2;

void setup()
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
  sine = new SineWave(440, 0.1, out.sampleRate());
  // set the portamento speed on the oscillator to 200 milliseconds
  sine.portamento(200);
  // add the oscillator to the line out
  out.addSignal(sine);
}

void draw()
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


void stop()
{
  out.close();
  minim.stop();
  
  super.stop();
}
