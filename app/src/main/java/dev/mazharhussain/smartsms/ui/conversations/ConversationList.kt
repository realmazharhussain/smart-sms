package dev.mazharhussain.smartsms.ui.conversations

import android.content.Context
import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mazharhussain.smartsms.common.tickerFlow
import dev.mazharhussain.smartsms.data.Message
import dev.mazharhussain.smartsms.data.mockList
import dev.mazharhussain.smartsms.extensions.withAlpha
import dev.mazharhussain.smartsms.ui.Screen
import dev.mazharhussain.smartsms.ui.common.LocalBackStack
import dev.mazharhussain.smartsms.ui.common.push
import dev.mazharhussain.smartsms.ui.theme.AppTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun ConversationList(threads: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(threads) { message ->
            ConversationListItem(message)
        }
    }
}

@Composable
fun ConversationListItem(message: Message, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val backStack = LocalBackStack.current

    val num by remember { tickerFlow(duration = 1.minutes) }
        .collectAsStateWithLifecycle(initialValue = 0)

    val time = remember(num, message.date, context) {
        getTimeText(timeMillis = message.date, context = context)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .clip(CircleShape)
            .clickable { backStack.push(Screen.Thread(message.threadId)) }
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
            text = time,
            style = MaterialTheme.typography.bodySmall,
            color = LocalContentColor.current.withAlpha { it * 0.8f },
            letterSpacing = 0.1.sp,
            modifier = Modifier.align(Alignment.Top),
        )
    }
}

fun getTimeText(timeMillis: Long, context: Context): String {
    val zoneId = ZoneId.systemDefault()
    val nowMillis = System.currentTimeMillis()
    val now = LocalDateTime.ofInstant(Instant.ofEpochMilli(/* epochMilli = */ nowMillis), zoneId)
    val then = LocalDateTime.ofInstant(Instant.ofEpochMilli(/* epochMilli = */ timeMillis), zoneId)
    val lastYear = LocalDateTime.of(now.year, now.month, 1, 0, 0).minusMonths(11)
    val midnight = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 0, 0)
    val weekAgo = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 0, 0).minusDays(6)

    val diff = (nowMillis - timeMillis).milliseconds
    val flags = when {
        diff < 1.minutes -> return "Now"
        diff < 1.hours -> return "${diff.inWholeMinutes} min"
        then > midnight -> DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_NO_YEAR
        then > weekAgo -> DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_NO_YEAR
        then > lastYear -> DateUtils.FORMAT_NO_YEAR
        else -> 0
    } or DateUtils.FORMAT_ABBREV_ALL
    return DateUtils.formatDateTime(context, timeMillis, flags)
}

@Preview
@Composable
private fun ConversationListPreview() {
    AppTheme { ConversationList(Message.mockList) }
}

@Preview
@Composable
private fun TimeExamples() {
    AppTheme {
        val context = LocalContext.current
        val times = remember(context) {
            val now = Clock.System.now()
            listOf(30.seconds, 2.minutes, 65.minutes, 1.days, 330.days, 350.days).map {
                getTimeText(timeMillis = (now - it).toEpochMilliseconds(), context = context)
            }
        }

        Column {
            times.forEach { text -> Text(text) }
        }
    }
}
