package com.example.expensestracker

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.expensestracker.ui.theme.ExpensesTrackerTheme

class ExpenseView : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModels<MainViewModel>()
        super.onCreate(savedInstanceState)

        tdb = TestDBOpenHelper(this, "ExpenseTracker.db", null, 1)
        sdb = tdb.writableDatabase

        setContent {
            ExpensesTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    Column {
                        Logo_expense(title = "My Expense Tracker Main Activity 2")
                        Row {
                            Text(text = "Income ")
                            TextField(
                                value = Income_value.value.toString(),
                                onValueChange = {
                                    Income_value.value = it.toInt()
                                    tdb.updateData(
                                        sdb,
                                        (this@ExpenseView).intent.getIntExtra("SID", 0),
                                        it.toInt()
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )

                        }
                        Row {
                            Text(text = "Deficit/Surplus ${deficit.value}")
                        }
                        ExpenseAddSheetButton(viewModel = viewModel) {
                            listExpenseSheet.add(it)
                            tdb.addExpenseSheet(sdb, it, sid.value)
                        }
                        ExpenseverticalList(listExpenseSheet)
                    }
                }

            }
        }

    }

    override fun onResume() {
        super.onResume()
        current_data.value = tdb.retrieveExpense(sdb)
        val sheetList = current_data.value.split("\n")
        for (i in 0 until sheetList.size - 1) {
            listExpenseSheet.add(sheetList[i])
        }
    }

    private var current_data = mutableStateOf("No data in database")

    private lateinit var tdb: TestDBOpenHelper

    private lateinit var sdb: SQLiteDatabase

    private var sid = mutableStateOf(0)
    private var Income_value = mutableStateOf(0)
    private var deficit = mutableStateOf(0)
    private var listExpenseSheet = mutableStateListOf<String>()
}



