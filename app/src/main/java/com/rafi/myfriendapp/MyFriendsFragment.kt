package com.rafi.myfriendapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_my_friends.*


class MyFriendsFragment : Fragment() {

    //    lateinit var listTeman : MutableList<MyFriend>
   // lateinit var listTeman: ArrayList<MyFriend>
    private var listTeman: List<MyFriend>? = null

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container:
        ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_my_friends, container, false
        )
    }

    override fun onViewCreated(
        view: View, savedInstanceState:
        Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        fabAddFriend.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }
//        simulasiDataTeman()
//        tampilTeman()
          ambilDataTeman()

    }

    private fun ambilDataTeman() {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(this, Observer { r -> listTeman = r
        when{
            listTeman?.size == 0 -> tampilToast("Belum ada data teman")

            else ->{
                 tampilTeman()
            }
        }})
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

//    private fun simulasiDataTeman() {
//        listTeman = ArrayList()
//
//        listTeman.add(MyFriend("Husnul", "Perempuan", "husnul@gmail.com", "08439276342", "Malang"))
//        listTeman.add(MyFriend("Ammar", "Laki", "ammar@gmail.com", "0734583845", "Pujon"))
//    }

    private fun tampilTeman() {
        listMyFriends.layoutManager = LinearLayoutManager(activity)
        listMyFriends.adapter = MyFriendAdapter(activity!!, listTeman!!)
    }

}
