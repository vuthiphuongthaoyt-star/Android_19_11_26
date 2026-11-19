package com.ute.studentmvvm.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ute.studentmvvm.data.AppDatabase
import com.ute.studentmvvm.databinding.DialogAddStudentBinding
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.network.RetrofitClient
import com.ute.studentmvvm.repository.StudentRepository
import com.ute.studentmvvm.viewmodel.StudentViewModel
import com.ute.studentmvvm.viewmodel.StudentViewModelFactory

class AddStudentDialogFragment : DialogFragment() {

    private val viewModel: StudentViewModel by viewModels({ requireParentFragment() }) {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = StudentRepository(database.studentDao(), RetrofitClient.instance)
        StudentViewModelFactory(repository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAddStudentBinding.inflate(LayoutInflater.from(requireContext()))
        return AlertDialog.Builder(requireContext())
            .setTitle("Thêm Sinh Viên Mới")
            .setView(binding.root)
            .setPositiveButton("Lưu vào Room") { _, _ ->
                val name = binding.etFullName.text.toString().trim()
                val code = binding.etStudentCode.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val gpa = binding.etGpa.text.toString().trim().toDoubleOrNull() ?: 0.0

                if (name.isEmpty() || code.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Họ tên và MSSV không được để trống!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val student = Student(
                    fullName = name,
                    studentCode = code,
                    email = if (email.isNotEmpty()) email else "${code.lowercase()}@ute.udn.vn",
                    gpa = gpa
                )
                viewModel.insert(student)
            }
            .setNegativeButton("Hủy", null)
            .create()
    }
}
