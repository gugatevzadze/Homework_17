package com.example.homework_17.login

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.R
import com.example.homework_17.common.Resource
import com.example.homework_17.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setUp() {
        //checking if the user is already logged in and navigate to home if true
        checkAndNavigateToHome()

        //listen for registration result using Fragment Result API
        setFragmentResultListener("registerResult") { _, bundle ->
            //retrieving registered email and password from the registration result
            val registeredEmail = bundle.getString("email", "")
            val registeredPassword = bundle.getString("password", "")

            binding.loginEmail.setText(registeredEmail)
            binding.loginPassword.setText(registeredPassword)

            //updating the login button state based on the registered email
            updateLoginButtonState(registeredEmail)
        }
    }

    override fun onClickListeners() {
        //click listener for the login button
        binding.loginBtn.setOnClickListener {

            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            //initiating the login process using the view model
            viewModel.login(email, password, requireContext())
        }

        //navigating to the registration page
        binding.registerPageBtn.setOnClickListener {
            navigateToRegisterPage()
        }

        //enable/disable the login button based on email and password
        binding.loginBtn.isEnabled = false
        binding.loginEmail.addTextChangedListener { editable ->
            //updating the login button state when the email field changes
            updateLoginButtonState(editable.toString())
        }
    }
    //function to bind observers for observing view model results
    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.collect { result ->
                    when (result) {
                        is Resource.Success -> handleLoginSuccess(result)
                        is Resource.Error -> handleLoginError(result)
                        is Resource.Loading -> handleLoading(result.loading)
                    }
                }
            }
        }
    }

    //function to check if the user is already logged in and navigate to home
    private fun checkAndNavigateToHome() {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("user_token", null)
        if (!userToken.isNullOrEmpty()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    //function to navigate to the registration page
    private fun navigateToRegisterPage() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    //function to update the login button state based on email validity
    private fun updateLoginButtonState(email: String) {
        val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordNotEmpty = binding.loginPassword.text!!.isNotEmpty()
        binding.loginBtn.isEnabled = isValidEmail && isPasswordNotEmpty
    }

    //function to handle the login
    private fun handleLoginSuccess(result: Resource.Success<LoginResponse>) {
        //saving session if remember me is checked
        if (binding.loginRememberMe.isChecked) {
            saveUserTokenToSharedPreferences(result.data?.token)
        }
        //navigating to the page
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    //function to handle the login error
    private fun handleLoginError(result: Resource.Error<LoginResponse>) {
        val errorMessage = result.errorMessage
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    //function to handle the loading state
    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    //function to save the user token to SharedPreferences
    private fun saveUserTokenToSharedPreferences(token: String?) {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_token", token)
            apply()
        }
    }
}
