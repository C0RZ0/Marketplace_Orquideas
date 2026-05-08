package com.orquicombeima.proyecto_orquideas.service;

import com.orquicombeima.proyecto_orquideas.dto.EstadisticasDTO;
import com.orquicombeima.proyecto_orquideas.dto.PedidoRecienteDTO;
import com.orquicombeima.proyecto_orquideas.dto.VentasMesDTO;
import com.orquicombeima.proyecto_orquideas.model.Pedido;
import com.orquicombeima.proyecto_orquideas.model.enums.EstadoPedido;
import com.orquicombeima.proyecto_orquideas.repository.PedidoRepository;
import com.orquicombeima.proyecto_orquideas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EstadisticasService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    // Metodo para obtener todas las estadisticas
    @Transactional
    public EstadisticasDTO obtenerEstadisticas() {

        LocalDateTime inicioMes =  LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finMes = LocalDateTime.now();

        // Obtener los pedidos del mes actual
        List<Pedido> pedidosMes = pedidoRepository.findByFechaPedidoBetween(inicioMes, finMes);

        // Obtener el total de ventas del mes, o sea aquellos pedidos que ya estan pagos
        double ventasMes = pedidosMes.stream()
                .filter(p -> p.getEstado().name().equals("PAGADO"))
                .mapToDouble(Pedido::getTotal)
                .sum();

        // Obtener la cantidad de pedidos pendientes
        int pedidosPendientes = pedidoRepository.findByEstado(EstadoPedido.PENDIENTE).size();

        // Obtener la cantidad de clientes registrados
        int clientesRegistrados = (int) usuarioRepository.count();

        // Ventas por mes ultimos 6 meses
        LocalDateTime hace6Meses = LocalDateTime.now().minusMonths(6);
        List<Object[]> resultados = pedidoRepository.findVentasPorMes(hace6Meses);

        List<VentasMesDTO> ventasPorMes = resultados.stream()
                .map(fila -> {
                    int mes = ((Number) fila[0]).intValue();
                    double total = ((Number) fila[2]).doubleValue();

                    String nombreMes = Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("es", "CO"));

                    return VentasMesDTO.builder()
                            .mes(nombreMes)
                            .totalVentas(total)
                            .build();

                }).toList();

        return EstadisticasDTO.builder()
                .ventasMes(ventasMes)
                .pedidosMes(pedidosMes.size())
                .clientesRegistrados(clientesRegistrados)
                .pedidosPendientes(pedidosPendientes)
                .ventasPorMes(ventasPorMes)
                .build();
    }

    // Metodo GET para obtener los pedidos recientes
    @Transactional(readOnly = true)
    public List<PedidoRecienteDTO> obtenerPedidosRecientes() {
        return pedidoRepository.findTop5ByOrderByFechaPedidoDesc().stream()
                .map(this::convertirAPedidoRecienteDTO)
                .toList();
    }

    // Función que permite convertir un pedido a un pedido reciente DTO
    private PedidoRecienteDTO convertirAPedidoRecienteDTO(Pedido pedido) {
        return PedidoRecienteDTO.builder()
                .id(pedido.getId())
                .nombreCliente(pedido.getUsuario().getNombre())
                .total(pedido.getTotal())
                .estado(pedido.getEstado().name())
                .tiempoTranscurrido(calcularTiempoTranscurrido(pedido.getFechaPedido()))
                .build();
    }

    // Función para calcular el tiempo transcurrido desde que se realizo el pedido
    private String calcularTiempoTranscurrido(LocalDateTime fechaPedido) {
        long minutos = Duration.between(fechaPedido, LocalDateTime.now()).toMinutes();

        if (minutos < 60) {
            return "Hace" + minutos + " minutos";
        } else if (minutos < 1440) {
            long horas = minutos / 60;
            return "Hace " + horas + " horas";
        } else {
            long dias = minutos / 1440;
            return "Hace " + dias + " días";

        }
    }

}
