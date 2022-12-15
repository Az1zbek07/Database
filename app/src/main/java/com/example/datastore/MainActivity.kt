package com.example.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.datastore.database.MyDatabase
import com.example.datastore.databinding.ActivityMainBinding
import com.example.datastore.model.Student
import com.example.datastore.util.toastMy

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val myDataBase by lazy { MyDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val name = binding.editTextTextPersonName.text.toString().trim()
            val lastName = binding.editTextTextPersonName2.text.toString().trim()
            if (name.isNotBlank() && lastName.isNotBlank()){
                val student = Student(name = name, lastName = lastName)
                myDataBase.saveStudent(student)
                toastMy("Successfully saved")
                binding.apply {
                    editTextTextPersonName.text?.clear()
                    editTextTextPersonName2.text?.clear()
                }
            }
            updateText()
        }

        binding.btnGet.setOnClickListener {
            val id = binding.editId.text.toString().trim().toInt()
            val student = myDataBase.getStudentById(id)
            binding.apply {
                editTextTextPersonName.setText(student.name)
                editTextTextPersonName2.setText(student.lastName)
            }
        }

        binding.btnDelete.setOnClickListener {
            val id = binding.editId.text.toString().trim().toInt()
            myDataBase.deleteStudent(id)
            toastMy("Deleted")
            updateText()
        }

        binding.btnUpdate.setOnClickListener {
            val id = binding.editId.text.toString().trim().toInt()
            val name = binding.editTextTextPersonName.text.toString().trim()
            val lastName = binding.editTextTextPersonName2.text.toString().trim()
            myDataBase.updateStudent(Student(id = id, name = name, lastName = lastName))
            toastMy("Updated")
            updateText()
        }
    }

    private fun updateText() {
        val stringBuilder = StringBuilder()
        myDataBase.getAllStudents().forEach{
            stringBuilder.append(it.toString()).append("\n")
        }
        binding.textName.text = stringBuilder
    }
}