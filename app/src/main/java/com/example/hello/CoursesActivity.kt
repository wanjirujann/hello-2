package com.example.hello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_courses.*


class CoursesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        var courseList = listOf(
            Course("1", "Google Cloud", "Cloud 101", "Nigel Poulton", "Cloud all the way"),
            Course("2", "Android Kotlin", "AAD 201", "Chet Hasse", "Design Development"),
            Course(
                "3",
                "Mysql",
                "InnoDb 103",
                "Brad Travey",
                "Oracle Mysql"
            ),
            Course("4", "Cisco", "cisco 202", "Romain Guy", "Network Config for modern apps")
        )

        rvCourses.layoutManager = LinearLayoutManager(baseContext)
        rvCourses.adapter = CoursesAdapter(courseList)
    }
}