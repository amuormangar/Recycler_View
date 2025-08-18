package com.daniella.recyclerviews
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPostActivity : AppCompatActivity() {

    private lateinit var tvPostTitle: TextView
    private lateinit var tvPostBody: TextView
    private lateinit var rvComments: RecyclerView
    private var postId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (intent.extras != null) {
            postId = intent.extras!!.getInt("POST_ID")
            Toast.makeText(this, "Post ID: $postId", Toast.LENGTH_SHORT).show()
        }
        tvPostTitle = findViewById(R.id.tvPostTitle)
        tvPostBody = findViewById(R.id.tvPostBody)
        rvComments = findViewById(R.id.rvComments)
        rvComments.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        fetchPostById()
        fetchComments()
    }

    private fun fetchPostById() {
        val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
        val request = retrofit.getPostsById(postId)
        request.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    if (post != null) displayPost(post)
                } else {
                    Toast.makeText(baseContext, response.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(baseContext, "Failed to load post: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayPost(post: Post) {
        tvPostTitle.text = post.title
        tvPostBody.text = post.body
    }

    private fun fetchComments() {
        val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
        val request = retrofit.getCommentsForPost(postId)
        request.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val comments = response.body() ?: emptyList()
                    val commentAdapter = CommentRvAdapter(comments)
                    rvComments.adapter = commentAdapter
                } else {
                    Toast.makeText(this@ViewPostActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Toast.makeText(this@ViewPostActivity, "Failed to load comments: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
