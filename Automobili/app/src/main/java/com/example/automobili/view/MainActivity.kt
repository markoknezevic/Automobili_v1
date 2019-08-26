package com.example.automobili.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.automobili.R
import com.example.automobili.domen.Database
import com.example.automobili.kontroler.Kontroler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Database.getInstance().setCon(this)
        Kontroler.getInstance()
        view()

        listView.onItemClickListener = AdapterView.OnItemClickListener{parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val intent = Intent(applicationContext,PrikazAutomobilaActivity::class.java)
            intent.putExtra("index",position)
            startActivity(intent)
        }
    }


    fun view(){
        val list : ArrayList<String> ?= arrayListOf()

        for(i in Kontroler.getInstance().getList())
            list!!.add(i.marka)

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        listView.adapter = arrayAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuinflater = menuInflater
        menuinflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.dodaj){
            val intent = Intent(applicationContext,DodajAutomobilActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}
