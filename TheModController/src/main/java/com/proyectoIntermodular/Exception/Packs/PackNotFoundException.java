package com.proyectoIntermodular.Exception.Packs;

public class PackNotFoundException extends RuntimeException {

  public PackNotFoundException(Long id) {
    super("Could not find the pack with id " + id);
  }

  public PackNotFoundException(String name) {
    super("Could not find the pack with name: " + name);
  }
}