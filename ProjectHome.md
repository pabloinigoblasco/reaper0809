Authors: Pablo Iñigo Blasco & Rosa María Burrueco Jiménez (Universidad de Sevilla)
Master thesis work.

# Tool Overview #
This tool proposes a generic and extensible model to download huge collections of web pages that belong to the Deep Web called **Wreap**. This tool is designed to fill automatically html forms to get thousands of samples of specific data of a web page. This is done through a set of criteria defined by the user. This criteria set is specified in a high abstraction requirements in a xml file. Thousands of Queries to html forms are generated and after this, the result is stored or if the result is a index result page (like parametrized search in Amazon or Barners&Noble ) it can iterate each detail webpage, download and store it. This tool also has some mechanism to avoid problems related with ajax, javascript and others so it can its download workflow to the singular design features of a specific website.

# Theoretical Background Overview #
Wrappers are systems which lets in a semi-automated way to extract structured information from web pages which were originally designed for human understanding, not machine understanding. Wrappers generators need collection (typically with a huge size) of web documents to learn how to extract the information in new samples. The problem is how to get that training set. Habitually the problem of massive webpage downloads of thousands of samples of a very specific information is not solved.

Crawlers provide a partial solution to this problem. However they cannot access to all the information on the Web due to html forms designed to be filled by humans. Some studies call this "The deep web". Besides, a typical crawler download all pages it finds so the set of web pages downloaded are not structured. The need of a new kind of tool appears, a tool that can overcome this problems. This is known as a reaper, and this project is a specific implementation of a reaper. It's called wreap (inspired in wget)

# Reaping Tools (Extended Seminar) #
By Rosa M. Burrueco, Pablo Íñigo
on Friday, January 22, 2010
at 05:00 PM
Rosa and Pablo report extensively on the reaping tools they have designed.  The presentation includes details about the user interface, the design of the tools, and several hand-on labs.

Presentation
http://www.tdg-seville.info//Download.ashx?id=142
Video
mms://streaming.tdg-seville.info/Seminars/semi-22-01-10.wmv


# Resumen (Spanish) #

Los wrappers son sistemas que permiten de una forma semi-automática extraer información estructurada a partir de páginas web que fueron originalmente pensadas tan sólo para personas. Los sistemas de generación de wrappers necesitan colecciones (generalmente extensas) de documentos para aprender a extraer información. El problema de la descarga masiva de páginas web a través de una consulta especializada no está completamente resuelto.

Los crawlers aportan una solución parcial a este problema, sin embargo no son capaces de acceder a toda la información existente en la Web, debido a la existencia de formularios pensados para ser rellenados por personas; además, un crawler típico descarga todas las páginas que encuentra, lo que puede dar lugar a problemas de escalabilidad debido al gran ancho de banda que esto puede suponer.

El presente trabajo propone un modelo genérico y extensible de obtención de colecciones de páginas de interés perteneciente al ámbito de la Deep Web. Se ha logrado un sistema automático que genera consultas a partir de criterios indicados por el usuario. Estas consultas son aplicadas a formularios, como resultado se obtienen las páginas de interés especificadas. Además, este sistema es capaz de iterar sobre páginas de índices, desde las que se puede acceder al detalle de los productos. De esta manera se cubren un mayor número de sitios web.


### Downloads ###

  * Latest Release (v0.9.11.12)
> http://reaper0809.googlecode.com/svn/trunk/reaper0809/Releases/wreap-0.9.11.12.rar

  * SlideShow / Presentación
> http://reaper0809.googlecode.com/svn/trunk/reaper0809/Memoria/Herramientas_Reaping_Sem(22-01-2010).ppt

  * Documentation / Documentación
> http://reaper0809.googlecode.com/svn/trunk/reaper0809/Memoria/Memoria_TFM.doc