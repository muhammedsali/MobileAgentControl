package com.example.mobileagentcontrol

import java.io.PrintWriter
import java.net.Socket

class SocketClient(private val serverIp: String, private val serverPort: Int) {
    private var socket: Socket? = null
    private var outputStream: PrintWriter? = null

    fun connect() {
        Thread {
            try {
                socket = Socket(serverIp, serverPort)
                outputStream = PrintWriter(socket?.getOutputStream(), true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun sendCharacterSelect(character: Character) {
        outputStream?.println("CLICK ${character.position.x} ${character.position.y}")
    }

    // Yeni eklenen metod
    fun sendLockCommand(x: Int, y: Int) {
        outputStream?.println("CLICK $x $y")
    }

    fun disconnect() {
        try {
            outputStream?.close()
            socket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}