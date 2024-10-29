package Agenda.modelo.repository.impl;


import Agenda.Persona;
import Agenda.modelo.ExcepcionPersona;
import Agenda.modelo.PersonaVO;
import Agenda.modelo.repository.PersonaRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String direccion = rs.getString("Direccion");
                String ciudad = rs.getString("Ciudad");
                String codigoPostal = rs.getString("CodigoPostal");
                String fechaNacimiento = rs.getString("FechaNacimiento");
                Integer codigo = rs.getInt("codigo");
                this.persona = new PersonaVO(nombre,apellido,direccion,ciudad,codigoPostal,fechaNacimiento);
                this.persona.setCodigo(codigo);
                this.personas.add(this.persona);
            }

            this.conexion.desconectarBD(conn);
            return this.personas;
        } catch (SQLException var6) {
            throw new ExcepcionPersona("No se ha podido realizar la operación");
        }
    }

    public void addPersona(PersonaVO p) throws ExcepcionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            this.stmt = conn.createStatement();

            this.sentencia = "INSERT INTO persona (Nombre, Apellido, Direccion, Ciudad, CodigoPostal, FechaNacimiento) " +
                    "VALUES ('" + p.getNombre() + "', '" + p.getApellido() + "', '" + p.getDireccion() + "', '" +
                    p.getCiudad() + "', '" + p.getCodigoPostal() + "', '" + p.getFechaNacimiento() + "')";



            this.stmt.executeUpdate(this.sentencia);
            this.stmt.close();
            this.conexion.desconectarBD(conn);
        } catch (SQLException var3) {
            throw new ExcepcionPersona("No se ha podido realizar la operación");
        }
    }

    public void deletePersona(Integer idPersona) throws ExcepcionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            this.stmt = conn.createStatement();
            Statement comando = conn.createStatement();
            String sql = String.format("DELETE FROM persona WHERE codigo = %d", idPersona);
            comando.executeUpdate(sql);
            this.conexion.desconectarBD(conn);
        } catch (SQLException var5) {
            throw new ExcepcionPersona("No se ha podido realizar la eliminación");
        }
    }

    public void editPersona(PersonaVO personaVO) throws ExcepcionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            this.stmt = conn.createStatement();
            String sql = String.format(
                    "UPDATE monedas SET nombre = '%s', apellido = '%s', direccion = '%s', ciudad = '%s', codigoPostal = '%s', fechaNacimiento = '%s' WHERE codigo = %d",
                    personaVO.getNombre(),
                    personaVO.getApellido(),
                    personaVO.getDireccion(),
                    personaVO.getCiudad(),
                    personaVO.getCodigoPostal(),
                    personaVO.getFechaNacimiento(),
                    personaVO.getCodigo()
            );
            this.stmt.executeUpdate(sql);
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
