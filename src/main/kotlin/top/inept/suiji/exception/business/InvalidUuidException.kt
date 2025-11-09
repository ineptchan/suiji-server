package top.inept.suiji.exception.business

import top.inept.suiji.exception.BusinessException

class InvalidUuidException(
    code: String = "message.common.invalid_uuid",
    message: String = code
) : BusinessException(code, message)