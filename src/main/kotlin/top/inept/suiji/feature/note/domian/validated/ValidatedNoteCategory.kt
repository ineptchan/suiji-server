package top.inept.suiji.feature.note.domian.validated

import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.constraints.PositiveOrZero
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [])
@PositiveOrZero(message = "valid.common.id")
annotation class  ValidatedNoteCategory(
    val message: String = "valid.common.unknown_error",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)