# Movies Now Para Android

Una app diseñada para buscar películas, guardar las mejores en favoritos y mucho más.


# Capas de la aplicacion

* Capa de presentación: HomeActivity.java - IntroActivity.java - MovieDetailsActivity.java - SettingsActivity.java - SettingsFragment.java - MovieAdapter.java - MovieTrailerAdapter.java
-La clase **HomeActivity.java** se encargar de visualizar el contenido de cada categoría por secciones verticales: Top Rated, Upcoming.
-La clase **IntroActivity.java** visualiza una interfaz de bienvenida para nuevos usuarios.
-La clase **MovieDetailsActivity.java** visualiza los detalles de una película o serie.
-La clase **SettingsActivity.java** vizualiza la interfaz de configuraciones de la aplicación.
-La clase **MovieAdapter.java** es la encargada de convertir objetos de películas en items visuales para el usuario.
-La clase **MovieTrailerAdapter.java** es la encargada de convertir objetos trailers en items visuales para el usuario.

* Capa de persistencia: MovieDbHelper.java - MovieDao.java
-La clase **MovieDbHelper.java** es la encargada de crear la base de datos local SQLite y esteblecer las conexiones.
-La clase **MovieDao.java** lleva todos los metodos necesarios para insertar, actualizar, obtener y borrar ese objeto de la base de datos.

* Capa de negocio: EndpointsApi.java - RetrofitInstance.java
-La clase **EndpointsApi.java** es la encargada de establecer la comunicación entre la API de **themoviedb** y la aplicación.
-La clase **RetrofitInstance.java** es una instancia del objeto **Retrofit** para asi proporcionar un punto de acceso global a ella. Además esta clase construye la base necesaria para iniciar los requets hacia la API de **themoviedb** y la serialización de los datos recuperados.

# Principio de responsabilidad única

* Son clases que tienen una sola responsabilidad, esto ayuda a que dicha clase no este sobrecargada de muchas responsabilidades y asi evitar un código dificil de mantener.

# Código limpio

* A simple vista podemos ver el propósito de dicho Código
* A la hora de integrar nuevos componentes o funcionalidades no se hace tan tedioso
* A la hora de hacer pruebas a el código se pueden indentificar errores dado el caso
* No es redundante
* Da satisfacción leer el código


## Librerias usadas

Esta aplicación usa varias Llibrerias necesarias para un buen rendimiento y aspecto visual de la misma.


*   Para el diseño:
    Appcompat
    Constraintlayout
    Google Material

*   Glide: Para descargar y guardar en memoria cache las imágenes que se desean mostrar en nuestras aplicaciones
    [Java](https://bumptech.github.io/glide/)

*   Retrofit: Hace que sea más fácil recuperar y cargar datos estructurados
    [Java](https://square.github.io/retrofit/)

*   DateTimeUtils:
    [Java]https://libraries.io/github/thunder413/DateTimeUtils)
