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

    private Usuario login;
    private UsuarioM model;

    public Usuario getLogin() {
        return login;
    }

    public void setLogin(Usuario login) {
        this.login = login;
    }

    public UsuarioM getModel() {
        return model;
    }

    public void setModel(UsuarioM model) {
        this.model = model;
    }

    public void getLogin(String user, String pass,Login form) {

        login = new Usuario();
        model = new UsuarioM();

        login.setUsuario(user);
        login.setContra(pass);

        login = model.getUsuario(login);
      
        if (login != null) {
            Board board = new Board(login);
            board.setVisible(true);
            form.dispose();
            

        } else {
            JOptionPane.showMessageDialog(null, "Usuario o Contrasea Incorrecta", "Error Acces", 0);
        }

    }

}
