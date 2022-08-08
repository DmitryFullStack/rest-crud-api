package ru.itone.kirilin.restapi.service

import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.itone.kirilin.restapi.dto.TestEntityDto
import ru.itone.kirilin.restapi.dto.fromDto
import ru.itone.kirilin.restapi.dto.toDto
import ru.itone.kirilin.restapi.repository.TestEntityRepository
import java.util.*
import javax.transaction.Transactional

@Service
class TestEntityServiceImpl(
    private val testEntityRepository: TestEntityRepository
): TestEntityService {

    override fun getAll(pageable: Pageable): Page<TestEntityDto> =
        testEntityRepository.findAll(pageable)
            .map {x -> x.toDto()}

    override fun getById(id: UUID): TestEntityDto =
        testEntityRepository.findById(id)
            .map { x -> x.toDto() }
            .orElseThrow{
                Exception("Test entity with id $id is not found")
            }

    override fun create(testEntityDto: TestEntityDto): TestEntityDto =
        testEntityRepository.save(testEntityDto.fromDto()).toDto()

    @Transactional
    override fun update(id: UUID, testEntityDto: TestEntityDto): TestEntityDto {
        val testEntity = testEntityRepository.findById(id)
            .orElseThrow {
                Exception("Test entity with id $id not found")
            }
        BeanUtils.copyProperties(testEntityDto, testEntity)
        return testEntityRepository.save(testEntity).toDto()
    }

    override fun delete(id: UUID) {
        testEntityRepository.deleteById(id)
    }
}
