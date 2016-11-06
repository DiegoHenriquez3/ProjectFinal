/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author Diego
 */
public class Validation extends javax.swing.JFrame {

    public Validation() {
    }

    public void justText(JTextField txt) {

        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();
                System.err.println();
                if (txt.getText().length() == 45) {
                    getToolkit().beep();

                    e.consume();

                } else if (c > 32 && c < 35) {
                    getToolkit().beep();

                    e.consume();
                } else if (c > 35 && c <=47) {
                    getToolkit().beep();

                    e.consume();

                } else if (c >= 58 && c <= 64) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 91 && c <= 96) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 123) {
                    getToolkit().beep();

                    e.consume();
                }

                }

                @Override
                public void keyPressed
                (KeyEvent e
                
                
                ) {

            }

            @Override
                public void keyReleased
                (KeyEvent e
                
                
            
            ) {

            }
        });

    }

    public void isNit(JTextField txt) {

        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (Character.isLetter(c) || txt.getText().length() == 14) {
                    getToolkit().beep();

                    e.consume();

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void isDui(JTextField txt) {

        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (Character.isLetter(c) || txt.getText().length() == 9) {
                    getToolkit().beep();

                    e.consume();

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void isPhoneNumber(JTextField txt) {

        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                System.err.println();
                if (Character.isLetter(c) || txt.getText().length() == 8) {
                    getToolkit().beep();

                    e.consume();

                } else if (c >= 32 && c <= 47) {
                    getToolkit().beep();

                    e.consume();

                } else if (c >= 58 && c <= 64) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 91 && c <= 96) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 123) {
                    getToolkit().beep();

                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void isNumber(JTextField txt) {

        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                System.err.println();
                if (Character.isLetter(c) || txt.getText().length() == 5) {
                    getToolkit().beep();

                    e.consume();

                } else if (c >= 32 && c <= 47) {
                    getToolkit().beep();

                    e.consume();

                } else if (c >= 58 && c <= 64) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 91 && c <= 96) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 123) {
                    getToolkit().beep();

                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void isName(JTextField txt) {

        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                System.err.println();
                if (Character.isDigit(c) || txt.getText().length() == 25) {
                    getToolkit().beep();

                    e.consume();

                } else if (c >= 32 && c <= 47) {
                    getToolkit().beep();

                    e.consume();

                } else if (c >= 58 && c <= 64) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 91 && c <= 96) {
                    getToolkit().beep();

                    e.consume();
                } else if (c >= 123) {
                    getToolkit().beep();

                    e.consume();
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

}
