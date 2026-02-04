package com.proyectoIntermodular.Exception.Mod;

public class ModNotFoundException extends RuntimeException {

  public ModNotFoundException(Long id) {
    super("Could not find the mod with id " + id);
  }

  public ModNotFoundException(String name) {
    super("Could not find the mod with name: " + name);
  }
}