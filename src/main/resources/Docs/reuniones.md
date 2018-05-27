# Agenda reuniones
## Reuniones
### 1. Reunión 1 (Organización inicial)

  - Fecha: 7/05/18

  - Resumen: En esta reunión se planeó lo que basicamente sería el proyecto, es decir un tablero que mostrara el flujo y tipo de cambio entre las criptomonedas más importantes, así como datos relevantes sobre ellas. También se decidieron los mecanismos que se implementarían para la visualización de estos datos, qué APIs y que herramientas.

  Estas fueron las dependencias que aprovecha el API de [binance](https://binance.com) para el correcto uso y visualización de los datos más importantes de las criptomonedas :
  
    * Vaadin 8: Un framework que sirve para toda la parte del Frontend. Permite diseñar GUI en Java para Web usando GTW de Google (https://vaadin.com).
    * OKHTTP3: Es una librería que sirve para hacer las llamadas via REST por el protocolo HTTP/S, usada en el Backend para obtener la información desde el servidor de Binance.
    * dGraph: Es una librería que funciona de adaptador entre jqPlot (http://www.jqplot.com) y los Components de Vaadin, para hacer gráficas del Frontend.
    * JSON: Es la librería que nos permite leer el JSON que regresa el API de Binance y manipular la información dentro del Backend.


  - A partir de esta reunión se realizaron 5 commits el día 9 de mayo, estos reflejaron lo acordado en la reunión pues se crearon los primeros repositorios, se hizo la organización de los directorios y se agregaron llamadas a las APIs en los archivos BackEnd.

### 2. Reunión 2 (Primera versión funcional)

  - Fecha: 10/05/18

  - Resumen: En esta reunión se acordó pulir la versión BackEnd para poder generar una primera versión funcional del tablero BitStats, es decir implementar un primer FrontEnd.

  - A partir de esta reunión se realizaron 3 commits que implementan la primera versión funcional del tablero, así como correcciones a errores de compilación.

### 3. Reunión 3 (Logs, javadoc y correcciones)

  - Fecha: 13/05/18

  - Resumen: En esta reunión se acordó la metodología que se llevaría en la documentación del proyecto es decir el DoD, esto incluyó las especificaciones de los logs y del JavaDoc, también se discutió sobre la actualización de los precios pues existía un error en el BackEnd en este rubro.

  A partir de esta reunión se realizaron 4 commits entre el 14 y el 15 de mayo, estos resolvieron los logs, el JavaDoc y la corrección en la actualización de los precios.

### 4. Reunión 4 (Comentarios doble barra, modificaciones menores)

  - Fecha: 16/05/18

  - Resumen: En esta reunión se dio seguimiento al DoD pero en los comentarios de doble barra dentro de las clases BackEnd y FrontEnd, también se acordó modificar el archivo ReadMe para dar una pequeña explicación de la implementación del proyecto, también se generaron algunas instrucciones que permiten al usuario compilar el proyecto.

  - A partir de esta reunión se realizaron 4 commits entre el 17 y el 18 de mayo, estos incluyeron la modificación al archivo ReadMe y la correcta implementación de los comentarios de doble barra.

### 5. Reunión 5 (Pruebas unitarias)

  - Fecha: 24/05/18

  - Resumen: Esta fue nuestra última reunión, en ella se discutieron las últimas modificaciones y funcionalidades necesarias para que nuestro proyecto cumpliera con los estándares planteados por el profesor, así como el correcto apego al DoD y a los estándares. Particularmente se discutió la forma en que las pruebas unitarias debían implementarse y para qué métodos. En las modificaciones y la funcionalidad basicamente se habló sobre las últimas pulidas al código, warnings y un último merge entre las branches que habían sido creadas en el repositorio.

  - A partir de esta reunión se hicieron 10 commits entre el 25 y el 27 de mayo. Estos incluyen las pruebas unitarias, las modificaciones e incluir este archivo como parte de la documentación del proyecto y del trabajo realizado.

## Extras

  * [Links a los commits](https://github.com/BitStats/CryptoDashboard/commits/master)
  * Definition of Done:
    - El programa permite a un usuario sin expertise en el uso de productos web ver el valor de compra y venta de BTC.
    - El programa permite ver el valor de varias otras monedas en dolares o BTC si es especificado.
    - Gráfica historica del valor de venta dela moneda seleccionada.
    - Gráfica historica del valor de compra de la moneda seleccionada.
    - Gráfica historica del valor de venta la moneda seleccionada contra bitcoins.
    - Gráfica historica del valor de venta la moneda seleccionada contra varias otras criptomonedas.
    - Por cada 100 lineas de código, 20 lineas de logging.
    - Tener todo el codigo al 100% el javadoc.
    - Por cada 100 lineas de código, 50 lineas de pruebas.
    - Pruebas unitarias al menos del 50%.
    - Que las pruebas unitarias cubran los casos corte.
    - Comentarios en codigo que no sean javadoc.
    - Apegarnos a las convenciones de código de Oracle para java.
