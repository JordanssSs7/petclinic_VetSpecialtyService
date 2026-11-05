## Integrante C — Ramses Alvarez: Especialidad principal

### Funcionalidad implementada

Se implementó el endpoint:

PUT /vet-specialties/{vetId}/{specId}/primary

Permite marcar una especialidad asignada como principal de un
veterinario, desmarcando la anterior. Devuelve HTTP 200 con la
asignación actualizada o HTTP 404 si la asignación no existe.

### Archivos agregados para las pruebas web

- VetSpecialtyPrimaryController.java: recibe la petición PUT
  y delega la operación al servicio.
- VetSpecialtyObjectCreator.java: construye datos de ejemplo
  para las pruebas con Mockito.
- VetSpecialtyPrimaryControllerTest.java: prueba el recorrido
  completo desde el controlador hasta H2.
- VetSpecialtyPrimaryControllerMockitoTest.java: prueba las
  respuestas del controlador con el servicio simulado.

### Escenarios probados

1. Marcar una especialidad como principal.
2. Cambiar la especialidad principal.
3. Rechazar una especialidad no asignada con HTTP 404.

La versión con H2 verifica que solo quede una principal y que
la anterior quede desmarcada. La versión con Mockito comprueba
las respuestas HTTP, el JSON y las llamadas al servicio.

### Ejecución

En Windows:

    .\mvnw.cmd clean test "-Dspring.profiles.active=h2"

Resultado de la ejecución realizada:
31 pruebas aprobadas, sin fallos, errores ni pruebas omitidas.