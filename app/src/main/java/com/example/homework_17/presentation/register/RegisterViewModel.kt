package com.example.homework_17.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.data.common.Resource
import com.example.homework_17.domain.register.RegisterRepository
import com.example.homework_17.domain.register.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    //current state of the register operation
    private val _registerResult = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading(loading = false))
    val registerResult: StateFlow<Resource<RegisterResponse>> get() = _registerResult

    //function to initiate register process
    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerRepository.register(email, password).collect{
                when(it) {
                    is Resource.Success -> _registerResult.value = Resource.Success(data = it.data!!)
                    is Resource.Error -> _registerResult.value = Resource.Error(errorMessage = it.errorMessage!!)
                    is Resource.Loading -> _registerResult.value = Resource.Loading(loading = it.loading)
                }
            }
        }
    }
}

