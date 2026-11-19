package com.ute.studentmvvm.network

import com.google.gson.annotations.SerializedName
import com.ute.studentmvvm.model.Student

data class StudentDto(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("fullName")
    val fullName: String = "",

    @SerializedName("studentCode")
    val studentCode: String = "",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("gpa")
    val gpa: Double = 0.0,

    @SerializedName("major")
    val major: String = "Công Nghệ Thông Tin"
) {
    fun toEntity(): Student {
        return Student(
            id = id,
            fullName = fullName,
            studentCode = studentCode,
            email = email,
            gpa = gpa,
            major = major
        )
    }

    companion object {
        fun fromEntity(student: Student): StudentDto {
            return StudentDto(
                id = student.id,
                fullName = student.fullName,
                studentCode = student.studentCode,
                email = student.email,
                gpa = student.gpa,
                major = student.major
            )
        }
    }
}
