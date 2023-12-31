package com.milantejani.theonepractical.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val firstName: String,
    val lastName: String,
    val email: String
)