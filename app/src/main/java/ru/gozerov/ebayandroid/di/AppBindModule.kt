package ru.gozerov.ebayandroid.di

import dagger.Binds
import dagger.Module
import ru.gozerov.data.remote.goods.GoodsRemote
import ru.gozerov.data.remote.goods.GoodsRemoteImpl
import ru.gozerov.data.remote.login.LoginRemote
import ru.gozerov.data.remote.login.LoginRemoteImpl
import ru.gozerov.data.repositories.goods.GoodsRepositoryImpl
import ru.gozerov.data.repositories.login.LoginRepositoryImpl
import ru.gozerov.domain.repositories.GoodsRepository
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Singleton

@Module
interface AppBindModule {

    @Binds
    @Singleton
    fun bindGoodsRemoteImplToGoodsRemote(goodsRemoteImpl: GoodsRemoteImpl) : GoodsRemote

    @Binds
    @Singleton
    fun bindGoodsRepoImplToGoodsRepo(goodsRepoImpl: GoodsRepositoryImpl) : GoodsRepository

    @Binds
    @Singleton
    fun bindLoginRemoteImplToLoginRemote(loginRemoteImpl: LoginRemoteImpl) : LoginRemote

    @Binds
    @Singleton
    fun bindLoginRepoImplToLoginRepo(loginRepositoryImpl: LoginRepositoryImpl) : LoginRepository

}
