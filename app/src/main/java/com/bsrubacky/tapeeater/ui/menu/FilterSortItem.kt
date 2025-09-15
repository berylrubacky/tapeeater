package com.bsrubacky.tapeeater.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsrubacky.tapeeater.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSortItem(image: Painter, text: String, active: Boolean = false, onClick: () -> Unit) {
    DropdownMenuItem(
        text = { Text(text, textAlign = TextAlign.Center) },
        leadingIcon = { Icon(image, contentDescription = "", modifier = Modifier.width(30.dp)) },
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

@Preview
@Composable
fun PreviewFilterItem() {
    FilterSortItem(painterResource(R.drawable.vinyl), stringResource(R.string.vinyl)) {}
}