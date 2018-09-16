package workflow_app;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

@SuppressWarnings("serial")
public class WFAppGUI extends Frame
{
	public WFAppGUI() throws HeadlessException
	{
		// TODO Auto-generated constructor stub
	}

	public WFAppGUI(GraphicsConfiguration gc)
	{
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public WFAppGUI(String title) throws HeadlessException
	{
		super(title);
		// TODO Auto-generated constructor stub
	}

	public WFAppGUI(String title, GraphicsConfiguration gc)
	{
		super(title, gc);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		int test_case = 1;
		String temp_title = "";
		
		switch(test_case)
		{
			case 1: new WFAppGUI();
			case 2: new WFAppGUI(temp_title);
			default: System.err.println("Invalid case");
		}
	}
}
