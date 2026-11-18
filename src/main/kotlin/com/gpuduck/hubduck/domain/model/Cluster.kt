package com.gpuduck.hubduck.domain.model

import java.time.Instant

enum class ClusterStatus {
	REGISTERED,
}

data class Cluster(
	val id: String,
	val name: String,
	val apiServer: String,
	val status: ClusterStatus = ClusterStatus.REGISTERED,
	val registeredAt: Instant,
) {
	init {
		require(name.isNotBlank()) { "클러스터 이름은 비어 있을 수 없습니다." }
		require(apiServer.isNotBlank()) { "API 서버 주소는 비어 있을 수 없습니다." }
	}
}
