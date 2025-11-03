package top.inept.suiji

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SuijiServerApplication

fun main(args: Array<String>) {
    runApplication<SuijiServerApplication>(*args)
}
