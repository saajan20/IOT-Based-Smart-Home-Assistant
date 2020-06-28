/***************************************************
  Adafruit MQTT Library ESP8266 Example

  Must use ESP8266 Arduino from:
    https://github.com/esp8266/Arduino

  Works great with Adafruit's Huzzah ESP board & Feather
  ----> https://www.adafruit.com/product/2471
  ----> https://www.adafruit.com/products/2821

  Adafruit invests time and resources providing this open source code,
  please support Adafruit and open-source hardware by purchasing
  products from Adafruit!

  Written by Tony DiCola for Adafruit Industries.
  MIT license, all text above must be included in any redistribution
 ****************************************************/
#include <ESP8266WiFi.h>
#include "Adafruit_MQTT.h"
#include "Adafruit_MQTT_Client.h"
#include "FirebaseArduino.h"
#include "DHT.h"

#define relay D0
#define relay2 D1
#define relay3 D2
#define relay4 D3

//DHT11 for reading temperature and humidity value
#define DHTPIN            D7

//Selection pins for multiplexer module to switch between different sensors and give data on a single analog pin.
#define S0                D5
#define S1                D6

//Analog pin to read the incoming analog value from different sensors.
#define analogpin         A0


//Motion detector
#define intrusion    D4

//Flame sensor
#define flame   D8
/************************* WiFi Access Point *********************************/

#define WLAN_SSID       "SAAJAN"
#define WLAN_PASS       "saajanjha"
#define FIREBASE_AUTH   "5cpzLONefCGgBSputjHSWRYIIMqLk2RWWzEGM9tn"
#define FIREBASE_HOST   "smart-home-91b82.firebaseio.com"
/************************* Adafruit.io Setup *********************************/

#define AIO_SERVER      "io.adafruit.com"
#define AIO_SERVERPORT  1883                   // use 8883 for SSL
#define AIO_USERNAME    "saajan_2"
#define AIO_KEY         "7233abe17323402583cf6b4199df9c9f"

/************ Global State (you don't need to change this!) ******************/

// Create an ESP8266 WiFiClient class to connect to the MQTT server.
WiFiClient client;
// or... use WiFiFlientSecure for SSL
//WiFiClientSecure client;

// Setup the MQTT client class by passing in the WiFi client and MQTT server and login details.
Adafruit_MQTT_Client mqtt(&client, AIO_SERVER, AIO_SERVERPORT, AIO_USERNAME, AIO_KEY);

/****************************** Feeds ***************************************/

// Setup a feed called 'photocell' for publishing.
// Notice MQTT paths for AIO follow the form: <username>/feeds/<feedname>
Adafruit_MQTT_Publish Humidity = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/mutton");
Adafruit_MQTT_Publish Temperature = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/chicken");
Adafruit_MQTT_Publish CO2 = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/air-quality");
Adafruit_MQTT_Publish Sound = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/sound");
Adafruit_MQTT_Publish Motion = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/motion");
Adafruit_MQTT_Publish Fire = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/fire");

// Setup a feed called 'onoff' for subscribing to changes.
Adafruit_MQTT_Subscribe light1 = Adafruit_MQTT_Subscribe(&mqtt, AIO_USERNAME "/feeds/relay1");
Adafruit_MQTT_Subscribe light2 = Adafruit_MQTT_Subscribe(&mqtt, AIO_USERNAME "/feeds/relay2");
Adafruit_MQTT_Subscribe light3 = Adafruit_MQTT_Subscribe(&mqtt, AIO_USERNAME "/feeds/relay3");
Adafruit_MQTT_Subscribe light4 = Adafruit_MQTT_Subscribe(&mqtt, AIO_USERNAME "/feeds/relay4");


/************ Necessary declaration for DHT11 ******************/
#define DHTTYPE           DHT11     // DHT 11 

DHT dht(DHTPIN, DHTTYPE);
uint32_t delayMS;

/*************************** Sketch Code ************************************/

// Bug workaround for Arduino 1.6.6, it seems to need a function declaration
// for some reason (only affects ESP8266, likely an arduino-builder bug).
void MQTT_connect();

void setup() {
  Serial.begin(115200);
  delay(10);
  pinMode(relay,OUTPUT);
  pinMode(relay2,OUTPUT);
  pinMode(relay3,OUTPUT);
  pinMode(relay4,OUTPUT);
  pinMode(intrusion,INPUT);
  pinMode(flame,INPUT);
  pinMode(S0, OUTPUT);
  pinMode(S1, OUTPUT);
  pinMode(A0, INPUT);
  Serial.println(F("Adafruit MQTT demo"));

  // Connect to WiFi access point.
  Serial.println(); Serial.println();
  Serial.print("Connecting to ");
  Serial.println(WLAN_SSID);

  WiFi.begin(WLAN_SSID, WLAN_PASS);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println();

  Serial.println("WiFi connected");
  Serial.println("IP address: "); Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);    
    
  //Setting up DHT sensor
  dht.begin();

  // Setup MQTT subscription for onoff feed.
  mqtt.subscribe(&light1);
  mqtt.subscribe(&light2);
  mqtt.subscribe(&light3);
  mqtt.subscribe(&light4);
 
}

uint32_t x=0;

void loop() {
  // Ensure the connection to the MQTT server is alive (this will make the first
  // connection and automatically reconnect when disconnected).  See the MQTT_connect
  // function definition further below.
  MQTT_connect();

  // this is our 'wait for incoming subscription packets' busy subloop
  // try to spend your time here

  Adafruit_MQTT_Subscribe *subscription;
  while ((subscription = mqtt.readSubscription(20000))) {
    if (subscription == &light1) {
      Serial.print(F("Got: "));
      Serial.println((char *)light1.lastread);
      int state=atoi((char *)light1.lastread);
      digitalWrite(relay,state);
      Serial.println(state);
      Firebase.setString("ApplianceStatus/Appliance1",String(state));  
      
    }
     if (subscription == &light2) {
      Serial.print(F("Got: "));
      Serial.println((char *)light2.lastread);
      int state=atoi((char *)light2.lastread);
      digitalWrite(relay2,state);
      Firebase.setString("ApplianceStatus/Appliance2",String(state));  
    }
     if (subscription == &light3) {
      Serial.print(F("Got: "));
      Serial.println((char *)light3.lastread);
      int state=atoi((char *)light3.lastread);
      digitalWrite(relay3,state);
    }
     if (subscription == &light4) {
      Serial.print(F("Got: "));
      Serial.println((char *)light4.lastread);
      int state=atoi((char *)light4.lastread);
      digitalWrite(relay4,state);
    }
  }

 

  digitalWrite(S0, LOW);
  digitalWrite(S1, LOW);
  Serial.print("C02 "); Serial.println(analogRead(analogpin));
  Serial.print("...");
  int  Value = analogRead(analogpin);
  if (! CO2.publish(Value)) {
    Serial.println(F("Failed"));
  } else {
    Serial.println(F("OK!"));
    Firebase.setString("LiveMonitoring/Air Quality",String(Value));
  }


  digitalWrite(S0, LOW);
  digitalWrite(S1, HIGH);
  Serial.print("Sound "); Serial.println(analogRead(analogpin));
  Serial.print("...");
  int raw_sound = analogRead(analogpin);
  Value = map(raw_sound,0,1024,0,100);
  if (! Sound.publish(Value)) {
    Serial.println(F("Failed"));
  } else {
    Serial.println(F("OK!"));
    Firebase.setString("LiveMonitoring/Sound",String(Value));
  }

  digitalWrite(S0, HIGH);
  digitalWrite(S1, HIGH);
  Serial.print("Light "); Serial.println(analogRead(analogpin));
  Serial.print("...");
  int raw_light = analogRead(analogpin);
  Value = map(raw_light,1024,0,0,100);
  Firebase.setString("LiveMonitoring/Light",String(Value));
  


  
  int  val = digitalRead(intrusion);   // read sensor value
  if (val == HIGH) { 
     Firebase.setString("LiveMonitoring/Intrusion",String(val));  
     Motion.publish(val);
  }
  else{
    Firebase.setString("LiveMonitoring/Intrusion",String(val));  
    Motion.publish(val);
  }

 int flame_detector= digitalRead(flame);   // read sensor value
  
  if (flame_detector == 1) { 
     Firebase.setString("LiveMonitoring/Fire",String(flame_detector));
     Serial.println(flame_detector);  
     Fire.publish(flame_detector);
  }
  else{
    Firebase.setString("LiveMonitoring/Fire",String(flame_detector)); 
    Serial.println(flame_detector); 
    Fire.publish(flame_detector);
  }
// Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float h = dht.readHumidity();
  // Read temperature as Celsius (the default)
  float t = dht.readTemperature();
  // Read temperature as Fahrenheit (isFahrenheit = true)
  float f = dht.readTemperature(true);

  // Check if any reads failed and exit early (to try again).
  if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  
                                 
  if (! Humidity.publish(h)) {
    Serial.println(F("Failed"));
  } else {
    Serial.println(F("OK!"));
    Serial.println(h);
    Firebase.setString("LiveMonitoring/Humidity",String(h));                                  //setup path and send readings
     
    
  }
  if (! Temperature.publish(t)) {
    Serial.println(F("Failed"));
  } else {
    Serial.println(t);
    Serial.println(F("OK!"));
      Firebase.setString("LiveMonitoring/Temperature",String(t));    
  }

}

// Function to connect and reconnect as necessary to the MQTT server.
// Should be called in the loop function and it will take care if connecting.
void MQTT_connect() {
  int8_t ret;

  // Stop if already connected.
  if (mqtt.connected()) {
    return;
  }

  Serial.print("Connecting to MQTT... ");

  uint8_t retries = 3;
  while ((ret = mqtt.connect()) != 0) { // connect will return 0 for connected
       Serial.println(mqtt.connectErrorString(ret));
       Serial.println("Retrying MQTT connection in 5 seconds...");
       mqtt.disconnect();
       delay(5000);  // wait 5 seconds
       retries--;
       if (retries == 0) {
         // basically die and wait for WDT to reset me
         while (1);
       }
  }
  Serial.println("MQTT Connected!");
}
