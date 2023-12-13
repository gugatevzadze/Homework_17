package com.example.homework_17.home

import android.content.Context
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.R
import com.example.homework_17.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    //initializing the shared preferences using lazy
    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
    }

    override fun setUp() {
        //displaying the users email address when the fragment is set up
        displayUserEmail()
    }

    override fun onClickListeners() {
        //click listener for the logout button
        binding.buttonLogout.setOnClickListener {
            //logout and navigating to the login screen
            logoutAndNavigateToLogin()
        }
    }

    override fun bindObservers() {}

    //displaying the users email
    private fun displayUserEmail() {
        val userEmail = sharedPreferences.getString("user_email", "")
        binding.homeEmail.text = "Email: $userEmail"
    }

    //logout and navigating to the login screen
    private fun logoutAndNavigateToLogin() {
        //clearing the user session data in SharedPreferences
        sharedPreferences.edit().clear().apply()
        //navigating to the login
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }
}
