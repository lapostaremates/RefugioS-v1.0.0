# Subir a GitHub - Pasos rápidos

## Opción 1: Desde el navegador (sin terminal)

### 1. Ve a https://github.com/new
### 2. Crea un nuevo repositorio:
   - Name: `refugios-android`
   - **NO** marcar "Add a README file"
   - Click "Create repository"

### 3. Aquí verás instrucciones. Copiá y ejecutá en terminal:

```bash
cd "C:\Users\Mostrador\Desktop\Nueva carpeta\refugiOs\RefugioS-android"
echo "# RefugioS" > README.md
git init
git add .
git commit -m "RefugioS v1.0.0"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/refugios-android.git
git push -u origin main
```

*(Reemplazá TU_USUARIO con tu usuario de GitHub)*

### 4. Listo! El código está en GitHub

---

## Opción 2: Si no tenés Git instalado

1. Descarga Git: https://git-scm.com/download/win
2. Instalá con todas las opciones por defecto
3. Abrí "Git Bash"
4. Ejecutá los comandos de arriba

---

## Después de subir

Yo lo compilo y te paso el APK.

---

## Para ejecutar en terminal:

1. presiona Windows + R
2. Escribí: `cmd`
3. En la terminal, escribí los comandos de arriba