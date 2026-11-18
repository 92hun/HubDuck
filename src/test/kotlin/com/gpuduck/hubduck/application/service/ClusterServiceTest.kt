package com.gpuduck.hubduck.application.service

import com.gpuduck.hubduck.application.port.inbound.RegisterClusterCommand
import com.gpuduck.hubduck.application.port.outbound.ClusterRepository
import com.gpuduck.hubduck.domain.exception.ClusterNotFoundException
import com.gpuduck.hubduck.domain.exception.DuplicateClusterNameException
import com.gpuduck.hubduck.domain.model.Cluster
import com.gpuduck.hubduck.domain.model.ClusterStatus
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ClusterServiceTest {

	private class FakeClusterRepository : ClusterRepository {
		private val store = linkedMapOf<String, Cluster>()

		override fun save(cluster: Cluster): Cluster {
			store[cluster.id] = cluster
			return cluster
		}

		override fun findAll(): List<Cluster> = store.values.toList()

		override fun findById(id: String): Cluster? = store[id]

		override fun existsByName(name: String): Boolean = store.values.any { it.name == name }
	}

	private val service = ClusterService(FakeClusterRepository())

	@Test
	fun `클러스터를 등록하면 REGISTERED 상태로 저장된다`() {
		val cluster = service.register(RegisterClusterCommand("aws-gpu-1", "https://1.2.3.4:6443"))

		assertEquals("aws-gpu-1", cluster.name)
		assertEquals(ClusterStatus.REGISTERED, cluster.status)
		assertEquals(cluster, service.getById(cluster.id))
	}

	@Test
	fun `같은 이름으로 등록하면 DuplicateClusterNameException이 발생한다`() {
		service.register(RegisterClusterCommand("aws-gpu-1", "https://1.2.3.4:6443"))

		assertFailsWith<DuplicateClusterNameException> {
			service.register(RegisterClusterCommand("aws-gpu-1", "https://5.6.7.8:6443"))
		}
	}

	@Test
	fun `없는 id를 조회하면 ClusterNotFoundException이 발생한다`() {
		assertFailsWith<ClusterNotFoundException> { service.getById("missing") }
	}

	@Test
	fun `등록한 클러스터가 목록에 모두 나온다`() {
		service.register(RegisterClusterCommand("a", "https://a:6443"))
		service.register(RegisterClusterCommand("b", "https://b:6443"))

		assertEquals(listOf("a", "b"), service.getAll().map { it.name })
	}

	@Test
	fun `이름이 비어 있으면 IllegalArgumentException이 발생한다`() {
		assertFailsWith<IllegalArgumentException> {
			service.register(RegisterClusterCommand("", "https://a:6443"))
		}
	}
}
