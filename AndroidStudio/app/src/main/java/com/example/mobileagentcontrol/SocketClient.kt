package com.example.mobileagentcontrol

import java.io.PrintWriter
import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SocketClient(private val ipAddress: String, private val port: Int) {
    private var socket: Socket? = null
    private var writer: PrintWriter? = null

    fun connect() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                socket = Socket(ipAddress, port)
                writer = PrintWriter(socket!!.getOutputStream(), true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendCharacterSelect(character: Character) {
        GlobalScope.launch(Dispatchers.IO) {
            writer?.println("SELECT:${character.name}:${character.position.x}:${character.position.y}")
        }
    }
} 