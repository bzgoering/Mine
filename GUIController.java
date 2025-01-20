package application;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class GuiController 
{
	//to - do
	/*
	 * read file to get use money
	 * save user total money (wallet) to file when cash out is pressed
	 */
	//user interface
	@FXML Slider BetSlider;
	@FXML Text BetAmountTxt;
	@FXML Slider BombSlider;
	@FXML Text BombTxt;
	@FXML Text UserMoneyTxt;
	@FXML Text Profit;
	@FXML Button PlayButton;
	@FXML Button PickRandButton;
	@FXML Button CashoutButton;
	@FXML Button SaveButton;
	
	//game variables
	double bet;
	double winnings;
	double profit;
	int bomb;
	int gem;
	double multiplier;
	
	//other variables
	private DecimalFormat money = new DecimalFormat("$###,###,###,###.00");
	
	//mines
	@FXML Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
	@FXML Button b11,b12,b13,b14,b15,b16,b17,b18,b19,b20;
	@FXML Button b21,b22,b23,b24,b25,b26,b27,b28,b29,b30;
	@FXML Button b31,b32,b33,b34,b35,b36,b37,b38,b39,b40;
	@FXML Button b41,b42,b43,b44,b45,b46,b47,b48,b49,b50;
	@FXML Button b51,b52,b53,b54,b55,b56,b57,b58,b59,b60;
	@FXML Button b61,b62,b63,b64,b65,b66,b67,b68,b69,b70;
	@FXML Button b71,b72,b73,b74,b75,b76,b77,b78,b79,b80;
	@FXML Button b81,b82,b83,b84,b85,b86,b87,b88,b89,b90;
	@FXML Button b91,b92,b93,b94,b95,b96,b97,b98,b99,b100;

	private ArrayList<Mine> mines = new ArrayList<>();
	
	//player variables
	private double wallet = 100000;
				
	@FXML //game start up
	private void initialize()
	{
		setVariables();
		//genKey();
		loadWallet();	
		saveWallet();
		
		//game GUI set up
		UserMoneyTxt.setText(money.format(wallet));
		
		PickRandButton.setVisible(false);
		CashoutButton.setVisible(false);
		
		configButtons();
	}
	//Game Functions
		//calculates multiplier based of chances of hitting bomb

		//bet amount method
	public void confirmBetSlider(MouseEvent event)
		{
			BetSlider.valueProperty().addListener(new ChangeListener<Number>()
			{
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) 
				{
					bet = Math.round(BetSlider.getValue() * 100)/100;
					BetAmountTxt.setText(money.format(bet));						
				}
			});
		}
		
		//bet amount method
	public void confirmBombSpin(MouseEvent event)
		{
			BombSlider.valueProperty().addListener(new ChangeListener<Number>()
			{
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) 
				{
					bomb = (int)BombSlider.getValue();
					gem = 100 - bomb;
					BombTxt.setText(bomb + "x");						
				}
			});
		}
	
		//play button
	public void buttonPlay(MouseEvent event)
		{
			if (bet > 0)
			{
				//GUI variable changes
				wallet -= bet;
				profit = 0 - bet;
				updateText();
				
				//gui changes
				inGame();
				
				//game variable changes
				getMultiplier();				
				setBombs();
			}
		}
	
	public void cashOut(MouseEvent event)
		{
			gameOver();
		}
		
	public void check(ActionEvent event)
		{
			Button buttonPressed = (Button) event.getSource();
			
			for (Mine x: mines)
			{
				if (x.getButt().equals(buttonPressed) && x.isBomb())
				{
					gameOver();
				}
				else if (x.getButt().equals(buttonPressed))
				{
					buttonPressed.setVisible(false);
					winnings += (bet * multiplier);
					gem --;
					getMultiplier();
					Profit.setText(money.format(profit+winnings));
				}
			}
		}
	
	public void pickRandom()
	{
		Mine mine= getRandomMine();
		
		if (mine.isBomb())
		{
			gameOver();
		}
		else
		{
			mine.getButt().setVisible(false);
			winnings += (bet * multiplier) * 1.5;
			gem --;
			getMultiplier();
			Profit.setText(money.format(profit+winnings));
		}
	}
	
	public void admin()
	{
		Scanner scan = new Scanner(System.in);
		String PassCode = "1111";
		String input;
		
		System.out.print("Enter PassCode: ");
		input = scan.next();
		scan.close();
		
		if (input.equals(PassCode))
		{
			wallet += 1000000000;
			colorButtons();
			updateText();
		}
	}

	public void save()
	{
		saveWallet();
	}
	
	//helpers
	private void setVariables()
	{
		//default variable settings
		bet = 0;
		bomb = 1;
		gem = 99;
		winnings = 0;
		profit = 0;
		Profit.setText("$0.00");
		BetSlider.setMax(wallet);
	}
	private void configButtons()
	{
		addButtons();
		noGame();
	}
	private void addButtons()
	{
		mines.add(new Mine(b1));mines.add(new Mine(b2));mines.add(new Mine(b3));mines.add(new Mine(b4));mines.add(new Mine(b5));mines.add(new Mine(b6));mines.add(new Mine(b7));mines.add(new Mine(b8));mines.add(new Mine(b9));mines.add(new Mine(b10));
		mines.add(new Mine(b11));mines.add(new Mine(b12));mines.add(new Mine(b13));mines.add(new Mine(b14));mines.add(new Mine(b15));mines.add(new Mine(b16));mines.add(new Mine(b17));mines.add(new Mine(b18));mines.add(new Mine(b19));mines.add(new Mine(b20));
		mines.add(new Mine(b21));mines.add(new Mine(b22));mines.add(new Mine(b23));mines.add(new Mine(b24));mines.add(new Mine(b25));mines.add(new Mine(b26));mines.add(new Mine(b27));mines.add(new Mine(b28));mines.add(new Mine(b29));mines.add(new Mine(b30));
		mines.add(new Mine(b31));mines.add(new Mine(b32));mines.add(new Mine(b33));mines.add(new Mine(b34));mines.add(new Mine(b35));mines.add(new Mine(b36));mines.add(new Mine(b37));mines.add(new Mine(b38));mines.add(new Mine(b39));mines.add(new Mine(b40));
		mines.add(new Mine(b41));mines.add(new Mine(b42));mines.add(new Mine(b43));mines.add(new Mine(b44));mines.add(new Mine(b45));mines.add(new Mine(b46));mines.add(new Mine(b47));mines.add(new Mine(b48));mines.add(new Mine(b49));mines.add(new Mine(b50));
		mines.add(new Mine(b51));mines.add(new Mine(b52));mines.add(new Mine(b53));mines.add(new Mine(b54));mines.add(new Mine(b55));mines.add(new Mine(b56));mines.add(new Mine(b57));mines.add(new Mine(b58));mines.add(new Mine(b59));mines.add(new Mine(b60));
		mines.add(new Mine(b61));mines.add(new Mine(b62));mines.add(new Mine(b63));mines.add(new Mine(b64));mines.add(new Mine(b65));mines.add(new Mine(b66));mines.add(new Mine(b67));mines.add(new Mine(b68));mines.add(new Mine(b69));mines.add(new Mine(b70));
		mines.add(new Mine(b71));mines.add(new Mine(b72));mines.add(new Mine(b73));mines.add(new Mine(b74));mines.add(new Mine(b75));mines.add(new Mine(b76));mines.add(new Mine(b77));mines.add(new Mine(b78));mines.add(new Mine(b79));mines.add(new Mine(b80));
		mines.add(new Mine(b81));mines.add(new Mine(b82));mines.add(new Mine(b83));mines.add(new Mine(b84));mines.add(new Mine(b85));mines.add(new Mine(b86));mines.add(new Mine(b87));mines.add(new Mine(b88));mines.add(new Mine(b89));mines.add(new Mine(b90));
		mines.add(new Mine(b91));mines.add(new Mine(b92));mines.add(new Mine(b93));mines.add(new Mine(b94));mines.add(new Mine(b95));mines.add(new Mine(b96));mines.add(new Mine(b97));mines.add(new Mine(b98));mines.add(new Mine(b99));mines.add(new Mine(b100));
	}
	private void updateText()
	{
		UserMoneyTxt.setText(money.format(wallet));
		Profit.setText(money.format(profit));
	}
	private void inGame()
	{
		PlayButton.setVisible(false);
		PickRandButton.setVisible(true);
		CashoutButton.setVisible(true);
		BetSlider.setVisible(false);
		BombSlider.setVisible(false);
		setMinesVisible(true);
	}
	private void noGame()
	{
		PlayButton.setVisible(true);
		PickRandButton.setVisible(false);
		CashoutButton.setVisible(false);
		BetSlider.setVisible(true);
		BombSlider.setVisible(true);
		setMinesVisible(false);
	}
	private void getMultiplier() 
	{
		multiplier = ((double)bomb/gem)*2.0;
	}
	private void setMinesVisible(boolean isVis)
	{
		for (Mine x: mines)
		{
			x.getButt().setVisible(isVis);
		}
	}
	private void setBombs()
	{
		Random rand = new Random();
		for (int x = 0; x < bomb;)
		{
			int index = rand.nextInt(mines.size()-1);
			
			if (mines.get(index).isBomb() == false)
			{
				mines.get(index).setBomb(true);
				x++;
			}
		}
	}
	private Mine getRandomMine()
	{
		Random rand = new Random();
		int index = rand.nextInt(mines.size()-1);
		return mines.get(index);
	}
	private void colorButtons()
	{
		for (Mine x: mines)
		{
			if (x.isBomb())
			{
				x.getButt().setStyle("-fx-background-color: #FF0000");
			}
		}
	}
	private void gameOver()
	{
		wallet += winnings;
		noGame();
		updateText();
		setVariables();
		saveWallet();
	}
	private void genKey() 
	{
		try 
		{
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	        keyGen.init(256, new SecureRandom());
	        SecretKey seckey = keyGen.generateKey();

	        // Save the key
	        byte[] keyBytes = seckey.getEncoded();
	        FileOutputStream keyOut = new FileOutputStream("secret.key");
	        keyOut.write(keyBytes);
	        keyOut.close();

	        System.out.println("Key generated and saved successfully!");
	    } 
		catch (Exception e) 
		{
	    	System.out.println("Error generating key");
	    }
	 }
	 // Method to read and decrypt the wallet amount from Money.txt
	 private void loadWallet() 
	 {
		 try 
		 {
			 System.out.println("Starting decryption process...");

	         // Read the secret key from the file
	         System.out.println("Reading the secret key...");
	         byte[] keyBytes = Files.readAllBytes(new File("secret.key").toPath());
	         SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

	         // Read the encrypted content from the file
	         System.out.println("Reading the encrypted content...");
	         byte[] encryptedBytes = Files.readAllBytes(new File("Money.txt").toPath());
	         System.out.println("Encrypted bytes read from file: " + Base64.getEncoder().encodeToString(encryptedBytes));

	         // Decrypt the content
	         System.out.println("Initializing decryption cipher...");
	         Cipher cipher = Cipher.getInstance("AES");
	         cipher.init(Cipher.DECRYPT_MODE, secretKey);
	         System.out.println("Decrypting the content...");
	         byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
	         String decryptedContent = new String(decryptedBytes);
	         System.out.println("Decrypted content (String): " + decryptedContent);

	         // Convert the decrypted content to a double
	         wallet = Double.parseDouble(decryptedContent);
	         System.out.println("Decrypted double: " + wallet);

	     } 
		 catch (Exception e) 
		 {
			 System.out.println("Error decrypting the data");
	     }
	 }
	 // Method to encrypt and save the wallet amount to Money.txt
	 private void saveWallet() 
	 {
		 try 
		 {
			 // Load the secret key
		     byte[] keyBytes = Files.readAllBytes(new File("secret.key").toPath());
		     SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
	
		     // Load content
		     String content = String.valueOf(wallet);
		     System.out.println("Original content: " + content);
		     byte[] contentBytes = content.getBytes();
	
		     // Encrypt the content
		     Cipher cipher = Cipher.getInstance("AES");
		     cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		     byte[] encryptedBytes = cipher.doFinal(contentBytes);
		     System.out.println("Encrypted bytes: " + Base64.getEncoder().encodeToString(encryptedBytes));
	
		     // Save to file
		     FileOutputStream fileOut = new FileOutputStream("Money.txt");
		     fileOut.write(encryptedBytes);
		     fileOut.close();
		     System.out.println("Encryption completed successfully!");
	        } 
		  catch (Exception e)
		  {
			  System.out.println("Error encrypting the data");
	      }
	  }
}
