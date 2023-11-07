package ru.gozerov.ebayandroid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gozerov.data.remote.goods.GoodsRemote
import ru.gozerov.data.remote.goods.GoodsRemoteImpl
import ru.gozerov.data.remote.login.LoginRemote
import ru.gozerov.data.remote.login.LoginRemoteImpl
import ru.gozerov.data.remote.sales.SalesRemote
import ru.gozerov.data.remote.sales.SalesRemoteImpl
import ru.gozerov.data.repositories.goods.GoodsRepositoryImpl
import ru.gozerov.data.repositories.login.LoginRepositoryImpl
import ru.gozerov.data.repositories.sales.SalesRepositoryImpl
import ru.gozerov.domain.repositories.GoodsRepository
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.repositories.SalesRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
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

    @Binds
    @Singleton
    fun bindSalesRemoteImplToLoginRemote(salesRemoteImpl: SalesRemoteImpl) : SalesRemote

    @Binds
    @Singleton
    fun bindSalesRepoImplToLoginRepo(salesRepositoryImpl: SalesRepositoryImpl) : SalesRepository

}
