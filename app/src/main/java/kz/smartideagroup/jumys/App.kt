package kz.smartideagroup.jumys

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import kz.smartideagroup.jumys.common.utils.YANDEX_API_KEY

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initYandexMapKit()
    }

    private fun initYandexMapKit() {
        MapKitFactory.setApiKey(YANDEX_API_KEY)
        MapKitFactory.initialize(this)
    }

}