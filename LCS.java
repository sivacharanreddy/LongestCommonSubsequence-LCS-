package syntax;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LCS extends JApplet implements ActionListener, Runnable {
	private String s1, s2, rs1, rs2, res;
	private int len1, len2, rlen1, rlen2;
	private int I, J;
	private int loop_counter;
	private final int leftTop = 1; //Upper left arrow
	private final int top = 2; // top arrow
	private final int left = 3;// left

	private Integer B[][], C[][]; // Two arrays B and C for two tables
	private final String direction[] = { " ", "\u2196", "\u2191", "\u2190" };//Arrows Upper left,Top,Left
	
	private Border border;
	private JTextField jTextFieldS1, jTextFieldS2; // For strings s1 and s2
	private JLabel jLabelGridB[][], jLabelGridC[][]; // For two tables
	private JButton jButtonStart, jButtonNext, jButtonReset, jButtonAutoRun, jButtonRecursion;
	private JPanel jPanelMain, jPanelHeader, jPanelFooter, jPanelMiddle,
			jPanelTableB, jPanelTableC;

	private JLabel jlabelLCSLen, jLabelLCSString; // For the Footer Panel

	private boolean flag; // to terminate the thread
	private Thread thread;
	
    
	// This is the start point of execution
	public void init() 
	{
		
		jPanelMain = new JPanel();
		jPanelMain.setLayout(new BorderLayout());

		// Header Panel
		jPanelHeader = new JPanel();
		jPanelHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
		jPanelHeader.setBackground(Color.YELLOW);
		jTextFieldS1 = new JTextField(25);
		jTextFieldS2 = new JTextField(25);
		jButtonStart = new JButton("Start");
		jButtonStart.addActionListener(this);
		jButtonNext = new JButton("Next");
		jButtonNext.setEnabled(false);
		jButtonNext.addActionListener(this);
		jButtonReset = new JButton("Reset");
		jButtonReset.addActionListener(this);
		jButtonAutoRun = new JButton("Auto Run");
		jButtonAutoRun.addActionListener(this);
		jButtonRecursion = new JButton("Recursion");
		jButtonRecursion.addActionListener(this);
		jPanelHeader.add(new Label("String 1"));
		jPanelHeader.add(jTextFieldS1);
		jPanelHeader.add(new Label("String 2"));
		jPanelHeader.add(jTextFieldS2);
		jPanelHeader.add(jButtonStart);
		jPanelHeader.add(jButtonNext);
		jPanelHeader.add(jButtonReset);
		jPanelHeader.add(jButtonAutoRun);
		jPanelHeader.add(jButtonRecursion);
		jPanelMain.add(jPanelHeader, BorderLayout.NORTH);

		// Middle Panel
		jPanelMiddle = new JPanel();
		jPanelMiddle.setLayout(new GridLayout(1, 2));
		jPanelTableB = new JPanel();
		jPanelTableC = new JPanel();
		jPanelMiddle.add(jPanelTableB);
		jPanelTableB.setBackground(Color.GREEN);
		jPanelMiddle.add(jPanelTableC);
		jPanelTableC.setBackground(Color.CYAN);
		jPanelMain.add(jPanelMiddle, BorderLayout.CENTER);

		// Footer Panel
		jPanelFooter = new JPanel();
		jPanelFooter.setLayout(new GridLayout(1, 2));

		jlabelLCSLen = new JLabel(" ");
		ChangeLabelSytle(jlabelLCSLen, Color.GREEN, Color.BLACK);

		jLabelLCSString = new JLabel(" ");
		ChangeLabelSytle(jLabelLCSString, Color.CYAN, Color.BLACK);

		jPanelFooter.add(jlabelLCSLen);
		jPanelFooter.add(jLabelLCSString);
		
		jPanelMain.add(jPanelFooter, BorderLayout.SOUTH);

		setSize(1000, 500);
		add(jPanelMain);

		border = BorderFactory.createLineBorder(Color.BLACK);
	}

	// Method for Prompting the messages
	private void ShowMessage(String message) 
	{
		JOptionPane.showMessageDialog(null, message);
	}

	// LCS Algorithm
	private void lcs(int i, int j) {
		if (i <= 0 || j <= 0 || i > len1 || j > len2) {
			return;
		} else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
			B[i][j] = B[i - 1][j - 1] + 1;
			C[i][j] = leftTop;
		} else if (B[i - 1][j] > B[i][j - 1]) {
			B[i][j] = B[i - 1][j];
			C[i][j] = top;
		} else {
			B[i][j] = B[i][j - 1];
			C[i][j] = left;
		}
	}

	// Initializing the arrays 
	private void Init() {
		res = "";
		s1 = jTextFieldS1.getText();
		s2 = jTextFieldS2.getText();
		boolean a1=isAlpha(s1);
		boolean a2=isAlpha(s2);
		len1 = s1.length();
		len2 = s2.length();
		if(a1&&a2)
		{	
			if (len1 < 1) {
				ShowMessage("First String cannot be empty");
				return;
			} else if (len1 > 20) {
				ShowMessage("First String greater than 20 characters");
				return;
			} else if (len2 < 1) {
				ShowMessage("Second String cannot be empty");
				return;
			} else if (len2 > 20) {
				ShowMessage("Second String is greater than 20 characters");
				return;
			}
			B = new Integer[len1 + 1][len2 + 1];
			C = new Integer[len1 + 1][len2 + 1];
			for (int i = 0; i <= len1; i++) {
				for (int j = 0; j <= len2; j++) {
					B[i][j] = C[i][j] = 0;
				}
			}
			I = 1;
			J = 0;
	
			jLabelGridB = new JLabel[len1 + 2][len2 + 2];
			jLabelGridC = new JLabel[len1 + 2][len2 + 2];
	
			DrawTables();
			jButtonStart.setEnabled(false);
			jButtonNext.setEnabled(true);
			jTextFieldS1.setEditable(false);
			jTextFieldS2.setEditable(false);
			}
		else
		{
			ShowMessage("Enter only alphabets as input strings");
		}
	}

	// Initializing the panel grids 
	private void DrawTables() {
		int i, j;
		jLabelGridB[0][0] = new JLabel(" ");
		jLabelGridC[0][0] = new JLabel(" ");
		for (i = 1; i <= len1; i++) {
			jLabelGridB[i][0] = new JLabel("" + s1.charAt(i - 1));
			jLabelGridC[i][0] = new JLabel("" + s1.charAt(i - 1));
		}
		for (i = 1; i <= len2; i++) {
			jLabelGridB[0][i] = new JLabel("" + s2.charAt(i - 1));
			jLabelGridC[0][i] = new JLabel("" + s2.charAt(i - 1));
		}
		for (i = 1; i <= len1; i++) {
			for (j = 1; j <= len2; j++) {
				jLabelGridB[i][j] = new JLabel(" ");
				jLabelGridC[i][j] = new JLabel(direction[C[i][j]]);
			}
		}
		jPanelTableB.removeAll();
		jPanelTableB.setLayout(new GridLayout(len1 + 1, len2 + 1));
		for (i = 0; i <= len1; i++) {
			for (j = 0; j <= len2; j++) {
				jLabelGridB[i][j].setBorder(border);
				jLabelGridB[i][j].setHorizontalAlignment(JLabel.CENTER);
				jLabelGridB[i][j].setVerticalAlignment(JLabel.CENTER);
				jPanelTableB.add(jLabelGridB[i][j]);
			}
		}
		jPanelTableC.removeAll();
		jPanelTableC.setLayout(new GridLayout(len1 + 1, len2 + 1));
		for (i = 0; i <= len1; i++) {
			for (j = 0; j <= len2; j++) {
				jLabelGridC[i][j].setBorder(border);
				jLabelGridC[i][j].setHorizontalAlignment(JLabel.CENTER);
				jLabelGridC[i][j].setVerticalAlignment(JLabel.CENTER);
				jPanelTableC.add(jLabelGridC[i][j]);
			}
		}
		refresh();
	}

	// Resetting all the components
	private void Reset() {
		s1 = s2 = "";
		jTextFieldS1.setEditable(true);
		jTextFieldS1.setText("");
		jTextFieldS2.setEditable(true);
		jTextFieldS2.setText("");
		jPanelTableB.removeAll();
		jPanelTableC.removeAll();
		jButtonStart.setEnabled(true);
		jButtonNext.setEnabled(false);
		jlabelLCSLen.setText("");
		jLabelLCSString.setText("");
		refresh();
	}

	private void refresh() {
		this.setSize(1950, 500);
		this.setSize(1000, 500);
	}

	// Returns the LCS length and LCS string 
	void PrintLCS(int i, int j) {
		if (i <= 0 || j <= 0)
			return;
		if (C[i][j] == leftTop) {
			PrintLCS(i - 1, j - 1);
			ChangeLabelSytle(jLabelGridC[i][j], Color.RED, Color.WHITE);
			res += s1.charAt(i - 1);
		} else if (C[i][j] == top) {
			PrintLCS(i - 1, j);
		} else {
			PrintLCS(i, j - 1);
		}
	}

	// Actions are performed here
	@Override
	public void actionPerformed(ActionEvent action) 
	{
		if (action.getSource() == jButtonStart) 
		{
			Init();
		} 
		else if (action.getSource() == jButtonNext) 
		{
			Traves();
		} 
		else if (action.getSource() == jButtonReset) 
		{
			Reset();
		} 
		else if (action.getSource() == jButtonAutoRun) 
		{
			autorun();
		}
		else if(action.getSource() == jButtonRecursion)
		{
			Recursion();
		}
	}

	private void FinalState(int i, int j) {
		ChangeLabelSytle(jLabelGridC[i][j], Color.CYAN, Color.BLACK);
		jlabelLCSLen.setText("Length of LCS is :: " + B[len1][len2].toString());
		ShowMessage("Length of Longest Common Sequence is :: " + B[len1][len2]);
		PrintLCS(len1, len2);
		jLabelLCSString.setText("LCS is :: " + res);
		ShowMessage("Longest Common Sequence is :: " + res);
		jButtonNext.setEnabled(false);
		flag = false;
	}

	// Upon clicking Button Next
	private void Traves() {
		ChangeLabelSytle(jLabelGridB[I][J], Color.GREEN, Color.BLACK);
		ChangeLabelSytle(jLabelGridC[I][J], Color.cyan, Color.BLACK);
		J++;
		if (J > len2) {
			I++;
			J = 1;
			if (I > len1)
				I = 1;
		}
		lcs(I, J);
		jLabelGridB[I][J].setText(B[I][J].toString());
		ChangeLabelSytle(jLabelGridB[I][J], Color.RED, Color.WHITE);
		jLabelGridC[I][J].setText(direction[C[I][J]]);
		ChangeLabelSytle(jLabelGridC[I][J], Color.RED, Color.WHITE);
		if (I == len1 && J == len2) {
			FinalState(I, J);
		}
	}

	private void ChangeLabelSytle(JLabel label, Color bColor, Color fColor) 
	{
		label.setBackground(bColor);
		label.setForeground(fColor);
		label.setOpaque(true);
	}
	
	// Recursion Approach
    public void Recursion()
    {
    	
    	rs1 = jTextFieldS1.getText();
		rs2 = jTextFieldS2.getText();
		boolean b1=isAlpha(rs1);
		boolean b2=isAlpha(rs2);
		if(b1&&b2)
		{
		int a = r_lcs(rs1,rs2);
		ShowMessage("LCS is: "+a);
		ShowMessage("Number of recursive calls: "+(loop_counter-1));
		loop_counter=0;
		}
		else
		{
			ShowMessage("Enter only alphabets as input strings");
		}

    }
    
    public int r_lcs (String x,String y)
	{
    	
    	rlen1 = x.length();
		rlen2 = y.length();
		
		
		loop_counter=loop_counter+1;
		
		if(rlen1==0||rlen2==0)
			return 0;
		
		else if (rlen1 == 1) 
		    return find(x.charAt(0), y);
		
		else if (rlen2 == 1)
		    return find(y.charAt(0), x);
		          
		else if(x.charAt(0) == y.charAt(0))
			return 1+r_lcs(x.substring(1), y.substring(1));
		
		else 
		{
		   
			return max(r_lcs(x,y.substring(1)), r_lcs(x.substring(1),y));
		}
		
	}
    
    public int find(char c, String x)
    {
        
        for (int i=0; i<x.length(); i++)
        {
          if (c == x.charAt(i))
            return 1;
        }
        
        //loop_counter++;
        return 0;
     }
	
    public int max(int x, int y) 
    {
        if (x > y)
          return x;
        else
          return y;
    }
    
    public boolean isAlpha(String name) 
    {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
    
	public void autorun() 
	{
		Init();
		jButtonNext.setEnabled(false);
		flag = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (flag) {
			Traves();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
