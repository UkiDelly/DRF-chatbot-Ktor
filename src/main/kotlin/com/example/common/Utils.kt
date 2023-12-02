import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toInstant(): Instant {
	return this.atZone(ZoneId.systemDefault()).toInstant()
}


fun Instant.toLocalDateTime(): LocalDateTime {
	return LocalDateTime.ofInstant(this, ZoneId.systemDefault())
}