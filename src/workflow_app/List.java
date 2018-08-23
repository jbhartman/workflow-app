package workflow_app;

import java.util.ArrayList;

/* Methods to add:
 * private boolean is_in_list(Task) [done]
 * public int get_index(Task) [done]
 * public Task get_task(index) [done]
 * public void set_name() [done]
 * public void append(Task) [done]
 * public void insert(Task, index) [done]
 * public void replace(new_task, index) [tests written]
 * public Task remove(Task) [tests written]
 * public Task remove(index) [tests written]
 * public void transfer(Task/index, list2) [writing tests]
 *   -- A method to transfer a task from one list to another
 * public void transfer(index, list2, dest_index) []
 *   -- dest_index stands for "destination_index"
 * - Might need a method to stage list for graphical display,
 * 		will know more once closer to such a point and have
 * 		becomed more acquainted with what's required
 */

/**
 * <b>List</b> class <br>
 * <br>
 * Container class holding Tasks.  It has various methods to act
 * upon itself which will be a bulk of the user-facing
 * functionalities are contained within this class as the List
 * class will be (at least what I'm imagining right now) the bulk
 * of the tech at least under the hood.  I'm sure there'll still be
 * quite a bit left graphically. <br>
 * <br>
 * Bugs: None known
 * 
 * @author		Jacob Hartman (jabrhartman@gmail.com)
 * @version		Pre-alpha
 * @see also	Task
 *
 */
public class List
{
	// Attributes
	String my_name;
	ArrayList<Task> my_tasks;
	
	/**
	 * <b>ListException</b> <br>
	 * Extension of Exception to handle exceptions pertaining
	 * to the List class
	 */
	@SuppressWarnings("serial")
	public class ListException extends Exception
	{
		String my_location;
		String my_message;
		
		ListException(String where, String message)
		{
			my_location = where;
			my_message = message;
		}
		
		public String get_error_src()
		{
			return my_location;
		}
		
		public String toString()
		{
			return "List::" + my_location +
					": " + my_message;
		}
	}
	
	/**
	 * <b>Default constructor</b> <br>
	 * 
	 * Sets instance variables to default values <br>
	 * -- my_name gets empty string<br>
	 * -- my_tasks gets empty ArrayList&lt;Task>
	 * 
	 * @see {@link #List(String)}, explicit constructor <br>
	 * {@link #List(List)}, copy constructor
	 */
	public List()
	{
		my_name = "";
		my_tasks = new ArrayList<Task>();
	}
	
	/**
	 * <b>Explicit constructor</b> <br>
	 * Sets my_name to be the passed String argument and my_tasks
	 * to be an empty ArrayList&lt;Task>
	 * 
	 * @param name - a String, the name of the list
	 * 
	 * @see {@link #List()}, default constructor <br>
	 * {@link #List(List)}, copy constructor
	 */
	public List(String name)
	{
		my_name = name;
		my_tasks = new ArrayList<Task>();
	}
	
	/**
	 * <b>Copy constructor</b> <br>
	 * Creates a new List instance which is a copy the given
	 * original list
	 * 
	 * @param original - a List
	 * 
	 * @see {@link #List()}, default constructor <br>
	 * {@link #List(String)}, explicit constructor
	 */
	public List(List original)
	{
		my_name = original.my_name;
		my_tasks = new ArrayList<Task>();
		
		for(int idx=0; idx < original.get_size(); idx++)
		{
			my_tasks.add(original.get_array().get(idx));
		}
	}
	
	
	///////////////
	// Accessors //
	///////////////
	
	/**
	 * Returns name of the list
	 * 
	 * @return Name of the list, a String
	 */
	public String get_name() { return my_name; }
	
	/**
	 * Returns size or length of the list
	 * 
	 * @return Length of the list, an int
	 */
	public int get_size() { return my_tasks.size(); }
	
	/**
	 * Returns the ArrayList&lt;Task> which contains the tasks for
	 * the List instance. <br><br>
	 * <b>Note</b>: This method is public but will only be so while unit
	 * testing is ongoing.
	 * 
	 * @return my_tasks, an ArrayList&lt;Task>, the list of tasks
	 * contained within the List instance
	 */
	public ArrayList<Task> get_array() { return my_tasks; }

	/**
	 * Determines whether a given task is in the list or not <br>
	 * <b>Note</b>: This method will only be public while unit
	 * testing is ongoing, at which point it will become protected.
	 * 
	 * @param given_task - a Task, the Task being searched for
	 * 					   in the list
	 * 
	 * @return
	 * 		true, if a task with the same details is in the
	 * 		  list <br>
	 * 		false, if there is no task in the list with the same
	 * 		   details.
	 */
	public boolean is_in_list(Task given_task)
	{
		for(int idx=0; idx < get_size(); idx++)
		{
			if(my_tasks.get(idx).equal_details(given_task))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Provided a Task instance with details equivalent to the
	 * passed String exists in the list, the method returns the
	 * index of that task.
	 * 
	 * @param task_details - a String
	 * 
	 * @return Index of the Task with details equivalent to the
	 * 		   string passed as the argument
	 * 
	 * @throws ListException if a task with details equivalent to
	 * 		   the string argument <tt>task_details</tt> is not
	 * 		   found in the list
	 * 
	 * @see {@link #get_index(Task)}
	 */
	public int get_index(String task_details) throws ListException
	{
		if( !is_in_list(new Task(task_details)) )
		{
			throw new ListException("get_index(String)", "No task exists in the list with the given details.");
		}
		
		int task_idx = 0;
		
		for(int idx=0; idx < get_size(); idx++)
		{
			if(my_tasks.get(idx).get_details() == task_details)
			{
				task_idx = idx;
				break;
			}
		}
		
		return task_idx;
	}
	
	/**
	 * Provided a task equivalent to that of the passed argument
	 * exists in the list, the method returns the index of that
	 * task.  Otherwise, throws a <tt>ListException</tt>
	 * 
	 * @param given_task - a Task
	 * 
	 * @return Index of the task equivalent to the passed task
	 * 		   argument
	 * 
	 * @throws ListException if equivalent task does not exist in
	 * 		   the list
	 * 
	 * @see {@link get_index(String)}
	 */
	public int get_index(Task given_task) throws ListException
	{
		/*
		 * This if-loop only determines if a Task with the same
		 * details exists in the list.  If the loop is passed,
		 * then the method still needs to check if that task has
		 * the same assigned_user as given_task has.
		 */
		if( !is_in_list(given_task) )
		{
			throw new ListException("get_index(Task)", "No task with equivalent details exists in list.");
		}
		
		int task_idx = get_index(given_task.get_details());
		
		if( my_tasks.get(task_idx).get_user()
				== given_task.get_user())
		{
			return task_idx;
		}
		else
		{
			throw new ListException("get_index(Task)", "There is not task in the list identical to the passed task.");
		}
	}
	
	/**
	 * Returns task at the index given.  Throws ListException if
	 * the given index is out of range.
	 * 
	 * @param index - an int
	 * 
	 * @return Task at the index of <tt>index</tt> in the list
	 * 
	 * @throws ListException if <tt>index</tt> is negative or
	 * past the end of the list
	 */
	public Task get_task(int index) throws ListException
	{
		if(index >= get_size())
		{
			throw new ListException("get_task(index)", "Trying to index past end of list.");
		}
		else if(index < 0)
		{
			throw new ListException("get_task(index)", "Given index is negative.");
		}
		
		return my_tasks.get(index);
	}
	
	
	//////////////
	// Mutators //
	//////////////
	
	/**
	 * Appends a task to the list provided the task is not
	 * already in the list
	 * 
	 * @param new_task - a Task
	 * @throws ListException if a task with equivalent details to
	 * that of <tt>new_task</tt> is already in the list
	 */
	public void append(Task new_task) throws ListException
	{
		if(is_in_list(new_task))
		{
			throw new ListException("append(new_task)", "Given task is already in the list.");
		}
		
		my_tasks.add(new_task);
	}
	
	/**
	 * Changes name of List instance to the given String
	 * 
	 * @param new_name - a String
	 */
	public void set_name(String new_name)
	{
		my_name = new_name;
	}
	
	/**
	 * Inserts a task at the specified index and shifts any tasks
	 * previously at that index over one
	 * 
	 * @param index - an int, must be greater than or equal to 0
	 * @param new_task - a Task
	 * 
	 * @throws ListException - if <tt>index</tt> is negative or if
	 * <tt>new_task</tt> has details equivalent to that of any
	 * task already in the list
	 */
	public void insert(int index, Task new_task) throws ListException
	{
		if(index < 0)
		{
			throw new ListException("insert(index, new_task)",
					"Given index must be greater than zero.");
		}
		else if(index >= get_size())
		{
			append(new_task);
		}
		else	// 0 <= index < get_size()
		{
			if(is_in_list(new_task))
			{
				throw new ListException("insert(index, new_task)",
						"Tried to insert preexisting task.");
			}
			
			my_tasks.add(index, new_task);
		}
	}
	
	/**
	 * Replaces task at specified index with the new task given
	 * 
	 * @param index - an int, must be nonnegative
	 * @param replacement - a Task
	 * 
	 * @throws ListException if <tt>index</tt> is negative or if
	 * the details of <tt>replacement</tt> are equivalent to that
	 * of a task already in the list
	 */
	public void replace(int index, Task replacement) throws ListException
	{
		
	}
	
	public Task remove(Task removed_task) throws ListException
	{
		return new Task();
	}
	
	public Task remove(int removed_idx) throws ListException
	{
		return new Task();
	}
	
	public void transfer(int trans_idx, List dest_list,
			int dest_idx) throws ListException
	{
		
	}
	
	public void transfer(Task trans_idx, List dest_list,
			int dest_idx) throws ListException
	{
		
	}
	
	public void transfer(int trans_idx, List dest_list)
	throws ListException
	{
		// Basically just use transfer with more args, but have
		// the destination index be the end of the destination
		// list
		transfer(trans_idx, dest_list, dest_list.get_size());
	}
	
	public void transfer(Task trans_task, List dest_list)
	throws ListException
	{
		
	}
	
	// This method won't be tested with JUnit as much as I will
	// just use it and see what the output is.
	public String toString()
	{
		String ret_str = "";
		
		ret_str += my_name + ":\n";
		
		for(int idx=0; idx < get_size(); idx++)
		{
			ret_str += "   " + (idx+1) + ": "
					+ my_tasks.get(idx) + '\n';
		}
		
		return ret_str;
	}

	public static void main(String[] args) throws ListException
	{
		// just a visual test of the toString() method
		List l0 = new List("Test list");
		l0.append(new Task("Finish tasklist project"));
		l0.append(new Task("Get funding"));
		l0.append(new Task("Profit"));
		
		l0.insert(1, new Task("Find cofounder"));
		
		System.out.println(l0);
	}

}
