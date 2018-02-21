#include <Adafruit_NeoPixel.h>

//define NeoPixel Pin and Number of LEDs
#define PIN 5
#define NUM_LEDS 60

//create a NeoPixel strip
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_LEDS, PIN, NEO_GRB + NEO_KHZ800); 

void setup() {
  // start the strip and blank it out
  strip.begin();
  strip.show();
}

void pacman() {
    for (int t = 0; t <= NUM_LEDS; t++){
    strip.setPixelColor(t, 255, 0, 0); // red
    strip.setPixelColor(t-1, 255, 0, 255); //pink 
    strip.setPixelColor(t-2, 0, 255, 255); //cyan
    strip.setPixelColor(t-3, 200, 60, 0); //orange
    strip.setPixelColor(t-5, 255, 255, 0); //yellow
    strip.show();
    delay(100);

    strip.setPixelColor(t-5, 0, 0, 0);
    strip.setPixelColor(t-3, 0, 0, 0);
    strip.setPixelColor(t-2, 0, 0, 0);
    strip.setPixelColor(t-1, 0, 0, 0);
    strip.setPixelColor(t, 0, 0, 0);
    delay(100);
    strip.show();
  }
}
void led_sequence(int color1R, int color1G, int color1B, int color2R, int color2G, int color2B) {
  for (int i = 61; i >= 0; i-=1){
      strip.setPixelColor(i, color2R, color2G, color2B);
      strip.show();
      delay(30);
    }
    for (int i = 0; i < 61; i+=1){
      strip.setPixelColor(i, color1R, color1G, color1B);
      strip.show();
      delay(30);
    }
    for (int i = 61; i >= 0; i-=2){
      strip.setPixelColor(i, color2R, color2G, color2B);
      strip.show();
      delay(75);
    }
    for (int i = 0; i < 61; i+=1){
      strip.setPixelColor(i, color2R, color2G, color2B);
      strip.show();
    }
    for (int i = 0; i < 61; i+=2){
      strip.setPixelColor(i, color1R, color1G, color1B);
      strip.show();
          delay(75);
    }
}

void loop() {
  led_sequence(200, 10, 120, 20, 150, 255);
  led_sequence(0, 0, 255, 255, 255, 255);
}
