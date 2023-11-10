package com.example.expensestracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class TestDBOpenHelper(context: Context, name:String,factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version)

{
    override fun onCreate(p0: SQLiteDatabase) {
        p0?.execSQL(CREATE_TABLE)
        p0?.execSQL(CREATE_TABLE_Expense)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db?.execSQL(DROP_TABLE_Expense)
        db?.execSQL(DROP_TABLE)
        db?.execSQL(CREATE_TABLE)
        db?.execSQL(CREATE_TABLE_Expense)
    }
    private val CREATE_TABLE: String= "create table MonthSheet(" +
            "MID integer primary key autoincrement,"+
            "Month," +
            "Year," +
            "Income," +
            "Balance"+")"
    private val CREATE_TABLE_Expense : String= "create table ExpenseSheet("+
            "ID integer primary key autoincrement," +
            "Expense_name,"+
            "Expense_cost,"+
            "Expense_day,"+
            "MID"+")"
    private val DROP_TABLE : String= "drop table MonthSheet"
    private val DROP_TABLE_Expense: String= "drop table ExpenseSheet"

    fun addMonthSheet(sdb: SQLiteDatabase, info: String, income: Int=0) {
        val listInfo = info.split('/')
        val row1: ContentValues= ContentValues().apply{
            put("Month", listInfo[0])
            put("Year", listInfo[1])
            put("Income", income)
            put("Balance",0)
        }
        sdb.insert("MonthSheet", null, row1)

    }

    fun retrieveMonths(sdb: SQLiteDatabase): String {
        val table_name: String = "MonthSheet"

        val columns: Array<String> = arrayOf("MID", "Month", "Year", "Income", "Balance")
        val where: String? = null
        val where_args: Array<String>? = null
        val group_by: String? = null
        val having: String? = null
        val order_by: String? = null

        var c: Cursor = sdb.query(table_name, columns, where, where_args, group_by, having, order_by)
        var sb: StringBuilder = StringBuilder()
        c.moveToFirst()
        for (i in 0 until c.count) {
            sb.append(c.getInt(0).toString())
            sb.append(" ")
            sb.append(c.getString(1).toString())
            sb.append(" ")
            sb.append(c.getString(2).toString())
            sb.append(" ")
            sb.append(c.getString(3).toString())
            sb.append(" ")
            sb.append(c.getString(4).toString())
            sb.append("\n")
            c.moveToNext()
        }
        return sb.toString()
    }
    fun addExpenseSheet(sdb: SQLiteDatabase, info: String, sid: Int) {
        val listInfo = info.split('/')
        val row1: ContentValues= ContentValues().apply{
            put("Expense_name", listInfo[0])
            put("Expense_cost", listInfo[1])
            put("Expense_day", listInfo[2])
            put("MID",sid)
        }
        sdb.insert("ExpenseSheet", null, row1)

    }

    fun retrieveExpense(sdb: SQLiteDatabase): String {
        val table_name: String = "ExpenseSheet"

        val columns: Array<String> = arrayOf("ID", "Item_Name", "Cost", "Day", "MID")
        val where: String? = null
        val where_args: Array<String>? = null
        val group_by: String? = null
        val having: String? = null
        val order_by: String? = null

        var c: Cursor = sdb.query(table_name, columns, where, where_args, group_by, having, order_by)
        var sb: StringBuilder = StringBuilder()
        c.moveToFirst()
        for (i in 0 until c.count) {
            sb.append(c.getInt(0).toString())
            sb.append(" ")
            sb.append(c.getString(1).toString())
            sb.append(" ")
            sb.append(c.getString(2).toString())
            sb.append(" ")
            sb.append(c.getString(3).toString())
            sb.append(" ")
            sb.append(c.getString(4).toString())
            sb.append("\n")
            c.moveToNext()
        }
        return sb.toString()
    }
    fun updateData(sdb: SQLiteDatabase, id: Int, income: Int=0){
        val row: ContentValues= ContentValues().apply {
            put("Income", income)
        }
        var table: String = "MonthSheet"
        var where: String= "MID = ?"
        var where_args: Array<String> = arrayOf(id.toString())
        sdb.update(table,row,where,where_args)
    }

}
