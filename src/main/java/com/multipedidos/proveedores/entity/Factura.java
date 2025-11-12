package com.multipedidos.proveedores.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_factura", nullable = false, unique = true)
    private String numeroFactura;
    
    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;
    
    @Column(name = "total_factura")
    private Double totalFactura;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
    
    // Lista de pedidos asociados (IDs de pedidos del Componente A)
    @ElementCollection
    @CollectionTable(name = "factura_pedidos", joinColumns = @JoinColumn(name = "factura_id"))
    @Column(name = "pedido_id")
    private List<Long> pedidosIds = new ArrayList<>();
}