create table test_entity (
    id uuid not null,
    dictionary_value_id uuid,
    dictionary_value_name uuid,
    document_date varchar(255),
    document_id uuid,
    test_id uuid,
    test_name varchar(255),
    primary key (id)
);

insert into test_entity values
    (random_uuid(), random_uuid(), random_uuid(), 100, random_uuid(), random_uuid(), 'one'),
    (random_uuid(), random_uuid(), random_uuid(), 100, random_uuid(), random_uuid(), 'two'),
    (random_uuid(), random_uuid(), random_uuid(), 100, random_uuid(), random_uuid(), 'three'),
    (random_uuid(), random_uuid(), random_uuid(), 100, random_uuid(), random_uuid(), 'four');