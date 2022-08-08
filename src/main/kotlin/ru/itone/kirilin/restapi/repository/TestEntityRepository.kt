package ru.itone.kirilin.restapi.repository

import org.springframework.data.repository.PagingAndSortingRepository
import ru.itone.kirilin.restapi.entity.TestEntity
import java.util.*

interface TestEntityRepository : PagingAndSortingRepository<TestEntity, UUID>
