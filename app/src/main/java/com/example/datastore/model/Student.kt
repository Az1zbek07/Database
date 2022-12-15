package com.example.datastore.model

data class Student(
    val id: Int? = null,
    val name: String? = null,
    val lastName: String? = null
){
    override fun toString(): String {
        return "$id \t $name \t $lastName"
    }
}
