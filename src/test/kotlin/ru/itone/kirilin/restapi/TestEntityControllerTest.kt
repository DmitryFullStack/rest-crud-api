package ru.itone.kirilin.restapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.itone.kirilin.restapi.dto.TestEntityDto
import ru.itone.kirilin.restapi.entity.TestEntity
import ru.itone.kirilin.restapi.repository.TestEntityRepository
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
class TestEntityControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @SpyBean
    lateinit var repository: TestEntityRepository

    val url = "/api/v1/testEntities"

    private val testEntity = TestEntity(
        documentId = anyUUID,
        documentDate = "documentDate",
        dictionaryValueId = anyUUID,
        dictionaryValueName = anyUUID
    )

    private val testEntityDto = TestEntityDto(
        documentId = anyUUID,
        documentDate = "documentDate",
        dictionaryValueId = anyUUID,
        dictionaryValueName = anyUUID,
        testId = null,
        testName = "testName"
    )

    var testUUID: UUID? = null

    @AfterEach
    fun reset() {
        Mockito.reset<Any>(repository)
    }


    @Test
    @DisplayName("получение всех сущностей")
    fun `should return all test entities`() {
        val argument = ArgumentCaptor.forClass(
            Pageable::class.java
        )
        val expectedCount = repository.count()

        val mvcResult = mockMvc.get(url)
            .andExpect { status { isOk() } }
            .andReturn()
        val resp = objectMapper.readTree(mvcResult.response.contentAsString)
            .findValue("content").toList()

        verify(repository, times(1)).findAll(argument.capture())

        assertEquals(expectedCount.toInt(), resp.size)
    }

    @Test
    @DisplayName("получение сущность по идентификатору")
    fun `should return test entity by id`() {
        testUUID = repository.save(testEntity).id
        val mvcResult = mockMvc.get("$url/$testUUID")
            .andExpect { status { isOk() } }
            .andReturn()
        val resp = objectMapper.readValue(mvcResult.response.contentAsString, TestEntityDto::class.java)

        verify(repository, times(1)).findById(testUUID!!)

        Assertions.assertAll(
            { assertEquals(testEntity.documentId, resp.documentId) },
            { assertEquals(testEntity.documentDate, resp.documentDate) },
            { assertEquals(testEntity.dictionaryValueId, resp.dictionaryValueId) },
            { assertEquals(testEntity.dictionaryValueName, resp.dictionaryValueName) },
        )
        repository.deleteById(testUUID!!)
    }

    @Test
    @DisplayName("создание новой сущности")
    fun `should create new test entity`() {
        val expectedCount = repository.count() + 1
        val argument = ArgumentCaptor.forClass(
            TestEntity::class.java
        )

        val mvcResult = mockMvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testEntityDto))
        ).andExpect(status().isOk).andReturn()
        val resp = objectMapper.readValue(mvcResult.response.contentAsString, TestEntityDto::class.java)

        verify(repository, times(1)).save(argument.capture())

        Assertions.assertAll(
            { assertEquals(testEntityDto.documentId, resp.documentId) },
            { assertEquals(testEntityDto.documentDate, resp.documentDate) },
            { assertEquals(testEntityDto.dictionaryValueId, resp.dictionaryValueId) },
            { assertEquals(testEntityDto.dictionaryValueName, resp.dictionaryValueName) },
            { assertEquals(testEntityDto.testId, resp.testId) },
            { assertEquals(testEntityDto.testName, resp.testName) },
        )

        assertEquals(expectedCount, repository.count())

        repository.deleteById(resp.id!!)
    }

    @Test
    @DisplayName("обновление сущности")
    fun `should update test entity`() {
        testUUID = repository.save(testEntity).id
        val expectedCount = repository.count()
        val argument = ArgumentCaptor.forClass(
            TestEntity::class.java
        )

        val mvcResult = mockMvc.perform(
            patch("$url/$testUUID", testEntityDto)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testEntityDto))
        ).andExpect(status().isOk).andReturn()
        val resp = objectMapper.readValue(mvcResult.response.contentAsString, TestEntityDto::class.java)

        verify(repository, times(1)).findById(testUUID!!)
        verify(repository, times(2)).save(argument.capture())

        Assertions.assertAll(
            { assertEquals(testEntityDto.documentId, resp.documentId) },
            { assertEquals(testEntityDto.documentDate, resp.documentDate) },
            { assertEquals(testEntityDto.dictionaryValueId, resp.dictionaryValueId) },
            { assertEquals(testEntityDto.dictionaryValueName, resp.dictionaryValueName) },
            { assertEquals(testEntityDto.testId, resp.testId) },
            { assertEquals(testEntityDto.testName, resp.testName) },
        )
        assertEquals(expectedCount, repository.count())

        repository.deleteById(resp.id!!)
    }

    @Test
    @DisplayName("удаление сущности по идентификатору")
    fun `should remove test entity by id`() {
        val entIdForRemove = repository.save(testEntity).id
        val expectedCount = repository.count() - 1
        mockMvc.delete("$url/$entIdForRemove")
            .andExpect { status { isOk() } }

        verify(repository, times(1)).deleteById(entIdForRemove!!)
        assertEquals(expectedCount, repository.count())
        assertFalse(repository.findById(entIdForRemove).isPresent)
    }

    companion object {
        val anyUUID = UUID.randomUUID()
    }
}