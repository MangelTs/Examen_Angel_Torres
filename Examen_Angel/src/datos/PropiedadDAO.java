/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import database.Conexion;
import datos.interfaces.CrudSimpleInterface;
import entidades.Propiedad;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ALUMNO
 */
public class PropiedadDAO implements CrudSimpleInterface<Propiedad>{

    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public PropiedadDAO() {
        CON=Conexion.getInstancia();
    }
    
    
    @Override
    public List<Propiedad> listar(String texto) {
        List<Propiedad> registros=new ArrayList();
        try {
            ps=CON.conectar().prepareStatement("SELECT * FROM propiedades WHERE nombre LIKE ?");
            ps.setString(1,"%" + texto +"%");
            rs=ps.executeQuery();
            while(rs.next()){
                registros.add(new Propiedad(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(5),rs.getDouble(6),rs.getString(7),rs.getString(8)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return registros;
    }

    @Override
    public boolean insertar(Propiedad obj) {
        resp=false;
        try {
            ps=CON.conectar().prepareStatement("INSERT INTO propiedades (nombre,direccion,caracteristicas,estado,precioalquiler) VALUES (?,?,?,?,?)");
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDireccion());
            ps.setString(3, obj.getCaracteristicas());
            ps.setString(4, obj.getEstado());
            ps.setDouble(5, obj.getPrecioAlquiler());
            if (ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Propiedad obj) {
        resp=false;
        try {
            ps=CON.conectar().prepareStatement("UPDATE propiedades SET nombre=?, direccion=?, caracteristicas=?, estado=?, precioalquiler=? WHERE id=?");
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDireccion());
            ps.setString(3, obj.getCaracteristicas());
            ps.setString(4, obj.getEstado());
            ps.setDouble(5, obj.getPrecioAlquiler());
            ps.setInt(6, obj.getId());
            if (ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean existe(String texto) {
        resp=false;
        try {
            ps=CON.conectar().prepareStatement("SELECT nombre FROM propiedades WHERE nombre=?");
            ps.setString(1, texto);
            rs=ps.executeQuery();
            rs.last();
            if(rs.getRow()>0){
                resp=true;
            }           
            ps.close();
            rs.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean eliminar(int id) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("DELETE FROM propiedades WHERE id = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }
    
}
