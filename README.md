# vetSystem_Avendanio_2C
 
Sistema de gestión veterinaria desarrollado en Java con Spring Boot.
 

## Tecnologías
 
- Java
- Spring Boot
- Maven
-Bootstrap

## Cómo levantar el proyecto
 
### Requisitos previos
 
- JDK instalado (11 o superior, según la versión configurada en `pom.xml`)
- Maven (o usar el wrapper `mvnw` incluido en el proyecto)
### Pasos
 
1. Clonar el repositorio:
```bash
   git clone https://github.com/TU_USUARIO/vetSystem_Avendanio_2C.git
   cd vetSystem_Avendanio_2C

```
## Parcial 1
### Relación Turno–Medicamento

Se eligió una relación Many-to-Many entre Turno y Medicamento porque un mismo turno puede tener varios medicamentos recetados a la vez, 
y un mismo medicamento se receta en múltiples turnos distintos El esquema de la tabla intermedia se generó automáticamente al levantar la aplicación gracias a spring.jpa.hibernate.ddl-auto=update, sin necesidad de escribir migraciones SQL manuales. 

### Validación de stock

La validación del stock se implementó en la capa de servicio, específicamente en el método asociarMedicamento de TurnoService,
y no en el controller. Antes de asociar un medicamento a un turno, el método busca ambas entidades por su id y valida que existan, 
lanzando ResourceNotFoundException si alguna no se encuentra. Luego se verifica si el stock del medicamento es mayor a cero: 
si no hay stock disponible, se lanza la excepción custom StockInsuficienteException, que es capturada por el GlobalExceptionHandler 
y devuelve un status 422 con un mensaje descriptivo indicando qué medicamento no tiene stock. Si el stock es suficiente, se decrementa en uno 
y se persisten tanto el medicamento actualizado como el turno con la nueva asociación. 

### Solapamiento

Para detectar el solapamiento de turnos se agregó el método findByVeterinarioIdAndFechaAndHora en TurnoRepository, 
que busca si ya existe un turno para el mismo veterinario, en la misma fecha y a la misma hora exacta. Este chequeo se ejecuta en 
TurnoService.registrarEntidad, antes de guardar el nuevo turno, y no en el controller, siguiendo el mismo criterio de mantener la lógica de negocio en la capa de servicio. Si el método encuentra un turno existente con esos tres parámetros coincidentes, se lanza la excepción TurnoSuperpuestoException con un mensaje que incluye el id y el horario del turno conflictivo, información que ayuda a quien consume la API a entender exactamente qué turno está generando el conflicto. Esta excepción es capturada por el GlobalExceptionHandler y devuelve un status 409 Conflict. Se decidió comparar veterinario, fecha y hora en conjunto, y no solo la fecha, porque el mismo veterinario puede perfectamente atender distintos turnos en distintos horarios del mismo día; lo que no puede pasar es que atienda dos turnos exactamente a la misma hora.

### Cupo de mascotas

Para controlar el cupo de mascotas por dueño se agregó el método countByDuenioId en MascotaRepository, que cuenta cuántas mascotas tiene registradas un dueño determinado. Esta validación se ejecuta en MascotaService.registrarEntidad, antes de guardar la nueva mascota: si el conteo ya es mayor o igual a 5, se lanza la excepción CupoExcedidoException, que el GlobalExceptionHandler traduce en un status 422 con un mensaje descriptivo. Como la entidad Mascota no cuenta con un campo de estado o soft-delete, se definió el criterio de "mascota activa" como toda mascota actualmente registrada en el sistema para ese dueño, ya que no existe ningún mecanismo de baja lógica que permita distinguir mascotas activas de inactivas. Esta decisión implica que, si en el futuro se quisiera dar de baja una mascota sin borrarla físicamente, habría que agregar un campo adicional a la entidad y ajustar tanto esta consulta como su criterio de conteo. Por ahora, el enfoque elegido es el más simple y consistente con el resto del modelo de datos existente.

### Decisión más difícil

La decisión más difícil fue definir correctamente el tipo de relación entre Turno y Medicamento y cómo declarar sus dos lados de forma consistente, ya que un error en el mappedBy o en el nombre del campo genera errores de arranque de Hibernate poco intuitivos de diagnosticar. También costó decidir en qué capa ubicar cada validación de negocio, para mantener el controller liviano y toda la lógica concentrada en los servicios, siguiendo el mismo patrón que ya se usaba en el resto del proyecto. Otro punto que requirió pensar dos veces fue la definición de "mascota activa" para el cupo, dado que la entidad no tenía ningún campo que representara ese estado. Finalmente, ordenar las validaciones dentro de asociarMedicamento (buscar turno, buscar medicamento, validar stock, recién ahí persistir) fue clave para que los mensajes de error fueran específicos y no genéricos. En conjunto, el mayor desafío no fue tanto la sintaxis sino decidir consistentemente dónde y cómo aplicar cada regla de negocio dentro de la arquitectura ya existente.