package com.rafi.myfriendapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_my_friends_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsAddFragment : Fragment() {

    private var namaInput  : String=""
    private var emailInput : String=""
    private var telpInput  : String=""
    private var alamatInput: String=""
    private var genderInput: String=""

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null


    companion object {
        fun newInstance(): MyFriendsAddFragment {
            return MyFriendsAddFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_friends_add,
            container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
        btnSave.setOnClickListener {validasiInput() }


    }

    private fun validasiInput() {
        namaInput   = edtName.text.toString()
        emailInput  = edtEmail.text.toString()
        telpInput   = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when {

            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih Jenis Kelamin") -> tampilToast("Kelamin harus dipilh")
            emailInput.isEmpty() -> edtEmail.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> edtTelp.error = "Telp tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat tidak boleh kosng"

            else -> {
                val teman = MyFriend(nama = namaInput, kelamin = genderInput,
                    email = emailInput, telp = telpInput, alamat = alamatInput)
                tambahDataTeman(teman)
            }

        }
    }

    private fun tambahDataTeman(teman: MyFriend) : Job {
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }

    private fun tampilToast(message: String){
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
