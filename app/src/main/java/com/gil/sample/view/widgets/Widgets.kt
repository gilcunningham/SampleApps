package com.gil.sample.view.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gil.sample.viewmodel.FirstFragmentViewModel

@Composable
fun FirstScreen(viewModel: FirstFragmentViewModel) {
    Surface(
        Modifier.fillMaxSize(),
        color = Color(0xFFF0F0F0)
    ) {
        Box {
            Column(
                Modifier.align(
                    Alignment.Center
                )
            ) {
                Button(
                    onClick = { viewModel.onClick1() }
                ) {
                    Text(text = "Fetch Users - onClick1")
                }
                Button(
                    onClick = { viewModel.onClick2() }
                ) {
                    Text(text = "Fetch Users - onClick2")
                }
                Button(
                    onClick = { viewModel.onClick3() }

                ) {
                    Text(text = "Fetch Users - onClick3")
                }
            }
        }
    }
}
