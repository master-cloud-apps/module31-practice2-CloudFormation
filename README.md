# Práctica 2. CloudFormation
##Enunciado
**Versión básica (8 puntos)**

Se desea automatizar el despliegue de la solución de la **Práctica 1 - Despliegue de una
aplicación Spring** utilizando AWS CloudFormation, creando una plantilla para ello.
Además, se grabará un video comprobando que la app es accesible y se puede crear un
nuevo evento.

Deberá parametrizarse la plantilla de manera que se puedan definir los siguientes
parámetros:
* El ID de la AMI que se utilizará para la instancia EC2
    * Por defecto: Ubuntu 18.04 en la región us-east-1
* La contraseña que utilizará la base de datos
* Nombre del bucket que utilizará la aplicación
* URL desde dónde se descargará la aplicación, por defecto
    * Por defecto: https://s3.amazonaws.com/practica-2.cloud.michel/app.jar
    
Consideraciones:
* Habrá 2 recursos principales, una instancia EC2 con la aplicación y una instancia
RDS para la base de datos
* Los recursos deben siempre mantenerse dentro de la capa gratuita de AWS
* La plantilla debe ser autocontenida, debe incluir la creación de todos los recursos
necesarios para que funcione el despliegue (no se pueden asumir que existen):
Security Groups, Roles, etc.
* La app deberá ser desplegada de manera automática, ofreciendo un endpoint en la
sección de Outputs.

**Versión avanzada (2 puntos)**
* Utilizar Spring Cloud para la conexión a la base de datos.
* Cambia la configuración necesaria sobre el bucket para que:
    * El bucket deberá ser creado por CloudFormation
    * Limitar que la aplicación solo pueda operar sobre el bucket proporcionado

**IMPORTANTE:** Una vez grabado el video de que la aplicación funciona, **se detendrá y
borrará el stack** para evitar costes adicionales.

## Formato de entrega
La práctica se entregará teniendo en cuenta los siguientes aspectos:
* La práctica se entregará como un fichero .zip del fichero YAML junto con el video
que muestre que se ha desplegado la aplicación. Se deberá incluir también el
proyecto Maven de la aplicación si se ha utilizado Spring Cloud. El nombre del
fichero .zip será el correo URJC del alumno (sin @alumnos.urjc.es).

Las prácticas se podrán realizar de forma individual o por parejas. En caso de que la
práctica se haga por parejas:
* Sólo será entregada por uno de los alumnos
* El nombre del fichero .zip contendrá el correo de ambos alumnos separado por
guión. Por ejemplo p.perezf2020-z.gonzalez2020.zip

## Resolución de la práctica
Se ha implementado la versión avanzada de la práctica. Para ello se han llevado a cabo los siguientes pasos partiendo 
de la solución de la práctica anterior:
1. Se han añadidos las dependencias necesarias en el pom.xml para usar spring cloud:
    ```xml
   <properties>
   		<spring-cloud-version>Hoxton.SR10</spring-cloud-version>
   	</properties>
   
    <!-- Spring Cloud dependencies -->
   <dependencies> 
       <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-aws-autoconfigure</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-aws-context</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-aws-jdbc</artifactId>
        </dependency>
   </dependencies>
   
   <dependencyManagement>
   		<dependencies>
   			<dependency>
   				<groupId>org.springframework.cloud</groupId>
   				<artifactId>spring-cloud-dependencies</artifactId>
   				<version>${spring-cloud-version}</version>
   				<type>pom</type>
   				<scope>import</scope>
   			</dependency>
   		</dependencies>
   	</dependencyManagement>
    ```
2. Se ha añadido una nueva clase de configuración 
[SpringCloudConfig](./src/main/java/es/codeurjc/mca/practica_1_cloud_ordinaria_2021/SpringCloudConfig.java) basada en el
 perfil _production_ para detectar la instancia de RDS:
    ```java
    @Profile("production")
    @Configuration
    @EnableRdsInstance(
            dbInstanceIdentifier = "${cloud.aws.rds.dbInstanceIdentifier}",
            password = "${cloud.aws.rds.p2rdsinstance.password}")
    public class SpringCloudConfig {
    }
    ```
   **NOTA:** Nótese que el identificador de la instancia se define de forma fija en la creación de la instancia del RDS 
   en el yaml del stack y que su nombre es _p2rdsinstance_.
3. Se ha modificado el fichero [application-production.properties](src/main/resources/application-production.properties) 
para eliminar propiedades innnecesarias y añadir nuevas, quedando de la siguiente forma:
    ```properties
    # MySQL
    spring.datasource.driverClassName=com.mysql.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    
    # FOR AWS FILES
    amazon.s3.bucket-name=${BUCKET_NAME}
    amazon.s3.region=${REGION}
    
    # Spring cloud config
    cloud.aws.stack.auto=false
    cloud.aws.rds.dbInstanceIdentifier=p2rdsinstance
    cloud.aws.rds.p2rdsinstance.password=${RDS_PASS}
    ```
   **NOTA:** Se ha eliminado la propiedad _amazon.s3.endpoint_ debido a que se ha modificado el servicio 
   [S3ImageService](src/main/java/es/codeurjc/mca/practica_1_cloud_ordinaria_2021/image/S3ImageService.java) para que 
   use el método proporcionado por Amazon _getUrl(bucketName, fileName)_ a la hora de devolver la URL de la imagen del
   evento en lugar de usar el endpoint indicado en las properties.
   
4. Con estos cambios se ha generado el jar para la práctica, que se ha liberado como una release pública de github, y 
cuya URL es la que se usa como valor por defecto en el yaml del stack de CloudFormation:
https://github.com/master-cloud-apps/module31-practice2-CloudFormation/releases/download/v1.0/app.jar

5. El template de CloudFormation se encuentra en el archivo [stack/practice2.yaml](stack/practice2.yaml) 
  
## Autor

👤 **Álvaro Martín Martín**

* Github: [@amartinm82](https://github.com/amartinm82)