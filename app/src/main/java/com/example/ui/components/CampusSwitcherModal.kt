package com.example.ui.components

import androidx.compose.runtime.Composable
import com.example.data.model.Campus

@Composable
fun CampusSwitcherModal(
    show: Boolean,
    currentCampus: Campus?,
    campuses: List<Campus>,
    onSelect: (Campus) -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        CampusSelectorSheet(
            currentCampus = currentCampus,
            campuses = campuses,
            onCampusSelected = onSelect,
            onDismiss = onDismiss
        )
    }
}
