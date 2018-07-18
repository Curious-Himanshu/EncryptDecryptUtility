package sample;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.*;
import java.awt.datatransfer.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.text.Text;
import javafx.scene.control.Label;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class Controller
{
    String initVector="1a2e3i5o7u11u13@";
    String key="8690333381690951";
    byte[] encrypted;
    @FXML private TextField txtVal;
    @FXML Label lblMsg;
    public void initialize(){
        txtVal.setText(null);
    }
    public void encryptFun()
    {
        String value= txtVal.getText();
        try {
            lblMsg.setText("");
              IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64(encrypted));
            String encodedString = new String((Base64.encodeBase64(encrypted)));
            System.out.println(encodedString);
            txtVal.setText(encodedString);

         }
        catch (NullPointerException eNull)
        {
            lblMsg.setText("Please Enter Text");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void decryptFun()
    {
        try {
            lblMsg.setText("");
            String value= txtVal.getText();
            System.out.println(value);
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] decodedValue= new Base64().decode(value.getBytes());
            byte[] decodedVal= cipher.doFinal(decodedValue);
            String output= new String(decodedVal);
            System.out.println(output);
            txtVal.setText(output);
         }
         catch (javax.crypto.IllegalBlockSizeException e)
         {
             System.out.println("Invalid Encryoted Value");
             lblMsg.setText("Invalid Encrypted Value");
         }
         catch (NullPointerException eNull)
         {
             lblMsg.setText("Please Enter Text");
         }
         catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clearFun()
    {
        txtVal.setText(null);
        lblMsg.setText(null);
    }
    public void copyFun()
    {
        System.out.println("Copy Click");
        StringSelection stringSelection = new StringSelection(txtVal.getText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }
}
