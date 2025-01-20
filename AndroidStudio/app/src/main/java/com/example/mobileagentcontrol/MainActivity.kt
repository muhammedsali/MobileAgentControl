package com.example.mobileagentcontrol

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var characterGrid: GridView
    private lateinit var ipAddress: EditText
    private lateinit var connectButton: MaterialButton
    private var socketClient: SocketClient? = null

    private val characters = listOf(
        Character("Jett", R.drawable.jett, CharacterPosition(522, 390)),    // Sol karakter
        Character("Raze", R.drawable.raze, CharacterPosition(862, 399)),    // Orta karakter
        Character("Omen", R.drawable.omen, CharacterPosition(1151, 338))    // Sağ karakter
    )

    private val lockButtonPosition = CharacterPosition(950, 730)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterGrid = findViewById(R.id.characterGrid)
        ipAddress = findViewById(R.id.ipAddress)
        connectButton = findViewById(R.id.connectButton)

        val adapter = CharacterAdapter(this, characters)
        characterGrid.adapter = adapter

        // Animasyon efekti ekle
        characterGrid.layoutAnimation = AnimationUtils.loadLayoutAnimation(
            this, 
            R.anim.layout_animation_fall_down
        )

        // Bağlan butonuna animasyon ekle
        connectButton.setOnClickListener {
            val ip = ipAddress.text.toString()
            if(ip.isNotEmpty()) {
                connectButton.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction {
                        connectButton.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()
                        
                        lifecycleScope.launch {
                            try {
                                socketClient = SocketClient(ip, 12345)
                                socketClient?.connect()
                                Toast.makeText(this@MainActivity, "Bağlanılıyor: $ip", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(this@MainActivity, "Bağlantı hatası: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .start()
            } else {
                Toast.makeText(this, "Lütfen IP adresi girin", Toast.LENGTH_SHORT).show()
            }
        }

        characterGrid.setOnItemClickListener { _, view, position, _ ->
            socketClient?.let { client ->
                val character = characters[position]
                
                // Karakter seçim animasyonu
                view.animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(100)
                    .withEndAction {
                        view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()
                        
                        lifecycleScope.launch {
                            try {
                                client.sendCharacterSelect(character)
                                Thread.sleep(500)
                                client.sendLockCommand(lockButtonPosition.x, lockButtonPosition.y)
                                Toast.makeText(this@MainActivity, "${character.name} seçildi ve kilitleniyor", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(this@MainActivity, "İşlem hatası: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .start()
            } ?: run {
                Toast.makeText(this, "Önce PC'ye bağlanın", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socketClient?.disconnect()
    }
}