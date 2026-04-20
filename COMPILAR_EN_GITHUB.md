# Cómo Compilar en GitHub (Sin instalar Android Studio)

Este método compila el APK automáticamente en la nube.

---

## Pasos para compilar

### 1. Crear cuenta en GitHub
Si no tenés cuenta, creala en: https://github.com/signup

### 2. Crear un nuevo repositorio
1. Ve a: https://github.com/new
2. Repository name: `refugios-android`
3. Choose "Public" o "Private"
4. Click: "Create repository"

### 3. Subir el proyecto
En tu terminal, ejecutá:
```bash
cd "C:\Users\Mostrador\Desktop\Nueva carpeta\refugiOs\RefugioS-android"

git init
git add .
git commit -m "RefugioS v1.0.0"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/refugios-android.git
git push -u origin main
```

*(Reemplazá TU_USUARIO con tu nombre de usuario de GitHub)*

### 4. Esperar la compilación
1. Ve a tu repositorio en GitHub: https://github.com/TU_USUARIO/refugios-android
2. Click en la pestaña "Actions"
3. Esperá a que diga "Build completed ✓"
4. Click en el build
5. Click en "refugios-apk" (en ARTIFACTS)
6. Descargá el archivo APK

---

##Eso es todo!

El APK se generará automáticamente cada vez que hagás push al repositorio.

---

## Pero primero... necesita

El proyecto necesita agregar el archivo `gradlew` (Gradle Wrapper). Voy a generarlo:

---

## Pero hay un problema

El proyecto necesita el wrapper de Gradle. Voy a crear los archivos mínimos necesarios: