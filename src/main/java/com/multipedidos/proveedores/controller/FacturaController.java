package com.multipedidos.proveedores.controller;

import com.multipedidos.proveedores.entity.Factura;
import com.multipedidos.proveedores.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<?> crearFactura(@RequestBody Factura factura, 
                                         @RequestParam Long proveedorId) {
        try {
            Factura facturaCreada = facturaService.crearFactura(factura, proveedorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(facturaCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFacturaPorId(@PathVariable Long id) {
        Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
        return factura.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}