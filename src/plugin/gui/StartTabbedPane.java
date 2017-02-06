/**
 * 
 */
package plugin.gui;

/**
 * @author jan
 *
 */
import javax.swing.JTabbedPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class StartTabbedPane extends JPanel {

	StartSettingDialog ssd;

	/**
	 * Variables for graphViz
	 */

	/**
	 * Detects the client's operating system.
	 */
	private final static String osName = System.getProperty("os.name").replaceAll("\\s", "");

	private static String tempDir;
	private static String executable;

	private static String winTempDir = "c:/temp";
	private static String winExe = "c:/Program Files (x86)/Graphviz 2.28/bin/dot.exe";
	private static String macTempDir = "/tmp";
	private static String macExe = "/usr/local/bin/dot";
	private static String linuxTempDir = "/tmp";
	private static String linuxExe = "/usr/bin/dot";

	public static String getOsname() {
		return osName;
	}

	public static String getTempDir() {
		return tempDir;
	}

	/**
	 * sets the temporary directory and execution file for GraphViz depending on
	 * the standard settings of the os. If this fails the user is asked to enter
	 * the correct paths. These settings are overwritten if the user specifies
	 * the paths in the textfields of the settings panel with setTempDir() and
	 * setExecutable
	 * 
	 * @param tempDir
	 */
	public void setDirOnStart() {
		if (osName.contains("Windows")) {
			StartTabbedPane.tempDir = winTempDir;
			StartTabbedPane.executable = winExe;
		} else if (osName.equals("MacOSX")) {
			StartTabbedPane.tempDir = macTempDir;
			StartTabbedPane.executable = macExe;
		} else if (osName.equals("Linux")) {
			StartTabbedPane.tempDir = linuxTempDir;
			StartTabbedPane.executable = linuxExe;
		}
		File dir = new File(tempDir);

		if (!dir.isDirectory()) {
			JFrame frame = new JFrame();
			if (dir.mkdir()) {
				JOptionPane.showMessageDialog(frame,
						"New temporary directory '" + dir.getAbsolutePath() + "' for GraphViz created.");
				;
			} else {
				JOptionPane.showMessageDialog(frame,
						"Failed to create new temporary directory '" + dir.getAbsolutePath() + "' for GraphViz.",
						tempDir, JOptionPane.ERROR_MESSAGE);
				;
			}
		}

		if (!new File(executable).canExecute()) {
			JFrame frame = new JFrame();
			while (!(new File(executable).isFile())) {
				executable = (String) JOptionPane.showInputDialog(frame,
						"Error: GraphViz cannot be found or executed.\n" + "The path '" + executable
								+ "'is not valid.\n"
								+ "Enter the valid path of your GraphViz-Installation or abort the Programm!",
						"Inane error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void setTempDir(String tempDir) {
		StartTabbedPane.tempDir = tempDir;
	}

	public static String getExecutable() {
		return executable;
	}

	public void setExecutable(String executable) {
		StartTabbedPane.executable = executable;
	}


	public static String getWinTempDir() {
		return winTempDir;
	}

	public void setWinTempDir(String winTempDir) {
		StartTabbedPane.winTempDir = winTempDir;
	}

	public static String getWinExe() {
		return winExe;
	}

	public void setWinExe(String winExe) {
		StartTabbedPane.winExe = winExe;
	}

	public static String getMacTempDir() {
		return macTempDir;
	}

	public void setMacTempDir(String macTempDir) {
		StartTabbedPane.macTempDir = macTempDir;
	}

	public static String getMacExe() {
		return macExe;
	}

	public void setMacExe(String macExe) {
		StartTabbedPane.macExe = macExe;
	}

	public static String getLinuxTempDir() {
		return linuxTempDir;
	}

	public void setLinuxTempDir(String linuxTempDir) {
		StartTabbedPane.linuxTempDir = linuxTempDir;
	}

	public static String getLinuxExe() {
		return linuxExe;
	}

	public void setLinuxExe(String linuxExe) {
		StartTabbedPane.linuxExe = linuxExe;
	}

	/**
	 * End of Variabels for GraphViz
	 */

	public StartTabbedPane() {
		
		super(new GridLayout(1, 1));
		
		setDirOnStart();
		System.out.println("StartTabbedPane " + StartTabbedPane.getTempDir());
		
		ssd = new StartSettingDialog();
		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("images/cfg-drawer.gif");

		JComponent panel1 = (JComponent) ssd.getPane();
		tabbedPane.addTab("Start", icon, panel1, "Does nothing");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = makeTextPanel("Output type: ");

		// Create the radio buttons for the output type selection

		ActionListener listenerSelectOutputType = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Fileformat set to: " + e.getActionCommand());
				// outputFormat = e.getActionCommand();
			}
		};
		JRadioButton pdf = new JRadioButton("pdf");
		pdf.setMnemonic(KeyEvent.VK_B);
		pdf.setActionCommand("pdf");
		pdf.setSelected(false);

		JRadioButton png = new JRadioButton("png");
		png.setMnemonic(KeyEvent.VK_B);
		png.setActionCommand("png");
		png.setSelected(true);

		JRadioButton ps = new JRadioButton("ps");
		ps.setMnemonic(KeyEvent.VK_B);
		ps.setActionCommand("ps");
		ps.setSelected(false);

		JRadioButton jpg = new JRadioButton("jpg");
		jpg.setMnemonic(KeyEvent.VK_B);
		jpg.setActionCommand("jpg");
		jpg.setSelected(false);

		ButtonGroup group = new ButtonGroup();
		group.add(pdf);
		group.add(png);
		group.add(ps);
		group.add(jpg);

		// Register a listener for the radio buttons.
		pdf.addActionListener(listenerSelectOutputType);
		png.addActionListener(listenerSelectOutputType);
		ps.addActionListener(listenerSelectOutputType);
		jpg.addActionListener(listenerSelectOutputType);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel FileTypePanel = new JPanel();
		FileTypePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		FileTypePanel.add(pdf);
		FileTypePanel.add(png);
		FileTypePanel.add(ps);
		FileTypePanel.add(jpg);

		panel2.add(FileTypePanel);
		tabbedPane.addTab("Settings", icon, panel2, "Does twice as much nothing");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JComponent panel3 = makeTextPanel("Info");
		tabbedPane.addTab("Info", icon, panel3, "Still does nothing");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JComponent panel4 = makeTextPanel("Panel #4 (has a preferred size of 410 x 50).");
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 4", icon, panel4, "Does nothing at all");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		// Add the tabbed pane to this panel.
		add(tabbedPane);
		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = StartTabbedPane.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("StartTabbedPane");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Add content to the window.
		frame.add(new StartTabbedPane(), BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}