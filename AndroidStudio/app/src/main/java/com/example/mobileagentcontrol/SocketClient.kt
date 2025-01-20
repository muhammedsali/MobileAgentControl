package com.example.mobileagentcontrol

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.PrintWriter
import java.net.Socket

class SocketClient(private val serverIp: String, private val serverPort: Int) {
    private var socket: Socket? = null
    private var outputStream: PrintWriter? = null
    private var isConnected = false

    suspend fun connect() {
        try {
            withContext(Dispatchers.IO) {
                socket = Socket(serverIp, serverPort)
                outputStream = socket?.getOutputStream()?.let { PrintWriter(it, true) }
                isConnected = true
                Log.d("SocketClient", "Bağlantı başarılı")
            }
        } catch (e: Exception) {
            Log.e("SocketClient", "Bağlantı hatası: ${e.message}")
            disconnect()
        }
    }

    suspend fun sendCharacterSelect(character: Character) {
        try {
            withContext(Dispatchers.IO) {
                if (isConnected) {
                    outputStream?.println("CLICK ${character.position.x} ${character.position.y}")
                    outputStream?.flush()
                    Log.d("SocketClient", "Karakter seçim komutu gönderildi: ${character.name}")
                }
            }
        } catch (e: Exception) {
            Log.e("SocketClient", "Komut gönderme hatası: ${e.message}")
            disconnect()
        }
    }

    suspend fun sendLockCommand(x: Int, y: Int) {
        try {
            withContext(Dispatchers.IO) {
                if (isConnected) {
                    outputStream?.println("CLICK $x $y")
                    outputStream?.flush()
                    Log.d("SocketClient", "Kilitleme komutu gönderildi")
                }
            }
        } catch (e: Exception) {
            Log.e("SocketClient", "Komut gönderme hatası: ${e.message}")
            disconnect()
        }
    }

    fun disconnect() {
        try {
            outputStream?.close()
            socket?.close()
            isConnected = false
            Log.d("SocketClient", "Bağlantı kapatıldı")
        } catch (e: Exception) {
            Log.e("SocketClient", "Bağlantı kapatma hatası: ${e.message}")
        }
    }
}