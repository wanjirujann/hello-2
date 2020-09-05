package com.example.hello.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.hello.models.Course
import com.example.hello.models.CoursesResponse
import com.example.hello.R
import com.example.hello.api.ApiClient
import com.example.hello.api.ApiInterface
import com.example.hello.database.HelloDatabase
import kotlinx.android.synthetic.main.activity_courses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CoursesActivity : AppCompatActivity() {

    lateinit var database: HelloDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        database = Room.databaseBuilder(baseContext, HelloDatabase::class.java, "hello-db").build()

        fetchCourses()
    }

    private fun fetchCourses() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val accessToken = sharedPreferences.getString("ACCESS_TOKEN_KEY", "")

        val apiClient = ApiClient.buildService(ApiInterface::class.java)
        val coursesCall = apiClient.getCourses("Bearer $accessToken")


        coursesCall.enqueue(object : Callback<CoursesResponse> {
            override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()

                fetchCoursesFromDatabase()
            }

            override fun onResponse(
                call: Call<CoursesResponse>,
                response: Response<CoursesResponse>
            ) {
                if (response.isSuccessful) {
                    var courseList = response.body()?.courses as List<Course>
                    Thread {
                        courseList.forEach { course ->
                            database.courseDao().insertCourse(course)
                        }
                    }.start()

                    displayCourses(courseList)
                } else {
                    Toast.makeText(baseContext, response.errorBody().toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    fun fetchCoursesFromDatabase() {
        Thread {
            val courses = database.courseDao().getAllCourses()

            runOnUiThread {
                displayCourses(courses)
            }
        }.start()
    }

    fun displayCourses(courses: List<Course>) {
        var coursesAdapter = CoursesAdapter(courses)
        rvCourses.layoutManager = LinearLayoutManager(baseContext)
        rvCourses.adapter = coursesAdapter
    }

}