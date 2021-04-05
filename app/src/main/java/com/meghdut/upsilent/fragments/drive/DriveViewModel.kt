package com.meghdut.upsilent.fragments.drive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.meghdut.upsilent.models.DriveFile
import com.meghdut.upsilent.models.GoogleDriveResponse
import com.meghdut.upsilent.network.GoogleDriveApi
import com.meghdut.upsilent.network.Query
import com.meghdut.upsilent.network.URLConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DriveViewModel(application: Application) : AndroidViewModel(application) {

    private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(URLConstants.DRIVE_ROOT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val googleDriveApi = retrofit.create(GoogleDriveApi::class.java)

    private val filesCache = hashMapOf<String, GoogleDriveResponse>()

    val folderPath = arrayListOf<String>()
    val fileList = MutableLiveData<List<DriveFile>>(listOf())
    val currentState = MutableLiveData<CurrentState<GoogleDriveResponse>>()


    fun explorePath(path: String) {
        if (path.isEmpty() && folderPath.isNotEmpty()) return
        val mainPath = getRelativePath(path)
        loadPath(mainPath) {
            folderPath.add(path)
        }
    }

    fun refreshList() {
        val mainPath = folderPath.joinToString("/") { android.net.Uri.encode(it, "utf-8") }
        loadPath(mainPath) { }
    }

    private fun loadPath(mainPath: String, onComplete: () -> Unit) {
        val query = Query()
        currentState.postValue(CurrentState.Loading)
        googleDriveApi.getData(mainPath, query).enqueue(object : Callback<GoogleDriveResponse> {
            override fun onResponse(call: Call<GoogleDriveResponse>, response: Response<GoogleDriveResponse>) {
                if (response.isSuccessful) {
                    val driveResponse = response.body()!!
                    fileList.postValue(driveResponse.data.files)
                    currentState.postValue(CurrentState.Success(driveResponse))
                    onComplete()
                    filesCache[mainPath] = driveResponse
                } else {
                    currentState.postValue(CurrentState.Error(Exception("Server error ${response.code()} ")))
                }
            }

            override fun onFailure(call: Call<GoogleDriveResponse>, t: Throwable) {
                currentState.postValue(CurrentState.Error(Exception(t.localizedMessage)))
            }

        })
    }

    fun goBack() {
        if (folderPath.size == 1) return
        val last = folderPath.lastIndex
        folderPath.removeAt(last)
        var mainPath = folderPath.joinToString("/") { android.net.Uri.encode(it, "utf-8") }
        if (mainPath.isEmpty()) mainPath = "/"
        val driveResponse = filesCache[mainPath] ?: return
        fileList.postValue(driveResponse.data.files)
        currentState.postValue(CurrentState.Success(driveResponse))
    }

    fun getRelativePath(item: String): String {
        val mainPath = folderPath.joinToString("/") { android.net.Uri.encode(it, "utf-8") }
        return mainPath + "/${android.net.Uri.encode(item, "utf-8")}"
    }


}