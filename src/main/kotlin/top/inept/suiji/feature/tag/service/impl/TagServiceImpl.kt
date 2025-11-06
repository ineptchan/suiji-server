package top.inept.suiji.feature.tag.service.impl

import org.springframework.context.support.MessageSourceAccessor
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import top.inept.suiji.exception.business.DuplicateException
import top.inept.suiji.exception.business.EntityNotFoundException
import top.inept.suiji.extensions.get
import top.inept.suiji.extensions.toPageRequest
import top.inept.suiji.feature.tag.domain.convert.toTag
import top.inept.suiji.feature.tag.domain.dto.CreateTagDTO
import top.inept.suiji.feature.tag.domain.dto.QueryTagDTO
import top.inept.suiji.feature.tag.domain.dto.UpdateTagDTO
import top.inept.suiji.feature.tag.domain.entity.Tag
import top.inept.suiji.feature.tag.repository.TagRepository
import top.inept.suiji.feature.tag.service.TagService

@Service
class TagServiceImpl(
    private val messages: MessageSourceAccessor,
    private val tagRepository: TagRepository
) : TagService {
    override fun getTags(dto: QueryTagDTO): Page<Tag> {
        val pageRequest = dto.toPageRequest()

        return if (dto.name.isEmpty()) {
            tagRepository.findAll(pageRequest)
        } else {
            tagRepository.findByNameContains(dto.name, pageRequest)
        }
    }

    override fun getTagById(id: Long): Tag {
        val tag = tagRepository.findByIdOrNull(id)
        if (tag == null) throw EntityNotFoundException(messages["message.tag.not_found"])
        return tag
    }

    override fun createTag(dto: CreateTagDTO): Tag {
        return try {
            tagRepository.save(dto.toTag())
        } catch (ex: DataIntegrityViolationException) {
            throw DuplicateException(messages["message.tag.duplicate_name"])
        }
    }

    override fun deleteTag(id: Long) {
        tagRepository.deleteById(id)
    }

    override fun updateTag(id: Long, dto: UpdateTagDTO): Tag {
        if (!tagRepository.existsById(id)) throw EntityNotFoundException(messages["message.tag.not_found"])

        try {
            return tagRepository.save(dto.toTag(id))
        } catch (ex: DataIntegrityViolationException) {
            throw DuplicateException(messages["message.tag.duplicate_name"])
        }
    }
}