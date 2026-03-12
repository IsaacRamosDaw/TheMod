package com.proyectoIntermodular.Exception.Mod;

public class ModCategoryNotFoundException extends RuntimeException {
    public ModCategoryNotFoundException(Long id) {
        super("No se pudo encontrar la categoría del mod con el ID: " + id);
    }
}
