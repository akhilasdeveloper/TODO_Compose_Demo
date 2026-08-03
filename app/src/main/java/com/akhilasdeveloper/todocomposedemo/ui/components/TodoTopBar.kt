package com.akhilasdeveloper.todocomposedemo.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.akhilasdeveloper.todocomposedemo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopBar(
    modifier: Modifier = Modifier,
    isSelection: Boolean = false,
    onAdd: () -> Unit,
    onDelete: () -> Unit,
    onMark: () -> Unit,
    onUnMark: () -> Unit,
    onCancel: () -> Unit,
) {
    TopAppBar(
        modifier = modifier, title = {
            Text(if (isSelection) "Select" else stringResource(R.string.app_name))
        },
        navigationIcon = if (isSelection) {{
            IconButton(onClick = onCancel) {
                Icon(painterResource(R.drawable.close_24px), contentDescription = "Delete button")
            }
        }} else {{}},
        actions = {
            if (isSelection) {
                IconButton(onClick = onMark) {
                    Icon(painterResource(R.drawable.check_24px), contentDescription = "Mark button")
                }
                IconButton(onClick = onUnMark) {
                    Icon(painterResource(R.drawable.remove_24px), contentDescription = "UnMark button")
                }
                IconButton(onClick = onDelete) {
                    Icon(painterResource(R.drawable.delete_24px), contentDescription = "Delete button")
                }
            } else {
                IconButton(onClick = onAdd) {
                    Icon(painterResource(R.drawable.add_24px), contentDescription = "Add button")
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    device = Devices.PHONE
)
@Composable
fun TodoTopBarPreview() {
    TodoTopBar(onAdd = {}, onDelete = {}, onMark = {}, onUnMark = {}, onCancel = {},)
}