package com.example.homework_17.di

import com.example.homework_17.data.login.LoginRepositoryImpl
import com.example.homework_17.data.login.service.LoginService
import com.example.homework_17.data.register.RegisterRepositoryImpl
import com.example.homework_17.data.register.service.RegisterService
import com.example.homework_17.datastore.DataStoreUtil
import com.example.homework_17.domain.login.LoginRepository
import com.example.homework_17.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataStoreUtil(): DataStoreUtil {
        return DataStoreUtil
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: LoginService, dataStoreUtil: DataStoreUtil): LoginRepository {
        return LoginRepositoryImpl(loginService = loginService, dataStoreUtil = dataStoreUtil)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(registerService: RegisterService, dataStoreUtil: DataStoreUtil): RegisterRepository {
        return RegisterRepositoryImpl(registerService = registerService, dataStoreUtil = dataStoreUtil)
    }
}