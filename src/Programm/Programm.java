package Programm;

import Datenbank.Datenbank;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dfleuren on 04.03.2016.
 */
public class Programm {

    public Programm() {
        Datenbank db = null;

        try {
            db = Datenbank.getInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("Datenbanktreiber nicht gefunden!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            db.dropIfExists("blob");
            db.dropIfExists("test");
            db.createTable("test");
            db.createTableMitBlob("blob");

            db.insertOrUpdate("Flurry", 10815);
            db.insertOrUpdate("Uwe", 4711);
            db.insertOrUpdate("Uwe", 4712);

            try {
                FileInputStream fis = new FileInputStream("Deadpool.jpeg");
                db.insertOrUpdateBlob("Flurry",fis);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileInputStream fis = new FileInputStream("sound.png");
                db.insertOrUpdateBlob("Flurry",fis);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                Icon icon = new ImageIcon(
                        ImageIO.read(db.getBlob("Flurry")));
                JOptionPane.showMessageDialog(
                        null,"Tolles Bid aus der DB", "Bildanzeige", JOptionPane.INFORMATION_MESSAGE, icon
                );

            }catch (IOException e){
                e.printStackTrace();
            }


            //selectabfrage
            db.printTable("test");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
