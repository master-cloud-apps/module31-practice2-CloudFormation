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

  
## Autor

👤 **Álvaro Martín Martín**

* Github: [@amartinm82](https://github.com/amartinm82)