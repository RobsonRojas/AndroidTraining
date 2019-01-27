#include <ESP8266WiFi.h> 
#include <WiFiClient.h> 
#include <ESP8266WebServer.h>

#define SSID "netvirtua_72" 
#define PASS "3137rik@l" 
#define LED LED_BUILTIN

ESP8266WebServer server(80);

int ledState = 0; 
void handleRoot(); 
void handleLed(); 
void handleNotFound();

void setup() {
  Serial.begin(115200);
  
  pinMode(LED, OUTPUT); 
  digitalWrite(LED, 1);
  
  WiFi.mode(WIFI_STA); 
  WiFi.begin(SSID, PASS); 
  
  while (WiFi.status() != WL_CONNECTED)
  delay(200);
  
  Serial.print("\n\nConectado.\nEndereco IP: "); 
  Serial.println(WiFi.localIP());
  
  server.on("/", handleRoot); 
  server.on("/led", handleLed); 
  server.onNotFound(handleNotFound);
  
  server.begin();
  
  Serial.println("Servidor HTTP inicializado!");
}

void loop() {
  server.handleClient();
}

void handleRoot() {
  int ldr = analogRead(A0); 
  String message = "{ name: 'IoT Node v1', IP: " +
  
  WiFi.localIP().toString() + ", led: " + String(ledState) + ", ldr: " + String(ldr) + " }";
  
  server.send(200, "application/json", message);
  
  Serial.println("Recebi conexão no servidor HTTP ...");
} 

void handleNotFound() {
  server.send(404, "text/plain", "404 - Page Not Found!");
}

void handleLed() {
  String type = "application/json";
  
  if (server.arg("state") == "0") { 
    ledState = 0; 
    digitalWrite(LED, 1); 
    server.send(200, type, "{ status: 'ok', message: 'Led off' } ");
  } else {
  ledState = 1; digitalWrite(LED, 0); server.send(200, type, "{ status: 'ok', message: 'Led on' } ");
  } 
}
