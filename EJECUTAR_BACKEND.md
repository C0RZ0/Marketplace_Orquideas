# 🚀 Ejecutar Backend desde ProyectoOrquideasApplication.java

## 📍 Archivo Principal
```
/backend/src/main/java/com/orquicombeima/proyecto_orquideas/ProyectoOrquideasApplication.java
```

---

## 🎯 OPCIÓN 1: Ejecutar desde VS Code (RECOMENDADO)

### Paso 1: Abre el proyecto backend
```bash
cd /home/user/Proyectos/fork/Marketplace_Orquideas/backend
code .
```

### Paso 2: Abre el archivo
Navega a: `src/main/java/com/orquicombeima/proyecto_orquideas/ProyectoOrquideasApplication.java`

### Paso 3: Click en "Run" (triángulo verde)
O presiona: **`Ctrl+F5`**

### Resultado:
- El backend se ejecuta EN LA TERMINAL DE VS CODE
- Ves todos los logs en tiempo real
- Presiona `Ctrl+C` para detener

---

## 🎯 OPCIÓN 2: Ejecutar desde Terminal (SIN background)

### Método A: Con Maven (más fácil)
```bash
cd /home/user/Proyectos/fork/Marketplace_Orquideas/backend
./mvnw spring-boot:run
```

### Método B: Java directo
```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-21.0.3.0.9-1.fc38.x86_64
cd /home/user/Proyectos/fork/Marketplace_Orquideas/backend
java -jar target/proyecto-orquideas-0.0.1-SNAPSHOT.jar
```

---

## ⚠️ ANTES DE EJECUTAR: Liberar puerto 8080

**Verificar si el puerto está ocupado:**
```bash
lsof -i :8080
```

**Si hay un proceso, obtén su PID y mátalo:**
```bash
ps aux | grep java | grep proyecto | grep -v grep
# Anota el PID (segundo número) y ejecuta:
kill -9 PID_NUMBER
```

---

## ✅ Verificar que funciona

En otra terminal:
```bash
curl http://localhost:8080/api/orquideas
```

Deberías recibir JSON con datos de orquídeas.

---

## 🛑 Detener el backend

- **En VS Code:** Click en botón "Stop" o `Ctrl+C`
- **En terminal:** `Ctrl+C`

**NO deja procesos en background** ✅

---

## 📊 Comparativa

| Método | Comando | Ejecución | Logs | Fácil Detener |
|--------|---------|-----------|------|--------------|
| **VS Code** | Ctrl+F5 | En el IDE | ✅ Sí | ✅ Sí |
| **Maven** | `./mvnw spring-boot:run` | Terminal | ✅ Sí | ✅ Ctrl+C |
| **Java JAR** | `java -jar ...` | Terminal | ✅ Sí | ✅ Ctrl+C |

---

## 🎯 Recomendación Final

**Usa VS Code:** 
1. Abre el archivo `ProyectoOrquideasApplication.java`
2. Presiona `Ctrl+F5`
3. Ve los logs en la terminal de VS Code
4. Presiona `Ctrl+C` para detener

**NO corre en background** → Tienes control total ✅

