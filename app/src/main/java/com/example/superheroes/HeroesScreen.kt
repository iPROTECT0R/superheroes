package com.example.superheroes

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroes.model.Hero
import com.example.superheroes.model.HeroesRepository
import com.example.superheroes.ui.theme.SuperheroesTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroesList(
    heroes: List        <Hero>,
    decoration: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // Initiating a state to manage animated transitions
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Let's get the show started immediately.
            targetState = true
        }
    }

    // Animating visibility for the whole shebang
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = decoration
    ) {
        // For easy scrolling, we've got a Lazy Column
        LazyColumn(contentPadding = contentPadding) {
        itemsIndexed(heroes) { index, hero ->
        // Each hero item gets its own spotlight
        DisplayHero(
        hero = hero,
        decoration = Modifier
            .padding(horizontal = 20.dp, vertical = 12.dp)
            // We'll animate the entrance of each hero
            .animateEnterExit(
                enter = slideInVertically(
                animationSpec = spring(
                    stiffness = StiffnessMediumLow,
                    dampingRatio = DampingRatioHighBouncy
                    ),
                    initialOffsetY = { it * (index + 1) } // Stagger the entrance
                    )
                )
            )
        }
    }
}
}

// Function to showcase a single hero
@Composable
fun DisplayHero(
    hero: Hero,
    decoration: Modifier = Modifier
) {
    // Wrapping the hero's story in a card
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = decoration,
    ) {
        Row(
        modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .sizeIn(minHeight = 62.dp)
        ) {
            // Hero's name and description take center stage
        Column(modifier = Modifier.weight(1f)) {
        Text(
            text = stringResource(hero.nameRes),
            style = MaterialTheme.typography.displaySmall
                )
            Text(
                text = stringResource(hero.descriptionRes),
                style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.width(12.dp))
            // The hero's image has its own frame
            Box(
                modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(8.dp))

            ) {
                // The image tells the hero's tale
                Image(
                painter = painterResource(hero.imageRes),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

// Preview function for a single hero, available in both light and dark themes
@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DisplayHeroPreview() {
    // Sample hero tale
    val hero = Hero(
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1
    )
    // Displaying the hero's tale within our Superheroes Theme
    SuperheroesTheme {
        DisplayHero(hero = hero)
    }
}

// Preview function for the complete lineup of heroes
@Preview("Heroes Lineup")
@Composable
fun DisplayHeroesPreview() {
    // Presenting the lineup of heroes within our Superheroes Theme
SuperheroesTheme(darkTheme = false) {
Surface (
    color = MaterialTheme.colorScheme.background
    ) {
    HeroesList(heroes = HeroesRepository.heroes)
    }
}
}


