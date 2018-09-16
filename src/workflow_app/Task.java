package workflow_app;

/**
 * Represents a task with my_details spelling out what needs to
 * be done and assigned_user being the person carrying out the
 * task. <br>
 * <br>
 * Bugs: None known
 * 
 * @author 		Jacob Hartman (jabrhartman@gmail.com)
 * @version 	Pre-alpha
 * @see also	TaskList
 */
public class Task
{
	////////////////
	// attributes //
	////////////////
	
	// A string which will have the details of the task
	// given; that is, whatever the task is, this string
	// will say that.
	private String my_details;
	
	// Will later be a custom-defined "User" Object
	// This is the given user working on the specified
	// task.
	private int assigned_user;
	
	/**
	 * Exception subclass for exceptions within Task class
	 * 
	 * @author Jacob Hartman (jabrhartman@gmail.com)
	 */
	@SuppressWarnings("serial")
	public class TaskException extends Exception
	{
		String my_location;
		String my_message;
		
		/**
		 * Creates a TaskException instance with the source of the
		 * exception, where, and the reason for the exception,
		 * message
		 * 
		 * @param	where
		 * 			a String, the method which threw the exception
		 * 
		 * @param 	message
		 * 			a String, the reason for the exception being
		 * 			thrown
		 */
		TaskException(String where, String message)
		{
			my_location = where;
			my_message = message;
		}
		
		/**
		 * Allows for vailidation in testing of the source of
		 * the exception
		 * 
		 * @return	The source of the exception
		 */
		public String get_error_src()
		{
			return my_location;
		}
		
		public String toString()
		{
			return "Task::" + my_location +
					": " + my_message;
		}
	}
	
	/////////////
	// Methods //
	/////////////
	
	/**
	 * <b>Default constructor</b> <br>
	 * 
	 * Instance variables set to default values <br>
	 * &mdash; my_details set to empty string <br>
	 * &mdash; assigned_user set to 0
	 * 
	 * @see	{@link #Task(String)}, explicit-A constructor
	 * @see	{@link #Task(int)}, explicit-B constructor
	 * @see	{@link #Task(String, int)}, explicit-C constructor
	 */
	public Task()
	{
		my_details = "";
		assigned_user = 0;
	}
	
	/**
	 * <b>Explicit-A constructor</b> <br>
	 * 
	 * Initialises task instance with details specified but no
	 * assigned user
	 * 
	 * @param	details
	 * 			a String, the details of the newly created task
	 * 
	 * @see		{@link #Task()}, default constructor
	 * @see		{@link #Task(int)}, explicit-B constructor
	 * @see		{@link #Task(String, int)}, explicit-C constructor
	 */
	public Task(String details)
	{
		my_details = details;
		assigned_user = 0;
	}
	
	/**
	 * <b>Explicit-B constructor</b> <br>
	 * 
	 * Initialises task instance with assigned user but no details
	 * 
	 * @param	user
	 * 			an int, the user assigned to the task; must be
	 * 			nonnegative
	 * 
	 * @throws	TaskException if the user number passed is negative
	 * 
	 * @see		{@link #Task()}, default constructor
	 * @see		{@link #Task(String)}, explicit-A constructor
	 * @see		{@link #Task(String, int)}, explicit-C constructor
	 */
	public Task(int user) throws TaskException
	{
		if(user < 0)
		{
			throw new TaskException("Task(int)", "Given user value must be nonnegative.");
		}
		
		my_details = "";
		assigned_user = user;
	}
	
	/**
	 * <b>Explicit-C constructor</b> <br>
	 * 
	 * Initialises task instance with specific details and assigned
	 * user
	 * 
	 * @param	details
	 * 			a String, the details of the newly created task
	 * 
	 * @param	user
	 * 			an int, the user assigned to the task; must be
	 * 			nonnegative
	 * 
	 * @throws	TaskException if the user number passed is negative
	 * 
	 * @see 	{@link #Task()}, default constructor
	 * @see		{@link #Task(String)}, explicit-A constructor
	 * @see		{@link #Task(int)}, explicit-B constructor
	 */
	public Task(String details, int user) throws TaskException
	{
		if(user < 0)
		{
			throw new TaskException("Task(String, int)", "Given user value must be nonnegative.");
		}
		
		my_details = details;
		assigned_user = user;
	}
	
	/**
	 * Returns details of the task
	 * 
	 * @return	details of the task
	 * 
	 * @see		{@link #get_user()}
	 */
	public String get_details() { return my_details; }
	
	/**
	 * Returns user assigned to the task
	 * 
	 * @return	user assigned to the task
	 * 
	 * @see		{@link #get_details()}
	 */
	public int get_user() { return assigned_user; }
	
	/**
	 * Change details of the task </br>
	 * 
	 * <b>Note</b>: As tasks will nearly always be used in TaskLists,
	 * it is important to note that we are not concerned with
	 * exceptions being raised for changing the details to being
	 * equivalent to that of another task here.  That is handled
	 * within the TaskList class by the methods listed in the "see
	 * also" section below.
	 * 
	 * @param 	new_details
	 * 			a String, the new details of the task which replace
	 * 			the previously held details
	 * 
	 * @see		{@link TaskList#append(Task)}
	 * @see		{@link TaskList#insert(int, Task)}
	 * @see		{@link TaskList#replace(int, Task)}
	 * @see		{@link TaskList#transfer(int, TaskList)}
	 * @see		{@link TaskList#transfer(String, TaskList)}
	 * @see		{@link TaskList#transfer(Task, TaskList)}
	 * @see		{@link TaskList#transfer(int, TaskList, int)}
	 * @see		{@link TaskList#transfer(String, TaskList, int)}
	 * @see		{@link TaskList#transfer(Task, TaskList, int)}
	 */
	public void set_details(String new_details) { my_details = new_details; }
	
	/**
	 * Changes user assigned to the task
	 * 
	 * @param 	new_user
	 * 			an int, the user number being assigned to the task;
	 * 			must be nonnegative 
	 */
	public void set_user(int new_user) throws TaskException
	{
		if(new_user < 0)
		{
			throw new TaskException("set_user(int)", "Given int value must be nonnegative.");
		}
		
		assigned_user = new_user;
	}
	
	/**
	 * Determines whether two Task instances have equivalent
	 * details.  The important distinction here is that it checks
	 * that the <i>details</i> are equivalent, not that the details
	 * and user assigned to the task are equivalent.
	 * 
	 * @param 	task2
	 * 			a Task, the task whose details are being compared
	 * 			against the details of <code>this</code>
	 * 
	 * @return 	<code>true</code> if the details of the two tasks are
	 * 			equivalent; <code>false</code> otherwise
	 * 
	 * @see 	{@link #not_equal_details(Task)}
	 * @see 	{@link #equals(Task)}
	 */
	public boolean equal_details(Task task2)
	{
		if(this == task2)
		{
			return true;
		}
		else
		{
			if(my_details == task2.get_details())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines whether two tasks <i>do not</i> have equivalent
	 * details
	 * 
	 * @param 	task2
	 * 			a Task, the task whose details are being compared
	 * 			against the details of <code>this</code>
	 * 
	 * @return	<code>true</code> if the details of the two tasks are
	 * 			different; <code>false</code> otherwise
	 * 
	 * @see		{@link #equal_details(Task)}
	 * @see		{@link #equals(Task)}
	 */
	public boolean not_equal_details(Task task2)
	{
		return !equal_details(task2);
	}
	
	/**
	 * Determines whether this task and a second task have both
	 * equivalent details <i>and assigned user</i>.  It is in
	 * comparing the assigned user that it differs from
	 * {@link #equal_details(Task)} as that method only compares
	 * the details of the two tasks.
	 * 
	 * @param 	task2
	 * 			a Task, the task being compared against
	 * 			<code>this</code>
	 * 
	 * @return 	<code>true</code> if the two tasks have equivalent
	 * 			details <i>and</i> user numbers; <code>false</code>
	 * 			otherwise
	 * 
	 * @see 	{@link #equal_details(Task)}
	 * @see		{@link #not_equal_details(Task)}
	 */
	public boolean equals(Task task2)
	{
		if(this == task2)
		{
			return true;
		}
		else
		{
			if(my_details == task2.my_details
					&& assigned_user == task2.assigned_user)
			{
				return true;
			}
		}
		
		return false;
	}

	public String toString()
	{
		return my_details;
	}
}
