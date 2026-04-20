#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
RefugioS - Gestor de Licencias

Este script permite gestionar múltiples licencias:
- Generar claves
- Verificar claves
- Listar licencias activas
- Revocar licencias
- Exportar/Importar licencias

Uso:
    python license_manager.py generar [ID]      # Generar licencia
    python license_manager.py verificar [ID] [CLAVE]  # Verificar
    python license_manager.py listar          # Listar todas
    python license_manager.py revocar [ID]    # Revocar licencia
    python license_manager.py exportar         # Exportar a CSV
"""

import hashlib
import sys
import os
import csv
import json
from datetime import datetime
from pathlib import Path

SALT = "REFUGIOS_V1_SURVIVAL"
VERSION = "1.0.0"
LICENSES_FILE = "licenses.json"

class LicenseManager:
    def __init__(self):
        self.licenses = self.load_licenses()
    
    def load_licenses(self) -> dict:
        """Carga las licencias desde el archivo."""
        if os.path.exists(LICENSES_FILE):
            try:
                with open(LICENSES_FILE, 'r', encoding='utf-8') as f:
                    return json.load(f)
            except:
                return {}
        return {}
    
    def save_licenses(self):
        """Guarda las licencias en el archivo."""
        with open(LICENSES_FILE, 'w', encoding='utf-8') as f:
            json.dump(self.licenses, f, indent=2, ensure_ascii=False)
    
    def create_key(self, device_id: str, nombre: str = "") -> str:
        """Genera y guarda una licencia."""
        device_id = device_id.upper().strip()
        
        # Generar clave
        combined = f"{device_id}{SALT}"
        hash_hex = hashlib.sha256(combined.encode()).hexdigest()
        clave = f"{hash_hex[:4].upper()}-{hash_hex[4:8].upper()}-{hash_hex[8:12].upper()}"
        
        # Guardar licencia
        self.licenses[device_id] = {
            "clave": clave,
            "nombre": nombre,
            "creada": datetime.now().isoformat(),
            "activa": True,
            "ultimo_uso": None
        }
        self.save_licenses()
        
        return clave
    
    def verify_key(self, device_id: str, clave: str) -> bool:
        """Verifica si una clave es válida."""
        device_id = device_id.upper().strip()
        
        if device_id not in self.licenses:
            return False
        
        license_data = self.licenses[device_id]
        
        if not license_data.get("activa", False):
            print(f"⚠️  La licencia está revocada")
            return False
        
        return license_data.get("clave", "").upper() == clave.upper()
    
    def list_licenses(self):
        """Lista todas las licencias."""
        if not self.licenses:
            print("No hay licencias guardadas")
            return
        
        print(f"{'ID Dispositivo':<20} {'Clave':<20} {'Nombre':<20} {'Estado':<10}")
        print("-" * 70)
        
        for device_id, data in self.licenses.items():
            estado = "✓ Activa" if data.get("activa", False) else "✗ Revocada"
            nombre = data.get("nombre", "")[:18]
            clave = data.get("clave", "")[:18]
            print(f"{device_id:<20} {clave:<20} {nombre:<20} {estado:<10}")
    
    def revoke_licenses(self, device_id: str):
        """Revoca una licencia."""
        device_id = device_id.upper().strip()
        
        if device_id not in self.licenses:
            print(f"✗ No encontrada: {device_id}")
            return False
        
        self.licenses[device_id]["activa"] = False
        self.save_licenses()
        print(f"✓ Licencia revocada: {device_id}")
        return True
    
    def export_csv(self, filename: str = "licenses_export.csv"):
        """Exporta licencias a CSV."""
        with open(filename, 'w', newline='', encoding='utf-8') as f:
            writer = csv.writer(f)
            writer.writerow(["ID Dispositivo", "Clave", "Nombre", "Creada", "Estado", "Último Uso"])
            
            for device_id, data in self.licenses.items():
                estado = "Activa" if data.get("activa", False) else "Revocada"
                writer.writerow([
                    device_id,
                    data.get("clave", ""),
                    data.get("nombre", ""),
                    data.get("creada", ""),
                    estado,
                    data.get("ultimo_uso", "")
                ])
        
        print(f"✓ Exportado a: {filename}")

def print_banner():
    print("=" * 50)
    print("  RefugioS - Gestor de Licencias")
    print(f"  Versión {VERSION}")
    print("=" * 50)
    print()

def main():
    print_banner()
    
    if len(sys.argv) < 2:
        print("Uso:")
        print("  python license_manager.py generar [ID] [nombre]  # Generar licencia")
        print("  python license_manager.py verificar [ID] [CLAVE] # Verificar")
        print("  python license_manager.py listar         # Listar todas")
        print("  python license_manager.py revocar [ID]    # Revocar")
        print("  python license_manager.py exportar       # Exportar CSV")
        return 1
    
    manager = LicenseManager()
    command = sys.argv[1].lower()
    
    if command == "generar":
        if len(sys.argv) < 3:
            print("Error: Necesitas el ID del dispositivo")
            print("Uso: python license_manager.py generar [ID] [nombre]")
            return 1
        
        device_id = sys.argv[2].upper()
        nombre = sys.argv[3] if len(sys.argv) > 3 else ""
        
        clave = manager.create_key(device_id, nombre)
        print(f"✓ Licencia creada:")
        print(f"  ID: {device_id}")
        print(f"  Clave: {clave}")
        if nombre:
            print(f"  Nombre: {nombre}")
    
    elif command == "verificar":
        if len(sys.argv) < 4:
            print("Error: Necesitas ID y clave")
            print("Uso: python license_manager.py verificar [ID] [CLAVE]")
            return 1
        
        device_id = sys.argv[2].upper()
        clave = sys.argv[3]
        
        if manager.verify_key(device_id, clave):
            print(f"✓ Clave válida")
        else:
            print(f"✗ Clave inválida")
            return 1
    
    elif command == "listar":
        manager.list_licenses()
    
    elif command == "revocar":
        if len(sys.argv) < 3:
            print("Error: Necesitas el ID del dispositivo")
            print("Uso: python license_manager.py revocar [ID]")
            return 1
        
        device_id = sys.argv[2].upper()
        manager.revoke_licenses(device_id)
    
    elif command == "exportar":
        manager.export_csv()
    
    else:
        print(f"Comando desconocido: {command}")
        return 1
    
    return 0

if __name__ == "__main__":
    sys.exit(main())