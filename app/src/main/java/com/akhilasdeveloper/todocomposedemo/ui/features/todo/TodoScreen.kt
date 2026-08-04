package com.akhilasdeveloper.todocomposedemo.ui.features.todo

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akhilasdeveloper.todocomposedemo.data.db.entities.TodoEntity
import com.akhilasdeveloper.todocomposedemo.ui.features.todo.components.TodoItem
import com.akhilasdeveloper.todocomposedemo.ui.features.todo.components.TodoTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewState: TodoUiState = TodoUiState(),
    onAction: (TodoUiAction) -> Unit = {}
) {

    Scaffold(
        modifier = modifier, topBar = {
            TodoTopBar(
                isSelection = viewState.isSelection,
                onAdd = {
                    onAction(TodoUiAction.ShowAddDialog)
                }, onCancel = {
                    onAction(TodoUiAction.ClearSelection)
                }, onDelete = {
                    onAction(TodoUiAction.DeleteSelection)
                }, onMark = {
                    onAction(TodoUiAction.MarkSelectionCompleted)
                }, onUnMark = {
                    onAction(TodoUiAction.MarkSelectionIncompleted)
                })
        }) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            if (viewState.todos.isEmpty()) {

                Text("Empty TODOs")

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewState.todos, key = {it.id}) { entity ->

                        val isSelected = entity in viewState.selectedTodos

                        TodoItem(
                            modifier = Modifier.combinedClickable(
                                onClick = {
                                    onAction(TodoUiAction.PerformClick(entity))
                                },
                                onLongClick = {
                                    onAction(TodoUiAction.StartSelection(entity))
                                }
                            ), isSelected = isSelected,
                            isSelectionMode = viewState.isSelection,
                            todo = entity,
                            onDelete = {
                                onAction(TodoUiAction.DeleteTodo(entity))
                            },
                            onCheckChanged = {
                                onAction(TodoUiAction.PerformClick(entity))
                            },
                            onEdit = {
                                onAction(TodoUiAction.ShowEditDialog(entity))
                            })
                    }
                }

            }

            if (viewState.showLoader) {
                CircularProgressIndicator()
            }

        }
    }

}


@Preview(
    showBackground = true, device = Devices.PHONE,
    name = "Empty"
)
@Composable
fun TodoScreenErrorPreview() {
    TodoScreen(modifier = Modifier.fillMaxSize(), )
}

@Preview(
    showBackground = true, device = Devices.PHONE,
    name = "Data"
)
@Composable
fun TodoScreenDataPreview() {
    val todos = remember {
        TodoUiState(
            todos = listOf(
                TodoEntity(id = 1L, text = "Demo todo 1"),
                TodoEntity(id = 2L, text = "Demo todo 2", isDone = true),
                TodoEntity(id = 3L, text = "Demo todo 3", isDone = true),
                TodoEntity(id = 4L, text = "Demo todo 4"),
                TodoEntity(id = 5L, text = "Demo todo 5"),
            )
        )
    }
    TodoScreen(modifier = Modifier.fillMaxSize(), todos)
}

@Preview(
    showBackground = true, device = Devices.PHONE,
    name = "Selection"
)
@Composable
fun TodoScreenDataSelectionPreview() {
    val todos = remember {
        TodoUiState(
            todos = listOf(
                TodoEntity(id = 1L, text = "Demo todo 1"),
                TodoEntity(id = 2L, text = "Demo todo 2", isDone = true),
                TodoEntity(id = 3L, text = "Demo todo 3", isDone = true),
                TodoEntity(id = 4L, text = "Demo todo 4"),
                TodoEntity(id = 5L, text = "Demo todo 5"),
            ),
            isSelection = true,
            selectedTodos = setOf(
                TodoEntity(id = 1L, text = "Demo todo 1"),
                TodoEntity(id = 2L, text = "Demo todo 2", isDone = true),
            )
        )
    }
    TodoScreen(modifier = Modifier.fillMaxSize(), todos)
}