package com.prologistik.test.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
){
    override fun toString(): String = name
}
