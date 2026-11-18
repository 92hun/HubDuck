package com.gpuduck.hubduck.infrastructure.adapters.inbound.http.controller.dto

import com.gpuduck.hubduck.domain.model.Cluster
import java.time.Instant

data class RegisterClusterRequest(
	val name: String,
	val apiServer: String,
)

data class ClusterResponse(
	val id: String,
	val name: String,
	val apiServer: String,
	val status: String,
	val registeredAt: Instant,
) {
	companion object {
		fun from(cluster: Cluster) = ClusterResponse(
			id = cluster.id,
			name = cluster.name,
			apiServer = cluster.apiServer,
			status = cluster.status.name,
			registeredAt = cluster.registeredAt,
		)
	}
}

data class ErrorResponse(
	val message: String,
)
