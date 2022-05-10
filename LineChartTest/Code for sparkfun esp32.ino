
#include "BluetoothSerial.h"
#include<driver/rtc_io.h>

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif


// Rotary Encoder Inputs
#define inputCLK 18
#define inputDT 19
#define forceInput 35
#define batteryLevel A0
#define led 12

#define AM0 4095
#define AMref 3971
#define Mref 0.2

int counter = 0;
int currentStateCLK;
int previousStateCLK;

unsigned long previousTime = 0 ;
unsigned long currentTime;
int interval = 60000;
int count =0;
String stopData;


BluetoothSerial SerialBT;

void pulse() {
  counter++;
  //   if(digitalRead(inputDT) == 1){
  //    counter++;
  ////
  //   }else{
  //
  //    counter--;
  //
  //   }
}

void setup() {
  // Setup Serial Monitor
  Serial.begin (115200);
  SerialBT.enableSSP();
  SerialBT.begin("SparkFun"); //Bluetooth device name

  // Set encoder pins as inputs
  pinMode (inputCLK, INPUT);
  pinMode (inputDT, INPUT);
  pinMode(forceInput, INPUT);
  pinMode(batteryLevel, INPUT);
  pinMode(led, OUTPUT);
    digitalWrite(led,HIGH);
    delay(1000);
    digitalWrite(led,LOW);
      

  attachInterrupt(digitalPinToInterrupt(inputCLK), pulse, FALLING);

  esp_sleep_enable_ext0_wakeup(GPIO_NUM_36, 1);


}


void loop() {

  long count1 = counter;
  




  currentTime = millis();


//  if (currentTime - previousTime >= interval) {
//    SerialBT.println("in deep Sleep");
//    if (SerialBT.disconnect()){
//      SerialBT.println("Deep Sleep");
//      digitalWrite(led, HIGH);
//      delay(2000);
//      esp_deep_sleep_start();
//    }
//    previousTime = currentTime;
//  }



  long forceInputRef = analogRead(forceInput);
//  Serial.print(forceInputRef);
//  Serial.print(" -;- ");
  float force = ((forceInputRef - AM0) / (AMref - AM0)) * Mref;
  double forceOutput = forceInput * 3.3 / 4095;


  long inputBattery = analogRead(batteryLevel);
  double battery = (inputBattery * (3.3 / 4095)) * 2;

//  Serial.print(force);
//  Serial.print(" -;- ");
//  Serial.print(inputBattery);
//  Serial.print(" -;- ");
//  Serial.println(battery);

  if(SerialBT.available()){
    
    stopData = SerialBT.read();
    if(stopData = "test"){
      digitalWrite(led,HIGH);
      delay(1000);
      esp_deep_sleep_start();
    }
  }
  SerialBT.print(force * 9.80665);
  SerialBT.print(";");
  SerialBT.print(counter);
  SerialBT.print(";");
  SerialBT.println(battery);
 
  delay(10);
  SerialBT.flush();
}
