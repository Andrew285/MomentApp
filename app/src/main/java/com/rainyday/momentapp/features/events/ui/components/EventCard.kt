package com.rainyday.momentapp.features.events.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rainyday.momentapp.core.ui.components.TextIcon
import com.rainyday.momentapp.core.ui.theme.MomentAppTheme
import com.rainyday.momentapp.core.ui.utils.toFormattedDate
import com.rainyday.momentapp.features.events.domain.models.Event

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    highlight: Boolean = false,
    dimmed: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (dimmed) 0.6f else 1f)
        ,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(if (highlight) 8.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlight) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {

            // Title
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            // Description
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Row(
               modifier = Modifier
                   .fillMaxWidth()
            ) {
                // Date
                TextIcon(
                    text = event.date.toFormattedDate(),
                    icon = Icons.Default.DateRange
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewEventCard() {
    MomentAppTheme {
        EventCard(
            event = Event(
                id = "0",
                title = "Романтична вечеря",
                description = "Чудовий вечір у затишному ресторані з італійською кухнею. Замовили пасту карбонара та тірамісу. Атмосфера була нейовірна",
                date = 1773532800
            ),
            onClick = { },
            highlight = true,
            dimmed = true,
        )
    }
}