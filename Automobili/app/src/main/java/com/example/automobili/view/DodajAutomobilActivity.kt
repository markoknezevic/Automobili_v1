package com.example.automobili.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.automobili.R
import com.example.automobili.domen.Automobil
import com.example.automobili.kontroler.Kontroler
import kotlinx.android.synthetic.main.activity_dodaj_automobil.*


class DodajAutomobilActivity : AppCompatActivity() {


    var index : Int ?= null
    var auto : Automobil ?= null
    var image : Bitmap ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_automobil)

        index = intent.getIntExtra("index",-1)

        if(index != -1)
            view()
    }

    fun view(){

        auto = Kontroler.getInstance().getList().get(index!!)
        markaText.setText(auto!!.marka)
        cenaText.setText(auto!!.cena.toString())
        godisteText.setText(auto!!.godiste.toString())
        opisText.setText(auto!!.opis)
        imageView.setImageBitmap(auto!!.slika)
        image=auto!!.slika
    }

    fun sacuvaj(view: View){
        val key = intent.getIntExtra("key",0)

        if(key == 1){
            izmeni()
        }else{
            unesi()
        }
    }


    fun unesi(){
        Kontroler.getInstance().unesi(makeAuto())
        startIntent()
        Toast.makeText(applicationContext,"Automobil dodat", Toast.LENGTH_LONG).show()
    }

    fun izmeni(){
        Kontroler.getInstance().edit(makeAuto(),index!!)
        startIntent()
        Toast.makeText(applicationContext,"Izmena izvrsena", Toast.LENGTH_LONG).show()
    }

    fun startIntent(){
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
    }

    fun makeAuto():Automobil{
        val marka = markaText.text.toString()
        val cena = cenaText.text.toString().toDouble()
        val godiste = godisteText.text.toString().toInt()
        val opis = opisText.text.toString()

        val auto = Automobil(marka,cena,godiste,opis,image!!)
        return auto
    }

    fun pickImage(view:View){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            mediaStore()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mediaStore()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun mediaStore(){
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            val uri = data.data
            image = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            imageView.setImageBitmap(image)

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
