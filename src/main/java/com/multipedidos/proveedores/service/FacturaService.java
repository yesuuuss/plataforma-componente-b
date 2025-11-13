package com.multipedidos.proveedores.service;

import com.multipedidos.proveedores.entity.Factura;
import com.multipedidos.proveedores.entity.Proveedor;
import com.multipedidos.proveedores.repository.FacturaRepository;
import com.multipedidos.proveedores.repository.ProveedorRepository;
import com.multipedidos.common.OperacionesNegocio;
import com.multipedidos.common.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
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

        if (factura.getClienteId() != null) {
            System.out.println("🔍 Validando cliente ID: " + factura.getClienteId() + " en Componente A...");
            
            List<Producto> productosSimulados = new ArrayList<>();
            productosSimulados.add(new Producto("Factura-Producto", 100.0));
            
            boolean clienteValido = OperacionesNegocio.procesarPedidoConValidacion(
                factura.getClienteId(), 
                productosSimulados
            );
            
            if (!clienteValido) {
                throw new RuntimeException("No se puede crear factura: Cliente con ID " + 
                    factura.getClienteId() + " no existe en el sistema de pedidos (Componente A)");
            }
            System.out.println("✅ Cliente validado exitosamente en Componente A");
        }

        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isEmpty()) {
            factura.setNumeroFactura(OperacionesNegocio.generarNumeroFactura("PRO"));
        }

        if (facturaRepository.existsByNumeroFactura(factura.getNumeroFactura())) {
            throw new RuntimeException("El número de factura ya existe: " + factura.getNumeroFactura());
        }

        if (!OperacionesNegocio.validarCodigo(factura.getNumeroFactura())) {
            throw new RuntimeException("Formato de número de factura inválido. Debe ser: AAA-1234");
        }

        factura.setProveedor(proveedor);
        
        if (factura.getTotalFactura() == null) {
            double subtotal = calcularSubtotalDesdePedidos(factura.getPedidosIds());
            double totalConIVA = OperacionesNegocio.calcularTotalConIVA(subtotal);
            factura.setTotalFactura(totalConIVA);
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

    private double calcularSubtotalDesdePedidos(List<Long> pedidosIds) {
        if (pedidosIds == null || pedidosIds.isEmpty()) {
            return 0.0;
        }
        return pedidosIds.size() * 100.0;
    }

    public Factura aplicarDescuentoAFactura(Long facturaId, double porcentajeDescuento) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + facturaId));

        double totalConDescuento = OperacionesNegocio.aplicarDescuento(
            factura.getTotalFactura(), 
            porcentajeDescuento
        );

        factura.setTotalFactura(totalConDescuento);
        return facturaRepository.save(factura);
    }
}