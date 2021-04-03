package com.meghdut.upsilent.network

import com.meghdut.upsilent.models.GoogleDriveResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class Query(var q: String = "", var password: String = "", var page_index: Int = 0)




interface GoogleDriveApi {
    @POST("/0:/{path}/")
    @Headers("cookie: __cfduid=d")
    fun getData(@Path("path", encoded = true) path: String,
                @Body query: Query): retrofit2.Call<GoogleDriveResponse>

}

fun main() {

    val retrofit = Retrofit.Builder()
            .baseUrl(URLConstants.DRIVE_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val googleDriveApi = retrofit.create(GoogleDriveApi::class.java)
    val query = Query()
    val execute = googleDriveApi.getData("", query).execute()
    println(execute.body())
}