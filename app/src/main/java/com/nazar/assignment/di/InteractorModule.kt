package com.nazar.assignment.di

import com.nazar.assignment.core.domain.interactor.SportsInteractor
import com.nazar.assignment.core.domain.interactor.SportsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class InteractorModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSportsInteractor(sportsInteractor: SportsInteractorImpl): SportsInteractor
}
