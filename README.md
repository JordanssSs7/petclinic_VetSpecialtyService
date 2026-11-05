# Spring Boot : Application PetClinic

## Features  

### 1.  Unit Test  --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2

## Grupo 5 — VetSpecialtyController: pruebas de integración (capa web)

Pruebas de la capa web del bloque `VetSpecialty`, en dos versiones por integrante:
`XxxControllerTest` (integración real con `@SpringBootTest` + `MockMvc` + H2, sin mocks) y
`XxxControllerMockitoTest` (servicio simulado con `@MockitoBean`).

| Integrante | Bloque | Rama |
|---|---|---|
| Yoselin | A — Asignación | `yoselin-Asignación` |
| Jordan Reyes | B — Consultas | `jordan_consultas` |
| Ramses Alvarez | C — Especialidad principal | `ramses-EspecialidadPrincipal` |

### Integrante A — Yoselin: Asignación

Se implementó la base de datos de la relación veterinario–especialidad y la lógica de asignación:

- `VetSpecialty.java` y `VetSpecialtyId.java`: entidad y clave compuesta (`vetId` + `specialtyId`).
- `VetSpecialtyRepository.java`: acceso a la tabla `vet_specialties`.
- `VetSpecialtyAlreadyAssignedException.java` y `VetSpecialtyNotFoundException.java`: errores de negocio.
- `VetSpecialtyService` / `VetSpecialtyServiceImpl`: `assignSpecialtyToVet` (rechaza asignaciones duplicadas)
  y `removeSpecialtyFromVet` (falla si la asignación no existe).
- Pruebas de servicio: `VetSpecialtyServiceTest`.

### Integrante B — Jordan Reyes: Consultas

Se implementaron y probaron los endpoints de consulta:

- `GET /vets/{vetId}/specialties`: especialidades de un veterinario.
- `GET /specialties/{specId}/vets`: veterinarios que tienen una especialidad.

Archivos agregados o modificados:

- `VetSpecialtyController.java`: controlador REST con los dos endpoints (inyección por constructor con `@RequiredArgsConstructor`).
- `Vet.java` y `Specialty.java`: se añadió `@JsonIgnore` a la relación `@ManyToMany` para evitar la
  recursión infinita al serializar a JSON (Vet → Specialty → Vet → …).
- `TObjectCreator.java`: helpers `getSpecialtiesForVet3()` y `getVetsForSpecialty1()` con los datos esperados.
- `webs/VetSpecialtyControllerTest.java`: integración real contra H2 con los datos de `data.sql`.
- `webs/VetSpecialtyControllerMockitoTest.java`: controlador aislado con el servicio simulado.

Escenarios probados (en ambas versiones):

1. `testFindSpecialtiesByVet`: `GET /vets/3/specialties` → `200 OK`, 2 especialidades (surgery, dentistry).
2. `testFindVetsBySpecialty`: `GET /specialties/1/vets` → `200 OK`, vets 2 y 5.
3. `testFindSpecialtiesByVet_Empty`: `GET /vets/1/specialties` → `200 OK`, lista vacía.

La versión real verifica el contenido con `jsonPath` sobre los datos semilla. La versión con Mockito define las
respuestas con `Mockito.when(...)` y confirma con `Mockito.verify(...)` que el controlador invoca al servicio.

### Integrante C — Ramses Alvarez: Especialidad principal

Se implementó el endpoint `PUT /vet-specialties/{vetId}/{specId}/primary`, que marca una especialidad asignada
como principal y desmarca la anterior (200 OK, o 404 si la asignación no existe).
Ver el detalle, los archivos y los escenarios probados en su sección de este mismo README.

### Ejecución de las pruebas

En Windows (PowerShell):

    .\mvnw.cmd clean test "-Dspring.profiles.active=h2"

### Conflictos resueltos

_Pendiente de completar al fusionar las ramas en `main` (README y `TObjectCreator.java` son los archivos que más
probablemente coincidan entre integrantes)._
