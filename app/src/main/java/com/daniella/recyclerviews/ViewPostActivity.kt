package com.daniella.recyclerviews

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response

class ViewPostActivity : AppCompatActivity() {
    lateinit var  tvPostBody:TextView
    lateinit var  tvPostTitle:TextView
    lateinit var  rvComments:RecyclerView
    var postId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(intent.extras!=null){
            postId = intent.extras!!.getInt("POST_ID")
            Toast.makeText(this,"$postId",Toast.LENGTH_SHORT)
        }
    }

    override fun onResume() {
        super.onResume()
        tvPostBody = findViewById(R.id.tvPostBody)
        tvPostTitle = findViewById(R.id.tvPostTitle)
        rvComments = findViewById(R.id.rvComments)
        fetchPostById()
    }
    fun displayPost(post:Post){

    }
    fun fetchPostById(){
        val retrofit= ApiClient.buildApiClient((ApiInterface::class.java))
        val request = retrofit.getPostsById(postId)
        request.enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful){
                   displayPost(response.body()!!)
                }else{
                    Toast.makeText(baseContext,response.errorBody()?.string(),Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }

        })
    }
}