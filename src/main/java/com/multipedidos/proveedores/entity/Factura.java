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

    @Column(name = "numero_factura", unique = true, nullable = false)
    private String numeroFactura;

    @Column(name = "total_factura")
    private Double totalFactura;
    @Column(name = "cliente_id")
    private Long clienteId;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @ElementCollection
    @CollectionTable(name = "factura_pedidos", joinColumns = @JoinColumn(name = "factura_id"))
    @Column(name = "pedido_id")
    private List<Long> pedidosIds;

   
    public Factura() {}

    public Factura(String numeroFactura, Double totalFactura, Long clienteId, Proveedor proveedor) {
        this.numeroFactura = numeroFactura;
        this.totalFactura = totalFactura;
        this.clienteId = clienteId;
        this.proveedor = proveedor;
    }

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public Double getTotalFactura() { return totalFactura; }
    public void setTotalFactura(Double totalFactura) { this.totalFactura = totalFactura; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public List<Long> getPedidosIds() { return pedidosIds; }
    public void setPedidosIds(List<Long> pedidosIds) { this.pedidosIds = pedidosIds; }
}