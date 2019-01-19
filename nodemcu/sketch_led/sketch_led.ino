#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#define SSID "ICOMP_TE"
#define PASS ""
#define CLIENT_ID "NODEMCUROB"
#define MQTT_TOPIC "82202320210"
#define LED LED_BUILTIN

WiFiClient espClient;
PubSubClient mqtt(espClient);

int ledState = 0;
long lastMsg = 0;
char msg[50] = {};
const char* mqtt_server = "200.17.49.151";

void connect();
void handleMessage(char* topic, byte* payload, unsigned int length);

void setup() {
  Serial.begin(1152200);
  pinMode(LED, OUTPUT);
  digitalWrite(LED, 1);

  WiFi.mode(WIFI_STA);
  WiFi.begin(SSID, PASS);
  
  while(WiFi.status() != WL_CONNECTED) {
    delay(200);      
  }
  
  Serial.print("\n\nConectado.\nEndereo IP: ");
  Serial.println(WiFi.localIP());

  mqtt.setServer(mqtt_server, 1883);
  mqtt.setCallback(handleMessage);
}

void loop() {
  if (!mqtt.connected()) {
    connect();
  }

  mqtt.loop();

  long now = millis();
  
  if (now - lastMsg > 1000) {
    lastMsg = now;
    snprintf(msg, 50, "led: %d, ldr: %d", ledState, analogRead(A0));
    
    Serial.print("Publish message: ");

    Serial.println(msg);
    mqtt.publish(MQTT_TOPIC, msg);
  }
}

void connect() {
  while(!mqtt.connected()){
    Serial.print("Attempting MQTT connection...");

    
    if (mqtt.connect(CLIENT_ID)) {
      Serial.println("Connected");
      mqtt.subscribe(MQTT_TOPIC);
    } 
    else
    {
      Serial.print("failed, rc=");
      Serial.print(mqtt.state());
      Serial.println("try again in 5 seconds");
      
      delay(5000);
    }
  }
}

void handleMessage(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  
  for(int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }

  Serial.println();

  
  if ((char) payload[0] == 'L') {
    if ((char) payload[1] == '1') {
      ledState = 1;
      digitalWrite(LED, 0);
    } else {
      ledState = 0;
      digitalWrite(LED, 1);
    }
  }
  
      
  
      
      
      
}
