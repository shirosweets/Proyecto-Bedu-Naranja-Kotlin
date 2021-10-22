<div align="center">

<img src="assets/android-kotlin.png" height="90">

## Team Primer

---

![Kotlin](https://img.shields.io/badge/-Kotlin-05122A?style=flat&logo=kotlin)
![Android Studio](https://img.shields.io/badge/-Android_Studio-05122A?style=flat&logo=android-studio)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![GitHub version](https://badge.fury.io/gh/shirosweets%2FProyecto-Bedu-Naranja-Kotlin.svg)](https://badge.fury.io/gh/shirosweets%2FProyecto-Bedu-Naranja-Kotlin)

</div>

# Proyecto Bedu Naranja X - Kotlin 2021

> <div style="text-align: justify;">

<img src="assets/android.gif" align="right" height="90" hspace="3">

<p>
Proyecto del curso de Kotlin de Naranja X proporcionado por BEDU. El desarrollo de este proyecto transcurre desde el módulo 2 hasta el módulo 3 implementando los conceptos aprendidos durante el curso. Se enfocó en la experiencia amena con el usuario y la personalización de la app.
</p>

&nbsp;

---

**[Documentación](documents/root.md)** | **[Recursos de BEDU y Referencias](documents/resources.md)**

---

# Tabla de contenidos

- [Proyecto Bedu Naranja X - Kotlin 2021](#proyecto-bedu-naranja-x---kotlin-2021)
- [Tabla de contenidos](#tabla-de-contenidos)
- [Integrantes](#integrantes)
- [Forma de trabajo](#forma-de-trabajo)
- [Videos](#videos)
  - [Demo N°0](#demo-n0)
  - [Puntos desarrollados en la demo N°0](#puntos-desarrollados-en-la-demo-n0)
  - [Demo N°1](#demo-n1)
  - [Puntos desarrollados en la demo N°1](#puntos-desarrollados-en-la-demo-n1)

# Integrantes

* López, Ricardo [@RicardoLopez9908](https://github.com/RicardoLopez9908)
* Domínguez, Agustín Marcelo [@AgustinMDominguez](https://github.com/AgustinMDominguez)
* Vispo, Valentina Solange [@shirosweets](https://github.com/shirosweets)

# Forma de trabajo
Principalmente el método de trabajo que se utilizó fue por medio del pair-programming a través de la plataforma de Discord para la comunicación y compartir código en pantalla.

Como el IDE de Android Studio no permite tener una herramienta similar a Live Share de Visual Studio Code los commits no tienen como co-autor ya que nos olvidamos de realizarlo en todos los commits.

# Videos

## Demo N°0

> `Hacer click en la imagen para reproducirlo`

[![Bedu Project - Team Primer Demo #0](https://img.youtube.com/vi/HByUtwHdyWo/0.jpg)](https://www.youtube.com/watch?v=HByUtwHdyWo)

## Puntos desarrollados en la demo N°0

* Uso de Material Design para los componentes
* Nombre de las variables autodocumentadas
* Librerías externas usadas:
  * Picasso
    > para el fetcheo de imágenes a través de url
  * SafeArgs
    > del recyclerView al Detail
* Transiciones entre los fragmentos y views
  * Transiciones de pop
  * Implementadas en el Navigation
* Toggle Password
* Types para los inputs de usuario
  > password | email | phone | text
* ScrollView en Register y Login para hacerlo responsivo en modo Portrait
* RecyclerView para los Productos
  > presentados como CardView
* Build Variants
  * Por tema (dark/light)
  * Por release/debug
* Modularización de código
* Implementación de tipos segura (no "!!")
* Implementación de estilos por categoría

## Demo N°1

> `Hacer click en la imagen para reproducirlo`

[![Bedu Project - Team Primer Demo #1 Parte I](https://img.youtube.com/vi/I84r8EGBX0c/0.jpg)](https://youtu.be/I84r8EGBX0c)
[![Bedu Project - Team Primer Demo #1 Parte II](https://img.youtube.com/vi/4O2uFQCFui4/0.jpg)](https://youtu.be/4O2uFQCFui4)

## Puntos desarrollados en la demo N°1

* Snackbar al no ingresar los datos en el Login y el Register
* Marcado de errores individuales al iniciar sesión o registrarse
  > campo incompleto

  > contraseña menor a 8 caracteres
* Consumo de API a partir de metodos POST y GET, para verificar el inicio de sesión (usando Corrutinas y Retrofit 2)
* Manejo de SharedPreferences para acceso rapido de usuario registrado
* Consumo de API a partir de metodo GET, para la carga de productos en pantalla de inicio (usando Corrutinas y Retrofit 2)
* Actualizacion real de la ubicación a partir de GPS (se almacenan hasta 3 ubicaciones con SharedPreferences)
* Posibilidad de cerrar sesión
* Icono personalizado para la app
* Lista de productos almacenados y modificados en base de datos (usando Realm)
* Añadir productos al carrito
* Realizar pagos
* Notificación con redirección al comprar un producto
* Test Unitarios en JVM sin dependencia del framework de Android
* Test Unitarios con uso del framework de Android (no instrumentados)
* Cambio de idiomas según preferencias del usuario
* Switch para cambio de temas, disponible para usuarios registrados

---

**[Documentación](documents/root.md)** | **[Recursos de BEDU y Referencias](documents/resources.md)**

*[Decisiones de implementación](documents/implementation.md)* | *[Decisiones de diseño](documents/design.md)* | *[Extras](documents/extras.md)*