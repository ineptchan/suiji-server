package top.inept.suiji.exception.business

import top.inept.suiji.exception.BusinessException

class EntityNotFoundException(
    code: String = "error.entity_not_found",
    message: String = code
) : BusinessException(code, message)