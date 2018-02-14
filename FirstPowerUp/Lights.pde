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

void loop() {

for (int i = 0; i < 61; i++){
  for (int k = 0; k < 256; k+=5){
    strip.setPixelColor(i, k, 0, 0);
    strip.show();
    delay(5);
  }
}
for (int i = 0; i < 61; i++){
  for (int k = 0; k < 256; k+=5){
    strip.setPixelColor(i, 0, 0, k);
    strip.show();
    delay(5);
  }
}
 

////  Cop Chase
//  for (int t = 0; t <= NUM_LEDS; t++){
//    strip.setPixelColor(t, 255, 20, 255);
//    strip.setPixelColor(t-2, 255, 0, 0);
//  
//    strip.setPixelColor(t-3, 0, 0, 255);
//    strip.show();
//    delay(50);
//
//    strip.setPixelColor(t, 0, 0, 0);
//    strip.setPixelColor(t-3, 0, 0, 0);
//    strip.setPixelColor(t-2, 0, 0, 0);
//    strip.show();
//  }
//
//  
//// OTHER THING:
//  for (int x = 0; x <= NUM_LEDS; x++){
//    if (x%2 == 0){
//      strip.setPixelColor(x, 255, 0, 0);
//      strip.show();
//    }
//    else if (x%3 == 0) {
//      strip.setPixelColor(x, 255, 255, 0);
//      strip.show();
//    }
//    else {
//      strip.setPixelColor(x, 255, 255, 255);
//      strip.show();
//    }
//    delay(10);
//  }
//
//  for (int x = 0; x <= NUM_LEDS; x++){
//    strip.setPixelColor(x, 0, 255, 0);
//    strip.show();
//    delay(10);
//  }
//  for (int x = 0; x <= NUM_LEDS; x++){
//    strip.setPixelColor(x, 255, 255, 255);
//    strip.show();
//    delay(10);
//  }
//  for (int y = 0; y <= NUM_LEDS; y++){
//    strip.setPixelColor(y, 0, 0, 0);
//    strip.show();
//     delay(50);
//  }
}
