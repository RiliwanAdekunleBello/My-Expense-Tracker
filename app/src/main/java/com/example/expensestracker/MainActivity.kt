package com.example.expensestracker

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.expensestracker.ui.theme.ExpensesTrackerTheme

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tdb= TestDBOpenHelper(this, "ExpenseTracker.db",null, 1)
        sdb= tdb.writableDatabase

        setContent {

            ExpensesTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )

                {
                    Column {
                        Logo_expense(title = "My Expense Tracker Main Activity 1")
                        AddSheetButton(viewModel= viewModel) {
                            listofMonthSheet.add(it)
                            tdb.addMonthSheet(sdb, it)
                        }
                        verticalList(listofMonthSheet) {
                            startActivity(intentCreation(it))
                        }

                    }
                }


            }
        }

        }
    override fun onResume(){
        super.onResume()

        current_data.value= tdb.retrieveMonths(sdb)
        listofMonthSheet.clear()
        val sheetList = current_data.value.split("\n")
        for (i in 0 until sheetList.size - 1) {
            listofMonthSheet.add(sheetList[i])
        }

    }

    private fun intentCreation(sid: Int) : Intent {
        val intent: Intent = Intent(this, ExpenseView::class.java)

        intent.putExtra("SID", sid)

        return intent
    }

    private var listofMonthSheet = mutableStateListOf<String>()
}



private var current_data= mutableStateOf("No data in database")

private lateinit var tdb: TestDBOpenHelper

private lateinit var sdb: SQLiteDatabase



