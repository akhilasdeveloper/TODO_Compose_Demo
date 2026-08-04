package com.akhilasdeveloper.todocomposedemo.ui.features.todo

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import com.akhilasdeveloper.todocomposedemo.ui.features.todo.components.TodoInput
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun TodoRoute(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = koinInject()
) {

    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            viewModel.viewEvent.collectLatest {
                when(it){
                    is TodoUiEvent.ShowMessage -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        launch {
            viewModel.performAction(TodoUiAction.FetchTodos)
        }
    }

    TodoScreen(
        modifier = modifier,
        viewState = viewState,
        onAction = viewModel::performAction
    )

    BackHandler(viewState.isSelection) {
        viewModel.performAction(TodoUiAction.ClearSelection)
    }

    if (viewState.showInputDialog) {
        Dialog(
            onDismissRequest = {
                viewModel.performAction(TodoUiAction.HideAddDialog)
            }
        ) {
            TodoInput(
                title = "Add Todo",
                onOk = {
                    viewModel.performAction(TodoUiAction.AddNewTodo(it))
                    viewModel.performAction(TodoUiAction.HideAddDialog)
                }, onCancel = {
                    viewModel.performAction(TodoUiAction.HideAddDialog)
                })
        }
    }

    viewState.editRequest?.let { entity ->
        Dialog(
            onDismissRequest = {
                viewModel.performAction(TodoUiAction.HideEditDialog)
            }
        ) {
            TodoInput(
                title = "Edit Todo",
                value = entity.text,
                onOk = {
                    viewModel.performAction(TodoUiAction.UpdateTodo(it, entity))
                    viewModel.performAction(TodoUiAction.HideEditDialog)
                }, onCancel = {
                    viewModel.performAction(TodoUiAction.HideEditDialog)
                })
        }
    }

}