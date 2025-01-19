package com.example.mobileagentcontrol

import android.os.Bundle
import android.widget.GridView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var characterGrid: GridView
    private lateinit var ipAddress: EditText
    private lateinit var connectButton: Button
    private var socketClient: SocketClient? = null

    private val characters = listOf(
        Character("Omen", R.drawable.omen, CharacterPosition(100, 200)),
        Character("Jett", R.drawable.jett, CharacterPosition(200, 200)),
        Character("Raze", R.drawable.raze, CharacterPosition(300, 200))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterGrid = findViewById(R.id.characterGrid)
        ipAddress = findViewById(R.id.ipAddress)
        connectButton = findViewById(R.id.connectButton)

        val adapter = CharacterAdapter(this, characters)
        characterGrid.adapter = adapter

        connectButton.setOnClickListener {
            val ip = ipAddress.text.toString()
            if(ip.isNotEmpty()) {
                socketClient = SocketClient(ip, 12345)
                socketClient?.connect()
                Toast.makeText(this, "Bağlanılıyor: $ip", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Lütfen IP adresi girin", Toast.LENGTH_SHORT).show()
            }
        }

        characterGrid.setOnItemClickListener { _, _, position, _ ->
            socketClient?.let { client ->
                val character = characters[position]
                client.sendCharacterSelect(character)
                Toast.makeText(this, "${character.name} seçildi", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this, "Önce PC'ye bağlanın", Toast.LENGTH_SHORT).show()
            }
        }
    }
}