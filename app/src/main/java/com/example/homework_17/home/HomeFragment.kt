package com.example.homework_17.home

import android.content.Context
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.R
import com.example.homework_17.databinding.FragmentHomeBinding
import com.example.homework_17.datastore.DataStoreUtil
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

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
        viewLifecycleOwner.lifecycleScope.launch {
            val userEmail = DataStoreUtil.getUserEmail().first()
            binding.homeEmail.text = "Email: $userEmail"
        }
    }

    //logout and navigating to the login screen
    private fun logoutAndNavigateToLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Clearing the user session data in DataStore
            DataStoreUtil.saveUserEmail("")
            // Navigating to the login
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

}
