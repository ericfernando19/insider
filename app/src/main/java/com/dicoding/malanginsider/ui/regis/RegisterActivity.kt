package com.dicoding.malanginsider.ui.regis

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.databinding.ActivityRegisterBinding
import com.dicoding.malanginsider.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupTextWatchers()

        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (validateInput(name, email, password)) {
                setupRegisterButton(name, email, password)
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.edRegisterName.error = "Nama tidak boleh kosong"
            isValid = false
        }

        if (email.isEmpty()) {
            binding.edRegisterEmail.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edRegisterEmail.error = "Format email tidak valid"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.edRegisterPassword.error = "Password tidak boleh kosong"
            isValid = false
        } else if (password.length < 8) {
            binding.edRegisterPassword.error = "Password minimal 8 karakter"
            isValid = false
        }

        return isValid
    }

    private fun setupTextWatchers() {
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val name = binding.edRegisterName.text.toString()
                val email = binding.edRegisterEmail.text.toString()
                val password = binding.edRegisterPassword.text.toString()
                binding.registerButton.isEnabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
                binding.registerButton.setBackgroundColor(
                    if (binding.registerButton.isEnabled) resources.getColor(R.color.biru, null)
                    else resources.getColor(R.color.biru_pudar, null)
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.edRegisterName.addTextChangedListener(watcher)
        binding.edRegisterEmail.addTextChangedListener(watcher)
        binding.edRegisterPassword.addTextChangedListener(watcher)
    }

    private fun setupRegisterButton(name: String, email: String, password: String) {
        val snackbar = Snackbar.make(binding.root, "Loading...", Snackbar.LENGTH_INDEFINITE)
        snackbar.show()

        lifecycleScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@RegisterActivity) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(this@RegisterActivity, "Registrasi berhasil! ${user?.email}", Toast.LENGTH_LONG).show()

                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                snackbar.dismiss()
            }
        }
    }
}
