package com.ute.studentmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    val allStudents: LiveData<List<Student>> = repository.allStudents.asLiveData(Dispatchers.IO)

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> get() = _statusMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun insert(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insert(student)
            _statusMessage.postValue("Đã thêm sinh viên: ${student.fullName}")
        } catch (e: Exception) {
            _statusMessage.postValue("Lỗi khi thêm: ${e.message}")
        }
    }

    fun update(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.update(student)
            _statusMessage.postValue("Đã cập nhật sinh viên: ${student.fullName}")
        } catch (e: Exception) {
            _statusMessage.postValue("Lỗi khi cập nhật: ${e.message}")
        }
    }

    fun delete(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.delete(student)
            _statusMessage.postValue("Đã xóa sinh viên: ${student.fullName}")
        } catch (e: Exception) {
            _statusMessage.postValue("Lỗi khi xóa: ${e.message}")
        }
    }

    fun syncFromCloud() = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.postValue(true)
        val result = repository.syncFromCloud()
        _isLoading.postValue(false)
        if (result.isSuccess) {
            _statusMessage.postValue("Đã đồng bộ ${result.getOrNull()} sinh viên từ máy chủ!")
        } else {
            _statusMessage.postValue("Chưa thể kết nối máy chủ Cloud, sử dụng dữ liệu cục bộ.")
        }
    }
}
