package dev.mazharhussain.smartsms.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mazharhussain.smartsms.ui.extensions.withAlpha
import dev.mazharhussain.smartsms.ui.theme.AppTheme

@Composable
fun ConversationList(threads: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(threads) { message ->
            ConversationListItem(message)
        }
    }
}

@Composable
fun ConversationListItem(message: Message, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .clip(CircleShape)
            .clickable {}
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Green.copy(alpha = 0.7f).compositeOver(Color.Black))
                .size(52.dp)
                .padding(8.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .background(color = Color.White, shape = CircleShape)
                    .size(14.dp)
            )

            Spacer(
                modifier = Modifier
                    .scale(scaleX = 1.5f, scaleY = 1f)
                    .background(color = Color.White, shape = CircleShape)
                    .size(16.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = message.address,
                style = MaterialTheme.typography.bodyLarge,
                letterSpacing = 0.1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = message.body,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current.withAlpha { it * 0.8f },
                letterSpacing = 0.1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Text(
            text = "2:48 PM",
            style = MaterialTheme.typography.bodySmall,
            color = LocalContentColor.current.withAlpha { it * 0.8f },
            letterSpacing = 0.1.sp,
            modifier = Modifier.align(Alignment.Top),
        )
    }
}


@Preview
@Composable
private fun ConversationListPreview() {
    AppTheme { ConversationList(Message.mockList) }
}
