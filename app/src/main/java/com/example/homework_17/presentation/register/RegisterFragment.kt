package com.example.homework_17.presentation.register

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.data.common.Resource
import com.example.homework_17.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun setUp() {}

    override fun onClickListeners() {
        //click listener for the register button
        binding.registerBtn.setOnClickListener {

            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()
            val repeatPassword = binding.registerRepeatPassword.text.toString()

            //checking if passwords match before initiating registration
            if (password == repeatPassword) {
                viewModel.register(email, password)
            } else {
                showToast("Passwords do not match")
            }
        }

        //click listener for navigating back to the login page
        binding.backBtn.setOnClickListener {
            navigateBackToLogin()
        }

        //disabling the register button
        binding.registerBtn.isEnabled = false

        //text change listeners to update the button state when input fields change
        binding.registerEmail.addTextChangedListener {
            updateRegisterButtonState()
        }
        binding.registerPassword.addTextChangedListener {
            updateRegisterButtonState()
        }
        binding.registerRepeatPassword.addTextChangedListener {
            updateRegisterButtonState()
        }
    }

    //function to bind observers for observing view model results
    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResult.collect { result ->
                    when (result) {
                        is Resource.Success -> handleRegisterSuccess()
                        is Resource.Error -> handleRegisterError(result.errorMessage!!)
                        is Resource.Loading -> handleLoading(result.loading)
                    }
                }
            }
        }
    }

    //function to update the register button state based on input field
    private fun updateRegisterButtonState() {
        val isEmailValid = isValidEmail(binding.registerEmail.text.toString())
        val isPasswordNotEmpty = binding.registerPassword.text!!.isNotEmpty()
        val isRepeatPasswordNotEmpty = binding.registerRepeatPassword.text!!.isNotEmpty()
        binding.registerBtn.isEnabled = isEmailValid && isPasswordNotEmpty && isRepeatPasswordNotEmpty
    }

    //function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    //function to navigate back to the login page
    private fun navigateBackToLogin() {
        findNavController().navigateUp()
    }

    //function to handle the successful registration
    private fun handleRegisterSuccess() {
        //setting the results using Fragment Result API
        setFragmentResult(
            "registerResult",
            bundleOf(
                "email" to binding.registerEmail.text.toString(),
                "password" to binding.registerPassword.text.toString()
            )
        )
        //navigating back to the login page
        navigateBackToLogin()
    }

    //function to handle the registration error
    private fun handleRegisterError(errorMessage: String) {
        // Show a toast message with the error
        showToast(errorMessage)
    }

    //function to handle the loading state
    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    //function to check if an email is valid
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
