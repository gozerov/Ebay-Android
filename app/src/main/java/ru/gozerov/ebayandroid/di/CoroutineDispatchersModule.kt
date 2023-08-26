package ru.gozerov.ebayandroid.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ru.gozerov.data.utils.dispatchers.Dispatcher

@Module
class CoroutineDispatchersModule {

    @Provides
    fun provideIODispatcher(): Dispatcher = Dispatcher.IODispatcher(Dispatchers.IO)

}