# Usar una imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado por Gradle
COPY build/libs/webAPI-0.0.1-SNAPSHOT.jar app.jar

# Copiar la carpeta uploads al contenedor
COPY uploads /uploads

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]