package com.gpuduck.hubduck.infrastructure.adapters.outbound.persistence

import com.gpuduck.hubduck.application.port.outbound.ClusterRepository
import com.gpuduck.hubduck.domain.model.Cluster
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryClusterRepository : ClusterRepository {

	private val clusters = ConcurrentHashMap<String, Cluster>()

	override fun save(cluster: Cluster): Cluster {
		clusters[cluster.id] = cluster
		return cluster
	}

	override fun findAll(): List<Cluster> =
		clusters.values.sortedBy { it.registeredAt }

	override fun findById(id: String): Cluster? = clusters[id]

	override fun existsByName(name: String): Boolean =
		clusters.values.any { it.name == name }
}
