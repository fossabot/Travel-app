package com.peranidze.data.user.interactor

import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.interpretator.CompletableUseCase
import com.peranidze.data.repository.UserRepository
import io.reactivex.Completable

open class DeleteUserUseCase(
    private val userRepository: UserRepository,
    executor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<String>(executor, postExecutionThread) {

    public override fun buildUseCase(params: String): Completable = userRepository.deleteUser(params)
}
