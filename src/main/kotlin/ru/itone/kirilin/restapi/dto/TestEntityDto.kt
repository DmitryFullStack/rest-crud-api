package ru.itone.kirilin.restapi.dto

import ru.itone.kirilin.restapi.entity.TestEntity
import java.util.*

data class TestEntityDto(
    val id: UUID? = null,
    val documentId: UUID? = null,
    val documentDate: String = "",
    val dictionaryValueId: UUID? = null,
    val dictionaryValueName: UUID? = null,
    val testId: UUID? = null,
    val testName: String
)

fun TestEntity.toDto(): TestEntityDto{
    return TestEntityDto(
        id = id,
        documentId = documentId,
        documentDate = documentDate,
        dictionaryValueId = dictionaryValueId,
        dictionaryValueName = dictionaryValueName,
        testId = testId,
        testName = testName
    )
}

fun TestEntityDto.fromDto(): TestEntity{
    return TestEntity(
        documentId = documentId,
        documentDate = documentDate,
        dictionaryValueId = dictionaryValueId,
        dictionaryValueName = dictionaryValueName
    ).also {
        it.testId = testId
        it.testName = testName
    }
}