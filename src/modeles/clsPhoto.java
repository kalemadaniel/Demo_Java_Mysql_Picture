/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles;

import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ulb-Kalema
 */
public class clsPhoto {
    private int id_photo;
    private Byte[] image;
    private String chemin;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    String url = "jdbc:mysql://localhost:3306/bd_Demo_Java_Mysql_Picture";
    String user = "root";
    String pass = "root";
    FileNameExtensionFilter filter = null;
    public String cheminPhoto = "";
    ImageIcon format = null;

    /**
     * @return the id_photo
     */
    public int getId_photo() {
        return id_photo;
    }

    /**
     * @param id_photo the id_photo to set
     */
    public void setId_photo(int id_photo) {
        this.id_photo = id_photo;
    }
    /**
     * @return the image
     */
    public Byte[] getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Byte[] image) {
        this.image = image;
    }

    /**
     * @return the chemin
     */
    public String getChemin() {
        return chemin;
    }

    /**
     * @param chemin the chemin to set
     */
    public void setChemin(String chemin) {
        this.chemin = chemin;
    }
    
    
    public void InsererPhoto() throws Exception {
        try {
            InputStream picture =new FileInputStream (new File(cheminPhoto));
            con = DriverManager.getConnection(url, user, pass);
            st = con.prepareStatement("INSERT INTO `tb_profil`(`ch_photo`) VALUES (?)");
            st.setBinaryStream(1, picture);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Photo ajouté avec succès");
        } catch (SQLException | HeadlessException ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public void retourphoto(JLabel lblphoto){
        try {
            con = DriverManager.getConnection(url, user, pass);
            st = con.prepareStatement("SELECT ch_photo FROM tb_profil "
                    + "WHERE id_photo=(SELECT MAX(id_photo) FROM tb_profil)");
            rs = st.executeQuery();
            if (rs.next()) {
                byte[] bt=rs.getBytes("ch_photo");
                ImageIcon Format = new ImageIcon(bt);
                Image img = Format.getImage().getScaledInstance(lblphoto.getWidth(), lblphoto.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon Traité = new ImageIcon(img);
                lblphoto.setIcon(Traité);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" + e.getMessage());
        }
    }
    
    public void choisirPhoto(JLabel photo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            cheminPhoto = selectedFile.getAbsolutePath();
            ImageIcon imgIcon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
            java.awt.Image img = imgIcon.getImage().getScaledInstance(photo.getWidth(), photo.getHeight(), java.awt.Image.SCALE_DEFAULT);
            ImageIcon imgIco = new ImageIcon(img);
            photo.setIcon(imgIco);
        }
    }
    
    
}
