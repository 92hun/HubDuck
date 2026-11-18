package com.gpuduck.hubduck.application.port.outbound

import com.gpuduck.hubduck.domain.model.Cluster

interface ClusterRepository {

	fun save(cluster: Cluster): Cluster

	fun findAll(): List<Cluster>

	fun findById(id: String): Cluster?

	fun existsByName(name: String): Boolean
}
