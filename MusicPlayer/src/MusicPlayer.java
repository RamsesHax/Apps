import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MusicPlayer implements ChangeListener , ActionListener , MouseListener , ListSelectionListener{
			
			JFrame frame;
			JPanel panel;
			JLabel label;
			JSlider slider;
			JProgressBar progBar;
			JScrollPane scrollBar;
			JList<String> list;
			DefaultListModel<String> model;
			JButton addButton ;
			JButton deleteButton;
			JButton frdButton;
			JButton bkdButton;
			JButton playButton;
			JButton pauseButton;
			MarqueePanel textCaseMusicPlaying;
			JLabel text;
			File file;
			FileInputStream fileInputStream;
			BufferedInputStream bufferedInputStream;
			Player player;
			int totalLength;
		    int pause;
		    int testPlay = 4;
		    int posLive;
		    MusicPlayer(){
		    	GUI();
		    }
		    int maxProgBar;
		    int currProgBar;
		    
		public void GUI(){
			
			frame = new JFrame("Music Player");
			frame.setSize(new Dimension(1000,350));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			try {
				frame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			text = new JLabel("Now playing:");
			text.setBounds(500,0,400,70);
			text.setFont(loadFont("CHILLER.ttf" ,60 ,Font.TYPE1_FONT ));
			text.setForeground(Color.black);
			
			
			pauseButton = new JButton("pause");
			pauseButton.setFont(loadFont("CHILLER.ttf" ,30 ,Font.TYPE1_FONT ));
			pauseButton.setForeground(new Color(192, 217, 235));
			pauseButton.setFocusable(false);
			pauseButton.setBounds(615,175,180,25);
			pauseButton.setBackground(new Color(28, 34, 38));
			pauseButton.setBorder(BorderFactory.createBevelBorder(0 , new Color(94, 77, 105) , Color.DARK_GRAY));
			pauseButton.addActionListener(this);
			
			playButton = new JButton("play");
			playButton.setFont(loadFont("CHILLER.ttf" ,30 ,Font.TYPE1_FONT ));
			playButton.setForeground(new Color(192, 217, 235));
			playButton.setFocusable(false);
			playButton.setBounds(440,175,180,25);
			playButton.setBackground(new Color(28, 34, 38));
			playButton.setBorder(BorderFactory.createBevelBorder(0 , new Color(94, 77, 105) , Color.DARK_GRAY));
			playButton.addActionListener(this);
			
			bkdButton = new JButton("<<");
			bkdButton.setFont(loadFont("CHILLER.ttf" ,30 ,Font.TYPE1_FONT ));
			bkdButton.setForeground(new Color(192, 217, 235));
			bkdButton.setFocusable(false);
			bkdButton.setBounds(310,150,125,50);
			bkdButton.setBackground(new Color(28, 34, 38));
			bkdButton.setBorder(BorderFactory.createBevelBorder(0 , new Color(94, 77, 105) , Color.DARK_GRAY));
			bkdButton.addActionListener(this);
			
			frdButton= new JButton(">>");
			frdButton.setFont(loadFont("CHILLER.ttf" ,30 ,Font.TYPE1_FONT ));
			frdButton.setForeground(new Color(192, 217, 235));
			frdButton.setFocusable(false);
			frdButton.setBounds(800,150,125,50);
			frdButton.setBackground(new Color(28, 34, 38));
			frdButton.setBorder(BorderFactory.createBevelBorder(0 , new Color(94, 77, 105) , Color.DARK_GRAY));
			frdButton.addActionListener(this);
			
			deleteButton = new JButton("delete song");
			deleteButton.setBounds(126, 17, 124,21);
			deleteButton.setFocusable(false);
			deleteButton.setBackground(new Color(28, 34, 38));
			deleteButton.setForeground(new Color(192, 217, 235));
			deleteButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(28, 34, 90)));
			deleteButton.addActionListener(this);
			
			addButton = new JButton("add");
			addButton.setBounds(0, 17, 124,21);
			addButton.setFocusable(false);
			addButton.setBackground(new Color(28, 34, 38));
			addButton.setForeground(new Color(192, 217, 235));
			addButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(28, 34, 90)));
			addButton.addActionListener(this);
			
			list = new JList<>();
			model=new DefaultListModel<>();
			list.setModel(model);
			list.setCursor(new Cursor(Cursor.HAND_CURSOR));
			list.setBorder(BorderFactory.createLoweredBevelBorder());
			list.setBackground(new Color(28, 34, 38));
			list.setForeground(new Color(126, 247, 140));
			list.setOpaque(true);
			list.getSelectionModel().addListSelectionListener(this);
			
			file = new File("Songs.txt");
			readFile(file);
			
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(1000,350));
			panel.setBackground(new Color(67,62,99));
			panel.setLayout(null);
			
			slider = new JSlider(0 , 100 , 50);
			slider.setOpaque(true);
			slider.setBackground(new Color( 56, 52, 80 ));
			slider.setBorder(BorderFactory.createEtchedBorder( ));
			slider.setCursor(new Cursor(Cursor.HAND_CURSOR));
			slider.setUI(new coloredThumbSliderUI(slider, Color.DARK_GRAY));
			slider.setPaintTicks(false);
			slider.setMinorTickSpacing(0);
			slider.setFocusable(false);
			slider.setValue(slider.getValue());
			
			slider.setPaintTrack(true);
			slider.setMajorTickSpacing(0);
			slider.setPaintLabels(true);
			slider.setBounds(655,285, 300, 25);;
			slider.addChangeListener(this);
			slider.addMouseListener(this);
			
			label = new JLabel();
			label.setOpaque(false);
			label.setText("  Volume:" +slider.getValue());
			label.setBounds(542 , 286 , 110 ,20);
			label.setFont(loadFont("CHILLER.ttf" ,25 ,Font.TYPE1_FONT ));
			label.setForeground(new Color(2, 2, 24));
			label.setVisible(false);
			
			progBar = new JProgressBar();
			progBar.setBounds(275, 255, 680, 15);
			progBar.setBackground(null);
			progBar.setBorderPainted(true);
			progBar.setBorder(BorderFactory.createLoweredBevelBorder());
			progBar.setForeground(new Color(255,133,51));
			progBar.addMouseListener(this);
			
			
			scrollBar = new JScrollPane(list);
			scrollBar.setBounds(0, 40, 250, 263);
			scrollBar.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(28, 34, 90)));
			
			textCaseMusicPlaying = new MarqueePanel("Ready for some music?",40);
			textCaseMusicPlaying.setBounds(418, 80, 400, 50);
			textCaseMusicPlaying.setBackground(new Color(28, 34, 38));
			textCaseMusicPlaying.setBorder(BorderFactory.createLoweredBevelBorder());
			
			panel.add(text);
			panel.add(textCaseMusicPlaying);
			panel.add(pauseButton);
			panel.add(playButton);
			panel.add(bkdButton);
			panel.add(frdButton);
			panel.add(deleteButton);
			panel.add(addButton);
			panel.add(scrollBar);
			panel.add(progBar);
			panel.add(slider);
			frame.add(label);
			frame.add(panel);
			frame.setVisible(true);
			
			textCaseMusicPlaying.start();
			bkdButton.setEnabled(false);
			frdButton.setEnabled(false);
			
		}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
			label.setText("  Volume:" +slider.getValue());
			setGain(slider.getValue());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addButton) {
			JFileChooser fileChooser=new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("MPEG Layer 3", "mp3", "MP3","MPEG3");
			fileChooser.setFileFilter(filter);
			fileChooser.showOpenDialog(null);
			int flag = 0 ;
			
			for(int i = 0 ; i < model.getSize() ; i++) {
				if(model.get(i).equals(fileChooser.getName(fileChooser.getSelectedFile()))) {
					flag = 1;
				}
			}	
			if(flag==0) {
				model.addElement(new String(fileChooser.getName(fileChooser.getSelectedFile())));
				saveFile(model.lastElement() , fileChooser.getSelectedFile());
			}
			else {
				ImageIcon image = new ImageIcon();
				try {
					image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] responses = {"No, you're awesome!", "Thank you" , "*blush*"};
				JOptionPane.showOptionDialog(null, 
						"You are awesome but this song already exists!", 
						"Secret message", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, 
						image, 
						responses, 
						2);
			}
		}
		
		if(e.getSource()==deleteButton) {
			testPlay=0;
			ImageIcon image = new ImageIcon();
			try {
				image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String[] responses = {"I'm suure!" , "Sorry! Didn't wanted that!"};
			int answer = JOptionPane.showOptionDialog(null, 
					"Heyy!\n Are you sure you wanna delete this song?", 
					"Secret message", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					image, 
					responses, 
					2);
			if(answer == 0) {
			deleteFile();
			model.removeElementAt(list.getSelectedIndex());
			}
		}
		
		if(e.getSource()==playButton){
			if(testPlay!=4) {
			testPlay = 1 ;
			bkdButton.setEnabled(true);
			frdButton.setEnabled(true);
			}
			if(testPlay==1) {
				playButton.setEnabled(false);
			}else {
				playButton.setEnabled(true);
			}
			try {
			if(testPlay==0) {
				testPlay=1;
				fileInputStream = new FileInputStream(getFilePath(list.getSelectedValue()));
				bufferedInputStream = new BufferedInputStream(fileInputStream);
				player= new Player(bufferedInputStream);
				totalLength = fileInputStream.available();
				
				new Thread() {
					public void run() {
						try {
							player.play();
							if(currProgBar==0 && fileInputStream!= null) {
								list.setSelectedIndex(list.getSelectedIndex()+1);
								}
						}catch(JavaLayerException e) {
							
						}
					}
				}.start();
			}
			else if(testPlay==1){
				if(model.getSize()==1) {
					bkdButton.setEnabled(false);
					frdButton.setEnabled(false);
				}
				try {
					fileInputStream = new FileInputStream(getFilePath(list.getSelectedValue()));
					bufferedInputStream = new BufferedInputStream(fileInputStream);
					player = new Player(bufferedInputStream);
					maxProgBar = fileInputStream.available();
					fileInputStream.skip(totalLength - pause);
					
					new Thread() {
						public void run() {
							try {
								player.play();
								if(currProgBar==0 && fileInputStream!= null) {
									list.setSelectedIndex(list.getSelectedIndex()+1);
									}
								if(currProgBar==0 && model.getSize()==1) {
									ImageIcon image = new ImageIcon();
									try {
										image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									String[] responses = {"Thx bby <3 !" , "*blush <3*"};
									JOptionPane.showOptionDialog(null, 
											"Heyy sweetie!\n Your song is over :(,\n but don't worry, i replayed it for you :D !", 
											"Secret message", 
											JOptionPane.YES_NO_CANCEL_OPTION, 
											JOptionPane.INFORMATION_MESSAGE, 
											image, 
											responses, 
											2);
									if(player!=null) {
										player.close();
										try {
											fileInputStream = new FileInputStream(getFilePath(list.getSelectedValue()));
										} catch (FileNotFoundException e) {
											e.printStackTrace();
										}
										bufferedInputStream = new BufferedInputStream(fileInputStream);
										player = new Player(bufferedInputStream);
										player.play();		
									}							
								}
								if(currProgBar==0 && list.getSelectedIndex()==model.getSize()-1) {
									list.setSelectedIndex(0);
									ImageIcon image = new ImageIcon();
									try {
										image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									String[] responses = {"Thx bby <3 !" , "*blush <3*"};
									JOptionPane.showOptionDialog(null, 
											"Heyy sweetie!\n Your playlist is over :(,\n but don't worry, i replayed it for you :D !", 
											"Secret message", 
											JOptionPane.YES_NO_CANCEL_OPTION, 
											JOptionPane.INFORMATION_MESSAGE, 
											image, 
											responses, 
											2);
								}
							}catch(JavaLayerException e2) {
								
							}
						}
					}.start();
					if(testPlay==1) {
						new Thread() {
							public void run() {
								while (true) {
									try {
										currProgBar=fileInputStream.available();
										progBar.setMaximum(maxProgBar);
										progBar.setValue(maxProgBar-currProgBar);
										if(testPlay!=1) {
											break;
										}
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									try {
										Thread.sleep(10);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}.start();
					}
					
				}catch(JavaLayerException e2) {
					
				}catch(IOException e1) {
					
				}
				
			}
			
			}catch(JavaLayerException e1) {
				
			}catch(IOException e2) {
				
			}
			
        }
        if(e.getSource()==pauseButton){
        	testPlay = 0;
        	if(testPlay==1) {
				playButton.setEnabled(false);
			}else {
				playButton.setEnabled(true);
			}
        	if(player!= null) {
        		try {
        			pause = fileInputStream.available();
        			player.close();
        		}catch(IOException e1){
        			
        		}
        	}
        }
        
        if(e.getSource()==frdButton) {
        	testPlay=1;
        	if(testPlay==1) {
				playButton.setEnabled(false);
			}else {
				playButton.setEnabled(true);
			}
        	if(list.getSelectedIndex()+1<=model.getSize()-1) {
        	list.setSelectedIndex(list.getSelectedIndex()+1);
        	}else if(list.getSelectedIndex()+1>model.getSize()-1) {
        		list.setSelectedIndex(0);
        	}
        }
        
        if(e.getSource()==bkdButton) {
        	testPlay = 1 ;
        	if(testPlay==1) {
				playButton.setEnabled(false);
			}else {
				playButton.setEnabled(true);
			}
        	if(list.getSelectedIndex()-1>=0 ) {
        	list.setSelectedIndex(list.getSelectedIndex()-1);
        	}else if(list.getSelectedIndex()-1<0) {
        		if(player!= null) {
    				player.close();
    			}
        		list.setSelectedIndex(model.getSize()-1);
        	}
        	}
        	
        
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==progBar) {
			if(model.getSize()==1) {
				bkdButton.setEnabled(false);
				frdButton.setEnabled(false);
			}
			if(testPlay==1) {
			player.close();
			int mouseX = e.getX();
			int progressBarVal = (int)Math.round(((double)mouseX / (double)progBar.getWidth()) * progBar.getMaximum());
			try {
				fileInputStream = new FileInputStream(getFilePath(list.getSelectedValue()));
				bufferedInputStream = new BufferedInputStream(fileInputStream);
				player = new Player(bufferedInputStream);
				
				fileInputStream.skip(progressBarVal);
				new Thread() {
					public void run() {
						try {
							player.play();
							if(currProgBar==0 && fileInputStream!= null) {
								list.setSelectedIndex(list.getSelectedIndex()+1);
								}
							if(currProgBar==0 && model.getSize()==1) {
								ImageIcon image = new ImageIcon();
								try {
									image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String[] responses = {"Thx bby <3 !" , "*blush <3*"};
								JOptionPane.showOptionDialog(null, 
										"Heyy sweetie!\n Your song is over :(,\n but don't worry, i replayed it for you :D !", 
										"Secret message", 
										JOptionPane.YES_NO_CANCEL_OPTION, 
										JOptionPane.INFORMATION_MESSAGE, 
										image, 
										responses, 
										2);
								if(player!=null) {
									player.close();
									try {
										fileInputStream = new FileInputStream(getFilePath(list.getSelectedValue()));
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
									bufferedInputStream = new BufferedInputStream(fileInputStream);
									player = new Player(bufferedInputStream);
									player.play();		
								}							
							}
							if(currProgBar==0 && list.getSelectedIndex()==model.getSize()-1) {
								list.setSelectedIndex(0);
								ImageIcon image = new ImageIcon();
								try {
									image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String[] responses = {"Thx bby <3 !" , "*blush <3*"};
								JOptionPane.showOptionDialog(null, 
										"Heyy sweetie!\n Your playlist is over :(,\n but don't worry, i replayed it for you :D !", 
										"Secret message", 
										JOptionPane.YES_NO_CANCEL_OPTION, 
										JOptionPane.INFORMATION_MESSAGE, 
										image, 
										responses, 
										2);
							}
						}catch(JavaLayerException e2) {
						}
					}
				}.start();
				
			}catch(JavaLayerException e2) {
				
			}catch(IOException e1) {
				list.setSelectedIndex(0);
			}
			}else {
				progBar.setEnabled(false);
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		label.setVisible(true);
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		label.setVisible(false);
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		label.setVisible(true);
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		label.setVisible(false);
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource()==list.getSelectionModel()) {
			
			if(testPlay==1) {
				playButton.setEnabled(false);
				bkdButton.setEnabled(true);
				frdButton.setEnabled(true);
				if(model.getSize()==1) {
					bkdButton.setEnabled(false);
					frdButton.setEnabled(false);
				}
			}else {
				playButton.setEnabled(true);
			}
			if(player!= null) {
				player.close();
				testPlay = 0 ;
			}
			if(testPlay==0 || testPlay==4) {
				testPlay=1;
			try {
				fileInputStream = new FileInputStream(getFilePath(list.getSelectedValue()));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			try {
				player= new Player(bufferedInputStream);
			} catch (JavaLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				totalLength = fileInputStream.available();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			new Thread() {
				public void run() {
					try {
						player.play();
						if(currProgBar==0 && fileInputStream!= null) {
							list.setSelectedIndex(list.getSelectedIndex()+1);
							}
						if(currProgBar==0 && model.getSize()==1) {
							ImageIcon image = new ImageIcon();
							try {
								image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							String[] responses = {"Thx bby <3 !" , "*blush <3*"};
							JOptionPane.showOptionDialog(null, 
									"Heyy sweetie!\n Your song is over :(,\n but don't worry, i replayed it for you :D !", 
									"Secret message", 
									JOptionPane.YES_NO_CANCEL_OPTION, 
									JOptionPane.INFORMATION_MESSAGE, 
									image, 
									responses, 
									2);
							if(player!=null) {
								player.close();
								try {
									fileInputStream = new FileInputStream(getFilePath(list.getSelectedValue()));
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								bufferedInputStream = new BufferedInputStream(fileInputStream);
								player = new Player(bufferedInputStream);
								player.play();		
							}							
						}
						if(currProgBar==0 && list.getSelectedIndex()==model.getSize()-1) {
							list.setSelectedIndex(0);
							ImageIcon image = new ImageIcon();
							try {
								image.setImage(ImageIO.read(getClass().getResourceAsStream("/images/logo_EXTRA_mic.png"))); 
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							String[] responses = {"Thx bby <3 !" , "*blush <3*"};
							JOptionPane.showOptionDialog(null, 
									"Heyy sweetie!\n Your playlist is over :(,\n but don't worry, i replayed it for you :D !", 
									"Secret message", 
									JOptionPane.YES_NO_CANCEL_OPTION, 
									JOptionPane.INFORMATION_MESSAGE, 
									image, 
									responses, 
									2);
						}		
					}catch(JavaLayerException e) {
					}
				}
				
			}.start();
			
			if(testPlay==1) {
			new Thread() {
				public void run() {
					try {
						maxProgBar = fileInputStream.available();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					while (true) {
						try {
							currProgBar=fileInputStream.available();
							progBar.setMaximum(maxProgBar);
							progBar.setValue(maxProgBar-currProgBar);
							if(testPlay!=1) {
								break;
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
			}
			}
			textCaseMusicPlaying.removeAll();
			textCaseMusicPlaying= new MarqueePanel(list.getSelectedValue(),40);
			textCaseMusicPlaying.setBounds(418, 80, 400, 50);
			textCaseMusicPlaying.setBackground(new Color(28, 34, 38));
			textCaseMusicPlaying.setBorder(BorderFactory.createLoweredBevelBorder());
			panel.add(textCaseMusicPlaying);
			textCaseMusicPlaying.start();
		}
	}
	
	public void saveFile(String data , File f) {
		
		{
			 
	        try {
	        								String nameANDpath;
	        	String newPath = f.getAbsolutePath();      String newSong = data;   	 
	            String path;                               String songNameSearch;
        
	            File file = new File("Songs.txt");
	 
	            if (!file.exists()) {
	 
	                // Create a new file if not exists.
	                file.createNewFile();
	            }
	 
	            // Opening file in reading and write mode.
	 
	            RandomAccessFile raf = new RandomAccessFile(file, "rw");
	     
	            boolean found = false;

	            while (raf.getFilePointer() < raf.length()) {
	            	
	            	
	                // reading line from the file.
	            	nameANDpath = raf.readLine();
	            	
	            	String[] lineSplit
                    = nameANDpath.split("!");
	
	            	 path = lineSplit[0];
	            	 songNameSearch = lineSplit[1];
	                if (songNameSearch == newSong || path == newPath) {
	                    found = true;
	                    break;
	                }
	            }
	 
	            if (found == false) {
	               
	            	nameANDpath = newSong + "!"+ newPath;
	            	
	            	// writeBytes function to write a string
	                // as a sequence of bytes.
	                raf.writeBytes(nameANDpath);
	 
	                // To insert the next record in new line.
	                raf.writeBytes(System.lineSeparator());
	                raf.close();
	            }
	            else {
	                raf.close();
	            }
	        }
	 
	        catch (IOException ioe) {
	 
	        }
	        catch (NumberFormatException nef) {
	 
	        }
		}
	
	}

	public void readFile(File file) {
		try {
			
			String nameANDpath;  	                                String songNameSearch;
			
			
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");     

            while (raf.getFilePointer() < raf.length()) {
            	
            	nameANDpath = raf.readLine();
            	
            	 String[] lineSplit = nameANDpath.split("!");
            	 
            	 songNameSearch = lineSplit[0];
            	
                model.addElement(songNameSearch); 
                System.out.println(model.getElementAt(model.getSize()-1));
            }
            raf.close();
            }
 
            catch (IOException ioe)
            {
 
                System.out.println(ioe);
            }
            catch (NumberFormatException nef)
            {
 
                System.out.println(nef);
            }
		}	

	public String getFilePath(String data) {

		
		try {
			String path ="Nu s-a gasit";
			String nameANDpath;  	                                String songNameSearch;

			
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");     

            while (raf.getFilePointer() < raf.length()) {
            	
            	nameANDpath = raf.readLine();
            	
            	 String[] lineSplit = nameANDpath.split("!");
            	 
            	 songNameSearch = lineSplit[0];
            	 
            	 if(songNameSearch.equals(data)) {
            		 path = lineSplit[1];
            	 }
            	
            }
            raf.close();
            return path;
            }
 
            catch (IOException ioe)
            {
                System.out.println(ioe);
            }
            catch (NumberFormatException nef)
            {
                System.out.println(nef);
            }
		return data;
	}
	
	public void deleteFile() {
		try {
            String fileToDelete = list.getSelectedValue();
			String nameANDpath;  	              int index;                  String songNameSearch; 
            File file = new File("Songs.txt");

            if (!file.exists()) {

                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = true;

            while (raf.getFilePointer() < raf.length()) {
                nameANDpath = raf.readLine();
                
                String[] lineSplit = nameANDpath.split("!");
           	 	songNameSearch = lineSplit[0];
                
                // if condition to find existence of record.
                if (songNameSearch == fileToDelete) {
                    found = true;
                    break;
                }
            }
            if (found == true) {
                File tmpFile = new File("temp.txt");

                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

                // Set file pointer to start
                raf.seek(0);
                while (raf.getFilePointer() < raf.length()) {
                    nameANDpath = raf.readLine();
                    
                    index = nameANDpath.indexOf('!');
                    
                    songNameSearch = nameANDpath.substring(
                            0, index);
                    if (songNameSearch.equals(fileToDelete)) {
                        continue;
                    }
                    tmpraf.writeBytes(nameANDpath);
                    tmpraf.writeBytes(System.lineSeparator());
                }
                raf.seek(0);
                tmpraf.seek(0);

                while (tmpraf.getFilePointer()
                       < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }
                // Set the length of the original file
                // to that of temporary.
                raf.setLength(tmpraf.length());

                tmpraf.close();
                raf.close();
 
                tmpFile.delete();
            }

            else {
                raf.close();
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
	}
	
	public void setGain(float ctrl)  
    {          
        try {  
        Mixer.Info[] infos = AudioSystem.getMixerInfo();    
        for (Mixer.Info info: infos)    
        {    
           Mixer mixer = AudioSystem.getMixer(info);  
           if (mixer.isLineSupported(Port.Info.SPEAKER))    
           {    
              Port port = (Port)mixer.getLine(Port.Info.SPEAKER);    
              port.open();    
              if (port.isControlSupported(FloatControl.Type.VOLUME))    
              {    
                 FloatControl volume =  (FloatControl)port.getControl(FloatControl.Type.VOLUME);                    
                 volume.setValue((ctrl/100)*volume.getMaximum());  
              }    
              port.close();    
           }    
         }    
        } catch (Exception e) {   
            JOptionPane.showMessageDialog(null,"Erro\n"+e);  
        }  
    }
	
	private static Font loadFont(String fontName, float size, int style) {

        InputStream openStream = MarqueePanel.class
                .getResourceAsStream("/font/"
                        + fontName);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, openStream);
            Font finalFont = font.deriveFont((float) size).deriveFont(style);
            System.out.println("Loading font " + fontName + " " + finalFont);
            return finalFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (openStream != null) {
                try {
                    openStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}



		

