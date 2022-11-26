package kr.ac.hallym.backgroundnotice.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kr.ac.hallym.networkretrofit2.Model.UserTable


class DatabaseHelper private constructor(context: Context) : // private constructor => 싱글톤 패턴, 초기화 불가능
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val DATABASE_NAME = "DBName.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "USERID"
        const val COL1_id = "userid"
        const val COL2_createAt = "createAt"

        //https://www.charlezz.com/?p=45959
        //SingleTon Pattern(싱글톤 패턴)
        @Volatile
        private var instance: DatabaseHelper? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(DatabaseHelper::class.java) {
                instance ?: DatabaseHelper(context).also {
                    instance = it
                }
            }
        fun getInstance() = { instance }

    }


    fun insertData(userId: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL1_id, userId)
        }
        db.insert(TABLE_NAME, null, contentValues) // 값이 없으면 행을 삽입하지않음
    }

    fun updateData(id: String, name: String, phone: String, email: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL1_id, id)
        }
        db.update(TABLE_NAME, contentValues, "$COL1_id = ?", arrayOf(id))
    }

    fun deleteData(id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL1_id = ?", arrayOf(id))
    }
    fun dropTable(){
        val db = this.writableDatabase
        db.execSQL("delete from $TABLE_NAME")
    }

    fun getAllData(): MutableList<UserTable> {
        var result = mutableListOf<UserTable>()


        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)



        try {
            if (cursor.count != 0) {
                while (cursor.moveToNext()) {
                    result.add(UserTable(userid = cursor.getInt(0),
                    createAt =  cursor.getInt(1).toString()))
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return result
    }


    override fun onCreate(db: SQLiteDatabase?) {
        var i : List<Int>;
        val QueryCreateDB = "$COL1_id INT(64) NOT NULL PRIMARY KEY," +
                "$COL2_createAt DATETIME DEFAULT NULL DEFAULT CURRENT_TIMESTAMP"

        val createQuery = "CREATE TABLE if not exists $TABLE_NAME (" +
                "$QueryCreateDB"+
                ")"

        db?.execSQL(createQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

}