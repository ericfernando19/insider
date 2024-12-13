package com.dicoding.malanginsider.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.malanginsider.MainActivity
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.ViewModelFactory
import com.dicoding.malanginsider.data.pref.UserModel
import com.dicoding.malanginsider.databinding.ActivityLoginBinding
import com.dicoding.malanginsider.ui.home.HomeFragment
import com.dicoding.malanginsider.ui.regis.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        observeTextFields()
        setupView()
        setupFieldListeners()
        setupActions()
    }

    private fun observeTextFields() {
        binding.edLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                if (email.isEmpty()) {
                    binding.emailEditTextLayout.error = "Email tidak boleh kosong"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailEditTextLayout.error = "Format email tidak valid"
                } else {
                    binding.emailEditTextLayout.error = null
                }
                validateLoginButton()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if (password.isEmpty()) {
                    binding.passwordEditTextLayout.error = "Kata sandi tidak boleh kosong"
                } else if (password.length < 8) {
                    binding.passwordEditTextLayout.error = "Kata sandi minimal harus 8 karakter"
                } else {
                    binding.passwordEditTextLayout.error = null
                }
                validateLoginButton()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validateLoginButton() {
        val email = binding.edLoginEmail.text.toString().trim()
        val password = binding.edLoginPassword.text.toString().trim()
        val isValid = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 8

        binding.loginButton.isEnabled = isValid
        binding.loginButton.setBackgroundColor(
            if (isValid) resources.getColor(R.color.biru, null)
            else resources.getColor(R.color.hitam, null)
        )
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupActions() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (validateInput(email, password)) {
                loginUser(email, password)
            }
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        val snackbar = Snackbar.make(binding.root, "Loading...", Snackbar.LENGTH_INDEFINITE)
        snackbar.show()

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val userModel = UserModel(user?.email ?: "", "", true)
                            viewModel.saveSession(userModel)

                            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                snackbar.dismiss()
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.edLoginEmail.error = "Please input email"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.edLoginEmail.error = "Invalid email format"
                false
            }
            password.isEmpty() -> {
                binding.edLoginPassword.error = "Please input password"
                false
            }
            password.length < 8 -> {
                binding.edLoginPassword.error = "Password should be at least 8 characters"
                false
            }
            else -> true
        }
    }

    private fun setupFieldListeners() {
        binding.edLoginEmail.setOnFocusChangeListener { _, focused ->
            binding.emailEditTextLayout.boxStrokeWidth = if (focused) 2 else 0
        }
        binding.edLoginPassword.setOnFocusChangeListener { _, focused ->
            binding.passwordEditTextLayout.boxStrokeWidth = if (focused) 2 else 0
        }
    }
}
