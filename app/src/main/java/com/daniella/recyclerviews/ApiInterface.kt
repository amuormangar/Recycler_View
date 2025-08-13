package com.daniella.recyclerviews

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {
    @GET("/posts")
    fun getPosts(): Call <List<Post>>

    @GET("posts/{postId")
    fun getPostsById (@Path("postId") postId:Int):Call<Post>
}