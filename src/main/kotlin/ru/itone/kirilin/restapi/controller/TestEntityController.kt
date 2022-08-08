package ru.itone.kirilin.restapi.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itone.kirilin.restapi.dto.TestEntityDto
import ru.itone.kirilin.restapi.service.TestEntityService
import java.util.*

@RestController
@RequestMapping("/api/v1/testEntities")
class TestEntityController(
    private val testEntityService: TestEntityService
) {
    @GetMapping
    fun getAll(@PageableDefault pageable: Pageable): ResponseEntity<Page<TestEntityDto>> =
        ResponseEntity.ok(testEntityService.getAll(pageable))

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): ResponseEntity<TestEntityDto> =
        ResponseEntity.ok(testEntityService.getById(id))

    @PostMapping
    fun create(@RequestBody dto: TestEntityDto): ResponseEntity<TestEntityDto> =
        ResponseEntity.ok(testEntityService.create(dto))

    @PatchMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody dto: TestEntityDto) =
        ResponseEntity.ok(testEntityService.update(id, dto))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) =
        ResponseEntity.ok(testEntityService.delete(id))
}
