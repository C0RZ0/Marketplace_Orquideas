# ⚡ REFERENCIA RÁPIDA - Backend

## 🚀 Ejecutar Directamente (Sin Background)

### Opción 1: VS Code (Lo más fácil)
1. Abre: `/backend/src/main/java/com/orquicombeima/proyecto_orquideas/ProyectoOrquideasApplication.java`
2. Presiona: **`Ctrl+F5`**
3. Ver logs en terminal de VS Code
4. Detener: **`Ctrl+C`**

### Opción 2: Terminal con Maven
```bash
cd /home/user/Proyectos/fork/Marketplace_Orquideas/backend
./mvnw spring-boot:run
```

### Opción 3: Terminal con Java JAR
```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-21.0.3.0.9-1.fc38.x86_64
cd /home/user/Proyectos/fork/Marketplace_Orquideas/backend
java -jar target/proyecto-orquideas-0.0.1-SNAPSHOT.jar
```

---

## ✅ Verificar que funciona

```bash
curl http://localhost:8080/api/orquideas
```

---

## 🛑 Limpiar puerto 8080

```bash
ps aux | grep "[j]ava.*proyecto"
# Anotar el PID, luego:
kill -9 PID
```

---

## 📊 Configuración Java

- **Java Version:** 21.0.3
- **Backend Port:** 8080
- **Frontend Port:** 5173

---

## 🎯 Lo Importante

✅ **NO corre en background**  
✅ **Depende de ProyectoOrquideasApplication.java**  
✅ **Controlas cuándo inicia y detiene**  
✅ **Ves logs en tiempo real**
