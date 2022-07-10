package uriot.renmov;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

public class MainFrame extends javax.swing.JFrame {

	private final Logger log = Logger.getLogger("renmov");
	private final ArrayList<Item> items = new ArrayList<>(10);
	private final Controller controller = new Controller();
	private Settings settings;
	private ResourceBundle bundle;
	
	public MainFrame() {
		try (var fis = new FileInputStream("settings.ser");
             var ois = new ObjectInputStream(fis)) {
			settings = (Settings) ois.readObject();
			log.log(Level.INFO, "found settings {0}", settings);
        }
		catch (IOException | ClassNotFoundException ex) {
			var currentLocale = Locale.getDefault();
			settings = new Settings(false, false, currentLocale.getLanguage(), null, null);
			log.log(Level.INFO, "created settings {0}", settings);
		}
		controller.log = log;
		controller.settings = settings;
		updateLogger();
		
		initComponents();
		
		var itm = new ItemTableModel(items);
		itemTable.setModel(itm);
        var columnModel = itemTable.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(30);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(3).setMaxWidth(150);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(4).setMaxWidth(100);
        columnModel.getColumn(5).setMaxWidth(100);
		itemTable.setAutoCreateRowSorter(true);
		
		localize(settings.language);

		dir1.setText(settings.lastDir1);
		dir2.setText(settings.lastDir2);
		resetButtons();
		switch (settings.language) {
			case "en"  -> enLocaleMenuItem.setSelected(true);
			case "fr"  -> frLocaleMenuItem.setSelected(true);
			case "ru"  -> ruLocaleMenuItem.setSelected(true);
			case "default" -> defLocaleMenuItem.setSelected(true);
		}
	}

	private void localize(String language) {
		var locale = (language.equals("default")) ? Locale.getDefault() : new Locale(language);
        bundle = ResourceBundle.getBundle("i18n", locale);
		controller.bundle = bundle;

		this.setTitle(bundle.getString("title"));
	
        fileMenu.setText(bundle.getString("fileMenu"));
        exitMenuItem.setText(bundle.getString("exitMenuItem"));
        prefMenu.setText(bundle.getString("prefMenu"));
        logToFileMenuItem.setText(bundle.getString("logToFileMenuItem"));
        roundToSecondMenuItem.setText(bundle.getString("roundToSecondMenuItem"));
        enLocaleMenuItem.setText(bundle.getString("enLocaleMenuItem"));
        frLocaleMenuItem.setText(bundle.getString("frLocaleMenuItem"));
        ruLocaleMenuItem.setText(bundle.getString("ruLocaleMenuItem"));
        defLocaleMenuItem.setText(bundle.getString("defLocaleMenuItem"));
        helpMenu.setText(bundle.getString("helpMenu"));
        aboutMenuItem.setText(bundle.getString("aboutMenuItem"));
		
        dir1Button.setText(bundle.getString("directory1"));
        dir2Button.setText(bundle.getString("directory2"));
        dir1Button.setToolTipText(bundle.getString("directory1Tooltip"));
        dir2Button.setToolTipText(bundle.getString("directory2Tooltip"));
        searchButton.setText(bundle.getString("search"));
        executeButton.setText(bundle.getString("execute"));
		
  		itemTable.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("col.sel"));
  		itemTable.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("col.file1"));
  		itemTable.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("col.file2"));
  		itemTable.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("col.date"));
  		itemTable.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("col.size"));
  		itemTable.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("col.action"));
		itemTable.getTableHeader().resizeAndRepaint();
	}
	
	private void updateLogger() {
		if (settings.logToFile) {
			log.info("add log file appender");
			try {
				var fileHandler = new FileHandler("renmov.log");
				fileHandler.setFormatter(new SimpleFormatter());
				log.addHandler(fileHandler);
			}
			catch (IOException | SecurityException ex) {
				log.log(Level.SEVERE, "error creating log file handler", ex);
			}
		}
		else {
			log.info("remove log file appender");
			Arrays.stream(log.getHandlers()).filter(h -> h instanceof FileHandler).forEach(h -> log.removeHandler(h));
		}
	}
	
	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        dir1 = new javax.swing.JTextField();
        dir1Button = new javax.swing.JButton();
        dir2 = new javax.swing.JTextField();
        dir2Button = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        executeButton = new javax.swing.JButton();
        result = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        prefMenu = new javax.swing.JMenu();
        logToFileMenuItem = new javax.swing.JCheckBoxMenuItem();
        roundToSecondMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        enLocaleMenuItem = new javax.swing.JRadioButtonMenuItem();
        frLocaleMenuItem = new javax.swing.JRadioButtonMenuItem();
        ruLocaleMenuItem = new javax.swing.JRadioButtonMenuItem();
        defLocaleMenuItem = new javax.swing.JRadioButtonMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeWindow(evt);
            }
        });

        jPanel.setLayout(null);

        dir1.setName(""); // NOI18N
        dir1.setPreferredSize(new java.awt.Dimension(230, 27));

        dir1Button.setText("Directory 1");
        dir1Button.setToolTipText("Target directory");
        dir1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDir1(evt);
            }
        });

        dir2.setPreferredSize(new java.awt.Dimension(230, 27));

        dir2Button.setText("Directory 2");
        dir2Button.setToolTipText("Model directory");
        dir2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDir2(evt);
            }
        });

        itemTable.setColumnSelectionAllowed(true);
        jScrollPane.setViewportView(itemTable);
        itemTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search(evt);
            }
        });

        executeButton.setText("Execute");
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execute(evt);
            }
        });

        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar.add(fileMenu);

        prefMenu.setText("Preferences");

        logToFileMenuItem.setSelected(settings.logToFile);
        logToFileMenuItem.setText("Log to file");
        logToFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setLogToFile(evt);
            }
        });
        prefMenu.add(logToFileMenuItem);

        roundToSecondMenuItem.setSelected(settings.roundToSecond);
        roundToSecondMenuItem.setText("Round time to second");
        roundToSecondMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setRoundToSecond(evt);
            }
        });
        prefMenu.add(roundToSecondMenuItem);
        prefMenu.add(jSeparator1);

        enLocaleMenuItem.setText("english");
        enLocaleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enLocaleMenuItemActionPerformed(evt);
            }
        });
        prefMenu.add(enLocaleMenuItem);

        frLocaleMenuItem.setText("french");
        frLocaleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frLocaleMenuItemActionPerformed(evt);
            }
        });
        prefMenu.add(frLocaleMenuItem);

        ruLocaleMenuItem.setText("russian");
        ruLocaleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruLocaleMenuItemActionPerformed(evt);
            }
        });
        prefMenu.add(ruLocaleMenuItem);

        defLocaleMenuItem.setText("default");
        defLocaleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defLocaleMenuItemActionPerformed(evt);
            }
        });
        prefMenu.add(defLocaleMenuItem);

        jMenuBar.add(prefMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        jMenuBar.add(helpMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dir2Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dir1Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dir1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dir2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(executeButton)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(426, 426, 426))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dir1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dir1Button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dir2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dir2Button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchButton)
                    .addComponent(executeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(result)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chooseDir1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDir1
		var fileChooser = new JFileChooser(dir1.getText());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		var returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			var selectedFile = fileChooser.getSelectedFile();
			dir1.setText(selectedFile.getPath());
		}		
    }//GEN-LAST:event_chooseDir1

    private void chooseDir2(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDir2
		var fileChooser = new JFileChooser(dir2.getText());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		var returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			var selectedFile = fileChooser.getSelectedFile();
			dir2.setText(selectedFile.getPath());
		}		
    }//GEN-LAST:event_chooseDir2

    private void search(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search
		var res = new int[1];
		controller.search(items, dir1.getText(), dir2.getText(), res);
		switch (res[0]) {
			case 0 -> {
				itemTable.updateUI();
				itemTable.getRowSorter().allRowsChanged();
				((ItemTableModel) itemTable.getModel()).dir1Length = dir1.getText().length();
				((ItemTableModel) itemTable.getModel()).dir2Length = dir2.getText().length();
				result.setText(items.size() + bundle.getString("foundResult"));
			}
			case 1 -> result.setText(bundle.getString("error.dirs"));
			case 2 -> result.setText(bundle.getString("error"));
		}
		
    }//GEN-LAST:event_search

    private void execute(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execute
		var cpt = new int[2];
		controller.execute(items, dir1.getText(), dir2.getText(), cpt);
		itemTable.updateUI();
		itemTable.getRowSorter().allRowsChanged();
		result.setText(cpt[0] + java.text.MessageFormat.format(bundle.getString("executeResult"), new Object[] {cpt[1]}));
    }//GEN-LAST:event_execute

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
		settings.lastDir1 = dir1.getText();
		settings.lastDir2 = dir2.getText();
		try (var fos = new FileOutputStream("settings.ser");
			 var oos = new ObjectOutputStream(fos)) {
			oos.writeObject(settings);
			log.info("settings saved");
		}
		catch (IOException ex) {
			log.log(Level.SEVERE, "error writing settings", ex);
		}		
		System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        var jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        var scrollPane = new JScrollPane(jEditorPane);
        var kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);
//        StyleSheet styleSheet = kit.getStyleSheet();
//        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
//        styleSheet.addRule("h1 {color: blue;}");
//        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
        var htmlString = "<html><body><h1>Help not found !</h1></body></html>";
		var suffix = (settings.language.equals("default")) ? Locale.getDefault().getLanguage() : settings.language;
        var helpRes  = this.getClass().getResource("/about_"+suffix+".html");
        if (helpRes == null) {
	        helpRes  = this.getClass().getResource("/about.html");
		}
        var htmlFile  = new File(helpRes.getFile());
		try {
			var l = Files.readAllLines(htmlFile.toPath(), Charset.defaultCharset());
			var sb = new StringBuilder();
			l.forEach(s -> sb.append(s));
			htmlString = sb.toString();
		}
		catch (IOException ex) {
			log.log(Level.SEVERE, "help not found", ex);
		}
        var doc = kit.createDefaultDocument();
        jEditorPane.setDocument(doc);
        jEditorPane.setText(htmlString);
        var j = new JFrame(bundle.getString("aboutMenuItem"));
        j.getContentPane().add(scrollPane, BorderLayout.CENTER);
        j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        j.setSize(new Dimension(640,480)); //j.pack(); pack it, if you prefer
        j.setLocationRelativeTo(null); // center the jframe, then make it visible
        j.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void setLogToFile(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setLogToFile
        settings.logToFile = logToFileMenuItem.getState();
		updateLogger();
    }//GEN-LAST:event_setLogToFile

    private void setRoundToSecond(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setRoundToSecond
        settings.roundToSecond = roundToSecondMenuItem.getState();
    }//GEN-LAST:event_setRoundToSecond

    private void closeWindow(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeWindow
        exitMenuItemActionPerformed(null);
    }//GEN-LAST:event_closeWindow
	private void resetButtons() {
		enLocaleMenuItem.setSelected(false);
		frLocaleMenuItem.setSelected(false);
		ruLocaleMenuItem.setSelected(false);
		defLocaleMenuItem.setSelected(false);
	}	
    private void ruLocaleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruLocaleMenuItemActionPerformed
		resetButtons();
		ruLocaleMenuItem.setSelected(true);
        settings.language = "ru";
		localize(settings.language);
    }//GEN-LAST:event_ruLocaleMenuItemActionPerformed

    private void frLocaleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frLocaleMenuItemActionPerformed
		resetButtons();
		frLocaleMenuItem.setSelected(true);
        settings.language = "fr";
		localize(settings.language);
    }//GEN-LAST:event_frLocaleMenuItemActionPerformed

    private void enLocaleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enLocaleMenuItemActionPerformed
		resetButtons();
		enLocaleMenuItem.setSelected(true);
        settings.language = "en";
		localize(settings.language);
    }//GEN-LAST:event_enLocaleMenuItemActionPerformed

    private void defLocaleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defLocaleMenuItemActionPerformed
		resetButtons();
		defLocaleMenuItem.setSelected(true);
        settings.language = "default";
		localize(settings.language);
    }//GEN-LAST:event_defLocaleMenuItemActionPerformed

	public static void main(String args[]) {
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* Set the Nimbus look and feel */
//		try {
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		}
//		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
		//</editor-fold>

		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> {
			var f = new MainFrame();
			f.setLocation(0, 0);
			f.setSize(1000, 600);
			f.setVisible(true); 
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JRadioButtonMenuItem defLocaleMenuItem;
    public javax.swing.JTextField dir1;
    private javax.swing.JButton dir1Button;
    public javax.swing.JTextField dir2;
    private javax.swing.JButton dir2Button;
    private javax.swing.JRadioButtonMenuItem enLocaleMenuItem;
    private javax.swing.JButton executeButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JRadioButtonMenuItem frLocaleMenuItem;
    private javax.swing.JMenu helpMenu;
    public javax.swing.JTable itemTable;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JCheckBoxMenuItem logToFileMenuItem;
    private javax.swing.JMenu prefMenu;
    public javax.swing.JLabel result;
    private javax.swing.JCheckBoxMenuItem roundToSecondMenuItem;
    private javax.swing.JRadioButtonMenuItem ruLocaleMenuItem;
    private javax.swing.JButton searchButton;
    // End of variables declaration//GEN-END:variables
}
