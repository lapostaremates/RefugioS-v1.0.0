# RefugioS - Scripts de Administración

Este directorio contiene herramientas para gestionar las licencias de la app.

## Archivos

- `generate_key.py` - Generador de claves simple
- `license_manager.py` - Gestor completo de licencias

---

## Requisitos

Python 3.7 o superior:
```bash
pip install python3
```

---

## Uso Básico: generate_key.py

### Generar clave para un ID específico:
```bash
python generate_key.py A1B2C3D4E5F6
```

Salida:
```
ID del dispositivo: A1B2C3D4E5F6
Clave de activación: 7A3F-8B2C-1D4E
```

### Generar ejemplo de prueba:
```bash
python generate_key.py
```

Salida:
```
⚠️  ID aleatorio generado (para testing only)

ID del dispositivo: 9A2B7C1D4E6F8
Clave de activación: 1E2F-3A4B-5C6D
```

---

## Uso Avanzado: license_manager.py

### Crear licencia:
```bash
python license_manager.py generar A1B2C3D4E5F6 "Usuario Juan"
```
Salida:
```
✓ Licencia creada:
  ID: A1B2C3D4E5F6
  Clave: 7A3F-8B2C-1D4E
  Nombre: Usuario Juan
```

### Verificar licencia:
```bash
python license_manager.py verificar A1B2C3D4E5F6 7A3F-8B2C-1D4E
```
Salida:
```
✓ Clave válida
```

### Listar licencias:
```bash
python license_manager.py listar
```
Salida:
```
ID Dispositivo      Clave               Nombre              Estado
----------------------------------------------------------------------
A1B2C3D4E5F6      7A3F-8B2C-1D4E     Usuario Juan        ✓ Activa
F6E5D4C3B2A1      9C8D-7E6F-5A4B     Usuario María       ✓ Activa
```

### Revocar licencia:
```bash
python license_manager.py revocar A1B2C3D4E5F6
```
Salida:
```
✓ Licencia revocada: A1B2C3D4E5F6
```

### Exportar a CSV:
```bash
python license_manager.py exportar
```
Salida:
```
✓ Exportado a: licenses_export.csv
```

---

## Flujo de Trabajo

1. **Usuario instala la app** en su teléfono
2. **Usuario ve su ID** en pantalla de activación
3. **Usuario te envía su ID** (por WhatsApp, email, etc.)
4. **Vos generás la clave:**
   ```bash
   python generate_key.py A1B2C3D4E5F6
   ```
5. **Vos le'envás la clave** al usuario
6. **Usuario ingresa la clave** en la app
7. **¡Listo!** La app queda activada

---

## Notas de Seguridad

- El ID se genera del ANDROID_ID + fabricante + modelo
- No se puede copiar la app a otro dispositivo sin nueva activación
- Las licencias se guardan en `licenses.json`

---

## Troubleshooting

### Error "python no se reconoce"
Windows:
```cmd
py -3 generate_key.py
```

Linux/Mac:
```bash
python3 generate_key.py
```

---
RefugioS v1.0.0 - Admin Tools