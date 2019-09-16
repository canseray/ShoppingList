package com.example.shoppinglist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.item_categories.*
import org.greenrobot.eventbus.EventBus

class CategoriesActivity : MainActivity(1) {

    private val TAG = "CategoryiesActivity"
    lateinit var mRecyclerView : RecyclerView
    lateinit var mDatabase : DatabaseReference
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    lateinit var manager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setupBottomNavigation()

        Log.d(TAG,"onCreate")

        mDatabase = FirebaseDatabase.getInstance().getReference("Categories")
        mRecyclerView = findViewById(R.id.categories_recyclerview)
        //mRecyclerView.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)

        staggeredGridLayoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        mRecyclerView.layoutManager = staggeredGridLayoutManager

        manager=supportFragmentManager



        loadCategoriesRecyclerView()

    }

    private fun loadCategoriesRecyclerView(){

        val option = FirebaseRecyclerOptions.Builder<Categories>()
            .setQuery(mDatabase,Categories::class.java)
            .setLifecycleOwner(this)
            .build()

        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Categories, CategoriesViewHolder>(option){

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoriesViewHolder {
                val itemView = LayoutInflater.from(this@CategoriesActivity).inflate(R.layout.item_categories,p0,false)
                return CategoriesViewHolder(itemView)

            }

            override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int, model: Categories) {

                val placeid = getRef(position).key.toString()

                holder.itemView.setOnClickListener { view ->

                category_container.visibility = View.VISIBLE
                var transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.category_container, ProductListFragment())
                transaction.commit()

                }

                mDatabase.child(placeid).addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        holder.categoryName.setText(model.CategoryName)
                    }

                })
            }

        }
        mRecyclerView.adapter = FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter.startListening()
    }

    class CategoriesViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView!!){
        internal var categoryName : TextView = itemView!!.findViewById(R.id.item_category_name)
    }
}
