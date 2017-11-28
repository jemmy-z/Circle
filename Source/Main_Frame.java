import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class Main_Frame {
	//all panels and frames
	private static JFrame frame;
	private static JPanel panel;
	private static JPanel left_panel;
	private static JPanel right_panel;

	//version
	private static String code_version = "v.1.0.2";

	//Data lists
	private static ArrayList<Food> food_list = new ArrayList<Food>();
	private static ArrayList<Orders> order_list = new ArrayList<Orders>();

	private static boolean first_time = true;
	//color attributes
	private static String font = "Existence-Light";
	private static Color main_color = new Color(83, 200, 255);
	private static Color data_color = new Color(174, 234, 255);
	private static Color menu_color = new Color(168, 255, 255);

	//for getting screen size
	private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static int SCREEN_X = gd.getDisplayMode().getWidth();
	private static int SCREEN_Y = gd.getDisplayMode().getHeight();

	//order number
	private static int ORDER_NUM = 1;

	public static void main(String[] args) throws FileNotFoundException {
		frame = new JFrame("Circle");
		setFrame();
		setPanel();
		createMenu();
		frame.setVisible(true);
	}

    private static void setFrame() {
        frame.setSize(SCREEN_X, SCREEN_Y);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setUndecorated(true);
    }
    private static void setPanel() {
    	panel = new JPanel(new GridBagLayout());
		panel.setBackground(main_color);
		frame.add(panel);
    }
    private static void createMenu() throws FileNotFoundException {
    	if(first_time) {
    		File f = new File("FoodData.txt");
        	Scanner console = new Scanner(f);
        	while(console.hasNextLine()) {
        		food_list.add(new Food(console.nextLine(), Double.valueOf(console.nextLine())));
        	}

        	BufferedReader br = null;
   	    	String line = "";
   	    	String cvsSplitBy = ",";
   	        try {
   	            br = new BufferedReader(new FileReader("SalesData.csv"));
   	            while ((line = br.readLine()) != null) {
  	                String[] input = line.split(cvsSplitBy);

  	                String t = input[0];
  	                String on = input[1];
  	                ORDER_NUM = Integer.valueOf(on) + 1;
  	                String name = input[2];
  	                ArrayList<Integer> nums = new ArrayList<Integer>();
  	                for(int i = 3; i < input.length - 1; i++) {
  	                	nums.add(Integer.valueOf(input[i]));
  	                }
  	                Boolean b = Boolean.valueOf(input[input.length - 1]);
  	                Orders temp = new Orders(name, food_list, nums, t, on);
  	                temp.setStatus(b);
  	                order_list.add(temp);
   	            }

   	        } catch (FileNotFoundException e) {
   	            e.printStackTrace();
   	        } catch (IOException e) {
   	            e.printStackTrace();
   	        } finally {
   	            if (br != null) {
   	                try {
   	                    br.close();
   	                } catch (IOException e) {
   	                    e.printStackTrace();
   	                }
   	            }
   	        }

        	first_time = false;
        	console.close();
    	}


    	JLabel title = new JLabel("Circle");
    	title.setFont(new Font(font, Font.PLAIN, SCREEN_X/10));

    	JLabel version = new JLabel(code_version);
    	version.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

    	GridBagConstraints gc = new GridBagConstraints();

    	gc.gridx = 0;
    	gc.gridy = 0;
    	gc.gridwidth = 2;
    	gc.anchor = GridBagConstraints.CENTER;
    	panel.add(title, gc);

    	gc.gridx = 0;
    	gc.gridy = 1;
    	gc.gridwidth = 2;
    	gc.anchor = GridBagConstraints.CENTER;
    	panel.add(version, gc);

    	JButton b = new JButton("Add Food");
    	b.setPreferredSize(new Dimension(SCREEN_X/5, SCREEN_Y/15));
    	b.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));
        frame.getRootPane().setDefaultButton(b);

    	gc.gridx = 1;
    	gc.gridy = 3;
    	gc.gridwidth = 1;
    	gc.anchor = GridBagConstraints.CENTER;
    	gc.insets = new Insets(SCREEN_Y/20, 0, SCREEN_Y/50, 0);
    	panel.add(b, gc);
    	b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exit(panel);
				foodMenu();
            }
    	});

    	JButton enter = new JButton("Enter");
    	enter.setPreferredSize(new Dimension(SCREEN_X/5, SCREEN_Y/15));
    	enter.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));

    	gc.gridx = 1;
    	gc.gridy = 4;
    	gc.gridwidth = 1;
    	gc.anchor = GridBagConstraints.CENTER;
    	gc.insets = new Insets(SCREEN_Y/50, 0, SCREEN_Y/50, 0);
    	panel.add(enter, gc);
    	enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exit(panel);
            	main_menu();
            }
    	});

    	JButton report = new JButton("Reports");
    	report.setPreferredSize(new Dimension(SCREEN_X/5, SCREEN_Y/15));
    	report.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));

    	gc.gridx = 1;
    	gc.gridy = 5;
    	gc.gridwidth = 1;
    	gc.anchor = GridBagConstraints.CENTER;
    	gc.insets = new Insets(SCREEN_Y/50, 0, SCREEN_Y/50, 0);
    	panel.add(report, gc);
    	report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exit(panel);
				reportMenu();
            }
    	});

    	JButton exit = new JButton("Exit");
    	exit.setPreferredSize(new Dimension(SCREEN_X/5, SCREEN_Y/15));
    	exit.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));

    	gc.gridx = 1;
    	gc.gridy = 6;
    	gc.gridwidth = 1;
    	gc.anchor = GridBagConstraints.CENTER;
    	gc.insets = new Insets(SCREEN_Y/50, 0, SCREEN_Y/50, 0);
    	panel.add(exit, gc);
    	exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	rewriteFoodFile();
            	System.exit(0);
            }
    	});
    }
    private static void foodMenu() {
    	JLabel title = new JLabel("Add/Remove Food Items");
    	title.setFont(new Font(font, Font.PLAIN, SCREEN_X/20));

    	JLabel existing_foods = new JLabel("Existing Food Items: (10 items max)");
    	existing_foods.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));

    	ArrayList<JLabel> list = new ArrayList<JLabel>();
    	for(Food f:food_list) {
    		DecimalFormat df = new DecimalFormat("0.00");

    		list.add(new JLabel(f.getName() + " $" + df.format(f.getPrice())));
    	}
    	for(JLabel l:list) {
    		l.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
    	}

    	GridBagConstraints gc = new GridBagConstraints();
    	gc.anchor = GridBagConstraints.WEST;

    	gc.gridx = 0;
    	gc.gridy = 1;
    	gc.gridwidth = 2;
    	gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
    	panel.add(title, gc);

    	gc.gridx = 0;
    	gc.gridy = 2;
    	gc.gridwidth = 2;
    	gc.insets = new Insets(0, 0, 0, 0);
    	panel.add(existing_foods, gc);

    	gc.anchor = GridBagConstraints.WEST;
     	gc.gridwidth = 1;
    	gc.insets = new Insets(SCREEN_Y/200, SCREEN_X/30, SCREEN_Y/200, SCREEN_X/30);
    	for(int i = 0; i < list.size(); i++) {
    		if(i < 5) {
    			gc.gridx = 0;
    			gc.gridy = i + 3;
    		} else if(i >= 5 && i < 10) {
    			gc.gridx = 1;
    			gc.gridy = i - 2;
    		}
    		if(i < 10) {
    			panel.add(list.get(i), gc);
    		}
    	}

    	changeFood(gc);

    }
    private static void changeFood(GridBagConstraints gc) {
    	JLabel name = new JLabel("Food Name: ");
    	name.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

    	JLabel name2 = new JLabel("Food Name: ");
    	name2.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

    	JLabel price = new JLabel("Price: (0.00)");
    	price.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

    	JTextField add = new JTextField("");
        add.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
        add.setColumns(SCREEN_X/150);

        JTextField addP = new JTextField("");
        addP.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
        addP.setColumns(SCREEN_X/300);

        JTextField remove = new JTextField("");
        remove.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
        remove.setColumns(SCREEN_X/100);

        JButton addB = new JButton("Add Food");
        addB.setPreferredSize(new Dimension(SCREEN_X/5, SCREEN_Y/20));
        addB.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
        frame.getRootPane().setDefaultButton(addB);

        JButton removeB = new JButton("Remove Food");
        removeB.setPreferredSize(new Dimension(SCREEN_X/5, SCREEN_Y/20));
        removeB.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(SCREEN_X/10, SCREEN_Y/20));
        back.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));


        gc.insets = new Insets(SCREEN_Y/20, 0, SCREEN_Y/200, SCREEN_X/30);
        gc.anchor = GridBagConstraints.WEST;
        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy = 9;
        panel.add(name, gc);

        gc.gridx = 1;
        gc.gridy = 9;
        panel.add(price, gc);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(SCREEN_Y/200, 0, SCREEN_Y/50, SCREEN_X/30);
        gc.gridx = 0;
        gc.gridy = 10;
        panel.add(add, gc);

        gc.gridx = 1;
        gc.gridy = 10;
        panel.add(addP, gc);
        gc.fill = GridBagConstraints.NONE;

        gc.insets = new Insets(SCREEN_Y/200, SCREEN_X/30, SCREEN_Y/50, 0);
        gc.gridx = 2;
        gc.gridy = 10;
        panel.add(addB, gc);

        gc.insets = new Insets(SCREEN_Y/50, 0, SCREEN_Y/200, 0);
        gc.gridwidth = 3;
        gc.gridx = 0;
        gc.gridy = 11;
        panel.add(name2, gc);

        gc.gridwidth = 2;
        gc.insets = new Insets(SCREEN_Y/200, 0, 0, SCREEN_X/30);
        gc.gridx = 0;
        gc.gridy = 12;
        gc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(remove, gc);
        gc.gridwidth = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.insets = new Insets(SCREEN_Y/200, SCREEN_X/30, 0, 0);
        gc.gridx = 2;
        gc.gridy = 12;
        panel.add(removeB, gc);

        gc.insets = new Insets(SCREEN_Y/30, 0, 0, 0);
        gc.gridx = 0;
        gc.gridy = 13;
        panel.add(back, gc);

        DateFormat dateFormat = new SimpleDateFormat("MM.dd.yy_HH.mm.ss");
    	Date date = new Date();
    	String s = dateFormat.format(date) + "_SalesData.csv";

        addB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	generateReport(s);
            	clearReport();
            	if(!add.getText().equals("")) {
	            	String addF = new String(add.getText());
	            	String addPrice = new String(addP.getText());
	            	if(food_list.size() < 10) {
	            		if(!addF.equals(null)) {
	                		food_list.add(new Food(addF, Double.valueOf(addPrice)));
	                		exit(panel);
							rewriteFoodFile();
							foodMenu();
	                	}
	            	} else {
	            		JLabel err = new JLabel("ERROR: Too many items!");
	            		err.setFont(new Font(font, Font.PLAIN, SCREEN_X/70));

	            		gc.gridx = 0;
	            		gc.gridy = 0;
	            		gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
	            		panel.add(err, gc);
	            		panel.revalidate();
	            		frame.repaint();
	            	}
            	}
            }
    	});

        removeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	generateReport(s);
            	clearReport();

            	String remF = new String(remove.getText());
            	if(!remF.equals(null)) {
            		int ind = -1;
            		for(Food f:food_list) {
            			if (f.getName().equalsIgnoreCase(remF)) {
            				ind = food_list.indexOf(f);
            			}
            		}
            		if(ind != -1) {
            			food_list.remove(ind);
                		exit(panel);
						rewriteFoodFile();
						foodMenu();
            		} else {
            			JLabel err = new JLabel("ERROR: Food item not found.");
                		err.setFont(new Font(font, Font.PLAIN, SCREEN_X/70));

                		gc.gridx = 0;
                		gc.gridy = 0;
                		gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
                		panel.add(err, gc);
                		panel.revalidate();
               		}
            	}
            }
    	});

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exit(panel);
            	try {
					createMenu();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
    	});
    }
    private static void reportMenu() {
    	GridBagConstraints gc = new GridBagConstraints();
    	gc.anchor = GridBagConstraints.CENTER;

    	JLabel title = new JLabel("Reports");
    	title.setFont(new Font(font, Font.PLAIN, SCREEN_X/20));

    	gc.gridwidth = 3;
    	gc.gridx = 1;
    	gc.gridy = 2;
    	gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
    	panel.add(title, gc);

    	JButton generate = new JButton("Generate A Report");
    	generate.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));
    	generate.setPreferredSize(new Dimension(SCREEN_X/3, SCREEN_Y/15));

    	gc.gridx = 1;
    	gc.gridy = 3;
    	gc.insets = new Insets(SCREEN_Y/50, 0, SCREEN_Y/50, 0);
    	panel.add(generate, gc);

    	DateFormat dateFormat = new SimpleDateFormat("MM.dd.yy_HH.mm.ss");
    	Date date = new Date();
    	String s = dateFormat.format(date) + "_SalesData.csv";

    	JLabel copied = new JLabel("Report Generated!");
    	JLabel f_name = new JLabel("File Name: " + s);
    	JLabel cleared = new JLabel("Data Cleared!");

    	generate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	panel.remove(cleared);

            	generateReport(s);
	            copied.setFont(new Font(font, Font.PLAIN, SCREEN_X/60));
	            f_name.setFont(new Font(font, Font.PLAIN, SCREEN_X/60));

	            gc.anchor = GridBagConstraints.WEST;
	            gc.gridx = 0;
	            gc.gridy = 0;
	            gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
	            panel.add(copied, gc);

	            gc.gridx = 0;
	            gc.gridy = 1;
	            gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
	            panel.add(f_name, gc);

	            panel.revalidate();
	            frame.repaint();
	        }
    	});

    	JButton clear = new JButton("Clear Sales Data");
    	clear.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));
    	clear.setPreferredSize(new Dimension(SCREEN_X/3, SCREEN_Y/15));

    	gc.anchor = GridBagConstraints.CENTER;
    	gc.gridx = 1;
    	gc.gridy = 4;
    	gc.insets = new Insets(SCREEN_Y/50, 0, SCREEN_Y/50, 0);
    	panel.add(clear, gc);

    	clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	panel.remove(copied);
            	panel.remove(f_name);

            	clearReport();
	            cleared.setFont(new Font(font, Font.PLAIN, SCREEN_X/60));

	            gc.gridx = 0;
	            gc.gridy = 0;
	            gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
	            panel.add(cleared, gc);
	            panel.revalidate();
	            frame.repaint();
	        }
    	});

    	JButton back = new JButton("Back");
    	back.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));
    	back.setPreferredSize(new Dimension(SCREEN_X/3, SCREEN_Y/15));

    	gc.gridx = 1;
    	gc.gridy = 5;
    	gc.insets = new Insets(SCREEN_Y/50, 0, 0, 0);
    	panel.add(back, gc);

    	back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exit(panel);
            	try {
					createMenu();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
    	});

    }
    private static void generateReport(String s) {
    	File f = new File("Reports/" + s);
    	if(!f.exists()) {
    		try {
				f.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	FileWriter fw = null;
    	try {
    		fw = new FileWriter(f.getAbsolutePath());
    	} catch (IOException e1) {
    		e1.printStackTrace();
    	}
    	PrintWriter pw = new PrintWriter(fw);
    	DecimalFormat df = new DecimalFormat("0.00");

    	pw.print("Time Stamp,Order Number,Customer Name,");
    	for(Food fo: food_list) {
    		pw.print(fo.getName() + ",");
    	}
    	pw.println("Order Complete?,Total Sale");
    	for(Orders o:order_list) {
    		pw.print(o.toString());
    		pw.println(",$" + df.format(o.getTotalCost()));
    	}
    	pw.close();
    }
    private static void clearReport() {
    	File f = new File("SalesData.csv");
    	f.delete();

    	File f2 = new File("SalesData.csv");
    	try {
			f2.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	ORDER_NUM = 1;
    	order_list.clear();
    }
    private static void rewriteFoodFile() {
    	File f = new File("FoodData.txt");
    	if(!f.exists()) {
    		try {
				f.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}

    	FileWriter fw = null;
		try {
			fw = new FileWriter(f.getAbsolutePath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	PrintWriter pw = new PrintWriter(fw);

    	for(Food fo: food_list) {
    		pw.println(fo.getName());
    		pw.println(fo.getPrice());
    	}
    	pw.close();
    }
    private static void rewriteOrderFile() {
    	File f = new File("SalesData.csv");
    	if(!f.exists()) {
    		try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	FileWriter fw = null;
    	try {
    		fw = new FileWriter(f.getAbsolutePath());
    	} catch (IOException e1) {
    		e1.printStackTrace();
    	}
    	PrintWriter pw = new PrintWriter(fw);

    	for(Orders o:order_list) {
    		pw.println(o.toString());
    	}
    	pw.close();
    }
    private static void main_menu() {
    	exit(panel);

    	panel.setLayout(new GridLayout(1, 2));

    	left_panel = new JPanel(new GridBagLayout());
    	right_panel = new JPanel(new GridBagLayout());

    	left_panel.setBackground(data_color);
    	left_panel.setBounds(0, 0, SCREEN_X/2, SCREEN_Y);
    	left_panel.setBorder(BorderFactory.createMatteBorder(SCREEN_Y/40, SCREEN_X/60, SCREEN_Y/40, SCREEN_X/60, main_color));


    	right_panel.setBackground(menu_color);
    	right_panel.setBounds(SCREEN_X/2, 0, SCREEN_X/2, SCREEN_Y);

    	panel.add(left_panel);
    	panel.add(right_panel);

    	create_left_panel();
    	create_right_panel();
    }
    private static void create_left_panel() {
    	GridBagConstraints gc = new GridBagConstraints();
    	DecimalFormat df = new DecimalFormat("0.00");

    	JLabel order_n;
    	JLabel total_price;
    	if(order_list.size() > 0) {
    		Orders first = order_list.get(order_list.size() - 1);
    		order_n = new JLabel("Order Number " + first.getOrderNumber());
    		total_price = new JLabel("Total Price = $" + df.format(first.getTotalCost()));
    	} else {
    		order_n = new JLabel("Order Number ###");
    		total_price = new JLabel("Total Price = $#.##");
    	}

    	order_n.setFont(new Font(font, Font.PLAIN, SCREEN_X/30));
    	total_price.setFont(new Font(font, Font.BOLD, SCREEN_X/20));

    	gc.anchor = GridBagConstraints.WEST;
    	gc.gridx = 0;
    	gc.gridy = 0;
    	gc.gridwidth = 2;
    	gc.insets = new Insets(0, 0, SCREEN_Y/50, 0);
    	left_panel.add(order_n, gc);

    	gc.gridx = 0;
    	gc.gridy = 1;
    	gc.insets = new Insets(0, 0, SCREEN_Y/50, 0);
    	left_panel.add(total_price, gc);

    	gc.insets = new Insets(SCREEN_Y/100, 0, SCREEN_Y/100, 0);

    	JLabel[] list = new JLabel[5];
    	int i = 0;
       	for(Orders os:order_list) {
    		if(i < 5) {
    			if(!os.getStatus()) {
    				String name = os.getCustomerName();
        			String o_n = os.getOrderNumber();


        			String temp = o_n + " " + name + " <BR>";
        			int j = 1;
        			for(Food f: food_list) {
        				if(os.getOrderCount(f) > 0) {
        					temp += os.getOrderCount(f) + " " + f.getName() + ", ";
        					if(j%2 == 0) {
        						temp += "<BR>";
        					}
        					j++;
        				}
        			}

        			list[i] = new JLabel("");
        			list[i].setFont(new Font(font, Font.PLAIN, SCREEN_X/60));
        			list[i].setBackground(new Color(255, 255, 255));
        			list[i].setMinimumSize(new Dimension((int) (SCREEN_X/2.5), SCREEN_Y/10));
        			list[i].setPreferredSize(new Dimension((int) (SCREEN_X/2.5), SCREEN_Y/10));
        			list[i].setMaximumSize(new Dimension((int) (SCREEN_X/2.5), SCREEN_Y/10));
        			list[i].setOpaque(true);

        			list[i].setText("<html>" + temp + "</html>");

        			gc.gridwidth = 2;
        			gc.gridx = 0;
        			gc.gridy = i + 2;
        			left_panel.add(list[i], gc);
        			i++;
    			}
    		} else {
    			break;
    		}
    	}

    	JTextField num = new JTextField("Order #");
    	num.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

    	gc.anchor = GridBagConstraints.CENTER;
    	gc.gridwidth = 1;
    	gc.gridx = 0;
    	gc.gridy = 7;
    	gc.insets = new Insets(0, 0, 0, SCREEN_X/30);
    	left_panel.add(num, gc);

    	JButton complete = new JButton("Complete Order");
    	complete.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
    	complete.setPreferredSize(new Dimension(SCREEN_X/4, SCREEN_Y/15));

    	gc.gridx = 1;
    	gc.gridy = 7;
    	gc.insets = new Insets(0, SCREEN_X/30, 0, 0);
    	left_panel.add(complete, gc);

    	complete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
	            int index = Integer.valueOf(num.getText());
	            order_list.get(index - 1).setStatus(true);

	            exit(right_panel);
            	exit(left_panel);
            	create_right_panel();
            	create_left_panel();
            }
    	});
    }
	private static void create_right_panel() {
    	JButton back = new JButton("Back");
    	back.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

    	JButton addOrder = new JButton("Add Order");
    	addOrder.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));

    	JLabel name = new JLabel("Name: ");
    	name.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));

    	JLabel food_name = new JLabel("Food Name: ");
    	food_name.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));

    	JLabel quant = new JLabel("Quantity: ");
    	quant.setFont(new Font(font, Font.PLAIN, SCREEN_X/40));

    	JTextField name_text = new JTextField("");
    	name_text.setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
    	name_text.setColumns(SCREEN_X/10);

    	ArrayList<JLabel> food_choice = new ArrayList<JLabel>();
    	ArrayList<JTextField> food_quantity = new ArrayList<JTextField>();

    	for(int i = 0; i < food_list.size(); i++) {
    		food_choice.add(new JLabel(food_list.get(i).getName()));
    		food_quantity.add(new JTextField("0"));
    	}

    	GridBagConstraints gc = new GridBagConstraints();

    	gc.insets = new Insets(0, 0, SCREEN_Y/100, 0);
    	gc.gridx = 0;
    	gc.gridy = 1;
    	gc.gridwidth = 2;
    	gc.anchor = GridBagConstraints.WEST;
    	right_panel.add(name, gc);

    	gc.insets = new Insets(SCREEN_Y/100, 0, SCREEN_Y/40, 0);
    	gc.gridx = 0;
    	gc.gridy = 2;
    	gc.fill = GridBagConstraints.HORIZONTAL;
    	right_panel.add(name_text, gc);
    	gc.fill = GridBagConstraints.NONE;

    	gc.insets = new Insets(SCREEN_Y/40, 0, SCREEN_Y/200, SCREEN_X/20);
    	gc.gridwidth = 1;
    	gc.gridx = 0;
    	gc.gridy = 3;
    	right_panel.add(food_name, gc);

    	gc.insets = new Insets(SCREEN_Y/40, SCREEN_X/20, SCREEN_Y/100, 0);
    	gc.gridx = 1;
    	gc.gridy = 3;
    	right_panel.add(quant, gc);

    	gc.insets = new Insets(SCREEN_Y/200, 0, SCREEN_Y/200, SCREEN_X/20);
    	for(int i = 0; i < food_choice.size(); i++) {
    		gc.gridx = 0;
    		gc.gridy = i + 4;
    		food_choice.get(i).setFont(new Font(font, Font.PLAIN, SCREEN_X/50));
    		right_panel.add(food_choice.get(i), gc);
    	}

    	gc.fill = GridBagConstraints.HORIZONTAL;
    	int i;
    	gc.insets = new Insets(SCREEN_Y/200, SCREEN_X/20, SCREEN_Y/200, 0);
    	for(i = 0; i < food_quantity.size(); i++) {
    		gc.gridx = 1;
    		gc.gridy = i + 4;
    		food_quantity.get(i).setFont(new Font(font, Font.PLAIN, SCREEN_X/60));
    		right_panel.add(food_quantity.get(i), gc);
    	}
    	gc.fill = GridBagConstraints.NONE;

    	gc.insets = new Insets(SCREEN_Y/40, 0, 0, 0);
    	gc.anchor = GridBagConstraints.WEST;
    	gc.gridx = 0;
    	gc.gridy = i + 4;
    	right_panel.add(back, gc);

    	gc.insets = new Insets(SCREEN_Y/40, 0, 0, 0);
    	gc.anchor = GridBagConstraints.EAST;
    	gc.gridx = 1;
    	gc.gridy = i + 4;
    	right_panel.add(addOrder, gc);

    	addOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
	            if(!name_text.getText().equals("")) {
	            	ArrayList<Integer> nums = new ArrayList<Integer>();
	            	for(JTextField j: food_quantity) {
	            		if(j.getText().equals("")) {
	            			JLabel l = new JLabel("ERROR: Invalid Input.");
	    	            	l.setFont(new Font(font, Font.PLAIN, SCREEN_X/80));
	    	            	gc.gridwidth = 2;
	    	            	gc.gridx = 0;
	    	            	gc.gridy = 0;
	    	            	gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
	    	            	gc.anchor = GridBagConstraints.WEST;
	    	            	right_panel.add(l, gc);
	    	            	right_panel.revalidate();
	    	            	gc.gridwidth = 1;
	    	            	gc.anchor = GridBagConstraints.CENTER;
	            		} else {
	            			nums.add(Integer.valueOf(j.getText()));
	            		}
	            	}

	            	DateFormat dateFormat = new SimpleDateFormat("MM.dd.yy HH:mm:ss");
	            	Date date = new Date();
	            	String s = dateFormat.format(date);

	            	DecimalFormat df = new DecimalFormat("000");
	            	String o_n = String.valueOf(df.format(ORDER_NUM));

	            	Orders temp = new Orders(name_text.getText(), food_list, nums, s, o_n);
	            	order_list.add(temp);
	            	rewriteOrderFile();
	            	ORDER_NUM++;
	            	exit(right_panel);
	            	exit(left_panel);
	            	create_right_panel();
	            	create_left_panel();
	            } else {
	            	JLabel l = new JLabel("ERROR: Name needed.");
	            	l.setFont(new Font(font, Font.PLAIN, SCREEN_X/80));
	            	gc.gridwidth = 2;
	            	gc.gridx = 0;
	            	gc.gridy = 0;
	            	gc.insets = new Insets(0, 0, SCREEN_Y/20, 0);
	            	gc.anchor = GridBagConstraints.WEST;
	            	right_panel.add(l, gc);
	            	right_panel.revalidate();
	            	gc.gridwidth = 1;
	            	gc.anchor = GridBagConstraints.CENTER;
	            }
            }
    	});

    	back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
	            exit(left_panel);
	            exit(right_panel);
	            exit(panel);

	            panel.setLayout(new GridBagLayout());
	            try {
					createMenu();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
    	});

    }
    private static void exit(JPanel p) {
    	p.removeAll();
    	p.revalidate();
    	p.repaint();
    }
}
