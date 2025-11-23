package com.sneekicks.sneekicks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sneekicks.sneekicks.model.User;
import com.sneekicks.sneekicks.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> obtenerTodosLosUsuarios() {
        List<User> usuarios = userService.obtenerUsuarios();
        if (usuarios.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuarioPorId(@PathVariable Long id) {
        User usuario = userService.obtenerUsuarioPorId(id);
        if (usuario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuario);
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody User user) {
        User nuevoUsuario = userService.guardarUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    // Actualizar un usuario completo
    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody User user) {
        User actualizado = userService.actualizarUsuario(id, user);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    // Actualizar parcialmente un usuario (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<User> actualizarUsuarioParcial(@PathVariable Long id, @RequestBody User user) {
        User actualizado = userService.actualizarUsuarioParcial(id, user);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        userService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        boolean valido = userService.validarLogin(user.getEmail(), user.getPassword());
        if (!valido) return ResponseEntity.status(401).body("Credenciales inválidas");

        User completo = userService.obtenerUsuarioPorEmail(user.getEmail());
        return ResponseEntity.ok(completo);
    }
}
