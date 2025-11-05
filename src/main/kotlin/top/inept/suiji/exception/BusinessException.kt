package top.inept.suiji.exception

open class BusinessException(
    val code: String,
    override val message: String = code
) : RuntimeException(message)
