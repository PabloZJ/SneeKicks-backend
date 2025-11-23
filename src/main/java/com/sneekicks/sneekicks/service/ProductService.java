package com.sneekicks.sneekicks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sneekicks.sneekicks.model.Product;
import com.sneekicks.sneekicks.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Obtener todos los productos
    public List<Product> obtenerProductos() {
        return productRepository.findAll();
    }

    // Obtener producto por ID
    public Product obtenerProductoPorId(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Crear nuevo producto
    public Product guardarProducto(Product product) {
        return productRepository.save(product);
    }

    // Actualizar producto completo
    public Product actualizarProducto(Long id, Product product) {
        return productRepository.findById(id).map(existente -> {
            existente.setName(product.getName());
            existente.setDescription(product.getDescription());
            existente.setPrice(product.getPrice());
            existente.setImageUrl(product.getImageUrl());
            return productRepository.save(existente);
        }).orElse(null);
    }

    // Actualizar parcialmente (PATCH)
    public Product actualizarProductoParcial(Long id, Product product) {
        return productRepository.findById(id).map(existente -> {
            if (product.getName() != null && !product.getName().isBlank()) {
                existente.setName(product.getName());
            }
            if (product.getDescription() != null) {
                existente.setDescription(product.getDescription());
            }
            if (product.getPrice() != null) {
                existente.setPrice(product.getPrice());
            }
            if (product.getImageUrl() != null && !product.getImageUrl().isBlank()) {
                existente.setImageUrl(product.getImageUrl());
            }
            return productRepository.save(existente);
        }).orElse(null);
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {
        productRepository.deleteById(id);
    }
}
