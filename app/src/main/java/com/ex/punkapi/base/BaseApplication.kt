package com.ex.punkapi.base

import android.app.Application
import com.ex.punkapi.manager.PurchaseCompleteManager
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApplication : Application() {

    var purchaseCompleteManager = PurchaseCompleteManager(this)

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }

    fun getRealm(): Realm{
        var config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        return Realm.getInstance(config)
    }
}