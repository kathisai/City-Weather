package com.prathap.weather.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prathap.weather.di.ViewModelKey
import com.prathap.weather.ui.HomeViewModel
import com.prathap.weather.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@SuppressWarnings("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindhomeViewModel(splashViewModel: HomeViewModel): ViewModel

    /**
     * To Inject View Model to any class we need @ViewModelProvider
     */
    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}