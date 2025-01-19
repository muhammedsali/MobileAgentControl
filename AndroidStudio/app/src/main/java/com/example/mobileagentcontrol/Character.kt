package com.example.mobileagentcontrol

data class Character(
    val name: String,
    val imageResourceId: Int,
    val position: CharacterPosition
)

data class CharacterPosition(
    val x: Int,
    val y: Int
) 