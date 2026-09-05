```
# LoginCrudFirebase050926

Aplicación móvil desarrollada en **Kotlin** para Android que integra autenticación de usuarios (**Firebase Authentication**) y un sistema **CRUD** (Crear, Leer, Actualizar, Eliminar) utilizando **Firebase Realtime Database**. 

Los datos están aislados por cada usuario autenticado bajo la ruta estricta `users/{uid}/personas`.

---

## 🚀 Características

* **Autenticación:**
  * Registro de nuevos usuarios con correo y contraseña.
  * Inicio de sesión (*Login*) con persistencia de sesión.
  * Cierre de sesión desde el menú de opciones (*Logout*).
* **Gestión de Datos (CRUD):**
  * **Crear:** Registro de personas (Nombre y DUI) asignadas al UID del usuario.
  * **Leer:** Sincronización en tiempo real mediante `ValueEventListener` y listado dinámico.
  * **Actualizar:** Modificación de registros existentes seleccionando un elemento de la lista.
  * **Eliminar:** Confirmación mediante cuadro de diálogo (`AlertDialog`) con pulsación prolongada (*Long Click*).
* **Navegación:**
  * Integración de `Toolbar` y menú desplegable de opciones.
  * Vistas *dummy* para **Opción 1** y **Opción 2**.

---

## 🛠️ Tecnologías y Dependencias

* **Lenguaje:** Kotlin (2.1.10)
* **Plataforma:** Android (AGP 8.13.2)
* **Backend como Servicio (BaaS):**
  * `firebase-auth` (23.2.0)
  * `firebase-database` (22.0.1)
* **UI:** Material Components, `AppCompatToolbar`, `ListView`, `FloatingActionButton`.

---

## 📁 Estructura del Proyecto

```text
sv.edu.udb.crudfirebase050926/
├── datos/
│   └── Persona.kt               # Modelo de datos
├── AdaptadorPersona.kt          # Adaptador personalizado para ListView
├── AddPersonaActivity.kt        # Formulario para agregar/editar registros
├── LoginActivity.kt             # Pantalla de inicio de sesión
├── MainActivity.kt              # Pantalla principal (CRUD y lista)
├── Opcion1Activity.kt           # Vista de prueba para Opción 1
├── Opcion2Activity.kt           # Vista de prueba para Opción 2
└── RegisterActivity.kt          # Pantalla de registro
```

---

## 🔒 Reglas de Seguridad en Firebase

Para el correcto funcionamiento de las operaciones de lectura y escritura de los usuarios autenticados, se aplican las siguientes reglas en la consola de Firebase Realtime Database:

```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

---

## 🗄️ Estructura de la Base de Datos (JSON)

Los datos se guardan aislando cada cuenta mediante su identificador único de usuario (`UID`):

```json
{
  "users": {
    "UID_DEL_USUARIO": {
      "email": "usuario@ejemplo.com",
      "personas": {
        "-P0nWEzYvK2MyTooGxLw": {
          "dui": "123456789",
          "nombre": "Alexander"
        }
      }
    }
  }
}
```

---

## 📋 Requisitos de Ejecución

1. Clonar el repositorio.
2. Añadir el archivo `google-services.json` generado desde la consola de Firebase en el directorio `app/`.
3. Sincronizar el proyecto con los archivos de Gradle (`Sync Project with Gradle Files`).
4. Compilar y ejecutar en un emulador o dispositivo físico Android.

```
