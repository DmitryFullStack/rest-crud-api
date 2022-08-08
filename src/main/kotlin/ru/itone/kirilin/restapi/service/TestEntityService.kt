package ru.itone.kirilin.restapi.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.itone.kirilin.restapi.dto.TestEntityDto
import java.util.*

interface TestEntityService {

    fun getAll(pageable: Pageable): Page<TestEntityDto>

    fun getById(id: UUID): TestEntityDto?

    fun create(testEntityDto: TestEntityDto): TestEntityDto

    fun update(id: UUID, testEntityDto: TestEntityDto): TestEntityDto

    fun delete(id: UUID)
}