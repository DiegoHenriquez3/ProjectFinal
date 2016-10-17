/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Usuario;
import Model.UsuarioM;
import View.Board;
import View.Login;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauricio
 */
public class UsuarioController {
    
     public void getLogin(String user,String pass){
        
        Usuario login= new Usuario(); 
        UsuarioM model = new UsuarioM();
        
        login.setUsuario(user);
        login.setContra(pass);
        
        login=model.getUsuario(login);
        Login lg= new Login();
        if(login!=null){
            
            Board board = new Board();
            board.setVisible(true);
            lg.setVisible(false);
            
        }
        else{
             JOptionPane.showMessageDialog(lg, "Usuario o Contrasea Incorrecta","Error Acces", 0);
        }
        
    }
    
    
    
    
}
