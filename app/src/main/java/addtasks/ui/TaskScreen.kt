package addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun TaskScreen(taskViewModel: TaskViewModel){
    Box {
        FabDialog(Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun FabDialog(modifier: Modifier){
    FloatingActionButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(16.dp)) {
        Icon(Icons.Filled.Add,contentDescription = null )
    }
}

@Composable
fun TasksScreen(tasksViewModel: TaskViewModel) {
    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)
    val myTaskText: String by tasksViewModel.myTaskText.observeAsState("")

    Box(modifier = Modifier.fillMaxSize()) {
        AddTasksDialog(
            show = showDialog,
            myTaskText = myTaskText,
            onDismiss = { tasksViewModel.onDialogClose() },
            onTaskAdded = { tasksViewModel.onTaskCreated() },
            onTaskTextChanged = { tasksViewModel.onTaskTextChanged(it) }
        )
        FabDialog(
            Modifier.align(Alignment.BottomEnd),
            onNewTask = { tasksViewModel.onShowDialogClick() })
        TasksList(tasksViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun AddTasksDialog(
    show: Boolean,
    myTaskText: String,
    onDismiss: () -> Unit,
    onTaskAdded: () -> Unit,
    onTaskTextChanged: (String) -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myTaskText,
                    onValueChange = { onTaskTextChanged(it) },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        onTaskAdded()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }
}


@Composable
fun TasksList(tasksViewModel: TaskViewModel) {
    val myTasks: List<TaskModel> = tasksViewModel.tasks

    LazyColumn {
        //El parámetro opcional key ayuda a optimizar el LazyColumn
        //Al indicarle que la clave es el id va a ser capaz de identificar cada tarea sin problemas
        items(myTasks, key = { it.id }) { task ->
            ItemTask(
                task,
                onTaskRemove = { tasksViewModel.onItemRemove(it) },
                onTaskCheckChanged = { tasksViewModel.onCheckBoxSelected(it) }
            )
        }
    }
}