wreap es un reaper creado en la Universidad de Sevilla 2009.

== INTRODUCCIÓN ==

Se trata de una herramienta de línea de comandos cuyo objetivo es descargar grandes
volúmenes de páginas web ocultas tras formularios. Al ser una herramienta de comandos
puede utilizarse en conjunción con el planificador del sistema.

La herramienta necesita dos modelos suministrados en forma de fichero xml:
	- Modelo del formulario: fichero donde se especifican los campos del formulario, los 
	tipos de resultados y otros aspectos.
	- Modelo de consulta: fichero donde se especifica en rango de valores que tomará 
	cada campo, importaciones de scripts y definición de eventos (para la extensión 
	del proceso mediante JavaScript)

Se trata de un prototipo de la especificación propuesta en el estudio:
"Herramientas de Reaping". Unviersidad de Sevilla 2009.
Rosa María Burrueco y Pablo Iñigo Blasco

Este estudio puede considerarse la documentación oficial de la herramienta. Está centrada
en aspectos teóricos y muestra gran cantidad de ejemplos de uso y la razón de existencia
de todas las características existentes.

Se debe recordar que es un prototipo cuyo objetivo es validar y verificar las teorías propuestas,
no obstante implementa prácticamente la totalidad de las especificaciones dadas y ha sido
probado y testado en diversos sitios web.

== CAPACIDADES PRINCIPALES ==

- Rellenado de formularios mediante generación de consultas automático a partir del cartesiano 
de los rangos de valores definidos para cada	campo del formulario.

- Identificación de distintos tipos de resultados para cada consulta mediante la aplicación de 
expresiones regulares al contenido del resultado o a la url.

- Capacidad de aplicar distintas acciones según el tipo de resultado: Almacenamiento, Descarte 
o Clasificación de los resultados o iteración de hubs.

- Para resultados de tipo HUB permite la iteración y almacenamiento de sus productos, así como
hubs con paginadores

- Refinamiento de las consultas generadas con producto cartesiano a partir de la mecanismos
para definir dependencia de valores entre campos.

- Calculo dinámico de  valores en el lenguaje de consultas para ser calculados en tiempo de ejecución 
mediante el lenguaje javascript: cálculo de fechas relativas a la ejecución, valores relativos al estado de 
la página o el instante en que han sido ejecutados.

- Eventos: un mecanismo que permite incrustar fragmentos de código personalizado. Estos permiten
soportar mayor número de páginas web ya que permiten modifica el flujo de navegación o incluso
el estado de una página. Los eventos también permiten sincronización con AJAX mediante condiciones
de sincronizado en javascript.

Todas estas características pueden ser estudiadas y analizadas en detalle en la documentación oficial de la
herramienta. Desde los capítulos 3 al 6

== USO ==

La herramienta es un archivo ejecutable de java en formato JAR. Para su ejecución simplemente hay 
que invocarlo pasando como parámetros los dos archivos que contienen los modelos de formulario
 y consulta como parámetros. El ejecutable está acompañado de un conjunto de ejemplos en la carpeta Examples. 

Ejemplo de uso:

prompt> ./wreap Examples/iberia/Iberia-formModel.xml Examples/Iberia/Iberia-queryModel.xml

Como puede observarse, la ruta de los ficheros es relativa al directorio de ejecución. Sin
embargo también pueden se absoluta.

Exsiten varios parámetros configurables que es recomendable analizar, pueden listarse
ejecutando la ayuda de la herramienta:

prompt> ./wreap  --help 

Un ejemplo de uso de la herramienta con configuración de parámetros es:
./wreap Examples/iberia/Iberia-formModel.xml Examples/Iberia/Iberia-queryModel.xml --port 4445 --out ./myResults

== ALMACENAMIENTO DE RESULTADOS ==

Los resultados se almacenan por defecto en la carpeta Results del directorio de ejecución aunque esto puede ser 
parametrizado.
Dentro se almacenan los resultados de las consultas de la siguiente manera: 
	${resultDirectory}/${formModelName}/${DateTime}/${queryIndex}.html

En el mismo directorio se almacena un log con la información del proceso de reaping:
	${resultDirectory}/${formModelName}/${DateTime}/ResultLog.txt

En el caso de tener resultados de tipo hub, los distintos detalles de productos se almacenarán de la siguiente manera:
	${resultDirectory}/${formModelName}/${DateTime}/${queryIndex}-${productIndex}.html

Esto puede estudiarse mas en detalle en el apartado "Persistencia" de la documentación oficial.

== MAPPING XML DE LOS MODELOS DE FORMULARIO Y CONSULTAS ==

La forma mas sencilla y rápida de aprender la sintáxis de los archivos xml es mirando los 
ejemplos. Para un conocimiento mas avanzado se debe estudiar los modelos de formulario 
y consultas en la documentación oficial. Finalmente una vez
puede verse el mapeo específico clases<->xml en los recursos incrustados en el jar:
- formmodel-mapping.xml
- querymodel-mapping.xml

Estos archivos pueden modificarse y crear un otro mapping específico siguiendo los convenios
de (Castor.XML). 

== PROBLEMAS TIPICOS SUPERADOS EN LOS EJEMPLOS MEDIANTE EVENTOS ==

- Paginas intermedias (como carteles de carga y publicidad)
- ComboBoxes que se rellenan con requests asincronos y rompen el flujo de navegación actual
- Hubs con productos cuyos enlaces son de tipo target="_blank"
- Campos abstractos de fechas distribuidos en varios controles html
- Asignación de valores de comboboxes donde es mas recomendable utilizar el name en lugar del contenido
- Checkeo y establecimento del idioma del sitio web haciendo


== LIMITACIONES ==

A pesar de ser una herramienta de línea de comandos necesita ser ejecutado en un sistema operativo gráfico ya que se basa en Selenium, 
un FormFiller que ejecuta una instancia de un navegador (Firefox, Opera, etc.), esto puede ser problemático en conjunción con el planificador
en sistemas linux se debe exportar la variable DISPLAY






