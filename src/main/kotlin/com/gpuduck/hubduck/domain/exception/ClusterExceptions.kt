package com.gpuduck.hubduck.domain.exception

class ClusterNotFoundException(id: String) :
	RuntimeException("클러스터를 찾을 수 없습니다. id=$id")

class DuplicateClusterNameException(name: String) :
	RuntimeException("이미 등록된 클러스터 이름입니다. name=$name")
