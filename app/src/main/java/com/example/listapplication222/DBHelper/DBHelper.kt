package com.example.listapplication222.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import com.example.listapplication222.fragment.Person

class DBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABADE_VER){

    companion object {
        private val DATABADE_VER = 1
        private val DATABASE_NAME = "SAMPLEKOTLIN.db"

        private val TABLE_NAME = "Person"
        private val COOL_TITLE = "title"
        private val COOL_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COOL_TITLE TEXT, $COOL_CONTENT TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    private val writeableDatabase: Any

    val allPerson:List<Person>
        get(){
            val IstPersons = ArrayList<Person>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler,null)
            if (cursor.moveToFirst())
            {
                do {
                    val person = Person()
                    person.title = cursor.getString(cursor.getColumnIndex(COOL_TITLE))
                    person.content = cursor.getString(cursor.getColumnIndex(COOL_CONTENT))

                    IstPersons.add(person)
                }while (cursor.moveToNext())
            }
            db.close()
            return IstPersons
        }
fun addPerson(person: Person)
{
    val db = this.writableDatabase
    val values = ContentValues()
    values.put(COOL_TITLE, person.title)
    values.put(COOL_CONTENT, person.content)

    db.insert(TABLE_NAME,null,values)
    db.close()

}

fun updatePerson(person: Person):Int
{
    val db = this.writableDatabase
    val values = ContentValues()
    values.put(COOL_TITLE, person.title)
    values.put(COOL_CONTENT, person.content)

    return db.update(TABLE_NAME, values, "$COOL_TITLE=?", arrayOf(person.title.toString()))

}

fun deletePerson(person: Person)
{
    val db = this.writeableDatabase

    db.delete(TABLE_NAME, "$COOL_TITLE=?", arrayOf(person.title.toString()))

    db.close()

}

}
