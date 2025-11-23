package com.sneekicks.sneekicks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sneekicks.sneekicks.model.User;
import com.sneekicks.sneekicks.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    public List<User> obtenerUsuarios() {
        return userRepository.findAll();
    }

    // Obtener usuario por ID
    public User obtenerUsuarioPorId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Crear un nuevo usuario
    public User guardarUsuario(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    // Actualizar usuario completo
    public User actualizarUsuario(Long id, User user) {
        return userRepository.findById(id).map(existente -> {
            existente.setUsername(user.getUsername());
            existente.setEmail(user.getEmail());
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                existente.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            existente.setProfileImage(user.getProfileImage());
            return userRepository.save(existente);
        }).orElse(null);
    }

    // Actualizar parcialmente (PATCH)
    public User actualizarUsuarioParcial(Long id, User user) {
        return userRepository.findById(id).map(existente -> {
            if (user.getUsername() != null && !user.getUsername().isBlank()) {
                existente.setUsername(user.getUsername());
            }
            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                existente.setEmail(user.getEmail());
            }
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                existente.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getProfileImage() != null) {
                existente.setProfileImage(user.getProfileImage());
            }
            return userRepository.save(existente);
        }).orElse(null);
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        userRepository.deleteById(id);
    }

    // Obtener usuario por email
    public User obtenerUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Validar login
    public boolean validarLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
