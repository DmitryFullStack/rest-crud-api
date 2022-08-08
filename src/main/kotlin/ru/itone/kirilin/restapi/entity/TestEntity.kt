package ru.itone.kirilin.restapi.entity

import lombok.EqualsAndHashCode
import lombok.ToString
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class TestEntity (
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    val id: UUID? = null,
    var documentId: UUID? = null,
    var documentDate: String = "",
    var dictionaryValueId: UUID? = null,
    var dictionaryValueName: UUID? = null,
        ){
    var testId: UUID? = null
    var testName: String = "Test"
}
