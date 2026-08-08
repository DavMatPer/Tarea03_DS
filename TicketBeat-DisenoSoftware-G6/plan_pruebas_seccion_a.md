# Plan de Pruebas — Sección A

**Proyecto:** TicketBeat
**Tarea:** Pruebas y Refactoring — Diseño de Software
**Sección:** A. Plan de Pruebas

## 1. Objetivo

Diseñar un plan de pruebas unitarias que cubra las 29 clases del proyecto, validando tanto el funcionamiento normal de cada método (casos típicos) como su comportamiento ante datos límite o inválidos (casos límite y de error), de acuerdo con lo solicitado en la Sección A de la tarea.

## 2. Enfoque de cobertura

Para diseñar el plan se siguieron tres criterios:

1. **Cobertura por clase.** Toda clase concreta del proyecto tiene al menos un caso de prueba. Las clases con lógica de negocio (gestores, patrones de diseño, decoradores) tienen entre 3 y 6 casos; las clases que solo almacenan datos (POJOs como `Pago`, `Incidente`, `NotificacionMensaje`) tienen casos más simples enfocados en verificar la correcta asignación y lectura de sus atributos.
2. **Tres tipos de caso, marcados en la columna "Propósito de la prueba":**
   - **[Típico]** — el "camino feliz": entradas válidas y comunes, para confirmar que el método hace lo que se espera.
   - **[Límite]** — valores en el borde de una condición: colecciones vacías, cantidades exactamente iguales al límite permitido, montos en cero, edades exactamente en el mínimo requerido. Estos casos son los que con más frecuencia revelan errores de un solo dígito (`>` en vez de `>=`, por ejemplo).
   - **[Error]** — entradas inválidas (`null`, negativos, datos faltantes) que exponen la ausencia de validaciones en el código actual. Varios de estos casos documentan explícitamente una excepción no controlada (`NullPointerException`) que el sistema lanzaría hoy, lo cual es información valiosa tanto para las pruebas (Sección B) como para el análisis de code smells (Sección C).
3. **Organización por capa arquitectónica.** Los casos se agrupan según el patrón de diseño o capa a la que pertenece la clase (Gestores, Cadena de Responsabilidad, Factory Method, Strategy, Decorator, Servicios, Modelo), en vez de por orden alfabético, para que el plan sea trazable con el diseño del sistema.

**Precondición del plan:** los casos asumen que ya se agregaron los getters/setters y correcciones descritos previamente en `Evento`, `Incidente`, `Comprador`, `Reserva`, `NotificacionMensaje`, `PoliticaEventoBase`, `GestorNotificaciones` y `PoliticaEvento`. Sin esos cambios, varios de los casos marcados como [Típico] o [Límite] en estas clases no serían implementables en JUnit por falta de forma de fijar o leer el estado interno.

## 3. Resumen de cobertura

| Tipo de caso | Cantidad | Qué valida |
|---|---|---|
| Típico | 40 | Funcionamiento correcto (10% de la Sección A) |
| Límite | 19 | Comportamiento en los bordes de una condición |
| Error | 7 | Búsqueda de errores y excepciones no controladas (10% de la Sección A) |
| **Total** | **66** | |

## 4. Plan de pruebas

### 4.1 Gestores

| Clase | ID | Método a probar | Datos de entrada | Salida esperada | Propósito de la prueba |
|---|---|---|---|---|---|
| GestorEventos | TC-001 | `solicitarResumenEvento` | Evento con `boletosVendidos=50`, `montoRecaudado=5000.0` | Imprime "Resumen: 50 boletos vendidos, Monto: $5000.0", sin excepciones | [Típico] Verificar que el resumen refleje correctamente los datos del evento |
| GestorEventos | TC-002 | `solicitarResumenEvento` | `evento = null` | Lanza `NullPointerException` | [Error] Detectar la ausencia de manejo de argumentos nulos |
| GestorEventos | TC-003 | `confirmarCancelacion` | Evento con 2 boletos (1 `VENDIDO`, 1 `DISPONIBLE`), política y notificador válidos, `motivo="Artista indispuesto"` | Estado del evento pasa a `"CANCELADO"`; se notifica solo al boleto `VENDIDO` (1 elemento en la lista de afectados) | [Típico] Verificar el flujo completo de cancelación, incluyendo el filtrado real de boletos afectados |
| GestorEventos | TC-004 | `confirmarCancelacion` | `politica = null` | Lanza `NullPointerException` | [Error] Detectar falta de validación cuando no se provee una política |
| GestorIncidentes | TC-005 | `registrarYClasificar` | `descripcion="El boleto no llegó al correo"` | Retorna `Incidente` con `getDescripcion()` igual al texto dado | [Típico] Verificar la creación correcta del incidente |
| GestorIncidentes | TC-006 | `registrarYClasificar` | `descripcion = null` | Retorna un `Incidente` con descripción `null`, sin excepción | [Límite] Verificar el comportamiento ante entrada nula |
| GestorIncidentes | TC-007 | `registrarIncidente` | `descripcion="Fallo simple en el login"` | Se resuelve en `AgenteSoporte`; no llega a Administración | [Típico] Verificar que la cadena resuelve el caso en el primer eslabón |
| GestorIncidentes | TC-008 | `registrarIncidente` | `descripcion="Fallo complejo en la pasarela de pagos"` | Escala hasta `DepartamentoAdministracion` y se cierra ahí | [Típico] Verificar el escalamiento correcto en la cadena |
| GestorNotificaciones | TC-009 | `iniciarProcesoDeNotificacion` | 1 comprador con `canalPreferido = new CanalEmail()`, `mensajeBase="Cambio de horario"` | Se invoca `canal.enviar(mensaje)` y `registrarEstadoDeEntrega()`; no se imprime "Canal no disponible" | [Típico] Verificar el camino de notificación exitosa por un canal disponible |
| GestorNotificaciones | TC-010 | `iniciarProcesoDeNotificacion` | 1 comprador con `canalPreferido = null` | Se imprime "Canal no disponible, intentar siguiente canal" | [Límite] Verificar el camino alterno cuando el comprador no tiene canal asignado |
| GestorNotificaciones | TC-011 | `iniciarProcesoDeNotificacion` | Lista vacía | Finaliza sin ejecutar el ciclo, sin errores | [Límite] Verificar el comportamiento ante una colección vacía |
| GestorNotificaciones | TC-012 | `iniciarProcesoDeNotificacion` | `lista = null` | Lanza `NullPointerException` en el for-each | [Error] Detectar la falta de validación de argumentos nulos |
| GestorNotificaciones | TC-013 | `notificarCompradores` | Lista de boletos afectados, `mensaje="Evento cancelado: lluvia"` | Imprime la notificación masiva sin excepciones | [Típico] Verificar la notificación usada tras una cancelación |
| GestorReservas | TC-014 | `elegirCantidadYTipoDeEntrada` | — (`verificarDisponibilidad()` fijo en `true`) | Se reservan las entradas y se inicia el temporizador | [Típico] Verificar el flujo normal de reserva de entradas |
| GestorReservas | TC-015 | `confirmarCompra` | `estrategiaPago = null`, `monto=150.0` | Imprime error y retorna sin excepción | [Límite] Verificar el manejo controlado de la falta de estrategia de pago |
| GestorReservas | TC-016 | `confirmarCompra` | `estrategiaPago = new PagoTarjetaStrategy("Visa")`, `monto=250.0` | Pago con estado `"COMPLETADO"`; boletos marcados como vendidos | [Típico] Verificar el flujo exitoso de confirmación de compra |
| GestorReservas | TC-017 | `confirmarCompra` | Estrategia válida, `monto=-100.0` | Se procesa igual como `"COMPLETADO"` (no valida montos negativos) | [Error] Detectar ausencia de validación de reglas de negocio sobre el monto |
| GestorReservas | TC-018 | `tiempoDeReservaExpirado` | — | Se liberan las entradas reservadas y se informa la expiración | [Típico] Verificar el flujo de expiración de una reserva |

### 4.2 Cadena de Responsabilidad (Incidentes)

| Clase | ID | Método a probar | Datos de entrada | Salida esperada | Propósito de la prueba |
|---|---|---|---|---|---|
| AgenteSoporte | TC-019 | `puedeResolver` | `descripcion="El boleto no llegó"` | `true` | [Típico] Verificar la clasificación de un incidente simple |
| AgenteSoporte | TC-020 | `puedeResolver` | `descripcion="Falla COMPLEJO en el servidor"` (mayúsculas) | `false` | [Límite] Verificar que la comparación no distingue mayúsculas/minúsculas |
| AgenteSoporte | TC-021 | `puedeResolver` | `descripcion = null` | `false`, sin excepción | [Límite] Verificar el manejo seguro de descripciones nulas |
| AgenteSoporte | TC-022 | `manejarIncidente` | Incidente complejo, `siguienteManejador = null` | Imprime "No hay más manejadores en la cadena" | [Límite] Verificar el comportamiento cuando la cadena está incompleta |
| DepartamentoAdministracion | TC-023 | `manejarIncidente` | Incidente válido | Se imprime la resolución final y el cierre | [Típico] Verificar el cierre correcto en el último eslabón |
| DepartamentoAdministracion | TC-024 | `resolucionFinal` | `descripcion = null` | Imprime "...aplicada: null" sin excepción | [Límite] Verificar el comportamiento ante datos incompletos |

### 4.3 Factory Method (Boletos)

| Clase | ID | Método a probar | Datos de entrada | Salida esperada | Propósito de la prueba |
|---|---|---|---|---|---|
| CreadorBoletoGeneral | TC-025 | `crearBoleto` | — | Instancia de `BoletoGeneral`, estado `DISPONIBLE`, precio `100.0` | [Típico] Verificar que el Factory Method crea el tipo correcto |
| CreadorBoletoReservado | TC-026 | `crearBoleto` | — | Instancia de `BoletoReservado`, `numeroAsiento="A15"` | [Típico] Verificar la creación del boleto reservado |
| CreadorBoletoVIP | TC-027 | `crearBoleto` | — | Instancia de `BoletoVIP` con 3 beneficios | [Típico] Verificar la creación del boleto VIP |
| CreadorBoleto | TC-028 | `procesarEmision` (vía `CreadorBoletoGeneral`) | — | Se invoca `mostrarDetalles()` del boleto creado, sin excepciones | [Típico] Verificar que el método plantilla usa correctamente el Factory Method |
| BoletoGeneral | TC-029 | `setEstado` / `getEstado` | `EstadoBoleto.VENDIDO` | `getEstado()` retorna `VENDIDO` | [Típico] Verificar la mutación de estado del boleto |
| BoletoVIP | TC-030 | `setBeneficios` + `mostrarDetalles` | `beneficios = null` | Lanza `NullPointerException` en `Arrays.toString` | [Error] Detectar un riesgo de excepción no controlada |
| BoletoReservado | TC-031 | `setNumeroAsiento` | `""` (cadena vacía) | Se asigna sin validación | [Límite] Detectar ausencia de validación de datos vacíos |

### 4.4 Strategy (Pagos)

| Clase | ID | Método a probar | Datos de entrada | Salida esperada | Propósito de la prueba |
|---|---|---|---|---|---|
| PagoTarjetaStrategy | TC-032 | `procesarPago` | `monto=300.0`, `datos={"numeroTarjeta":"1234"}` | `Pago` con estado `"COMPLETADO"` e `id` (UUID) no nulo | [Típico] Verificar el procesamiento exitoso de un pago con tarjeta |
| PagoMovilStrategy | TC-033 | `procesarPago` | `monto=50.0`, `datos={}` | `"COMPLETADO"` pese al mapa vacío | [Límite] Detectar falta de validación de los datos de pago |
| PagoTransferenciaStrategy | TC-034 | `revertirPago` | `pagoId="abc-123"` | `true`, mensaje de extorno impreso | [Típico] Verificar el flujo de reversión de un pago |
| PagoTarjetaStrategy | TC-035 | `procesarPago` | `monto=0.0` | `"COMPLETADO"` con monto `0.0` | [Límite] Verificar el comportamiento ante un monto en el límite inferior |
| PagoMovilStrategy | TC-036 | `procesarPago` | `monto=-50.0` | `"COMPLETADO"` con monto negativo | [Error] Detectar ausencia de validación de montos inválidos |

### 4.5 Decorator (Políticas de Compra)

| Clase | ID | Método a probar | Datos de entrada | Salida esperada | Propósito de la prueba |
|---|---|---|---|---|---|
| PoliticaEventoBase | TC-037 | `validarCompra` | Comprador válido, `cantidad=2` | `true` | [Típico] Verificar la validación base |
| PoliticaEventoBase | TC-038 | `calcularReembolso` | `new PoliticaEventoBase()` por defecto, `monto=200.0` | `160.0` (80% de reembolso) | [Típico] Verificar el cálculo del reembolso cuando se permiten devoluciones |
| PoliticaEventoBase | TC-039 | `calcularReembolso` | `new PoliticaEventoBase(false, 30, 0.8)`, `monto=200.0` | `0` | [Límite] Verificar la rama donde no se permiten devoluciones |
| LimiteBoletosDecorator | TC-040 | `validarCompra` | Límite=4, cantidad=3 | `true` (delega en el componente) | [Típico] Verificar la validación dentro del límite |
| LimiteBoletosDecorator | TC-041 | `validarCompra` | Límite=4, cantidad=4 y cantidad=5 | `true` en el límite exacto, `false` al superarlo | [Límite] Verificar el caso límite exacto del rechazo |
| RestriccionSocioDecorator | TC-042 | `validarCompra` | `requiereMembresia=true`, `esSocio=false` | `false` | [Típico] Verificar el rechazo de compradores no socios |
| RestriccionSocioDecorator | TC-043 | `validarCompra` | `requiereMembresia=false` | Resultado del componente envuelto, sin aplicar la restricción | [Límite] Verificar que la restricción se desactiva correctamente |
| VerificacionEdadDecorator | TC-044 | `validarCompra` | `edadMinima=18`, `edad=17` | `false` | [Típico] Verificar el rechazo por edad insuficiente |
| VerificacionEdadDecorator | TC-045 | `validarCompra` | `edadMinima=18`, `edad=18` | `true` | [Límite] Verificar el caso límite exacto de la edad mínima |
| Decoradores combinados | TC-046 | `validarCompra` (encadenado: VerificacionEdad + LimiteBoletos + PoliticaEventoBase) | Comprador de 20 años, cantidad=2, límite=3 | `true` tras pasar las 3 capas de validación | [Típico] Verificar la composición correcta de decoradores encadenados |
| PoliticaEvento | TC-047 | `aplicarPoliticaDevolucion` | Evento con 3 boletos: 1 `VENDIDO`, 1 `RESERVADO`, 1 `DISPONIBLE` | Retorna lista con los 2 boletos `VENDIDO`/`RESERVADO` | [Típico] Verificar que la política identifica correctamente los boletos afectados |
| PoliticaEvento | TC-048 | `aplicarPoliticaDevolucion` | Evento sin boletos (lista vacía) | Retorna una lista vacía, sin excepción | [Límite] Verificar el comportamiento ante un evento sin boletos |

### 4.6 Servicios (Canales)

| Clase | ID | Método a probar | Datos de entrada | Salida esperada | Propósito de la prueba |
|---|---|---|---|---|---|
| CanalEmail | TC-049 | `enviar` | `mensaje="Su compra fue confirmada"` | `true` | [Típico] Verificar el envío exitoso por correo |
| CanalEmail | TC-050 | `verificarDisponibilidad` | — | `true` (valor fijo) | [Típico] Verificar la disponibilidad del canal |
| CanalSMS | TC-051 | `enviar` | `mensaje=""` (cadena vacía) | `true`, sin validar contenido vacío | [Límite] Detectar la falta de validación de mensajes vacíos |
| CanalSMS | TC-052 | `enviar` | `mensaje = null` | `true`, imprime "...null" sin excepción | [Límite] Verificar el comportamiento ante un mensaje nulo |

### 4.7 Modelo (POJOs)

| Clase | ID | Método a probar | Datos de entrada | Salida esperada | Propósito de la prueba |
|---|---|---|---|---|---|
| Evento | TC-053 | `agregarBoleto` / `getBoletos` | `new BoletoGeneral()` | El tamaño de la lista pasa de 0 a 1 | [Típico] Verificar la correcta adición de boletos |
| Evento | TC-054 | `setEstado` / `getEstado` | `"CANCELADO"` | `getEstado()` retorna `"CANCELADO"` | [Típico] Verificar la mutación de estado del evento |
| Evento | TC-055 | `setBoletosVendidos` / `setMontoRecaudado` + getters | `boletosVendidos=50`, `montoRecaudado=5000.0` | Los getters retornan esos valores exactos | [Típico] Verificar la correcta inicialización de datos para pruebas |
| Incidente | TC-056 | `setDescripcion` / `getDescripcion` | `"Reembolso no procesado"` | Se retorna el mismo valor | [Típico] Verificar la correcta asignación de la descripción |
| Incidente | TC-057 | `setEstado` / `getEstado` | `"RESUELTO"` | `getEstado()` retorna `"RESUELTO"` | [Típico] Verificar la asignación del estado del incidente |
| Comprador | TC-058 | `setEdad` / `setEsSocio` | `edad=30`, `esSocio=true` | Los getters reflejan los valores | [Típico] Verificar la correcta gestión del estado del comprador |
| Comprador | TC-059 | `setLimiteCompra` / `getLimiteCompra` | `limiteCompra=5` | `getLimiteCompra()` retorna `5` | [Típico] Verificar la asignación del límite de compra |
| Comprador | TC-060 | `setEdad` | `edad=-5` (edad negativa) | El valor se acepta sin validar el rango | [Error] Detectar ausencia de validación de edades inválidas |
| Pago | TC-061 | Constructor + getters | `id="PAY-001"`, `monto=99.9`, `estado="COMPLETADO"` | Los 3 getters retornan exactamente lo asignado | [Típico] Verificar la correcta inicialización del objeto Pago |
| Reserva | TC-062 | Constructor + `getComprador` | `new Reserva(id, comprador, listaBoletos, fecha)` | `getComprador()` retorna el comprador asignado | [Típico] Verificar la correcta inicialización de la reserva |
| Reserva | TC-063 | Constructor vacío + `getComprador` | — | `getComprador()` retorna `null` | [Límite] Verificar el estado por defecto de una reserva sin datos |
| Organizador | TC-064 | `setNombreEmpresa` / `getNombreEmpresa` | `"EventosGYE S.A."` | Se retorna el valor asignado | [Típico] Verificar la correcta asignación del nombre de la empresa |
| NotificacionMensaje | TC-065 | `setContenido` / `getContenido` | `"Su reserva expiró"` | Se retorna el valor asignado | [Típico] Verificar la asignación del contenido del mensaje |
| NotificacionMensaje | TC-066 | `setAsunto` / `getAsunto` | `"Recordatorio de evento"` | Se retorna el valor asignado | [Típico] Verificar la asignación del asunto |
