# RefugioS - Preparar Pendrive USB

## Estructura de carpetas needed en el pendrive

Crea esta estructura en tu pendrive de 64GB:

```
/refugios/
├── wikipedia/
│   └── wikipedia_es_all_maxi_2026-02.zim (~50GB)
├── wikimed/
│   └── wiktionary_es_all_2025-03.zim
├── maps/
│   └── *.obf (Organic Maps)
├── survival/
│   ├── FM_21-76_Survival_Manual.pdf
│   ├── SAS_Survival_Handbook.pdf
│   └── ...
└── guides/
    ├── Mecanica_Basica.pdf
    ├── Electrificacion.pdf
    └── ...
```

**NOTA:** Las plantas medicinales están incluidas en la app, no necesitan archivo adicional.

**NOTA:** Los archivos ZIM funcionan en cualquier subcarpeta, la app los busca automáticamente.
/refugios/
├── wikipedia/
│   └── wikipedia_es_all_nov_2024.zim (descarga ~15GB)
├── wikimed/
│   └── wikimed_es_all_nov_2024.zim (descarga ~500MB)
├── maps/
│   ├── europe/ tiles
│   └── world/ tiles
├── survival/
│   ├── First_Aid_Guide.pdf
│   ├── SAS_Survival_Handbook.pdf
│   └── ...
└── guides/
    ├── Mecanica_Basica.pdf
    ├── Electrificacion.pdf
    └── ...
```

## Dónde descargar archivos

### Wikipedia Offline
- **Descarga directa (38GB):**
  ```
  https://download.kiwix.org/zim/wikipedia/wikipedia_es_all_maxi_2026-02.zim
  ```
- Alternativa más pequeña (9.5GB sin imágenes):
  ```
  https://download.kiwix.org/zim/wikipedia/wikipedia_es_all_nopic_2026-02.zim
  ```
- Versión mini (3.2GB):
  ```
  https://download.kiwix.org/zim/wikipedia/wikipedia_es_all_mini_2026-02.zim
  ```

### WikiMed/Wiktionary (Diccionario)
- **Descarga directa:**
  ```
  https://download.kiwix.org/zim/wiktionary/wiktionary_es_all_2025-03.zim
  ```
- Alternativa más reciente (535MB):
  ```
  https://download.kiwix.org/zim/wiktionary/wiktionary_es_all_nopic_2026-03.zim
  ```

### Mapas Offline (Organic Maps)

Descarga desde: https://organicmaps.app/

1. **App Organic Maps** (recomendada):
   - Descarga los mapas que necesites desde la app
   - Los archivos se guardan en `/storage/emulated/0/OrganicMaps/`
   - Copia esa carpeta al pendrive

2. **Estructura esperada**:
```
/refugios/
├── maps/
│   ├── europe/
│   │   └── europe.obf
│   └── world/
│       └── world.obf
```

3. **O usar osmdroid tile cache**:
   - Copia la carpeta `tiles` de osmdroid al pendrive

### Guías PDF

Descarga gratuitas (funcionando abril 2026):

1. **FM 21-76 - Army Survival Manual**
   - https://archive.org/details/Fm21-76SurvivalManual
   - Descarga directa: https://archive.org/download/Fm21-76SurvivalManual/FM21-76_SurvivalManual.pdf
   - Alternativa: https://commons.wikimedia.org/wiki/File:FM_3-05.70_(FM_21-76)_Survival_-_May_2002.pdf

2. **SAS Survival Handbook**
   - https://archive.org/details/sas_survival_handbook_john_1979
   - Descarga: https://archive.org/download/sas_survival_handbook_john_1979/SAS%20Survival%20Handbook.pdf

3. **US Army Ranger Handbook**
   - https://archive.org/details/FM_3-22_Army_Ranger_Handbook
   - Descarga: https://archive.org/download/FM_3-22_Army_Ranger_Handbook/Ranger_Handbook.pdf

4. **Air Force Survival Manual**
   - https://archive.org/details/AFM_3-05-11_Survival
   - Descarga: https://archive.org/download/AFM_3-05-11_Survival/AFM%203-05-11.pdf

5. **American Red Cross First Aid Manual**
   - https://archive.org/details/americanredcross00hand
   - Descarga directa: https://archive.org/download/americanredcross00hand/americanredcross00hand.pdf

6. **Canadian Red Cross First Aid (2017)**
   - https://cdn.redcross.ca/prodmedia/crc/pdf/First-Aid-and-CPR-2017_digital.pdf

5. **Mecánica Automotriz**
   - Manuales de taller genéricos

**Estructura recomendada**:
```
/refugios/
├── survival/
│   ├── FM_21-76_Survival_Manual.pdf
│   ├── SAS_Survival_Handbook.pdf
│   ├── First_Aid_Guide.pdf
│   └── ...
└── guides/
    ├── Mecanica_Basica.pdf
    ├── Electrificacion.pdf
    └── ...
```

## Instalación de la App

1. **Instala Android Studio** desde https://developer.android.com/studio

2. **Abre este proyecto** en Android Studio

3. **Conecta tu teléfono Android** (con debug USB habilitado)

4. **Build > Build APK** para generar el APK

5. **Instala el APK** en tu teléfono:
   ```
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Uso

1. Conecta el pendrive al teléfono usando un adaptor OTG
2. Abre la app RefugioS
3. Concede permisos de acceso al USB cuando se solicite
4. La app detectará automáticamente los datos en el pendrive

## Notas

- El teléfono debe support USB Host (casi todos los Android modernos lo supportan)
- Necesitas un cable OTG para conectar el pendrive
- Asegúrate de que el pendrive tenga formato FAT32 o exFAT