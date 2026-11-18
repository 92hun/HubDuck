package com.gpuduck.hubduck.application.service

import com.gpuduck.hubduck.application.port.inbound.GetClusterUseCase
import com.gpuduck.hubduck.application.port.inbound.RegisterClusterCommand
import com.gpuduck.hubduck.application.port.inbound.RegisterClusterUseCase
import com.gpuduck.hubduck.application.port.outbound.ClusterRepository
import com.gpuduck.hubduck.domain.exception.ClusterNotFoundException
import com.gpuduck.hubduck.domain.exception.DuplicateClusterNameException
import com.gpuduck.hubduck.domain.model.Cluster
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class ClusterService(
	private val clusterRepository: ClusterRepository,
) : RegisterClusterUseCase, GetClusterUseCase {

	override fun register(command: RegisterClusterCommand): Cluster {
		if (clusterRepository.existsByName(command.name)) {
			throw DuplicateClusterNameException(command.name)
		}
		val cluster = Cluster(
			id = UUID.randomUUID().toString(),
			name = command.name,
			apiServer = command.apiServer,
			registeredAt = Instant.now(),
		)
		return clusterRepository.save(cluster)
	}

	override fun getAll(): List<Cluster> = clusterRepository.findAll()

	override fun getById(id: String): Cluster =
		clusterRepository.findById(id) ?: throw ClusterNotFoundException(id)
}
