package top.inept.suiji.other

import org.junit.jupiter.api.Test
import java.util.UUID

class UUIDTest {
    @Test
    fun `test uuid length`() {
        val uuid = UUID.randomUUID()

        println(uuid.toString())
        println(uuid.toString().length)
    }
}