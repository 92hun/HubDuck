package com.gpuduck.hubduck.application.port.inbound

import com.gpuduck.hubduck.domain.model.Cluster

interface GetClusterUseCase {

	fun getAll(): List<Cluster>

	fun getById(id: String): Cluster
}
