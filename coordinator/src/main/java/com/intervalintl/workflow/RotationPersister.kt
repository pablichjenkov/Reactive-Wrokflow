package com.intervalintl.workflow

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RotationPersister internal constructor(activity: FragmentActivity) {
    private val coordinatorStore: CoordinatorStore

    init {
        val viewModelProvider = ViewModelProvider(activity, ViewModelProvider.NewInstanceFactory())
        coordinatorStore = viewModelProvider.get(COORDINATOR_STORE_VIEW_MODEL_ID, CoordinatorStore::class.java)
    }

    fun <F : Coordinator<*>> getRootFlow(): F? {
        return coordinatorStore.rootCoordinator as F?
    }

    fun setRootFlow(rootCoordinator: Coordinator<*>) {
        coordinatorStore.rootCoordinator = rootCoordinator
    }

    fun <F : Coordinator<*>> findFlowById(flowId: String): F? {
        return coordinatorStore.rootCoordinator?.depthFirstSearchFlowById(flowId)
    }

    class CoordinatorStore : ViewModel() {

        var rootCoordinator: Coordinator<*>? = null

        override fun onCleared() {
            rootCoordinator?.stop()
        }

    }

    companion object {
        private val COORDINATOR_STORE_VIEW_MODEL_ID = "coordinatorStoreViewModel"
    }

}