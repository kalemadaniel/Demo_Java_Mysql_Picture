# Demo_Java_Mysql_Picture
## Introduction 

Dans ce mini projet nous allons découvrir comment insérer et récupérer une image à partir d’une table de base de données MySQL à l’aide de Java. La plupart du temps, les images sont stockées dans des répertoires et stockent le chemin des images dans des tables de base de données. mais dans certains scénarios, nous devons insérer les images dans des tables de base de données au format binaire...

## Pré-requis

Les applications nécessaires pour contribuer au dévéloppement de ce projet :

- Téléchargez et installez JDK sur votre système.
- Netbeans (https://www.oracle.com/technetwork/java/javase/downloads/jdk-netbeans-jsp-3413139-esa.html)
- Téléchargez et installez le serveur MySql Xampp (https://www.apachefriends.org/fr/download.html)
- Téléchargez le connecteur(mysql-connector-java-5.1.49.zip) ou les pilotes MySQL pour utiliser MySQL avec Java.

## Installation

Les étapes pour installer le programme sont :

1. **Télécharger le dossier**
2. **Importer le projet**
3. **Changer les paramètres de connexion** 
4. **Executer le projet**

ET si vous souhaitez constituer un exécutable par la suite il faut faire le ``clean and build ``

## Comment ça marche ?


### Voici mes requêtes SQL 

Ensuite il faut creer la structure SQL qui correspond à notre fichier JSON

```
CREATE OR REPLACE DATABASE bd_Demo_Java_Mysql_Picture;
USE bd_Demo_Java_Mysql_Picture;
CREATE TABLE tb_profil 
(
    id_photo INT AUTO_INCREMENT PRIMARY KEY,
    ch_photo LONGBLOB
);

```
Dans MySQL, lorsque nous utilisons le type blob pour stocker les données, il ne supporte que 5 ko comme capacité d’image. cela dépend du SGBD. selon certains SGBD, le type d’objet blob prend en charge une grande capacité.

### Voici le code Java pour parcourir une photo

Premièrement il faut parcourir une photo puis il va nous la placée sur le controle Swing (JLabel)

```
//les déclarations des variables
    FileNameExtensionFilter filter = null;
    public String cheminPhoto = "";
    ImageIcon format = null;

//La procedure 
    void choisirPhoto(JLabel photo) {
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

```

### Enregistrer la photo 

Après avoir selectionner une photo, on l'enregistre dans la base des données. Voici le code Java pour enregistrer la photo 

```
    void InsererPhoto() throws Exception {
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
```

### Charger la photo 

Si la photo est enregistrée dans la base des données, on peut maintenant la chargée de la base des données jusqu'au controle Swing de Java. Code java pour charger la photo

```
    void retourphoto(JLabel lblphoto){
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
```

### La solution interface graphique

A l'ouverture de l'application, ça se presente comme ceci


Il faut ensuite parcourir et trouver la photo


Enregistrer la photo


Cliquer sur Charger


## Pour les contributions

J’accorde aux utilisateurs les droits d'utiliser, d'étudier, de modifier et de distribuer le logiciel et son code source à quiconque et à n'importe quelle fin.

## Versions
Ceci correspond à un état donné de l'évolution du logiciel et j'utilise le versionnage. Ci dessous les versions produites

**Dernière version stable :** 1.0

## Auteurs
le(s) auteur(s) du projet est(sont) :
* **Kalema daniel jonathan** _alias_ [@kalemadaniel](https://github.com/kalemadaniel)

## License

Ce projet est sous licence **``open source``** 
