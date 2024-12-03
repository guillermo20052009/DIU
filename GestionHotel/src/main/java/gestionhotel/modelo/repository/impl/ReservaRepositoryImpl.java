package gestionhotel.modelo.repository.impl;

import gestionhotel.Reserva;
import gestionhotel.modelo.PersonaVO;
import gestionhotel.modelo.Regimen;
import gestionhotel.modelo.ReservaVO;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.ExcepcionReserva;
import gestionhotel.modelo.repository.ReservaRepository;
import gestionhotel.modelo.tipo_habitacion;

import java.sql.*;
import java.util.ArrayList;

public class ReservaRepositoryImpl implements ReservaRepository {
    private final Conexion conexion = new Conexion();
    private Statement stmt;
    private String sentencia;
    private ArrayList<ReservaVO> reservas;
    private ReservaVO reserva;

    public  ReservaRepositoryImpl() {
    }

    public ArrayList<ReservaVO> ObtenerListaReservas() throws ExcepcionReserva {
        try {
            Connection conn = this.conexion.conectarBD();
            this.reservas = new ArrayList();
            this.stmt = conn.createStatement();
            this.sentencia = "SELECT * FROM reserva";
            ResultSet rs = this.stmt.executeQuery(this.sentencia);

            while(rs.next()) {
                int reservaid = rs.getInt("id_reserva");
                java.sql.Date fechaSalida = rs.getDate("fecha_Salida");
                java.sql.Date fechaLlegada = rs.getDate("fecha_Llegada");
                String tipoHabitacion = rs.getString("tipo_Hab"); // Cambié a `tipo_Hab`
                boolean fumador = rs.getBoolean("fumador");
                String regimen = rs.getString("regimen");
                int numeroHabitaciones = rs.getInt("num_hab"); // Cambié a `num_hab`
                String dniCliente = rs.getString("dni_cliente");
                this.reserva = new ReservaVO(reservaid,numeroHabitaciones,regimen,fumador,tipoHabitacion,fechaSalida, fechaLlegada,dniCliente);
                this.reservas.add(this.reserva);
            }

            this.conexion.desconectarBD(conn);
            return this.reservas;
        } catch (SQLException var6) {
            throw new ExcepcionReserva("No se ha podido realizar la operación");
        }
    }

    public void addReserva(ReservaVO reservaVO) throws ExcepcionReserva {
        try {
            Connection conn = this.conexion.conectarBD();
            String sql = "INSERT INTO reserva (fecha_Salida, fecha_Llegada, tipo_Hab, fumador, regimen, num_hab, dni_cliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

// Asumiendo que `reservaVO` tiene los métodos getters correctos
            stmt.setDate(1, reservaVO.getFechaSalida());  // Usa .getFechaSalida() que debería devolver un java.sql.Date
            stmt.setDate(2, reservaVO.getFechaLlegada());  // Usa .getFechaLlegada() que también debería devolver un java.sql.Date
            stmt.setString(3, reservaVO.getTipo_habitacion().replaceAll("_"," "));
            stmt.setBoolean(4, reservaVO.isFumador());  // Fumador como boolean
            stmt.setString(5, reservaVO.getRegimen().replaceAll("_"," "));  // Régimen como String
            stmt.setInt(6, reservaVO.getNumero_habitaciones());  // IntegerProperty necesita .get() para obtener el int
            stmt.setString(7, reservaVO.getDni_cliente());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();  // Imprime el detalle del error
            throw new ExcepcionReserva("No se ha podido realizar la edición");
        }
    }

    public void deleteReserva(int id_reserva) throws ExcepcionReserva {
        try {
            Connection conn = this.conexion.conectarBD();
            String sql = "DELETE FROM reserva WHERE  id_reserva= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_reserva);  // Asignar el valor de dniPersona de forma segura
            pstmt.executeUpdate();
            this.conexion.desconectarBD(conn);
        } catch (SQLException var5) {
            throw new ExcepcionReserva("No se ha podido realizar la eliminación");
        }
    }


    public void editReserva(ReservaVO reservaVO) throws ExcepcionReserva {
        try {
            Connection conn = this.conexion.conectarBD();
            String sql = "UPDATE reserva SET fecha_Salida = ?, fecha_Llegada = ?, tipo_Hab = ?, fumador = ?, regimen = ?, num_hab = ?, dni_cliente = ? WHERE id_reserva = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println(reservaVO);

// Asumiendo que `reservaVO` tiene los métodos getters correctos
            stmt.setDate(1, reservaVO.getFechaSalida());  // fecha_Salida como java.sql.Date
            stmt.setDate(2, reservaVO.getFechaLlegada());  // fecha_Llegada como java.sql.Date
            stmt.setString(3, reservaVO.getTipo_habitacion().replaceAll("_"," "));  // tipo_Hab como String
            stmt.setBoolean(4, reservaVO.isFumador());  // fumador como boolean
            stmt.setString(5, reservaVO.getRegimen().replaceAll("_"," "));  // regimen como String
            stmt.setInt(6, reservaVO.getNumero_habitaciones());  // num_hab como int, usando .get() para IntegerProperty
            stmt.setString(7, reservaVO.getDni_cliente());  // dni_cliente como String, usando .get() para StringProperty
            stmt.setInt(8, reservaVO.getIdReserva());


            stmt.executeUpdate();
        } catch (Exception var4) {
            throw new ExcepcionReserva("No se ha podido realizar la edición");
        }
    }

    public int lastId() throws ExcepcionReserva {
        int lastReservaId = 0;

        try {
            Connection conn = this.conexion.conectarBD();
            Statement comando = conn.createStatement();


            for(ResultSet registro = comando.executeQuery("SELECT id_reserva FROM reserva ORDER BY id_reserva DESC LIMIT 1"); registro.next(); lastReservaId = registro.getInt("id_reserva")) {
            }

            return lastReservaId;
        } catch (SQLException var5) {
            throw new ExcepcionReserva("No se ha podido realizar la busqueda del ID");
        }
    }

    @Override
    public int[] countActuales() throws ExcepcionReserva {
        return countByType();
    }

    private int[] countByType() throws ExcepcionReserva {
        int[] counts = new int[4]; // Array para almacenar las cantidades de cada tipo
        String[] tiposHabitacion = {"DOBLE INDIVIDUAL", "DOBLE", "JUNIOR SUITE", "SUITE"}; // Tipos de habitación
        String query = "SELECT COUNT(*) AS total " +
                "FROM reserva " +
                "WHERE tipo_Hab = ? AND fecha_Llegada <= CURRENT_DATE AND fecha_Salida >= CURRENT_DATE";

        try (Connection conn = this.conexion.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < tiposHabitacion.length; i++) {
                stmt.setString(1, tiposHabitacion[i]);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        counts[i] = rs.getInt("total"); // Almacenar la cantidad en el array
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counts;
    }


    @Override
    public int[] countMonthsByType(String tipoHabitacion) throws ExcepcionReserva {
        int[] countByMonth = new int[12]; // Un array para cada mes (de enero a diciembre)

        String sql = "SELECT m.mes, COUNT(r.id_reserva) AS total " +
                "FROM ( " +
                "    SELECT 1 AS mes UNION ALL " +
                "    SELECT 2 UNION ALL " +
                "    SELECT 3 UNION ALL " +
                "    SELECT 4 UNION ALL " +
                "    SELECT 5 UNION ALL " +
                "    SELECT 6 UNION ALL " +
                "    SELECT 7 UNION ALL " +
                "    SELECT 8 UNION ALL " +
                "    SELECT 9 UNION ALL " +
                "    SELECT 10 UNION ALL " +
                "    SELECT 11 UNION ALL " +
                "    SELECT 12 " +
                ") AS m " +
                "LEFT JOIN reserva r ON MONTH(r.fecha_Llegada) = m.mes AND r.tipo_Hab = ? " +
                "GROUP BY m.mes " +
                "ORDER BY m.mes";


        try (Connection conn = this.conexion.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoHabitacion);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int mes = rs.getInt("mes") - 1; // Los meses en la base de datos son de 1 a 12, pero el array empieza en 0
                    countByMonth[mes] = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionReserva("Error al contar las reservas por mes.");
        }

        return countByMonth;
    }

    @Override
    public int countConcretasByType(ReservaVO reserva) throws ExcepcionReserva {
        int count = 0;

        // Consulta SQL para contar las reservas con las condiciones especificadas
        String sql = "SELECT COUNT(*) AS total " +
                "FROM reserva " +
                "WHERE tipo_Hab = ? " +
                "AND fecha_Llegada < ? " +
                "AND fecha_Salida > ?";

        try (Connection conn = this.conexion.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los valores en la consulta preparada
            stmt.setString(1, reserva.getTipo_habitacion().replaceAll("_"," ")); // Tipo de habitación
            stmt.setDate(2, reserva.getFechaLlegada());      // Fecha de llegada de la reserva pasada como parámetro
            stmt.setDate(3, reserva.getFechaSalida());       // Fecha de salida de la reserva pasada como parámetro

            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total"); // Obtener el conteo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcepcionReserva("Error al contar las reservas específicas.");
        }

        return count;
    }


}
