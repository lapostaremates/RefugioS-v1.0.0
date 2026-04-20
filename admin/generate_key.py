#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
RefugioS - Generador de Claves de Activación

Este script permite generar claves de activación para dispositivos.
Uso:
    python generate_key.py              # Generar ID aleatorio + clave
    python generate_key.py A1B2C3D4E5F6 # Generar clave para ID específico
"""

import hashlib
import sys
import os

SALT = "REFUGIOS_V1_SURVIVAL"
VERSION = "1.0.0"

def generate_device_id():
    """Genera un ID de dispositivo aleatorio para testing."""
    import random
    chars = "ABCDEF0123456789"
    return ''.join(random.choices(chars, k=16))

def create_activation_key(device_id: str) -> str:
    """
    Genera una clave de activación a partir del ID del dispositivo.
    
    Args:
        device_id: ID único del dispositivo (16 caracteres)
    
    Returns:
        Clave de activación en formato XXX-XXX-XXX
    """
    # Normalizar a mayúsculas
    device_id = device_id.upper().strip()
    
    # Agregar salt y generar hash
    combined = f"{device_id}{SALT}"
    hash_obj = hashlib.sha256(combined.encode())
    hash_hex = hash_obj.hexdigest()
    
    # Extraer primeros 12 caracteres y formatear
    clave = f"{hash_hex[:4].upper()}-{hash_hex[4:8].upper()}-{hash_hex[8:12].upper()}"
    
    return clave

def verify_key(device_id: str, clave: str) -> bool:
    """
    Verifica si una clave es válida para un dispositivo.
    
    Args:
        device_id: ID del dispositivo
        clave: Clave a verificar
    
    Returns:
        True si la clave es válida
    """
    expected = create_activation_key(device_id)
    return expected.upper() == clave.upper()

def print_banner():
    """Imprime el banner del programa."""
    print("=" * 50)
    print("  RefugioS - Generador de Claves de Activación")
    print(f"  Versión {VERSION}")
    print("=" * 50)
    print()

def main():
    print_banner()
    
    if len(sys.argv) > 1:
        # ID proporcionado como argumento
        device_id = sys.argv[1].strip().upper()
        
        if len(device_id) < 8:
            print("Error: El ID debe tener al menos 8 caracteres")
            print("Uso: python generate_key.py [ID_DISPOSITIVO]")
            return 1
            
        print(f"ID del dispositivo: {device_id}")
        clave = create_activation_key(device_id)
        print(f"Clave de activación: {clave}")
        
    else:
        # Generar ID aleatorio para testing
        device_id = generate_device_id()
        
        print("⚠️  ID aleatorio generado (para testing only)")
        print()
        print(f"ID del dispositivo: {device_id}")
        print(f"Clave de activación: {create_activation_key(device_id)}")
        print()
        
        # Generar algunos ejemplos
        print("-" * 50)
        print("Ejemplos de IDs y claves:")
        print("-" * 50)
        
        for i in range(3):
            test_id = generate_device_id()
            test_key = create_activation_key(test_id)
            print(f"ID: {test_id}  →  Clave: {test_key}")
    
    print()
    print("-" * 50)
    print("Instrucciones:")
    print("-" * 50)
    print("1. El usuario te envía su ID de dispositivo")
    print("2. Vos generás la clave con este script")
    print("3. El usuario ingresa la clave en la app")
    print("4. ¡Listo! La app queda activada")
    print()
    return 0

if __name__ == "__main__":
    sys.exit(main())