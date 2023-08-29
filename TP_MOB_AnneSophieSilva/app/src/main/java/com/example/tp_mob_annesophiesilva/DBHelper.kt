package com.example.tp_mob_annesophiesilva

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "dbadm.db", null, 1) {

    val sql = arrayOf(
        "CREATE TABLE administrador (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)",
        "INSERT INTO administrador (username, password) VALUES ('adm1', 'pass1')",
        "INSERT INTO administrador (username, password) VALUES ('adm2', 'pass2')",
        "INSERT INTO administrador (username, password) VALUES ('adm3', 'pass3')",

        "CREATE TABLE curso (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, local TEXT, dataInicio TEXT, dataFim TEXT, preco DOUBLE , duracao INTEGER, edicao INTEGER, imagemId INTEGER)",
        "INSERT INTO curso (nome, local, dataInicio, dataFim, preco, duracao, edicao, imagemId) VALUES ('FRONT-END DEVELOPER', 'Lisboa', '2023/10/18', '2024/06/26', 0.0, 1000, 1, 2131165345)",
        "INSERT INTO curso (nome, local, dataInicio, dataFim, preco, duracao, edicao, imagemId) VALUES ('WEBSITES & eCOMMERCE', 'Viseu', '2023/10/02', '2023/11/16', 0.0, 75, 1, 2131165347)",
        "INSERT INTO curso (nome, local, dataInicio, dataFim, preco, duracao, edicao, imagemId) VALUES ('DASHBOARDS POWER BI', 'Porto', '2023/10/25', '2023/12/20', 0.0, 50, 1, 2131165343)",
        "INSERT INTO curso (nome, local, dataInicio, dataFim, preco, duracao, edicao, imagemId) VALUES ('DESIGN UX/UI', 'Porto', '2023/09/21', '2023/11/20', 0.0, 75, 1, 2131165344)"
    )


    override fun onCreate(db: SQLiteDatabase?) {
        sql.forEach {
            db?.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE curso")
        onCreate(db)
    }


    fun loginAdministrador(username: String, password: String): Administrador {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM administrador WHERE username = ? AND password = ?", arrayOf(username, password))
        var administrador = Administrador()

        if (cursor.count == 1) {
            cursor.moveToFirst()

            val idIndex = cursor.getColumnIndex("id")
            val usernameIndex = cursor.getColumnIndex("username")
            val passwordIndex = cursor.getColumnIndex("password")

            val id = cursor.getInt(idIndex)
            val username = cursor.getString(usernameIndex)
            val password = cursor.getString(passwordIndex)

            administrador = Administrador(id, username, password)
        }
        db.close()
        return administrador
    }


    /* --------------------CURSOS--------------------------*/

    fun insertCurso(nome: String, local: String , dataInicio: String, dataFim: String,
                    preco: Double, duracao: Int, edicao: Int, imagemId: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nome", nome)
        contentValues.put("local", local)
        contentValues.put("dataInicio", dataInicio)
        contentValues.put("dataFim", dataFim)
        contentValues.put("preco", preco)
        contentValues.put("duracao", duracao)
        contentValues.put("edicao", edicao)
        contentValues.put("imagemId", imagemId)
        val res = db.insert("curso", null, contentValues)
        db.close()
        return res
    }



    fun updateCursos(id: Int, nome: String, local: String , dataInicio: String, dataFim: String,
                     preco: Double, duracao: Int, edicao: Int, imagemId: Int): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nome", nome)
        contentValues.put("local", local)
        contentValues.put("dataInicio", dataInicio)
        contentValues.put("dataFim", dataFim)
        contentValues.put("preco", preco)
        contentValues.put("duracao", duracao)
        contentValues.put("edicao", edicao)
        contentValues.put("imagemId", imagemId)
        val res = db.update("curso", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }


    fun deleteCursos(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("curso","id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun selectCursoByIdObjeto(id: Int): Curso {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM curso WHERE id = ?", arrayOf(id.toString()))
        var curso = Curso()

        if (cursor.count == 1) {
            cursor.moveToFirst()

            val idIndex = cursor.getColumnIndex("id")
            val nomeIndex = cursor.getColumnIndex("nome")
            val localIndex = cursor.getColumnIndex("local")
            val dataInicioIndex = cursor.getColumnIndex("dataInicio")
            val dataFimIndex = cursor.getColumnIndex("dataFim")
            val precoIndex = cursor.getColumnIndex("preco")
            val duracaoIndex = cursor.getColumnIndex("duracao")
            val edicaoIndex = cursor.getColumnIndex("edicao")
            val imagemIdIndex = cursor.getColumnIndex("imagemId")

            val id = cursor.getInt(idIndex)
            val nome = cursor.getString(nomeIndex)
            val local = cursor.getString(localIndex)
            val dataInicio = cursor.getString(dataInicioIndex)
            val dataFim = cursor.getString(dataFimIndex)
            val preco = cursor.getDouble(precoIndex)
            val duracao= cursor.getInt(duracaoIndex)
            val edicao = cursor.getInt(edicaoIndex)
            val imagemId = cursor.getInt(imagemIdIndex)

            curso = Curso(id, nome, local, dataInicio, dataFim, preco, duracao, edicao, imagemId)
        }
        db.close()
        return curso
    }

    fun selectAllCursoLista(): ArrayList<Curso> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM curso", null)
        val listaCurso: ArrayList<Curso> = ArrayList()

        if (cursor.count > 0) {
            cursor.moveToFirst()
            val idIndex = cursor.getColumnIndex("id")
            val nomeIndex = cursor.getColumnIndex("nome")
            val localIndex = cursor.getColumnIndex("local")
            val dataInicioIndex = cursor.getColumnIndex("dataInicio")
            val dataFimIndex = cursor.getColumnIndex("dataFim")
            val precoIndex = cursor.getColumnIndex("preco")
            val duracaoIndex = cursor.getColumnIndex("duracao")
            val edicaoIndex = cursor.getColumnIndex("edicao")
            val imagemIdIndex = cursor.getColumnIndex("imagemId")
            do {
                val id = cursor.getInt(idIndex)
                val nome = cursor.getString(nomeIndex)
                val local = cursor.getString(localIndex)
                val dataInicio = cursor.getString(dataInicioIndex)
                val dataFim = cursor.getString(dataFimIndex)
                val preco = cursor.getDouble(precoIndex)
                val duracao= cursor.getInt(duracaoIndex)
                val edicao = cursor.getInt(edicaoIndex)
                val imagemId = cursor.getInt(imagemIdIndex)
                listaCurso.add(Curso(id, nome, local, dataInicio, dataFim, preco, duracao, edicao, imagemId))
            } while(cursor.moveToNext())
        }
        db.close()
        return listaCurso
    }



}