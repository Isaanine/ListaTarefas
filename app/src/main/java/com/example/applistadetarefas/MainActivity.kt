package com.example.applistadetarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TaskScreen()
            }
        }
    }

    data class Task(val id: Int, val title: String)

    @Composable
    fun TaskList(tasks: List<Task>, onDelete: (Int) -> Unit) {

        LazyColumn {
            items(tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        text = task.title,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { onDelete(task.id) },
                        modifier = Modifier.testTag("delete_button_${task.id}")
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun TaskScreen() {
        val tasks = remember { mutableStateListOf<Task>() }
        val newTaskTitle = remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = newTaskTitle.value,
                onValueChange = { newTaskTitle.value = it },
                label = { Text("Título da Tarefa") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val taskId = tasks.size + 1
                    tasks.add(Task(taskId, newTaskTitle.value))
                    newTaskTitle.value = ""
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Adicionar Tarefa")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TaskList(
                tasks = tasks,
                onDelete = { taskId -> tasks.removeAll { it.id == taskId } }
            )
        }

    }
}

