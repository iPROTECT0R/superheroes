package com.example.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.superheroes.model.HeroesRepository
import com.example.superheroes.ui.theme.SuperheroesTheme

class MainActivity : ComponentActivity() {
    // When the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Enable edge-to-edge display
    enableEdgeToEdge()
    // Set content using Compose
    setContent {
        // Apply our superhero theme
        SuperheroesTheme {
            // Create a surface to hold our app content
            Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
            ) {
            // Load our superhero app
            SuperheroesApp()
        }
    }
}
}

    // Our superhero app composable
    @Composable
    fun SuperheroesApp() {
        // Create a scaffold for our app
        Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        // Display the top app bar
        TopAppBar()
            }
        ) {
            // Get heroes from repository
            val heroes = HeroesRepository.heroes
            // Display the list of heroes
            HeroesList(heroes = heroes, contentPadding = it)
        }
    }

    // Composable for the top app bar
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(modifier: Modifier = Modifier) {
        // Center-aligned top app bar
    CenterAlignedTopAppBar(
        title = {
        // Display app name
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
                )
            },
            modifier = modifier
        )
    }

    // Preview function for our app
    @Preview(showBackground = true)
    @Composable
    fun SuperHeroesPreview() {
    // Display app in preview mode
    SuperheroesTheme {
    SuperheroesApp()
    }
}
}
