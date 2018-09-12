package net.slipp.misc

/**
 * @author galcyurio
 */
class AlreadyExistsEmailException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)