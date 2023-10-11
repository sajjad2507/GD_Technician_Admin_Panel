package com.example.dgtechnicainadminpanel.DataModels

data class PersonalInfoModel(
    val techId: String = "",
    val name: String = "",
    val phoneNo: String = "",
    val email: String = "",
    val imageUri: String = "",
    val category: String = "",
    val joiningDate: String = "",
    val rating: Double = 0.0,
    val subCategory: String = "",
    val appointmentType: String = "",
    val description: String = "",
    val status: String = ""
)