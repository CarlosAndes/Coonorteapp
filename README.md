#  CoonorteApp – Aplicación Móvil del Sistema de Transporte

Este repositorio contiene el **código fuente oficial** de la aplicación móvil **CoonorteApp**, desarrollada como parte del proyecto integral del SENA para la evidencia:

**GA8-220501096-AA2-EV02 – APK (Desarrollar módulos móvil según requerimientos del proyecto)**  
Programa: *Tecnología en Análisis y Desarrollo de Software*  
Aprendiz: **Carlos Mario Osorio Taborda**  
Ficha: 2977345

La aplicación representa la versión móvil del sistema Coonorte, permitiendo a los usuarios acceder a funciones clave del transporte, como autenticación, gestión de quejas, acceso a encomiendas y módulo de ventas (versión inicial estructural).

---

##  Características principales

La app incluye los módulos fundamentales definidos por los requerimientos del proyecto:

###  **1. Autenticación (Login)**
- Validación de credenciales.
- Manejo de errores.
- Navegación segura al menú principal.

###  **2. Registro de usuarios**
- Formulario para nuevos usuarios.
- Validación de campos vacíos.
- Retroalimentación visual.

###  **3. Menú principal**
Acceso directo a:

- Módulo de Quejas  
- Módulo de Encomiendas  
- Módulo de Ventas  
- Perfil de Usuario (extensible)  

###  **4. Módulo de Quejas**
- Formulario con cédula, nombre, correo y mensaje.  
- Validación completa del formulario.  
- Preparado para integración con backend.

###  **5. Encomiendas (modo prototipo)**
- Pantalla inicial preparada para mostrar envíos.

###  **6. Ventas (modo prototipo)**
- Listado base de compras del usuario.

---

##  Tecnologías utilizadas

La aplicación fue desarrollada con herramientas modernas del ecosistema Android:

- **Android Studio Ladybug (2024.1.1)**
- **Kotlin**
- **Jetpack Compose**
- **Navigation Compose**
- **Gradle**
- **Material Design 3**

---

##  Arquitectura del proyecto

El proyecto sigue una arquitectura modular y escalable basada en capas:

###  **Capa de Presentación (UI)**
Construida completamente en **Jetpack Compose**, utilizando:
- Components reutilizables
- Scaffold y Material 3
- Manejo de estado con `remember`
- Navegación declarativa

###  **Capa de Lógica**
Incluye:
- Validaciones de datos
- Control de pantallas
- Preparación para ViewModels (futuros)

###  **Capa de Datos**
- Modelos (`User`, `Queja`, etc.)
- Espacio para integrar API REST
- Preparada para Retrofit o Fetch

---

##  Estructura de paquetes

