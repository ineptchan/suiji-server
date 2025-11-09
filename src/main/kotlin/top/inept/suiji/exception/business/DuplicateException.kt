package top.inept.suiji.exception.business

import top.inept.suiji.exception.BusinessException

class DuplicateException(
    code: String = "message.common.unknown_field_duplicate",
    message: String = code
) : BusinessException(code, message)