package com.dicoding.malanginsider.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.dicoding.malanginsider.R
import com.dicoding.malanginsider.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        val btnBahasa = view.findViewById<Button>(R.id.btnBahasa)

        btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        btnBahasa.setOnClickListener {
            navigateToLanguageSettings()
        }
    }

    private fun showLogoutConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Keluar")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                // Proses logout (contoh: kembali ke LoginActivity)
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun navigateToLanguageSettings() {
        val intent = Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }
}
