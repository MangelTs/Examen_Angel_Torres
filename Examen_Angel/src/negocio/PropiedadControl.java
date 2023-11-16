/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.PropiedadDAO;
import entidades.Propiedad;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ALUMNO
 */
public class PropiedadControl {
    private final PropiedadDAO DATOS;
    private Propiedad obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados;

    public PropiedadControl() {
        this.DATOS=new PropiedadDAO();
        this.obj=new Propiedad();
        this.registrosMostrados=0;
    }
    
    public DefaultTableModel listar(String texto){
        List<Propiedad> lista=new ArrayList();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos={"Id","Nombre","Dirección","Características","Estado","Propiedad Alquiler","Created_at","Update_at"};
        this.modeloTabla=new DefaultTableModel(null,titulos);        
        
        String[] registro = new String[8];
        
        this.registrosMostrados=0;
        for (Propiedad item:lista){
            registro[0]=Integer.toString(item.getId());
            registro[1]=item.getNombre();
            registro[2]=item.getDireccion();
            registro[3]=item.getCaracteristicas();
            registro[4]=item.getEstado();
            registro[5]=Double.toString(item.getPrecioAlquiler());
            registro[6]=item.getCreated_at();
            registro[7]=item.getUpdated_at();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla;
    }
    
    public String insertar(String nombre, String direccion, String caracteristicas, String estado, double precioalquiler){
        if (DATOS.existe(nombre)){
            return "El registro ya existe.";
        }else{
            obj.setNombre(nombre);
            obj.setDireccion(direccion);
            obj.setCaracteristicas(caracteristicas);
            obj.setEstado(estado);
            obj.setPrecioAlquiler(precioalquiler);
            if (DATOS.insertar(obj)){
                return "OK";
            }else{
                return "Error en el registro.";
            }
        }
    }
    
    public String actualizar(int id, String nombre, String nombreAnt, String direccion, String caracteristicas, String estado, double precioalquiler){
        if (nombre.equals(nombreAnt)){
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setDireccion(direccion);
            obj.setCaracteristicas(caracteristicas);
            obj.setEstado(estado);
            obj.setPrecioAlquiler(precioalquiler);
            if(DATOS.actualizar(obj)){
                return "OK";
            }else{
                return "Error en la actualización.";
            }
        }else{
            if (DATOS.existe(nombre)){
                return "El registro ya existe.";
            }else{
                obj.setId(id);
                obj.setNombre(nombre);
                obj.setDireccion(direccion);
                obj.setCaracteristicas(caracteristicas);
                obj.setEstado(estado);
                obj.setPrecioAlquiler(precioalquiler);
                if (DATOS.actualizar(obj)){
                    return "OK";
                }else{
                    return "Error en la actualización.";
                }
            }
        }
    }
    
    public String eliminar(int id) {
            if (DATOS.eliminar(id)) {
                return "OK";
            } else {
                return "Error al eliminar el registro.";
            }

    }
   
    public int totalMostrados(){
        return this.registrosMostrados;
    }
    
}
