package com.example.shoppinglist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ListActivity : MainActivity(2) {

    private val TAG = "ListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupBottomNavigation()

    }
}
