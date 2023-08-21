package com.milantejani.theonepractical.ui.welcome

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.milantejani.theonepractical.AppConstant
import com.milantejani.theonepractical.BaseActivity
import com.milantejani.theonepractical.R
import com.milantejani.theonepractical.databinding.ActivityMainBinding
import com.milantejani.theonepractical.ui.user.UserListActivity
import com.milantejani.theonepractical.utils.PreferenceHelper
import com.milantejani.theonepractical.utils.PreferenceHelper.get
import com.milantejani.theonepractical.utils.PreferenceHelper.set
import com.milantejani.theonepractical.utils.isValidEmail
import com.milantejani.theonepractical.utils.viewBinding
import java.util.regex.Pattern


class MainActivity : BaseActivity() {

    private lateinit var pref: SharedPreferences
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pref = PreferenceHelper.customPrefs(this@MainActivity, AppConstant.USER_LOGIN)
        if (pref.contains(AppConstant.USER_LOGGED_IN)) {
            if (pref.get(AppConstant.USER_LOGGED_IN, false)) {
                loggedIn()
            }
        }

        Glide.with(binding.ivBackgroundImage).load(R.drawable.iv_background)
            .into(binding.ivBackgroundImage)

        binding.doLogin.setOnClickListener {
            if (binding.grpLoginUi.visibility == View.GONE) {
                showLoginUi()
            } else {
                //Do Login Process
                doLogin()
            }
        }
    }

    private fun doLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val pass = binding.edtPassword.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this@MainActivity, getString(R.string.empty_email), Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (pass.isEmpty()) {
            Toast.makeText(this@MainActivity, getString(R.string.empty_pass), Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (!email.isValidEmail()) {
            Toast.makeText(this@MainActivity, getString(R.string.invalid_email), Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (!isValidPassword(pass)) {
            Toast.makeText(this, getString(R.string.invalid_pass), Toast.LENGTH_SHORT).show()
            return
        }

        loggedIn()

    }

    private fun loggedIn() {
        pref.set(AppConstant.USER_LOGGED_IN, true)
        startActivity(Intent(this@MainActivity, UserListActivity::class.java))
        finish()
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun showLoginUi() {
        binding.ivBackgroundImage.alpha = 0.1f
        binding.grpLoginUi.visibility = View.VISIBLE
        binding.txtWelcomeTitle.text = getString(R.string.txt_login_2)
        binding.txtWelcomeDesc.text = getString(R.string.txt_login_desc)
    }


}