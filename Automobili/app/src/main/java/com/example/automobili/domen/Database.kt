package com.example.automobili.domen

import android.content.Context

class Database {
    public var context : Context ?= null

    companion object{
        private var instanca: Database ?= null

        fun getInstance() : Database{
            if(instanca==null)
                instanca= Database()
            return instanca!!
        }
    }
    fun setCon(context: Context){
        this.context = context
    }
}