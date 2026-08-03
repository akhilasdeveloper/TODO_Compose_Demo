package com.akhilasdeveloper.todocomposedemo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akhilasdeveloper.todocomposedemo.R
import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity

@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    todo: TodoEntity,
    isSelectionMode: Boolean = false,
    isSelected: Boolean = false,
    onCheckChanged: (Boolean) -> Unit,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .height(56.dp)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (!isSelected && !isSelectionMode) {
            Checkbox(checked = todo.isDone, onCheckedChange = onCheckChanged)
        }

        Text(
            modifier = Modifier.weight(1f),
            text = todo.text,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            textDecoration = if (todo.isDone) TextDecoration.LineThrough else null
        )

        if (!isSelected && !isSelectionMode) {
            IconButton(onClick = onEdit) {
                Icon(painterResource(R.drawable.edit_24px), contentDescription = "Delete button")
            }
            IconButton(onClick = onDelete) {
                Icon(painterResource(R.drawable.delete_24px), contentDescription = "Delete button")
            }
        }

    }
}

@Preview(
    showBackground = true, device = Devices.PHONE
)
@Composable
fun TodoItemPreview() {
    val entity = TodoEntity(
        id = 1L, text = "Sample todo", isDone = false
    )
    TodoItem(modifier = Modifier.fillMaxWidth(), entity, onDelete = {

    }, onCheckChanged = {

    })
}

@Preview(
    showBackground = true, device = Devices.PHONE
)
@Composable
fun TodoItemSelectionModePreview() {
    val entity = TodoEntity(
        id = 1L, text = "Sample todo", isDone = false
    )
    TodoItem(
        modifier = Modifier.fillMaxWidth(),
        todo = entity,
        isSelectionMode = true,
        onDelete = {

        }, onCheckChanged = {

        })
}


@Preview(
    showBackground = true, device = Devices.PHONE
)
@Composable
fun TodoItemSelectedPreview() {
    val entity = TodoEntity(
        id = 1L, text = "Sample todo", isDone = false
    )
    TodoItem(modifier = Modifier.fillMaxWidth(), isSelected = true, todo = entity, onDelete = {

    }, onCheckChanged = {

    })
}


@Preview(
    showBackground = true, device = Devices.PHONE
)
@Composable
fun TodoItemDonePreview() {
    val entity = TodoEntity(
        id = 1L, text = "Sample todo", isDone = true
    )
    TodoItem(modifier = Modifier.fillMaxWidth(), entity, onDelete = {

    }, onCheckChanged = {

    })
}