import paho.mqtt.client as mqtt
import json 

BROKER_ADDRESS = "localhost"
BROKER_PORT= 1883
TOPIC= "sensors/data"

def on_message(client, userdata, msg):
    payload_str =msg.payload.decode('utf-8')

    try:
        data =json.loads(payload_str)

        print(f"\n📩 Yeni mesaj geldi!")
        print(f"   🔖 Sensör: {data['sensor_id']}")
        print(f"   🌡️  Sıcaklık: {data['temperature']}°C")
        print(f"   💧 Nem: {data['humidity']}%")
        print(f"   🕐 Zaman: {data['timestamp']}")
        print("-" * 50)
    
    except json.JSONDecodeError as e:
        print(f"Json parse hatası: {e}")
        print(f"Raw mesaj: {payload_str}")


def on_connect(client, userdata, flags, rc, properties=None):

    if rc==0:
        print("✅ Mqtt Broker'a bağlandı!")
        print(f"📡 Dinlenen topic: {TOPIC}")
        print("=" * 50)
        print("🎧 Mesajlar dinleniyor... (Durdurmak için Ctrl+C)\n")

        client.subscribe(TOPIC) 
        #topic e abone olur ,bağlanır

    else:
        print(f" Bağlantı başarısız! Hata kodu: {rc}")

def main():
    client =mqtt.Client(mqtt.CallbackAPIVersion.VERSION1, "PythonSubscriber")

    client.on_connect =on_connect
    client.on_message =on_message

    print("MQTT Broker'a bağlanılıyor..")

    try:
        client.connect(BROKER_ADDRESS, BROKER_PORT, keepalive=60)
    except Exception as e:
        print(f"Bağlantı hatası :{e}")
        print("Mqtt Broker çalışıyor mu? (docker ps)")
        return
        
    try:
        client.loop_forever()
    except KeyboardInterrupt:
        print("\n\n Dinleme durduruldu! ")
    finally:
        print(" Bağlantı kapatılıyor...")
        client.disconnect()
        print(" Program sonlandı.")

if __name__=="__main__":
    main()