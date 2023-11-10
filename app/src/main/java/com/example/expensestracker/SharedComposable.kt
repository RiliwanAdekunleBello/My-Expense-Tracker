package com.example.expensestracker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun Logo_expense(title:String,modifier: Modifier = Modifier){
    Box(modifier = Modifier
        .background(Color.Magenta)
        .fillMaxWidth()
        .height(50.dp)
        .offset(10.dp, 5.dp), Alignment.Center) {
        Text(title, style = TextStyle(Color.White, fontSize = 15.sp,fontWeight =null, fontFamily = null) )
    }

}
@Composable
fun myiconbutton(modifier: Modifier=Modifier){
    Column(modifier=Modifier.padding(10.dp,10.dp),verticalArrangement = Arrangement.Center) {
        Button(onClick = { /*TODO*/ }) {

            Text(text = "ADD NEW SHEET")
            Icon(imageVector= Icons.Filled.Add, contentDescription =null )
            
        }
        }

    }

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun verticalList(items_list: MutableList<String>, onConfirm: (Int) -> Unit){
    LazyColumn{
        for (i in 0 until items_list.size){
            item{
                androidx.compose.material3.ListItem(headlineText = { Text("Month/Year $i") },
                    supportingText = { Text(text = items_list[i])},
                    modifier = Modifier.combinedClickable (
                        onClick = {
                            onConfirm(0)
                        }
                    )
                )
                Row {
                    Button(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Edit, contentDescription =null )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Delete, contentDescription =null )

                    }
                }
            }
        }
    }
}

@Composable
fun AddSheetButton(viewModel: MainViewModel, result: (String) -> Unit){
    Box(
        modifier = Modifier.padding(20.dp,10.dp)
    ) {
        Button({
            viewModel.onAddSheetClick()
        }, Modifier.padding(10.dp), shape = CircleShape  ) {
            Text(text = "ADD NEW SHEET", style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center, fontSize = 20.sp)
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }
    if(viewModel.isDialogShown) {
        monthlycustomdialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                result(it)
                viewModel.onDismissDialog()
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun monthlycustomdialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var Month by remember {
        mutableStateOf(" ")
    }
    var Year by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxSize(0.95f)
                .border(1.dp, color = Color.Magenta, shape = RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Month and Year",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextField(value = Month, onValueChange = {
                        Month = it
                    },modifier= Modifier.width(155.dp))
                    TextField(value = Year, onValueChange = {
                        Year = it
                    }, modifier = Modifier.width(155.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )

                    }
                    Button(
                        onClick = {
                            onConfirm("$Month / $Year")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Update",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )

                    }

                }

            }



        }
    }

}


@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun ExpenseverticalList(items_list: MutableList<String>){
    LazyColumn{
        for (i in 0 until items_list.size){
            item{
                androidx.compose.material3.ListItem(headlineText = { Text("Expense Sheet $i") },
                    supportingText = { Text(text = items_list[i])}
                )
                Row {
                    Button(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Edit, contentDescription =null )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Delete, contentDescription =null )

                    }
                }
            }
        }
    }
}
@Composable
fun ExpenseAddSheetButton(viewModel: MainViewModel, result: (String) -> Unit){
    Box(
        modifier = Modifier.padding(20.dp,10.dp)
    ) {
        Button({
            viewModel.onAddSheetClick()
        }, Modifier.padding(10.dp), shape = CircleShape  ) {
            Text(text = "ADD NEW SHEET", style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center, fontSize = 20.sp)
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }
    if(viewModel.isDialogShown) {
        Expensecustomdialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                result(it)
                viewModel.onDismissDialog()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Expensecustomdialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var Expense_name by remember {
        mutableStateOf(" ")
    }
    var Expense_cost by remember {
        mutableStateOf("")
    }
    var Expense_day by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxSize(0.95f)
                .border(1.dp, color = Color.Magenta, shape = RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Expense Description",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Expense Name")
                    TextField(value = Expense_name, onValueChange = {
                        Expense_name = it
                    })
                    Text(text = "Expense Cost")
                    TextField(value = Expense_cost, onValueChange = {
                        Expense_cost = it
                    })
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(text = "Expense Day")
                    TextField(value = Expense_day, onValueChange = {Expense_day = it})
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )

                    }
                    Button(
                        onClick = {
                            onConfirm("$Expense_name / $Expense_cost / $Expense_day")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Update",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )

                    }

                }

            }



        }
    }

}