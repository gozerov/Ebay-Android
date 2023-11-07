package ru.gozerov.ebayandroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import ru.gozerov.data.utils.dispatchers.Dispatcher

@InstallIn(SingletonComponent::class)
@Module
class CoroutineDispatchersModule {

    @Provides
    fun provideIODispatcher(): Dispatcher = Dispatcher.IODispatcher(Dispatchers.IO)

}