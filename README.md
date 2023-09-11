# Paquete Utilitario: ConexionesBD
**Descripción:** Permite manipular bases de datos relacionales de forma sencilla.
## Especificaciones
**Lenguaje:** Java

**Herramienta de construcción:** Maven

## Modo de uso
1. Clona el repositorio
2. Construye el paquete del proyecto
3. Ejecuta el siguiente código en el proyecto que lo utilizarás:
   ```
   mvn install:install-file -Dfile="path-to\conexionesbd\target\conexionesbd-1.0.jar"
   -DgroupId="com.snow.conexiones" -DartifactId=conexionesbd -Dversion="1.0"
   -Dpackaging=jar -DgeneratePom=true
   ```
   **NOTA:** Reemplaza `path-to` por la ubicación donde guardaste el proyecto
4. Agrega la dependencia a tu archivo `pom.xml`:
   ```
   <dependency>
     <groupId>com.snow.conexiones</groupId>
     <artifactId>conexionesbd</artifactId>
     <version>1.0</version>
   </dependency>
   ```
5. Además, agrega la dependencia a la base de datos que vayas a utilizar
   - MySQL
     ```
     <dependency>
       <groupId>com.mysql</groupId>
       <artifactId>mysql-connector-j</artifactId>
       <version>8.1.0</version>
     </dependency>
     ```
   - SQL Server
     ```
     <dependency>
       <groupId>com.microsoft.sqlserver</groupId>
       <artifactId>mssql-jdbc</artifactId>
       <version>6.1.0.jre8</version>
     </dependency>
     ```
   - PostgreSQL
     ```
     <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <version>42.6.0</version>
     </dependency>
     ```
6. ¡Ya puedes comenzar a utilizar el paquete conexionesbd!
7. No olvides leer la documentación

## Historial de versiones
### 1.0
- Bases de datos disponibles:
  - MySQL
  - SQL Server
  - PostgreSQL
- Incluye procesos de Data Query Language (DQL) básicos:
  - Seleccionar tablas y columnas (SELECT)
  - Filtrado de datos (WHERE)
