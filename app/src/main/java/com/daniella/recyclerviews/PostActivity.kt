package com.daniella.recyclerviews

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {
    lateinit var rvPost: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onResume() {
        super.onResume()
        rvPost = findViewById(R.id.rvPosts)
        rvPost.layoutManager = GridLayoutManager(this, 1)
        fetchPosts()


    }

    fun displayPosts(postList: List<Post>){
        val postsAdapter = PostsRvAdapter(this,postList)
        rvPost.adapter = postsAdapter
    }


    //API FETCHING
    fun fetchPosts(){
        val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
        val request = retrofit.getPosts()

//        Do the Api call on a separate thread from this main one(it is an intense process would cause the app to stop responding)
//
        request.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful){
                    val postsList = response.body()
                    if(postsList!=null){
                        displayPosts(postsList)
                    }
//                    Display posts
                }else{
                    Toast.makeText(baseContext, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                }
            }
//Notify if the call fails for any reason(happens on main thread)

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(baseContext,t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}

