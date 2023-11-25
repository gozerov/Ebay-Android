package ru.gozerov.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.Review
import ru.gozerov.presentation.R


@Composable
fun ReviewCard(review: Review) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                DefaultText(text = review.userEmail)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    repeat(5) {
                        if (it < review.rating.toInt())
                            StarIconFull(modifier = Modifier
                                .padding(start = if (it == 0) 2.dp else 0.dp, end = 2.dp))
                        else
                            StarIconOutlined(modifier = Modifier
                                .padding(start = if (it == 0) 2.dp else 0.dp, end = 2.dp))
                    }
                }
            }
            review.addedAgo?.let {
                DefaultText(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(top = 4.dp, end = 24.dp),
                    text = it
                )
            }

        }
        DefaultText(
            modifier = Modifier.padding(
                start = 84.dp, end = 24.dp, top = 8.dp, bottom = 12.dp
            ), text = "Some text"
        )
    }

}