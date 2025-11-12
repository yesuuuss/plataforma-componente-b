package com.multipedidos.proveedores.service;

import com.multipedidos.proveedores.entity.Factura;
import com.multipedidos.proveedores.entity.Proveedor;
import com.multipedidos.proveedores.repository.FacturaRepository;
import com.multipedidos.proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Factura crearFactura(Factura factura, Long proveedorId) {
   
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));

      
        if (facturaRepository.existsByNumeroFactura(factura.getNumeroFactura())) {
            throw new RuntimeException("El número de factura ya existe: " + factura.getNumeroFactura());
        }

     
        factura.setProveedor(proveedor);
        
    
        if (factura.getTotalFactura() == null) {
            factura.setTotalFactura(0.0); 
        }

        return facturaRepository.save(factura);
    }

    public List<Factura> obtenerTodasFacturas() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id);
    }

    public Optional<Factura> obtenerFacturaPorNumero(String numeroFactura) {
        return facturaRepository.findByNumeroFactura(numeroFactura);
    }
}