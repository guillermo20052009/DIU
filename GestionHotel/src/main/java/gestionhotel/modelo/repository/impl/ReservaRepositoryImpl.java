package gestionhotel.modelo.repository.impl;

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
            System.out.println(reservaVO);
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

// Asumiendo que `reservaVO` tiene los métodos getters correctos
            stmt.setDate(1, reservaVO.getFechaSalida());  // fecha_Salida como java.sql.Date
            stmt.setDate(2, reservaVO.getFechaLlegada());  // fecha_Llegada como java.sql.Date
            stmt.setString(3, reservaVO.getTipo_habitacion());  // tipo_Hab como String
            stmt.setBoolean(4, reservaVO.isFumador());  // fumador como boolean
            stmt.setString(5, reservaVO.getRegimen());  // regimen como String
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
            System.out.println("hola");

            return lastReservaId;
        } catch (SQLException var5) {
            throw new ExcepcionReserva("No se ha podido realizar la busqueda del ID");
        }
    }
}
