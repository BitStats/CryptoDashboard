CryptoDashboard
==============

El CryptoDashboard de **BitStats** es un tablero que sirve para dar seguimiento a las criptomonedas de forma gráfica y en tiempo real.

Implementación
========
Se utilizan varias dependencias para aprovechar el API de https://binance.com/, las más importantes son:
- **Vaadin 8**: Un framework que sirve para toda la parte del *Frontend*. Permite diseñar GUI en Java para Web usando GTW de Google (https://vaadin.com).
- **OKHTTP3**: Es una librería que sirve para hacer las llamadas via REST por el protocolo HTTP/S, usada en el *Backend* para obtener la información desde el servidor de Binance.
- **dGraph**: Es una librería que funciona de adaptador entre jqPlot (http://www.jqplot.com) y los Components de Vaadin, para hacer gráficas del *Frontend*.
- **JSON**: Es la librería que nos permite leer el JSON que regresa el API de Binance y manipular la información dentro del *Backend*.

Correr el proyecto
========
Para compilar el proyecto completo, ejecute `mvn install`

Para correr la aplicación, ejecute `mvn jetty:run` y abra en el explorador: http://localhost:8080/ .

Para poducir un WAR para hacer el deploy:
- cambie **productionMode** a *true* en la configuración de la clase **servlet**, que se encuentra dentro de la clase MainUI)
- Ejecute `mvn clean package` para compilar y crear el WAR
- Para correrlo sobre el servidor de Jetty use:  `mvn jetty:run-war`
