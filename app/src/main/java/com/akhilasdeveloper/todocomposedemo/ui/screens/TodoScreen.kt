package com.akhilasdeveloper.todocomposedemo.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity
import com.akhilasdeveloper.todocomposedemo.ui.components.TodoInput
import com.akhilasdeveloper.todocomposedemo.ui.components.TodoItem
import com.akhilasdeveloper.todocomposedemo.ui.components.TodoTopBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = koinInject()
) {

    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            viewModel.viewEvent.collectLatest {
                when(it){
                    is TodoViewEvent.ShowMessage -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        launch {
            viewModel.performAction(TodoAction.FetchTodos)
        }
    }

    TodoScreenContent(
        modifier = modifier,
        viewState = viewState,
        onAction = viewModel::performAction
    )

    BackHandler(viewState.isSelection) {
        viewModel.performAction(TodoAction.ClearSelection)
    }

    if (viewState.showInputDialog) {
        Dialog(
            onDismissRequest = {
                viewModel.performAction(TodoAction.HideAddDialog)
            }
        ) {
            TodoInput(
                title = "Add Todo",
                onOk = {
                    viewModel.performAction(TodoAction.AddNewTodo(it))
                    viewModel.performAction(TodoAction.HideAddDialog)
                }, onCancel = {
                    viewModel.performAction(TodoAction.HideAddDialog)
                })
        }
    }

    viewState.editRequest?.let { entity ->
        Dialog(
            onDismissRequest = {
                viewModel.performAction(TodoAction.HideEditDialog)
            }
        ) {
            TodoInput(
                title = "Edit Todo",
                value = entity.text,
                onOk = {
                    viewModel.performAction(TodoAction.UpdateTodo(it, entity))
                    viewModel.performAction(TodoAction.HideEditDialog)
                }, onCancel = {
                    viewModel.performAction(TodoAction.HideEditDialog)
                })
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreenContent(
    modifier: Modifier = Modifier,
    viewState: TodoViewState = TodoViewState(),
    onAction: (TodoAction) -> Unit = {}
) {

    Scaffold(
        modifier = modifier, topBar = {
            TodoTopBar(
                isSelection = viewState.isSelection,
                onAdd = {
                    onAction(TodoAction.ShowAddDialog)
                }, onCancel = {
                    onAction(TodoAction.ClearSelection)
                }, onDelete = {
                    onAction(TodoAction.DeleteSelection)
                }, onMark = {
                    onAction(TodoAction.MarkSelectionCompleted)
                }, onUnMark = {
                    onAction(TodoAction.MarkSelectionIncompleted)
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
                    items(viewState.todos) { entity ->

                        val isSelected by remember(viewState.selectedTodos, entity) {
                            derivedStateOf {
                                viewState.selectedTodos.contains(entity)
                            }
                        }

                        TodoItem(
                            modifier = Modifier.combinedClickable(
                                onClick = {
                                    onAction(TodoAction.PerformClick(entity))
                                },
                                onLongClick = {
                                    onAction(TodoAction.StartSelection(entity))
                                }
                            ), isSelected = isSelected,
                            isSelectionMode = viewState.isSelection,
                            todo = entity,
                            onDelete = {
                                onAction(TodoAction.DeleteTodo(entity))
                            },
                            onCheckChanged = {
                                onAction(TodoAction.PerformClick(entity))
                            },
                            onEdit = {
                                onAction(TodoAction.ShowEditDialog(entity))
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
    name = "Error"
)
@Composable
fun TodoScreenErrorPreview() {
    TodoScreenContent(modifier = Modifier.fillMaxSize(), )
}

@Preview(
    showBackground = true, device = Devices.PHONE,
    name = "Data"
)
@Composable
fun TodoScreenDataPreview() {
    val todos = remember {
        TodoViewState(
            todos = listOf(
                TodoEntity(id = 1L, text = "Demo todo 1"),
                TodoEntity(id = 2L, text = "Demo todo 2"),
                TodoEntity(id = 3L, text = "Demo todo 3"),
                TodoEntity(id = 4L, text = "Demo todo 4"),
                TodoEntity(id = 5L, text = "Demo todo 5"),
            )
        )
    }
    TodoScreenContent(modifier = Modifier.fillMaxSize(), todos)
}