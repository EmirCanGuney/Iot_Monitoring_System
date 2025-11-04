import paho.mqtt.client as mqtt
import time

BROKER_ADDRESS ="localhost"
BROKER_PORT =1883
TOPIC ="test/message"

def on_connect(client, userdata, flags, rc, properties=None):
    if rc == 0:
        print("MQTT Broker'a bağlandı!")
    else:
        print(f"Bağlantı başarısız. Hata kodu: {rc}")

def on_publish(client, userdata, mid, properties=None):
    print(f"Mesaj gönderildi! Message ID: {mid}")
    
client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION1, "PythonTestClient")

client.on_connect= on_connect
client.on_publish= on_publish

#Paho-MQTT kütüphanesi, sen client.on_connect = diye bir fonksiyon atarsan,
#onu “bağlantı kurulduğunda çağırılacak fonksiyon” olarak görür.

print(" MQTT Broker'a bağlanıyor..")
client.connect(BROKER_ADDRESS ,BROKER_PORT ,keepalive=60)

#keepalive=60 → Her 60 saniyede bir "ben hala buradayım" mesajı gönder

client.loop_start()

time.sleep(3)

message = "Merhaba MQTT ,bu ilk mesajımız."
print(f"Mesaj gönderiliyor: {message}")
result =client.publish(TOPIC, message)
time.sleep(2)

print("Bağlantı kesiliyor...")
client.loop_stop()
client.disconnect()
print("Program bitti!")

