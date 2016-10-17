/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Empleado;
import Model.UsuarioM;

/**
 *
 * @author Diego
 */
public class Usuario {


    private int idUser;
    private String contra;
    private String usuario;
    private Empleado idEmpleado;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
    public Usuario getLogin(Usuario user ){
        
        Usuario login= new Usuario(); 
        UsuarioM model = new UsuarioM();
        
        model.getUsuario(user);
        
       return login;
    }
    
   
}
