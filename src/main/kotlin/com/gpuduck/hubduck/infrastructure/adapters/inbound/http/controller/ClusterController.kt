package com.gpuduck.hubduck.infrastructure.adapters.inbound.http.controller

import com.gpuduck.hubduck.application.port.inbound.GetClusterUseCase
import com.gpuduck.hubduck.application.port.inbound.RegisterClusterCommand
import com.gpuduck.hubduck.application.port.inbound.RegisterClusterUseCase
import com.gpuduck.hubduck.infrastructure.adapters.inbound.http.controller.dto.ClusterResponse
import com.gpuduck.hubduck.infrastructure.adapters.inbound.http.controller.dto.RegisterClusterRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clusters")
class ClusterController(
	private val registerClusterUseCase: RegisterClusterUseCase,
	private val getClusterUseCase: GetClusterUseCase,
) {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun register(@RequestBody request: RegisterClusterRequest): ClusterResponse {
		val cluster = registerClusterUseCase.register(
			RegisterClusterCommand(name = request.name, apiServer = request.apiServer),
		)
		return ClusterResponse.from(cluster)
	}

	@GetMapping
	fun getAll(): List<ClusterResponse> =
		getClusterUseCase.getAll().map(ClusterResponse::from)

	@GetMapping("/{id}")
	fun getById(@PathVariable id: String): ClusterResponse =
		ClusterResponse.from(getClusterUseCase.getById(id))
}
