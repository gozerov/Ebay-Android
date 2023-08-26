package ru.gozerov.ebayandroid.di

import dagger.Module

@Module(
    includes = [
        AppBindModule::class,
        CoroutineDispatchersModule::class,
        StorageModule::class,
        RetrofitModule::class
    ]
)
class AppModule