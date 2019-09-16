package com.example.shoppinglist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.quizflix.models.ProductList
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class ProductListFragment : Fragment() {

    lateinit var gelenCategoryID : String
    lateinit var mRecyclerView : RecyclerView
    lateinit var mDatabase : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view=inflater!!.inflate(R.layout.fragment_product_list, container, false)

        mDatabase = FirebaseDatabase.getInstance().getReference("ProductList")
        mRecyclerView = view.findViewById(R.id.product_list_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        gelenCategoryID = "2"


        loadProductLisatRecyclerView(gelenCategoryID)

        return view
    }

    private fun loadProductLisatRecyclerView(gelenCaregoryID: String){
        var query = mDatabase.orderByChild("CategoryID").equalTo(gelenCaregoryID.toDouble())

            val option = FirebaseRecyclerOptions.Builder<ProductList>()
                .setQuery(query,ProductList::class.java)
                .setLifecycleOwner(this)
                .build()

            val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<ProductList,ProductListViewHolder>(option){

                override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductListViewHolder {
                    val itemView = LayoutInflater.from(activity).inflate(R.layout.item_list,p0,false)
                    return ProductListViewHolder(itemView)
                }

                override fun onBindViewHolder(holder: ProductListViewHolder, position: Int, model: ProductList) {

                    query.addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            holder.productName.setText(model.Name)
                        }

                    })

                }

            }
        mRecyclerView.adapter = FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter.startListening()

    }

    class ProductListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        internal var productName : TextView = itemView!!.findViewById(R.id.category_list_text)
    }




}
