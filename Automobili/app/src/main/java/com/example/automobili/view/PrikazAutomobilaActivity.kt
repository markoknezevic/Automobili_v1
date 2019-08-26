package com.example.automobili.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.automobili.R
import com.example.automobili.domen.Automobil
import com.example.automobili.kontroler.Kontroler
import kotlinx.android.synthetic.main.activity_prikaz_automobila.*

class PrikazAutomobilaActivity : AppCompatActivity() {

    var index : Int ?= null
    var auto : Automobil ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prikaz_automobila)

        view()
    }

    fun view(){
        index = intent.getIntExtra("index",0)
        auto = Kontroler.getInstance().getList().get(index!!)

        markaView.setText("Marka: "+auto!!.marka)
        cenaView.setText("Cena: "+auto!!.cena.toString())
        godisteView.setText("Godiste: "+auto!!.godiste.toString())
        opisView.setText("Opis: "+auto!!.opis)
        imageView.setImageBitmap(auto!!.slika)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.edit_delete_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.itemId == R.id.delete){
            Kontroler.getInstance().delete(auto!!)

            var intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        if(item!!.itemId == R.id.edit){
            var intent = Intent(applicationContext,DodajAutomobilActivity::class.java)
            intent.putExtra("key",1)
            intent.putExtra("index",index)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
