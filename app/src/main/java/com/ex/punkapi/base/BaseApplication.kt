package com.ex.punkapi.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }

    fun getRealm(): Realm{
        var config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        return Realm.getInstance(config)
    }
}