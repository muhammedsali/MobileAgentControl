# Mobile Agent Control for Valorant

A mobile application that allows you to select and lock agents in Valorant game through your Android device.

## Features

- Remote agent selection via mobile device
- Real-time mouse control
- Character locking functionality
- Modern UI with animations
- Secure socket communication

## Components

### Android Application
- Character selection grid with animations
- Socket client for PC communication
- Modern Material Design UI
- Error handling and connection status
- Character position tracking

### Windows Application
- TCP server for mobile connection
- Mouse position tracking (F8 toggle)
- Real-time cursor control
- Modern dark theme interface
- Connection status monitoring

## Setup

### Android Application
1. Install the APK on your Android device
2. Get the PC's IP address
3. Connect to the PC server
4. Select a character to control

### Windows Application
1. Run the Windows application
2. Start the server
3. Use F8 to track mouse positions
4. Monitor connection status

## Technical Details

- Android: Kotlin, Material Design, Coroutines
- Windows: C#, WinForms, TCP/IP
- Communication: Socket-based protocol
- Mouse Control: Win32 API

## Usage

1. Start the Windows application
2. Note the displayed IP address
3. Launch the Android application
4. Enter the PC's IP address
5. Connect to the server
6. Select an agent to control the mouse
7. The agent will be selected and locked automatically

---

# Valorant için Mobil Ajan Kontrolü

Android cihazınız üzerinden Valorant oyununda ajan seçimi ve kilitleme yapmanızı sağlayan bir mobil uygulama.

## Özellikler

- Mobil cihaz üzerinden uzaktan ajan seçimi
- Gerçek zamanlı fare kontrolü
- Karakter kilitleme işlevi
- Animasyonlu modern arayüz
- Güvenli soket iletişimi

## Bileşenler

### Android Uygulaması
- Animasyonlu karakter seçim ızgarası
- PC iletişimi için soket istemcisi
- Modern Material Design arayüz
- Hata yönetimi ve bağlantı durumu
- Karakter pozisyon takibi

### Windows Uygulaması
- Mobil bağlantı için TCP sunucusu
- Fare pozisyon takibi (F8 ile açma/kapama)
- Gerçek zamanlı imleç kontrolü
- Modern koyu tema arayüzü
- Bağlantı durumu izleme

## Kurulum

### Android Uygulaması
1. APK'yı Android cihazınıza yükleyin
2. PC'nin IP adresini alın
3. PC sunucusuna bağlanın
4. Kontrol etmek için bir karakter seçin

### Windows Uygulaması
1. Windows uygulamasını çalıştırın
2. Sunucuyu başlatın
3. Fare pozisyonlarını takip etmek için F8 kullanın
4. Bağlantı durumunu izleyin

## Teknik Detaylar

- Android: Kotlin, Material Design, Coroutines
- Windows: C#, WinForms, TCP/IP
- İletişim: Soket tabanlı protokol
- Fare Kontrolü: Win32 API

## Kullanım

1. Windows uygulamasını başlatın
2. Görüntülenen IP adresini not edin
3. Android uygulamasını başlatın
4. PC'nin IP adresini girin
5. Sunucuya bağlanın
6. Fareyi kontrol etmek için bir ajan seçin
7. Ajan otomatik olarak seçilecek ve kilitlenecektir
