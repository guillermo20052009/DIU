package gestionhotel.modelo.repository.impl;

import gestionhotel.modelo.PersonaVO;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.PersonaRepository;

import java.sql.*;
import java.util.ArrayList;


public class PersonaRepositoryImpl implements PersonaRepository {
    private final Conexion conexion = new Conexion();
    private Statement stmt;
    private String sentencia;
    private ArrayList<PersonaVO> personas;
    private PersonaVO persona;

    public PersonaRepositoryImpl() {
    }

    public ArrayList<PersonaVO> ObtenerListaPersonas() throws ExcepcionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            this.personas = new ArrayList();
            this.stmt = conn.createStatement();
            this.sentencia = "SELECT * FROM persona";
            ResultSet rs = this.stmt.executeQuery(this.sentencia);

            while(rs.next()) {
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String direccion = rs.getString("direccion");
                String localidad = rs.getString("localidad");
                String provincia = rs.getString("provincia");
                String DNI = rs.getString("DNI");
                this.persona = new PersonaVO(DNI,nombre,apellidos,direccion,localidad,provincia);
                this.personas.add(this.persona);
            }

            this.conexion.desconectarBD(conn);
            return this.personas;
        } catch (SQLException var6) {
            throw new ExcepcionPersona("No se ha podido realizar la operación");
        }
    }

    public void addPersona(PersonaVO personaVO) throws ExcepcionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            String sql = "INSERT INTO persona (nombre, apellidos, direccion, localidad, provincia, DNI) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, personaVO.getNombre());
            stmt.setString(2, personaVO.getApellidos());
            stmt.setString(3, personaVO.getDireccion());
            stmt.setString(4, personaVO.getLocalidad());
            stmt.setString(5, personaVO.getProvincia());
            stmt.setString(6, personaVO.getDNI());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();  // Imprime el detalle del error
            throw new ExcepcionPersona("No se ha podido realizar la edición");
        }
    }

    public void deletePersona(String dniPersona) throws ExcepcionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            String sql = "DELETE FROM persona WHERE DNI = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dniPersona);  // Asignar el valor de dniPersona de forma segur
            pstmt.executeUpdate();
            this.conexion.desconectarBD(conn);
        } catch (SQLException var5) {
            throw new ExcepcionPersona("No se ha podido realizar la eliminación");
        }
    }


    public void editPersona(PersonaVO personaVO) throws ExcepcionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            String sql = "UPDATE persona SET nombre = ?, apellidos = ?, direccion = ?, localidad = ?, provincia = ? WHERE DNI = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, personaVO.getNombre());
            stmt.setString(2, personaVO.getApellidos());
            stmt.setString(3, personaVO.getDireccion());
            stmt.setString(4, personaVO.getLocalidad());
            stmt.setString(5, personaVO.getProvincia());
            stmt.setString(6, personaVO.getDNI());
            stmt.executeUpdate();
        } catch (Exception var4) {
            throw new ExcepcionPersona("No se ha podido realizar la edición");
        }
    }

    public int lastId() throws ExcepcionPersona{
        int lastPersonaId = 0;

        try {
            Connection conn = this.conexion.conectarBD();
            Statement comando = conn.createStatement();

            for(ResultSet registro = comando.executeQuery("SELECT codigo FROM persona ORDER BY codigo DESC LIMIT 1"); registro.next(); lastPersonaId = registro.getInt("codigo")) {
            }

            return lastPersonaId;
        } catch (SQLException var5) {
            throw new ExcepcionPersona("No se ha podido realizar la busqueda del ID");
        }
    }
}
