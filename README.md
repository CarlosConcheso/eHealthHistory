# eHealthHistory

Aplicación de salud destinada al ambito futbolistico. Muy útil si la implementación, junto con su relleno correspondiente de datos en Firebase, se realizar de equipos en ambitos modestos.

![resumenApp](https://user-images.githubusercontent.com/35835151/173080418-abe949f7-09b7-4af0-971c-4479bc558676.jpg)

Esta aplicación maneja diferentes tecnologías. 
Para el almacenamiento de datos se utiliza CloudFirestore, perteneciente a Firebase de Google, y para generar diferentes archivos de texto con infromación sobre los cambios en los datos se utilizará IPFS.

<details><summary>CloudFirestore (Firebase)</summary>

<p>

Cloud Firestore es un servicio de almacenamiento de datos derivado de Google Cloud Platform, es una base de datos NoSQL. 
Cloud Firestore se organiza en forma de documentos agrupados en colecciones, dentro de estos, se pueden recoger campos de diversos tipos: cadenas de texto, números, puntos geográficos, referencias, arrays, booleanos, marcas de tiempo e incluso objetos propios. 

</p>

</details>

<details><summary>IPFS</summary>

<p>
IPFS (InterPlanetary File System), Sistema de archivos interplanetario, es un sistema de archivos distribuidos punto a punto, direccionable por contenido para guardar y compartir hipermedia en un sistema de archivos distribuido. 

IPFS aprovechó el protocolo de cadena de bloques de Bitcoin y la infraestructura de red para almacenar datos inalterables, eliminar archivos y obtener información para acceder a los nodos de almacenamiento para buscar archivos en la red; es decir, utiliza tecnología Blockchain para realizar sus diferentes funciones de lectura, almacenamiento y búsqueda.
Los archivos se identifican por sus valores hash y se distribuyen usando un protocolo basado en BitTorrent. Cloud Firestore es un servicio de almacenamiento de datos derivado de Google Cloud Platform, es una base de datos NoSQL. 

*Repositorio IPFS en GitHub: https://github.com/ipfs/ipfs *
</p>

</details>


Diagrama de navegación:

```mermaid
graph TD;
    PantallaPrincipal-->LogIn;
    
    LogIn-->Futbolista;
    LogIn-->Médico;
    LogIn-->Club;
    
    Futbolista-->DatosDeContacto;
    Futbolista-->HistorialCuidadosMédicos;
    Futbolista-->EquiposMédicosDeConfianza;
    EquiposMédicosDeConfianza-->Futbolista;
    
    Médico-->AñadirCuidadoMédico;
    AñadirCuidadoMédico-->Médico;
    
    Club-->AñadirNuevoEquipoMédico;
    AñadirNuevoEquipoMédico-->Club;

```
