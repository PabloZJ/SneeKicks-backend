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

import com.sneekicks.sneekicks.model.Product;
import com.sneekicks.sneekicks.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> obtenerTodosLosProductos() {
        List<Product> productos = productService.obtenerProductos();
        if (productos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(productos);
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> obtenerProductoPorId(@PathVariable Long id) {
        Product producto = productService.obtenerProductoPorId(id);
        if (producto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(producto);
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Product> crearProducto(@RequestBody Product product) {
        Product nuevoProducto = productService.guardarProducto(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // Actualizar un producto completo
    @PutMapping("/{id}")
    public ResponseEntity<Product> actualizarProducto(@PathVariable Long id, @RequestBody Product product) {
        Product actualizado = productService.actualizarProducto(id, product);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    // Actualizar parcialmente un producto (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Product> actualizarProductoParcial(@PathVariable Long id, @RequestBody Product product) {
        Product actualizado = productService.actualizarProductoParcial(id, product);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
