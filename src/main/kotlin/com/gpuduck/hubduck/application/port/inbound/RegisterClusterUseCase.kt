package com.gpuduck.hubduck.application.port.inbound

import com.gpuduck.hubduck.domain.model.Cluster

interface RegisterClusterUseCase {

	fun register(command: RegisterClusterCommand): Cluster
}

data class RegisterClusterCommand(
	val name: String,
	val apiServer: String,
)
