package com.ute.studentmvvm.network

import retrofit2.Response
import retrofit2.http.*

interface StudentApiService {

    @GET("students")
    suspend fun getStudents(): Response<List<StudentDto>>

    @POST("students")
    suspend fun addStudent(@Body student: StudentDto): Response<StudentDto>

    @PUT("students/{id}")
    suspend fun updateStudent(
        @Path("id") id: Int,
        @Body student: StudentDto
    ): Response<StudentDto>

    @DELETE("students/{id}")
    suspend fun deleteStudent(@Path("id") id: Int): Response<Unit>
}
