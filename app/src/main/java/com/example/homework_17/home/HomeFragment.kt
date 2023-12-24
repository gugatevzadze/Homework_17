package com.example.homework_17.home

import android.content.Context
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.R
import com.example.homework_17.databinding.FragmentHomeBinding
import com.example.homework_17.datastore.DataStoreUtil
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

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
            logoutAndNavigateToLogin()
        }
    }

    override fun bindObservers() {
        displayUserEmail()
    }
    //displaying the users email
    private fun displayUserEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userEmail = DataStoreUtil.getUserEmail().firstOrNull()
            binding.homeEmail.text = "Email: $userEmail"
        }
    }
    //logout and navigating to the login screen
    private fun logoutAndNavigateToLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            //clearing the user data in DataStore
            DataStoreUtil.clearUserData()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }
}