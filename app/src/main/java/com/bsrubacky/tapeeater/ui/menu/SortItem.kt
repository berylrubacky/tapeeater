package com.bsrubacky.tapeeater.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortItem(
    label: Painter,
    labelContent: String,
    direction: Painter,
    directionContent: String,
    active: Boolean = false,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Icon(
                direction,
                contentDescription = directionContent,
                modifier = Modifier.width(30.dp)
            )
        },
        leadingIcon = {
            Icon(
                label,
                contentDescription = labelContent,
                modifier = Modifier.width(30.dp)
            )
        },
        onClick = onClick,
        colors = if (active) {
            MenuDefaults.itemColors(
                textColor = MaterialTheme.colorScheme.onPrimary,
                leadingIconColor = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            MenuDefaults.itemColors()
        },
        modifier = if (active) {
            Modifier.background(MaterialTheme.colorScheme.primary)
        } else {
            Modifier
        }
    )
}
