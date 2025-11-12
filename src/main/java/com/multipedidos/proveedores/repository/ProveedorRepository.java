package com.multipedidos.proveedores.repository;

import com.multipedidos.proveedores.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}