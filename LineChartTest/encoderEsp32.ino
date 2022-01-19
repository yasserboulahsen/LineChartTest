#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif
 
 
 // Rotary Encoder Inputs
 #define inputCLK 18
 #define inputDT 19
 
 
 
 int counter = 0; 
 int currentStateCLK;
 int previousStateCLK; 
 
 BluetoothSerial SerialBT;
 
 
 void setup() { 
   // Setup Serial Monitor
   Serial.begin (115200);
   SerialBT.begin("ESP32"); //Bluetooth device name

   // Set encoder pins as inputs  
   pinMode (inputCLK,INPUT);
   pinMode (inputDT,INPUT);

   // Read the initial state of inputCLK
   // Assign to previousStateCLK variable
   previousStateCLK = digitalRead(inputCLK);

    
 
 
 } 

 
 void loop() { 
  
  // Read the current state of inputCLK
   currentStateCLK = digitalRead(inputCLK);
    
   // If the previous and the current state of the inputCLK are different then a pulse has occured
   if (currentStateCLK != previousStateCLK){ 
       
     // If the inputDT state is different than the inputCLK state then 
     // the encoder is rotating counterclockwise
     if (digitalRead(inputDT) != currentStateCLK) { 
       counter --;
       
   
       
     } else {
       // Encoder is rotating clockwise
       counter ++;
  
       
     }
     
     //Serial.print(" -- Value: ");
     //SerialBT.print(counter);
     //SerialBT.print(" , ");
    // SerialBT.println(-counter);
   }
   SerialBT.print(counter); 
    SerialBT.print(",");
    SerialBT.println(-counter);
   // Update previousStateCLK with the current state
   previousStateCLK = currentStateCLK; 
  
   delay(1);
 }
