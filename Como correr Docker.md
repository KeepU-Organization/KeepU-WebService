# 🚀 Cómo correr el proyecto con Docker

## ✅ Requisitos previos

Asegúrate de tener instalados los siguientes programas en tu máquina:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

Puedes verificar que estén correctamente instalados ejecutando:

```bash
docker --version  
docker-compose --version
```

---

## 🛠️ Pasos para ejecutar el proyecto

### 1. Clona el repositorio (si no lo has hecho)

git clone https://github.com/KeepU-Organization/KeepU-WebService  
cd KeepU-WebService

### 2. Construir y levantar los contenedores

Ejecuta el siguiente comando en la raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`):

```bash
docker-compose up --build
```

Este comando:
- 🏗️ Construye las imágenes de Docker necesarias para el proyecto.
- 🐳 Levanta los contenedores definidos en el archivo `docker-compose.yml`.
- 🚀 Inicia los servicios de la aplicación.

---

## 🌐 Acceder a la aplicación

Una vez que los contenedores estén corriendo, puedes acceder a la aplicación desde tu navegador web:

http://localhost:8080

> **Nota:** Reemplaza `PUERTO` con el puerto que hayas configurado en el `docker-compose.yml` (por ejemplo, `3000`, `8080`, etc.).

---

## 🧪 Verificar que todo funciona

Puedes verificar que los servicios estén corriendo correctamente con:

```bash
docker ps
```

Y ver los logs en tiempo real con:

```bash
docker-compose logs -f
```

---

## 🧹 Detener los contenedores

Para detener los contenedores presiona `Ctrl + C` en la terminal donde ejecutaste `docker-compose up`.

También puedes detenerlos con:

```bash
docker-compose down
```

Este comando:
- 🔻 Detiene y elimina todos los contenedores.
- 🧼 Elimina la red creada por `docker-compose`.
- ❌ No elimina las imágenes ni los volúmenes (puedes usar `--volumes` si deseas hacerlo).

---

## 🐞 Tips para solucionar errores comunes

- Asegúrate de que el puerto que estás usando no esté ocupado por otro proceso.
- Si modificaste el `Dockerfile` o `docker-compose.yml`, usa `--build` para reconstruir.
- Usa `docker-compose down -v` para eliminar también los volúmenes en caso de errores con la base de datos.


