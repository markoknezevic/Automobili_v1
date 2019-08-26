package com.example.automobili.kontroler

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.automobili.domen.Automobil
import com.example.automobili.domen.Database
import java.io.ByteArrayOutputStream

class Kontroler {
    var listaAutomobila : ArrayList<Automobil> ?= arrayListOf()
    companion object {
        private var instanca: Kontroler? = null

        fun getInstance(): Kontroler {
            if (instanca == null)
                instanca = Kontroler()

            return instanca!!
        }
    }
    init {
        getData()
    }
    fun getData(){

        val context = Database.getInstance().context!!.openOrCreateDatabase("Baza",Context.MODE_PRIVATE,null)
        context.execSQL("CREATE TABLE IF NOT EXISTS auto(marka VARCHAR, cena DOUBLE, godiste INT(2), opis VARCHAR, slika BLOB)")

        val cursor = context.rawQuery("SELECT * FROM auto",null)


        val markaInd = cursor.getColumnIndex("marka")
        val cenaInd = cursor.getColumnIndex("cena")
        val godisteInd = cursor.getColumnIndex("godiste")
        val opisInd = cursor.getColumnIndex("opis")
        val slikaInd = cursor.getColumnIndex("slika")

        cursor.moveToFirst()
        var i = cursor.count

        while(i != 0){

            val marka = cursor.getString(markaInd)
            val cena = cursor.getDouble(cenaInd)
            val godiste = cursor.getInt(godisteInd)
            val opis = cursor.getString(opisInd)
            val byteArray = cursor.getBlob(slikaInd)
            val image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            val auto = Automobil(marka,cena,godiste,opis,image)
            listaAutomobila!!.add(auto)

            cursor.moveToNext()
            i--
        }


    }

    fun unesi(auto : Automobil){
        listaAutomobila!!.add(auto)




        val context = Database.getInstance().context!!.openOrCreateDatabase("Baza",Context.MODE_PRIVATE,null)

        val insertString ="INSERT INTO auto(marka,cena,godiste,opis,slika) "
        val valueString = "VALUES(?,?,?,?,?)"

        val SQL = insertString+valueString
        val statment = context.compileStatement(SQL)
        statment.bindString(1,auto.marka)
        statment.bindDouble(2,auto.cena)
        statment.bindLong(3,auto.godiste.toLong())
        statment.bindString(4,auto.opis)
        statment.bindBlob(5,byteArrayMaker(auto))

        statment.execute()
    }

    fun delete(auto: Automobil){
        listaAutomobila!!.remove(auto)
        val context = Database.getInstance().context!!.openOrCreateDatabase("Baza",Context.MODE_PRIVATE,null)

        context.execSQL("DELETE FROM auto WHERE marka = '"+auto.marka+"' AND '"+auto.opis+"'")

    }

    fun edit(auto: Automobil,index : Int){
        val context = Database.getInstance().context!!.openOrCreateDatabase("Baza",Context.MODE_PRIVATE,null)
        val stariauto = listaAutomobila!!.get(index)



        listaAutomobila!!.removeAt(index)
        listaAutomobila!!.add(auto)
        val SQL = "UPDATE auto SET marka =?,cena =?,godiste =?,opis =?,slika=? WHERE marka = ? AND opis =?"
        val statment = context.compileStatement(SQL)
        statment.bindString(1,auto.marka)
        statment.bindDouble(2,auto.cena)
        statment.bindLong(3,auto.godiste.toLong())
        statment.bindString(4,auto.opis)
        statment.bindBlob(5,byteArrayMaker(auto))
        statment.bindString(6,stariauto.marka)
        statment.bindString(7,stariauto.opis)

        statment.execute()



    }

    fun byteArrayMaker(auto: Automobil):ByteArray{
        val outputStream = ByteArrayOutputStream()
        auto.slika.compress(Bitmap.CompressFormat.PNG,20,outputStream)
        return outputStream.toByteArray()
    }

    fun getList() : ArrayList<Automobil>{
        if(listaAutomobila!!.size != 0)
            return listaAutomobila!!
        else
            return arrayListOf()
    }
}