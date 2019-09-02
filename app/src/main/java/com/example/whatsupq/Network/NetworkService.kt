package com.example.whatsupq.Network

import com.example.whatsupq.Data.GetRegularListItem
import com.example.whatsupq.Data.GetThemeBoxItem
import retrofit2.http.*
import retrofit2.Call
interface NetworkService {
    //정기배송 리스트 조회
    @GET("product/regular")
    fun getRegularDataResponse(
        @Query("category") category: String,
        @Query("flag") flag : Int
    ) : Call<GetRegularListItem>

    //테마박스 리스트 조회
    @GET("product/themabox")
    fun getThemeBoxDataResponse(
        @Query("category") category: String,
        @Query("flas") flag: Int
    ) : Call<GetThemeBoxItem>
}