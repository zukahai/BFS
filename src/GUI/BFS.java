package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BFS extends JFrame implements ActionListener, KeyListener{
	int key = 1;
	boolean die = false;
	int M = 10, N = 10;
	JButton bt[][] = new JButton[100][100];
	int a[][] = new int [M][N];
	Container cn;
	Color cl[] = {Color.lightGray, Color.black, Color.red, Color.blue};
	
	String matrix[] = {"3333333333300000000330000000033000000003300001100330000200033000011003300000000330000000033333333333",
			"3333333333300000000330000100033000000003300000000330000200033001001003300000000330000000033333333333",
			"3333333333300000000330000000033000000003300000000330000200033000000003300000000330000000033333333333",
			"3333333333333333333333000000033300000003330000003333000200333300000033330000003333330003333333333333",
			""};
	
	public BFS() {
		int k = 3;
		String s = matrix[k];
		int index = 0;
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				a[i][j] = s.charAt(index++) - 48;
		cn = init();
	}
	
	public Container init() {
		Container cn = this.getContentPane();
		
		JPanel pn = new JPanel();
		pn.setLayout(new GridLayout(M, N));
		
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++) {
				bt[i][j] = new JButton();
				bt[i][j].addActionListener(this);
				bt[i][j].addKeyListener(this);
				bt[i][j].setBackground(cl[a[i][j]]);
				bt[i][j].setActionCommand(i * N + j + "");
				bt[i][j].setBorder(null);
				pn.add(bt[i][j]);
			}
		
		JPanel pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		
		cn.add(pn);
		cn.add(pn2, "South");
		cn.addKeyListener(this);
		
		this.setVisible(true);
		this.setSize(600, 650);
		this.setLocationRelativeTo(null);
//		setResizable(false);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		return cn;
	}

	public void BFS() {
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++) 
				bt[i][j].setBackground(cl[a[i][j]]);
		int MMM = -1111;
		Queue<Point> st = new LinkedList<>();
		int x[] = new int [M + 1];
		int y[] = new int [N + 1];
		int b[][] = new int [M + 1][N + 1];
		Point p = new Point();
		Point d = new Point();
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++) {
				b[i][j] = MMM;
				if (a[i][j] == 2) {
					p = new Point(i, j);
					b[i][j] = 0;
				}
				if (a[i][j] == 3) {
					d = new Point(i, j);
				}
			}
		
		st.add(p);
		boolean kt = false;
		while (!st.isEmpty() && !kt) {
			p = st.poll();
			int X = (int) p.getX();
			int Y = (int) p.getY();
//			System.out.println(b[X][Y]);
			for (int i = X - 1; i <= X + 1; i++)
				for (int j = Y - 1; j <= Y + 1; j++)
					if (i >= 0 && i < M && j >= 0 && j < N &&Math.abs(i - X) + Math.abs(j - Y) == 1) {
						if (b[i][j] == MMM && (a[i][j] == 0 || a[i][j] == 3)) {
							b[i][j] = b[X][Y] + 1;
							st.add(new Point(i, j));
						}
							
					}
			
		}
		
//		for (int i = 0; i < M; i++) {
//			for (int j = 0; j < N; j++)
//				System.out.print(b[i][j] + " ");
//			System.out.println();
//		}
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
				if (b[i][j] != MMM && a[i][j] == 3 && b[i][j] < b[(int) d.getX()][(int) d.getY()]) {
					d = new Point(i, j);
				}
		int X = (int) d.getX();
		int Y = (int) d.getY();
		if (b[X][Y] == MMM || a[X][Y] != 3) {
			JOptionPane.showMessageDialog(null, "Win");
			die = true;
		}
//		System.out.println(X + " " + Y + " " + b[X][Y]);
		if (b[X][Y] == 1) {
			die = true;
			JOptionPane.showMessageDialog(null, "Loss");
		}
		while (b[X][Y] > 0) {
			int XX = X;
			int YY = Y;
//			System.out.println(b[X][Y] + " --^^");
			for (int i = XX - 1; i <= XX + 1; i++)
				for (int j = YY - 1; j <= YY + 1; j++)
					if (i >= 0 && i < M && j >= 0 && j < N && Math.abs(i - X) + Math.abs(j - Y) == 1 && b[i][j] - b[X][Y] == -1) {
						if (a[i][j] != 2) {
//							System.out.println(i + " " + j);
							bt[i][j].setBackground(Color.orange);
						}
						X = i;
						Y = j;
						if (b[X][Y] == 1) {
							a[X][Y] = 2;
							bt[X][Y].setBackground(cl[a[X][Y]]);
						}
					}
		}
		a[X][Y] = 0;
		bt[X][Y].setBackground(cl[a[X][Y]]);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (die)
			return;
		int K = Integer.parseInt(e.getActionCommand());
		int J = K % N;
		int I = K / N;
		a[I][J] = key;
		if (key < 4)
			bt[I][J].setBackground(cl[key]);
		BFS();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void print() {
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(a[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
	
	public String print2() {
		String s = "";
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++)
				s = s + a[i][j];
		}
		return s;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		key = e.getKeyCode() - 48;
		if (key == 5) {
//			print();
//			System.out.println(print2());
			BFS();
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BFS();
	}
}
