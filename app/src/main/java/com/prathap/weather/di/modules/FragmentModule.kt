package com.prathap.weather.di.modules

import com.prathap.weather.ui.fragments.HelpFragment
import com.prathap.weather.ui.fragments.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeHelpFragment(): HelpFragment
}