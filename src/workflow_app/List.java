package workflow_app;

import java.util.ArrayList;

/* Methods to add:
 * private boolean is_in_list(Task) [done]
 * public int get_index(Task) [done]
 * public Task get_task(index) [done]
 * public void set_name() [done]
 * public void append(Task) [done]
 * public void insert(Task, index) [done]
 * public void replace(new_task, index) [done]
 * public Task remove(Task) [done]
 * public Task remove(index) [done]
 * public void transfer(Task/index, list2) [done]
 *   -- A method to transfer a task from one list to another
 * public void transfer(index, list2, dest_index) [done]
 *   -- dest_index stands for "destination_index"
 * - Might need a method to stage list for graphical display,
 * 		will know more once closer to such a point and have
 * 		becomed more acquainted with what's required
 */

/**
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
 * @see			{@link Task}
 *
 */
public class List
{
	// Attributes
	String my_name;
	ArrayList<Task> my_tasks;
	// might want an attribute for other lists to which a
	// given instance is connected to in a project, might
	// have that contained in a different class
	
	/**
	 * Extension of Exception to handle exceptions pertaining
	 * to the List class
	 */
	@SuppressWarnings("serial")
	public class ListException extends Exception
	{
		String my_location;
		String my_message;
		
		/**
		 * Creates a ListException instance with the source of the
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
		ListException(String where, String message)
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
			return "List::" + my_location +
					": " + my_message;
		}
	}
	
	/**
	 * <b>Default constructor</b> <br>
	 * 
	 * Sets instance variables to default values <br>
	 * &mdash; my_name gets empty string<br>
	 * &mdash; my_tasks gets empty <code>ArrayList&lt;Task></code>
	 * 
	 * @see 	{@link #List(String)}, explicit constructor
	 * @see 	{@link #List(List)}, copy constructor
	 */
	public List()
	{
		my_name = "";
		my_tasks = new ArrayList<Task>();
	}
	
	/**
	 * <b>Explicit constructor</b> <br>
	 * Sets my_name to be the passed String argument and my_tasks
	 * to be an empty <code>ArrayList&lt;Task></code>
	 * 
	 * @param 	name
	 * 			a String, the name of the list
	 * 
	 * @see 	{@link #List()}, default constructor
	 * @see 	{@link #List(List)}, copy constructor
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
	 * @param 	original
	 * 			a List, the list being copied
	 * 
	 * @see 	{@link #List()}, default constructor
	 * @see 	{@link #List(String)}, explicit constructor
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
	 * @return	my_name
	 * 			a String, the name of the list
	 */
	public String get_name() { return my_name; }
	
	/**
	 * Returns size or length of the list </br>
	 * This method utilises the <code>ArrayList</code> method
	 * <code>size()</code> to get the size of the list.
	 * 
	 * @return 	my_tasks.size()
	 * 			an int, the size of the list
	 */
	public int get_size() { return my_tasks.size(); }
	
	/**
	 * Returns the <code>ArrayList&lt;Task></code> which contains the
	 * tasks for the List instance. <br><br>
	 * <b>Note</b>: This method is public but will only be so while
	 * testing is ongoing.
	 * 
	 * @return 	my_tasks
	 * 			an <code>ArrayList&lt;Task></code>, the list of tasks
	 * 			contained within the List instance
	 */
	public ArrayList<Task> get_array() { return my_tasks; }

	/**
	 * Determines if a task with details equivalent to the details of
	 * the task given exists in the list
	 * 
	 * @param 	given_task
	 * 			a Task, the Task being searched for
	 * 					   in the list
	 * 
	 * @return	<code>true</code> if a task with the same details is
	 * 		  	in the list; <code>false</code> otherwise
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
	 * @param 	task_details
	 * 			a String, the details of the task whose index is
	 * 			returned by this method
	 * 
	 * @return 	Index of the Task with details equivalent to the
	 * 		   	string passed as the argument
	 * 
	 * @throws 	ListException if a task with details equivalent to
	 * 		   	the string argument <code>task_details</code> is not
	 * 		   	found in the list
	 * 
	 * @see 	{@link #get_index(Task)}
	 */
	public int get_index(String task_details)
			throws ListException
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
	 * task.  Otherwise, throws a <code>ListException</code>
	 * 
	 * @param 	given_task
	 * 			a Task, whose index is returned by the method
	 * 
	 * @return 	Index of the task equivalent to the passed task
	 * 		   	argument
	 * 
	 * @throws 	ListException if equivalent task does not exist in
	 * 		   	the list
	 * 
	 * @see 	{@link get_index(String)}
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
			throw new ListException("get_index(given_task)", "No task with equivalent details exists in list.");
		}
		
		int task_idx = get_index(given_task.get_details());
		
		if( given_task.get_user()
				== my_tasks.get(task_idx).get_user())
		{
			return task_idx;
		}
		else if( given_task.get_user() == 0)
		{
			return task_idx;
		}
		else
		{
			throw new ListException("get_index(given_task)", "There is no task in the list identical to the passed task.");
		}
	}
	
	/**
	 * Returns task at the index given.  Throws ListException if
	 * the given index is out of range.
	 * 
	 * @param	index
	 * 			an int, the index of the task being accessed
	 * 
	 * @return	Task at the index of <code>index</code> in the list
	 * 
	 * @throws	ListException if <code>index</code> is negative or
	 * 			past the end of the list
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
	 * @param	new_task
	 * 			a Task, the task being appended to the list
	 * 
	 * @throws	ListException if a task with equivalent details to
	 * 			that of <code>new_task</code> is already in the list
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
	 * @param	new_name
	 * 			a String, the new name of the list
	 */
	public void set_name(String new_name)
	{
		my_name = new_name;
	}
	
	/**
	 * Inserts a task at the specified index and shifts any tasks
	 * previously at that index over one
	 * 
	 * @param	index
	 * 			an int, the index of the list where the new task is
	 * 			being inserted, must be nonnegative; may be greater
	 * 			than or equal to size of list, in which case the task
	 * 			is appended to the list
	 * 
	 * @param 	new_task
	 * 			a Task, the task being inserted to the list; must have
	 * 			details unique to the list, that is, no other task in
	 * 			the list may have details equivalent to this task's
	 * 			details, else a ListException is thrown.
	 * 
	 * @throws	ListException if <code>index</code> is negative or if
	 * 			<code>new_task</code> has details equivalent to that
	 * 			of any task already in the list
	 */
	public void insert(int index, Task new_task)
			throws ListException
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
	 * @param	index
	 * 			an int, the index of the task which is being
	 * 			replaced; must be nonnegative and less than the size
	 * 			of the list
	 * 
	 * @param 	replacement
	 * 			a Task, the new task which is replacing the old task
	 * 			at <code>index</code>; must have details unique to the
	 * 			list, else a ListException is thrown
	 * 
	 * @throws	ListException if <code>index</code> is negative or
	 * 			past the end of the list or if the details of
	 * 			<code>replacement</code> are equivalent to that of a
	 * 			task already in the list
	 * 
	 * @see 	{@link #remove(int)}
	 * @see 	{@link #insert(int, Task)}
	 */
	public void replace(int index, Task replacement)
			throws ListException
	{
		// Although dumb, the method shouldn't fail just because
		// the "replacement" task is identical to the one at the
		// given index since it, in essence, is not really
		// replacing anything.  It's similar to self-assignment:
		// dumb, but not illegal.  So, this if-statement here
		// checks that, and if it's not really changing
		// anything, then the method does nothing.
		if( !(replacement.equals(get_task(index))) )
		{
			if(is_in_list(replacement))
			{
				throw new ListException("replace(index, replacement)",
						"There already exists a task with the same details as the given task argument in list.");
			}
			
			if(index < 0)
			{
				throw new ListException("replace(index, replacement)",
						"replace() cannot accept negative index.");
			}
			else if(index >= get_size())
			{
				throw new ListException("replace(index, replacement)",
						"Given index is past end of list.");
			}
			
			// Passed all the argument checks
			// Free to move forward with replacing
			
			remove(index);
			insert(index, replacement);
		}
	}
	
	/**
	 * Removes task, which has details equivalent to the details
	 * of the task given in the function call, from the list and
	 * returns it.  However, the returned task need not be used
	 * or assigned to a variable in order for the function to
	 * work.  It can be called simply on its own.  That is,
	 * given some task instance <code>a_task</code> which is in the
	 * list <code>a_list</code>, simply calling <code>remove()</code>
	 * as follows is fine:</br>
	 * <code>a_list.remove(a_task);</code>
	 * 
	 * @param	removed_task
	 * 			a Task, the task being removed; must be in the list,
	 *			else a ListException is thrown
	 * 
	 * @return 	The removed task
	 * 
	 * @throws 	ListException if the task given in the function call
	 * 			is not in the list
	 * 
	 * @see 	{@link #remove(int)}
	 */
	public Task remove(Task removed_task) throws ListException
	{
		if( !is_in_list(removed_task) )
		{
			throw new ListException("remove(removed_task)",
					"Given task is not in list.");
		}
		
		int task_idx = get_index(removed_task.get_details());
		
		return my_tasks.remove(task_idx);
	}
	
	/**
	 * Removes the task located at the index given in the function
	 * call
	 * 
	 * @param	removed_idx
	 * 			an int, the index of the task being removed; must be
	 * 			nonnegative and less than the size of the list being
	 * 			acted upon
	 * 
	 * @return 	The task removed from the index given
	 * 
	 * @throws 	ListException if the index given is negative or past
	 * 			the end of the list
	 * 
	 * @see 	{@link #remove(Task)}
	 */
	public Task remove(int removed_idx) throws ListException
	{
		if(removed_idx < 0)
		{
			throw new ListException("remove(removed_idx)",
					"Cannot accept negative index.");
		}
		else if(removed_idx >= get_size())
		{
			throw new ListException("remove(removed_idx)",
					"Cannot accept index past end of list.");
		}
		
		Task rm_task = my_tasks.remove(removed_idx);
		
		return rm_task;
	}
	
	/**
	 * Transfers the task at the given index <code>trans_idx</code>
	 * in its original list to the new index <code>dest_idx</code>
	 * in the list to which the task is being transferred,
	 * <code>dest_list</code>
	 * 
	 * @param	trans_idx
	 * 			an int, the index which the task being transferred is
	 * 			at in its original list.  Must be nonnegative and less
	 * 			than the length of the original list
	 * 
	 * @param	dest_list
	 * 			a List, the list to which the task in question will be
	 * 			transferred
	 * 
	 * @param 	dest_idx
	 * 			an int, the index in the destination list to which the
	 * 			task is transferred.  Must be nonnegative and less
	 * 			than the length of the destination list</br>
	 * 
	 * 			---<b>Note</b>: <code>dest_idx</code> may be
	 * 			<i>equal</i> to the size of <code>dest_list</code>.
	 * 			If this is the case, then the task being transferred
	 * 			will be appended to <code>trans_list</code>.  This is
	 * 			done to accomodate using the transfer methods which do
	 * 			not specify an index to which the task is transferred,
	 * 			opting instead to simply add the task to the end of
	 * 			<code>trans_list</code>.
	 * 
	 * @throws 	ListException if either <code>trans_idx</code> or
	 * 			<code>dest_idx</code> are negative,
	 * 			<code>trans_idx</code> is greater than or equal to the
	 * 			size of the original list &mdash; which would put it
	 * 			past the end of that list &mdash; or if
	 * 			<code>trans_idx</code> is greater than the size of
	 * 			<code>trans_list</code>
	 * 
	 * @see 	{@link #transfer(Task, List, int)}
	 * @see 	{@link #transfer(String, List, int)}
	 * @see 	{@link #transfer(int, List)}
	 * @see 	{@link #transfer(Task, List)}
	 * @see 	{@link #transfer(String, List)}
	 */
	public void transfer(int trans_idx, List dest_list,
			int dest_idx) throws ListException
	{
		if(trans_idx < 0)
		{
			throw new ListException("transfer(trans_idx, dest_list, dest_idx)",
					"Original index cannot be negative.");
		}
		else if(trans_idx >= get_size())
		{
			throw new ListException("transfer(trans_idx, dest_list, dest_idx)",
					"Original index cannot go past end of list.");
		}
		
		if(dest_idx < 0)
		{
			throw new ListException("transfer(trans_idx, dest_list, dest_idx)",
					"Destination index cannot be negative.");
		}
		else if(dest_idx > dest_list.get_size())
		{
			throw new ListException("transfer(trans_idx, dest_list, dest_idx)",
					"Destination index cannot go past end of list.");
		}
		
		Task trans_task = remove(trans_idx);
		
		// here we check if the transferred task is to be
		// inserted into the destination list -- that is, if the
		// destination index is on [0, dest_list.get_size())
		// -- or if the task is to be appended -- that is, if
		// the destination index equals dest_list.get_size().
		if(dest_idx == dest_list.get_size())
		{
			dest_list.append(trans_task);
		}
		else
		{
			dest_list.insert(dest_idx, trans_task);
		}
	}
	
	/**
	 * Transfers the task given in the function call to the
	 * index in the destination list given
	 * 
	 * @param 	trans_task
	 * 			a Task, the task being transferred,
	 * 			must be a task contained in the original list
	 * 
	 * @param 	dest_list
	 * 			a List, the list to which the task in
	 * 			question will be transferred
	 * 
	 * @param	dest_idx
	 * 			an int, the index in the destination
	 * 			list to which the task is transferred.  Must be
	 * 			nonnegative and less than the length of the
	 * 			destination list</br>
	 * 			
	 * 			---<b>Note</b>: <code>dest_idx</code> may be
	 * 			<i>equal</i> to the size of <code>dest_list</code>.
	 * 			If this is the case, then the task being transferred
	 * 			will be appended to <code>trans_list</code>.  This is
	 * 			done to accomodate using the transfer methods which do
	 * 			not specify an index to which the task is transferred,
	 * 			opting instead to simply add the task to the end of
	 * 			<code>trans_list</code>.
	 * 
	 * @throws	ListException if the given task is not in the
	 * 			original list, or if the destination index is
	 * 			negative or greater than the size of
	 * 			<code>dest_list</code>.
	 * 
	 * @see		{@link #transfer(int, List, int)}
	 * @see 	{@link #transfer(String, List, int)}
	 * @see 	{@link #transfer(Task, List)}
	 * @see 	{@link #transfer(int, List)}
	 * @see 	{@link #transfer(String, List)}
	 */
	
	public void transfer(Task trans_task, List dest_list,
			int dest_idx) throws ListException
	{
		// throw exception if task not in original list
		if( !(is_in_list(trans_task)) )
		{
			throw new ListException("transfer(trans_task, dest_list, dest_idx)",
					"Given task is not in original list.");
		}
		
		// throw exception if dest_idx negative
		if(dest_idx < 0)
		{
			throw new ListException("transfer(trans_task, dest_list, dest_idx)",
					"Destination index cannot be negative.");
		}
		
		// throw exception if dest_idx > dest_list.get_size()
		if(dest_idx > dest_list.get_size())
		{
			throw new ListException("transfer(trans_task, dest_list, dest_idx)",
					"Destination index cannot go past end of list.");
		}
		
		
		// get task with same details, then check that user
		// numbers are equal or that the task given in the
		// function call has null user number, indicating that
		// user number is not of interest in this situation
		int orig_task_idx = get_index(trans_task.get_details());
		
		if(trans_task.get_user() == 0 ||
				trans_task.get_user() ==
				get_task(orig_task_idx).get_user())
		{
			transfer(orig_task_idx, dest_list, dest_idx);
		}
		else
		{
			throw new ListException("transfer(trans_task, dest_list, dest_idx)",
					"Task passed as argument has nonnull user number which is not equivalent to the user number of the task in the original list.");
		}
	}
	
	/**
	 * Transfers a task with details equivalent to those given
	 * in the function call &mdash; provided such a task
	 * exists in the original list &mdash; to the index in the
	 * destination list given
	 * 
	 * @param	task_details
	 * 			a String, the details of a particular task in
	 * 			the original list which the caller is
	 * 			transferring to the destination list
	 * 
	 * @param	dest_list
	 * 			a List, the list to which the task in question
	 * 			is being transferred
	 * 
	 * @param	dest_idx
	 * 			an int, the index in the destination
	 * 			list to which the task is transferred.  Must be
	 * 			nonnegative and less than the length of the
	 * 			destination list</br>
	 * 			
	 * 			---<b>Note</b>: <code>dest_idx</code> may be
	 * 			<i>equal</i> to the size of <code>dest_list</code>.
	 * 			If this is the case, then the task being transferred
	 * 			will be appended to <code>trans_list</code>.  This is
	 * 			done to accomodate using the transfer methods which do
	 * 			not specify an index to which the task is transferred,
	 * 			opting instead to simply add the task to the end of
	 * 			<code>trans_list</code>.
	 * 
	 * @throws	ListException if there is no task in the
	 * 			original list with details equivalent to the
	 * 			string given, or if the destination index is
	 * 			negative or greater than the size of
	 * 			<code>dest_list</code>.
	 * 
	 * @see 	{@link #transfer(Task, List, int)}
	 * @see 	{@link #transfer(int, List, int)}
	 * @see 	{@link #transfer(Task, List)}
	 * @see 	{@link #transfer(int, List)}
	 * @see 	{@link #transfer(String, List)}
	 */
	
	public void transfer(String task_details, List dest_list,
			int dest_idx) throws ListException
	{
		if( !is_in_list(new Task(task_details)) )
		{
			throw new ListException("transfer(task_details, dest_list, dest_idx)",
					"No task in list matches task details given.");
		}
		
		if(dest_idx < 0)
		{
			throw new ListException("transfer(task_details, dest_list, dest_idx)",
					"Destination index cannot be negative.");
		}
		else if(dest_idx > dest_list.get_size())
		{
			throw new ListException("transfer(task_details, dest_list, dest_idx)",
					"Destination index cannot go past end of list.");
		}
		
		int task_idx = get_index(new Task(task_details));
		transfer(task_idx, dest_list, dest_idx);
	}
	
	
	/**
	 * Transfers the task at the given index in the original
	 * list to the destination list given in the function call.
	 * That task being transferred is appended to the
	 * destination list.
	 * 
	 * @param	trans_idx
	 * 			an int, the index of the task in the original
	 * 			list which is being transferred
	 * 
	 * @param	dest_list
	 * 			a List, the list to which the task is being
	 * 			transferred
	 * 
	 * @throws	ListException if <code>trans_idx</code> is
	 * 			negative or past the end of the original
	 * 			list.
	 * 
	 * @see 	{@link #transfer(int, List, int)}
	 * @see 	{@link #transfer(Task, List, int)}
	 * @see 	{@link #transfer(String, List, int)}
	 * @see 	{@link #transfer(Task, List)}
	 * @see 	{@link #transfer(String, List)}
	 */
	public void transfer(int trans_idx, List dest_list)
			throws ListException
	{
		// check for errors here so error source can be more
		// accurate and not just go to transfer(trans_idx,
		// dest_list, dest_idx)
		if(trans_idx < 0)
		{
			throw new ListException("transfer(trans_idx, dest_list)",
					"Original index cannot be negative.");
		}
		if(trans_idx >= get_size())
		{
			throw new ListException("transfer(trans_idx, dest_list)",
					"Original index cannot go past end of list.");
		}
		
		// Basically just use transfer with more args, but have
		// the destination index be the end of the destination
		// list
		transfer(trans_idx, dest_list, dest_list.get_size());
	}
	
	
	/**
	 * Transfers the task given to the destination list given in
	 * the function call.  That task is appended to the end of the
	 * destination list.
	 * 
	 * @param	trans_task
	 * 			a Task, the task being transferred.  Must be
	 * 			contained within the original list or else a
	 * 			ListException is thrown
	 * 
	 * @param	dest_list
	 * 			a List, the destination list
	 * 
	 * @throws	ListException if the task given is not in the
	 * 			original list
	 * 
	 * @see 	{@link #transfer(Task, List, int)}
	 * @see 	{@link #transfer(String, List, int)}
	 * @see 	{@link #transfer(int, List, int)}
	 * @see 	{@link #transfer(String, List)}
	 * @see 	{@link #transfer(int, List)}
	 */
	public void transfer(Task trans_task, List dest_list)
			throws ListException
	{
		if( !is_in_list(trans_task) )
		{
			throw new ListException("transfer(trans_task, dest_list)",
					"Given task is not in original list.");
		}
		
		// get task with same details, then check that user
		// numbers are equal or that the task given in the
		// function call has null user number, indicating that
		// user number is not of interest in this situation
		int orig_task_idx = get_index(trans_task.get_details());
		
		if(trans_task.get_user() == 0 ||
				trans_task.get_user() ==
				get_task(orig_task_idx).get_user())
		{
			transfer(trans_task, dest_list, dest_list.get_size());

		}
		else
		{
			throw new ListException("transfer(trans_task, dest_list)",
					"Task passed as argument has nonnull user number which is not equivalent to the user number of the task in the original list.");
		}
	}
	

	/**
	 * Transfers task with details equivalent to those given in
	 * the function call to the list.  That task is appended to
	 * the end of the destination list.
	 * 
	 * @param	task_details
	 * 			a String, the details of the task which is being
	 * 			transferred
	 * 
	 * @param	dest_list
	 * 			a List, the destination list, to which the task
	 * 			is being transferred and appended
	 * 
	 * @throws	ListException if there is no task in the original
	 * 			list with details equivalent to the string passed
	 * 
	 * @see 	{@link #transfer(Task, List, int)}
	 * @see 	{@link #transfer(String, List, int)}
	 * @see 	{@link #transfer(int, List, int)}
	 * @see 	{@link #transfer(Task, List)}
	 * @see 	{@link #transfer(int, List)}
	 */
	public void transfer(String task_details, List dest_list)
			throws ListException
	{
		if( !is_in_list(new Task(task_details)) )
		{
			throw new ListException("transfer(task_details, dest_list)",
					"No task in list matches task details given.");
		}
		
		transfer(task_details, dest_list,
				dest_list.get_size());
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
