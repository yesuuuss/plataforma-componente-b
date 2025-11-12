package com.multipedidos.proveedores.controller;

import com.multipedidos.proveedores.entity.Proveedor;
import com.multipedidos.proveedores.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<?> crearProveedor(@RequestBody Proveedor proveedor) {
        try {
            Proveedor proveedorCreado = proveedorService.crearProveedor(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedorCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosProveedores() {
        List<Proveedor> proveedores = proveedorService.obtenerTodosProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProveedorPorId(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.obtenerProveedorPorId(id);
        return proveedor.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
}