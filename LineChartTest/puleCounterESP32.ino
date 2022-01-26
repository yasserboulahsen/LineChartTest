#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif


 // Rotary Encoder Inputs
 #define inputCLK 18
 #define inputDT 19
 #define forceInput 34



 int counter = 0;
 int currentStateCLK ;
 int previousStateCLK ;

 BluetoothSerial SerialBT;

 void pulse(){

   if(digitalRead(inputDT) == 1){
    counter++;
//
   }else{

    counter--;

   }
 }








 void setup() {
   // Setup Serial Monitor
   Serial.begin (115200);
   SerialBT.begin("ESP32"); //Bluetooth device name

   // Set encoder pins as inputs
   pinMode (inputCLK,INPUT);
   pinMode (inputDT,INPUT);
   pinMode(forceInput,INPUT);

   // Read the initial state of inputCLK
   // Assign to previousStateCLK variable
//   previousStateCLK = digitalRead(inputCLK);

    attachInterrupt(digitalPinToInterrupt(inputCLK),pulse,FALLING);


//    NVIC_SET_PRIORITY(IRQ_PORTA,0);



 }


 void loop() {


      SerialBT.print(digitalRead(forceInput));
        SerialBT.print(",");
        SerialBT.println(counter);


   // Update previousStateCLK with the current state

      delay(1);








 }