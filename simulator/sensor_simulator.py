import paho.mqtt.client as mqtt
import json
import random
import time
from datetime import datetime

#pythonda sabit değerler büyük harflerle yazılır bu yazısız bir kuraldır

BROKER_ADDRESS= "localhost"
BROKER_PORT= 1883
TOPIC= "sensors/data"

SENSOR_COUNT= 3
SEND_INTERVAL= 5

def on_connect(client ,userdata, flags, rc, properties=None):
    if rc ==0:
        print("MQTT Broker'a bağlandı!")
        print(f"Topic: {TOPIC}")
        print(f"Sensör sayısı: {SENSOR_COUNT}")
        print(f"Gönderim aralığı: {SEND_INTERVAL}")
    else:
        print(f"Bağlantı başarısız! Hata kodu: {rc}")


def on_publish(client,userdata,mid,properties =None):
    print(f"Mesaj gönderildi! (ID: {mid})")

#rastgele veri üretim
def generate_sensor_data(sensor_id):

    """
    temperature = round(random.uniform(28.0, 35.0), 1)
    humidity = round(random.uniform(70.0, 85.0), 1)
    """

    temperature =round(random.uniform(18.0 ,30.0),1) #bir ondalık basamak
    humidity=round(random.uniform(35.0, 85.0),1)
    
    timestamp= datetime.now().isoformat() #iso8601 format

    data= {
        "sensor_id": sensor_id,
        "temperature": temperature,
        "humidity": humidity,
        "timestamp": timestamp,
        "unit_temp": "C",
        "unit_humidity": "%" #ölçü birimleri
    }

    return data

def main():
    client =mqtt.Client(mqtt.CallbackAPIVersion.VERSION1, "SensorSimulator")

    client.on_connect= on_connect
    client.on_publish= on_publish

    print( "MQTT brokera bağlanılıyor...")
    
    try:
        client.connect(BROKER_ADDRESS, BROKER_PORT, keepalive=60)
    except Exception as  e :
        print(f"Bağlantı Hatası: {e}")
        print("MQTT broker çalışıyor mu? (docker ps)")
        return
    
    client.loop_start()
    time.sleep(3)

    print("\n🚀 Sensör simülasyonu başladı! (Durdurmak için Ctrl+C)")
    print("=" * 50)

    try:
        message_count=0

        while True:
            message_count +=1
            print(f"\n Gönderim {message_count}")

            for i in range (1, SENSOR_COUNT +1):
                sensor_id= f"sensor_{i:03d}"

                sensor_data= generate_sensor_data(sensor_id)

                json_pyload =json.dumps(sensor_data)

                client.publish(TOPIC, json_pyload)

                print(f"{sensor_id}: {sensor_data['temperature']}°C, {sensor_data['humidity']}%")

            print(f"\n {SEND_INTERVAL} saniye bekleniyor..")
            time.sleep(SEND_INTERVAL)

    except KeyboardInterrupt:
        print("\n\n Simulasyon durduruldu !")

    finally:
        print("Bağlantı koparılıyor..")
        client.loop_stop()
        client.disconnect()
        print("Program sonladnı.")


    #if __name__ == "__main__" → Bu dosya direkt çalıştırılıyorsa çalıştır başka yerden importla çalıştırma. Standart pratik pythonda.

if __name__ == "__main__":
    main()


