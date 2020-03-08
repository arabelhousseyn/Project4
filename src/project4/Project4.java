
package project4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.*;

public class Project4 extends JFrame {
    
    private JLabel id;
    private JLabel adress;
    private JLabel bed;
    private JLabel square;
    private JLabel price;
    ////////////////////////////////
    private JTextField id_txt;
    private JTextField adress_txt;
    private JTextField bed_txt;
    private JTextField square_txt;
    private JTextField price_txt;
    //////////////////////////////////
    private JButton process;
    private JButton change;
    ////////////////////////////////
    private JComboBox insert;
    private JComboBox sale;
    ///////////////////////////////
    private String db = "jdbc:mysql://localhost/estate" + "?useUnicode=yes&characterEncoding=UTF-8";
    private String user = "root";
    private String pass = "";
    private Connection con = null;
    private PreparedStatement stm = null;
    private ResultSet res =null;
    private Property data;
    
    
        
    
    private void design()
    {
        id = new JLabel("Transaction No:");
        id.setBounds(20, 20, 100, 20);
        
        adress = new JLabel("Adress:");
        adress.setBounds(20, 60, 100, 20);
        
        bed = new JLabel("Bedrooms");
        bed.setBounds(20, 100, 100, 20);
        
        square = new JLabel("Square Footage:");
        square.setBounds(20, 140, 100, 20);
        
        price = new JLabel("Price:");
        price.setBounds(20, 180, 100, 20);
        ///////////////////////////////////////////
        
        id_txt = new JTextField();
        id_txt.setBounds(140, 20, 100, 20);
        
        adress_txt = new JTextField("a");
        adress_txt.setBounds(140, 60, 100, 20);
        
        bed_txt = new JTextField("1");
        bed_txt.setBounds(140, 100, 100, 20);
        
        square_txt = new JTextField("1");
        square_txt.setBounds(140, 140, 100, 20);
        
        price_txt = new JTextField("1");
        price_txt.setBounds(140, 180, 100, 20);
        ///////////////////////////////////////
        process = new JButton("Process");
        process.setBounds(20, 220, 120, 20);
        
        change = new JButton("Change Status");
        change.setBounds(20, 250, 120, 20);
        ///////////////////////////////////////
        insert = new JComboBox();
        insert.setBounds(160, 220, 120, 20);
        insert.addItem("Insert");
        insert.addItem("Delete");
        insert.addItem("Find");
        
        sale = new JComboBox();
        sale.setBounds(160, 250, 120, 20);
        sale.addItem(Status.FOR_SALE.toString());
        sale.addItem(Status.UNDER_CONTRACT.toString());
        sale.addItem(Status.SOLD.toString());   
    }
   
    
    private void process() throws SQLException
    {
        String idd = id_txt.getText();
        String adresss = adress_txt.getText();
        String bedd = bed_txt.getText();
         String squaree = square_txt.getText();
         String pricee = price_txt.getText();
         String sql = ""; 
         
        data = new Property(adresss,Integer.parseInt(bedd),Integer.parseInt(squaree),Double.valueOf(pricee));
        data.changeState(sale.getSelectedItem().toString());
        String statu = data.getStaut();
        String omp = insert.getSelectedItem().toString();
            if(omp == "Insert")
                 {
                     sql = "INSERT INTO data (id, adress, bed, square, price, statu) VALUES (?,?,?,?,?,?)";
                  
                       con = DriverManager.getConnection(db, user, pass);
                       stm = con.prepareStatement(sql);
                       stm.setInt(1, Integer.parseInt(idd));
                       stm.setString(2, data.getAdress());
                       stm.setInt(3, data.getBedrooms());
                       stm.setInt(4, data.getSquare());
                       stm.setDouble(5, data.getPrice());
                       stm.setString(6, statu);
                       stm.executeUpdate();
                       JOptionPane.showMessageDialog(null, "thank you", "sucess", JOptionPane.INFORMATION_MESSAGE);
                       idd.equals("");adresss.equals("");bedd.equals("");squaree.equals("");pricee.equals("");
                  }
        if(omp == "Delete")
        {
            idd = id_txt.getText();
            adresss = adress_txt.getText();
            bedd = bed_txt.getText();
            squaree = square_txt.getText();
            pricee = price_txt.getText();
            sql = "DELETE FROM data WHERE id=?";
            con = DriverManager.getConnection(db, user, pass);
            stm = (com.mysql.jdbc.PreparedStatement)con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(idd));
            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "thank you ", "sucess", JOptionPane.INFORMATION_MESSAGE);
            idd.equals("");adresss.equals("");bedd.equals("");squaree.equals("");pricee.equals("");
            
        }
        if(omp == "Find")
        {
            idd = id_txt.getText();
            adresss = adress_txt.getText();
            bedd = bed_txt.getText();
            squaree = square_txt.getText();
            pricee = price_txt.getText();
            statu = sale.getSelectedItem().toString();
            sql = "SELECT * FROM data WHERE id=?";
            try{
                con = DriverManager.getConnection(db, user, pass);
            stm = (com.mysql.jdbc.PreparedStatement)con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(idd));
            res = stm.executeQuery();
            while(res.next())
            {
                adress_txt.setText(res.getString(2));
                bed_txt.setText(res.getString(3));
                square_txt.setText(res.getString(4));
                price_txt.setText(res.getString(5));
                sale.setSelectedItem(res.getString(6));
                if(Integer.parseInt(idd) == res.getInt(1))
                {
                    JOptionPane.showMessageDialog(null, "found ", "sucess", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "not found ", "warning", JOptionPane.WARNING_MESSAGE);
                    adress_txt.setText("none");
                    bed_txt.setText("0");
                    square_txt.setText("0");
                    price_txt.setText("0");
                }
                
            }
             
            
            idd.equals("");adresss.equals("");bedd.equals("");squaree.equals("");pricee.equals("");
            
            }catch(Exception ff)
            {
                JOptionPane.showMessageDialog(null, "not found ", "warning", JOptionPane.WARNING_MESSAGE);
            }
               
        } 
    }
   
    private void change() throws SQLException
    {
       String sql = "UPDATE data SET statu=? WHERE id=?";
       con = DriverManager.getConnection(db, user, pass);
       stm = (com.mysql.jdbc.PreparedStatement)con.prepareStatement(sql);
       stm.setString(1, sale.getSelectedItem().toString());
       stm.setInt(2, Integer.parseInt(id_txt.getText()));
       stm.executeUpdate();
       JOptionPane.showMessageDialog(null, "updated ", "sucess", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public Project4()
    {
        init();
    }
    private void init()
    {
        design();
        
        process.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    process();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Alleredy exist", "warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    change();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "warning", "warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setTitle("workShop");
        add(id);add(adress);add(bed);add(square);add(price);
        add(id_txt);add(adress_txt);add(square_txt);add(bed_txt);add(price_txt);
        add(process);add(change);add(insert);add(sale);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Project4();
            }
        });
       
       
    }
    
}
