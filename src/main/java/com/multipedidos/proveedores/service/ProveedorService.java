package com.multipedidos.proveedores.service;

import com.multipedidos.proveedores.entity.Proveedor;
import com.multipedidos.proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Proveedor crearProveedor(Proveedor proveedor) {
        
        if (proveedorRepository.existsByCorreo(proveedor.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado: " + proveedor.getCorreo());
        }
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> obtenerTodosProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Optional<Proveedor> obtenerProveedorPorCorreo(String correo) {
        return proveedorRepository.findByCorreo(correo);
    }
}