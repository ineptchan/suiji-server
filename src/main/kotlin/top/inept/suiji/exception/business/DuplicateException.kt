package top.inept.suiji.exception.business

import top.inept.suiji.exception.BusinessException

class DuplicateException(
    code: String = "error.duplicate",
    message: String = code
) : BusinessException(code, message)