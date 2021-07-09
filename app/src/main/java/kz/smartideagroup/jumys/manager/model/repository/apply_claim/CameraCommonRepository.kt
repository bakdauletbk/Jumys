package kz.smartideagroup.jumys.manager.model.repository.apply_claim

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kz.smartideagroup.jumys.BuildConfig
import kz.smartideagroup.jumys.common.views.BaseRepository
import kz.smartideagroup.jumys.manager.model.request.apply_claim.RequestWorkUpload
import okhttp3.ResponseBody
import retrofit2.Response

class CameraCommonRepository(application: Application) : BaseRepository(application) {

    suspend fun senVideoUpload(requestWorkUpload: RequestWorkUpload): Response<ResponseBody> =
        networkService.setWorkUpload(
            BuildConfig.VERSION_NAME,
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6IjRmMWcyM2ExMmFhIn0.eyJpc3MiOiJodHRwOlwvXC9uYWltaS5sb2MiLCJhdWQiOiJodHRwOlwvXC9uYWltaS5sb2MiLCJqdGkiOiI0ZjFnMjNhMTJhYSIsImlhdCI6MTYyNTMxNjE1MywiZXhwIjoxNjI1MzE5NzUzLCJ1aWQiOjEwMH0.dmZTXifcqVLqdAQPck4NkbEU6-WUsO9Angy7pR6ZaAw",
            requestWorkUpload)

}