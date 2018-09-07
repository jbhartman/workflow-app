package test.java;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import workflow_app.List;
import workflow_app.List.ListException;
import workflow_app.Task;
import workflow_app.Task.TaskException;

import java.util.ArrayList;

class ListTest
{
	// Need to make this an assertion method
	// I wrote this method to check that two ArrayList instances
	// were equivalent since I couldn't find a method already
	// made to do this.
	private boolean assertArrayListEquals(ArrayList<Task> al1,
			ArrayList<Task> al2)
	{
		if(al1.size() == al2.size())
		{
			for(int idx=0; idx < al1.size(); idx++)
			{
				if(al1.get(idx) != al2.get(idx))
				{
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	@Test
	void test_default_constructor() throws ListException
	{
		List l0 = new List();
		
		assert(l0.get_name() == "");
		assert(l0.get_size() == 0);
		
		boolean result = assertArrayListEquals(l0.get_array(),
				new ArrayList<Task>(0));
		assert(result);
	}
	
	@Test
	void test_explicit_constructor()
	{
		List l0 = new List("testname");
		
		assert(l0.get_name() == "testname");
		assert(l0.get_size() == 0);
		
		boolean result = assertArrayListEquals(l0.get_array(),
				new ArrayList<Task>(0));
		assert(result);
	}
	
	@Test
	void test_copy_constructor() throws ListException
	{
		List l0 = new List();
		List l1 = new List("just a name");
		List l2 = new List("name and tasks");
		
		l2.append(new Task("task1"));
		l2.append(new Task("task2"));
		l2.append(new Task("task3"));
		
		// copy with empty List
		List l3 = new List(l0);
		
		assert(l3.get_name() == "");
		assert(l3.get_size() == 0);
		assertEquals(l3.get_array(), new ArrayList<Task>());
		
		// copy with List with name but no tasks
		List l4 = new List(l1);
		
		assert(l4.get_name() == "just a name");
		assert(l4.get_size() == 0);
		assertEquals(l4.get_array(), new ArrayList<Task>());
		
		// copy with List with name and tasks
		List l5 = new List(l2);
		
		assert(l5.get_name() == "name and tasks");
		assert(l5.get_size() == 3);
		assert(l5.get_array().get(0).equals(new Task("task1")));
		assert(l5.get_array().get(1).equals(new Task("task2")));
		assert(l5.get_array().get(2).equals(new Task("task3")));
	}

	@Test
	void test_append() throws ListException
	{
		List l0 = new List();
		
		l0.append(new Task("task1"));
		assert(l0.get_size() == 1);
		assert(l0.get_array().get(0).equals(new Task("task1")));
		
		l0.append(new Task("task2"));
		assert(l0.get_size() == 2);
		assert(l0.get_array().get(0).equals(new Task("task1")));
		assert(l0.get_array().get(1).equals(new Task("task2")));
		
		try
		{
			l0.append(new Task("task1"));
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "append(new_task)");
			
			// check that the list hasn't been changed
			assert(l0.get_size() == 2);
			assert(l0.get_array().get(0).equals(new Task("task1")));
			assert(l0.get_array().get(1).equals(new Task("task2")));
		}
	}

	@Test
	void test_is_in_list() throws ListException, TaskException
	{
		List l0 = new List();
		l0.append(new Task("task1"));
		l0.append(new Task("task2", 68));
		
		Task task_in = new Task("task1");
		Task task_with_user_in = new Task("task2", 68);
		Task task_not_in = new Task("task3");
		Task task_with_user_not_in = new Task("task4", 68);
		Task task_with_diff_user_in = new Task("task2", 70);
		
		assert(l0.is_in_list(task_in));
		assert(l0.is_in_list(task_with_user_in));
		assert( !(l0.is_in_list(task_not_in)) );
		assert( !(l0.is_in_list(task_with_user_not_in)) );
		assert(l0.is_in_list(task_with_diff_user_in));
	}

	@Test
	void test_get_index_with_string() throws ListException, TaskException
	{
		List l0 = new List();
		
		l0.append(new Task("task1"));
		l0.append(new Task("task2", 45));
		l0.append(new Task("task3"));
		
		/*
		 * So the list should look like
		 * { [0] {"task1", 0} , [1] {"task2", 45} ,
		 *   [2] {"task3", 0} }
		 * with {String, int} representing each Task.
		 */
		
		assert(l0.get_index("task1") == 0);
		assert(l0.get_index("task2") == 1);
		assert(l0.get_index("task3") == 2);
		
		try
		{
			@SuppressWarnings("unused")
			int temp_idx = l0.get_index("task4");
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "get_index(String)");
		}
	}
	
	@Test
	void test_get_index_with_task() throws ListException, TaskException
	{
		List l0 = new List();
		
		l0.append(new Task("task1"));
		l0.append(new Task("task2", 45));
		l0.append(new Task("task3"));
		
		/*
		 * So the list should look like
		 * { [0] {"task1", 0} , [1] {"task2", 45} ,
		 *   [2] {"task3", 0} }
		 * with {String, int} representing each Task.
		 */
		
		Task t0 = new Task("task1");
		Task t1 = new Task("task2", 45);
		Task t2 = new Task("task3");
		
		assert(l0.get_index(t0) == 0);
		assert(l0.get_index(t1) == 1);
		assert(l0.get_index(t2) == 2);
		
		try
		{
			Task t3 = new Task("task4");
			@SuppressWarnings("unused")
			int temp_idx = l0.get_index(t3);
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "get_index(given_task)");
		}
	}
	
	@Test
	void test_get_task() throws ListException, TaskException
	{
		List l0 = new List();
		
		l0.append(new Task("task1", 1));
		l0.append(new Task("task2", 2));
		l0.append(new Task("task3", 3));
		
		assert(l0.get_task(0).equals(new Task("task1", 1)));
		assert(l0.get_task(1).equals(new Task("task2", 2)));
		assert(l0.get_task(2).equals(new Task("task3", 3)));
	}
	
	@Test
	void test_get_task_error_handling()
	throws ListException, TaskException
	{
		List l0 = new List();
		
		l0.append(new Task("task1", 1));
		l0.append(new Task("task2", 2));
		l0.append(new Task("task3", 3));
		
		try
		{
			@SuppressWarnings("unused")
			Task t0 = l0.get_task(3);
			System.err.println("\"Successfully\" indexed past end of list.");
			System.exit(1);
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "get_task(index)");
		}
		
		try
		{
			@SuppressWarnings("unused")
			Task t0 = l0.get_task(-1);
			System.err.println("\"Successfully\" indexed with negative index.");
			System.exit(1);
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "get_task(index)");
		}
	}
	
	@Test
	void test_set_name() throws ListException
	{
		List l0 = new List("original name");
		l0.set_name("new name");
		assert(l0.get_name() == "new name");
		
		// test set_name() to same name
		l0.set_name("new name");
		assert(l0.get_name() == "new name");
		
		// test set_name() to empty string
		l0.set_name("");
		assert(l0.get_name() == "");
	}
	
	@Test
	void test_insert() throws ListException
	{
		List l0 = new List("yea, I'm naming it l0 again. fight me.");
		l0.append(new Task("task1"));
		l0.append(new Task("task2"));
		l0.append(new Task("task3"));
		
		// insert at beginning of list
		l0.insert(0, new Task("task0"));
		assert(l0.get_size() == 4);
		assert(l0.get_task(0).equals(new Task("task0")));
		assert(l0.get_task(1).equals(new Task("task1")));
		assert(l0.get_task(2).equals(new Task("task2")));
		assert(l0.get_task(3).equals(new Task("task3")));
		
		// insert in middle of list
		l0.insert(2, new Task("task1.5"));
		assert(l0.get_size() == 5);
		assert(l0.get_task(0).equals(new Task("task0")));
		assert(l0.get_task(1).equals(new Task("task1")));
		assert(l0.get_task(2).equals(new Task("task1.5")));
		assert(l0.get_task(3).equals(new Task("task2")));
		assert(l0.get_task(4).equals(new Task("task3")));
		
		// insert at end of list (should just use append())
		l0.insert(5, new Task("task4"));
		assert(l0.get_size() == 6);
		assert(l0.get_task(0).equals(new Task("task0")));
		assert(l0.get_task(1).equals(new Task("task1")));
		assert(l0.get_task(2).equals(new Task("task1.5")));
		assert(l0.get_task(3).equals(new Task("task2")));
		assert(l0.get_task(4).equals(new Task("task3")));
		assert(l0.get_task(5).equals(new Task("task4")));
		
		// insert way past end of list
		l0.insert(25, new Task("task5"));
		assert(l0.get_size() == 7);
		assert(l0.get_task(0).equals(new Task("task0")));
		assert(l0.get_task(1).equals(new Task("task1")));
		assert(l0.get_task(2).equals(new Task("task1.5")));
		assert(l0.get_task(3).equals(new Task("task2")));
		assert(l0.get_task(4).equals(new Task("task3")));
		assert(l0.get_task(5).equals(new Task("task4")));
		assert(l0.get_task(6).equals(new Task("task5")));
	}
	
	@Test
	void test_insert_error_handling() throws ListException
	{
		List l0 = new List("yea, I'm naming it l0 again. fight me.");
		l0.append(new Task("task1"));
		l0.append(new Task("task2"));
		l0.append(new Task("task3"));
		
		try
		{
			l0.insert(1, new Task("task3"));
			System.err.println("insert() worked for adding preexisting task to list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "insert(index, new_task)");
		}
	}
	
	@Test
	void test_replace() throws ListException, TaskException
	{
		// create list
		List l0 = new List();
		
		// append a few tasks
		l0.append(new Task("task1"));
		l0.append(new Task("task2"));
		l0.append(new Task("task3", 25));
		
		// then replace one of the tasks
		l0.replace(1, new Task("task4"));
		
		// check that everything went alright
		assert(l0.get_size() == 3);
		assert(l0.get_task(0).equals(new Task("task1")));
		assert(l0.get_task(1).equals(new Task("task4")));
		assert(l0.get_task(2).equals(new Task("task3", 25)));
		
		// now replace w/ sth else at that same index
		l0.replace(1, new Task("task5", 25));
		
		// check that everything went alright
		assert(l0.get_size() == 3);
		assert(l0.get_task(0).equals(new Task("task1")));
		assert(l0.get_task(1).equals(new Task("task5", 25)));
		assert(l0.get_task(2).equals(new Task("task3", 25)));
		
		// replace at different index w/ identical task at that index
		l0.replace(2, new Task("task3", 25));
		
		// check that everything went alright
		assert(l0.get_size() == 3);
		assert(l0.get_task(0).equals(new Task("task1")));
		assert(l0.get_task(1).equals(new Task("task5", 25)));
		assert(l0.get_task(2).equals(new Task("task3", 25)));
	}
	
	@Test
	void test_replace_error_handling()
			throws ListException, TaskException
	{
		// create list
		List l0 = new List();
		
		// append a few tasks
		l0.append(new Task("task1"));
		l0.append(new Task("task2", 5));
		
		// try to replace with preexisting task
		// catch error
		// check that nothing changed
		try
		{
			l0.replace(1, new Task("task1"));
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "replace(index, replacement)");
			assert(l0.get_size() == 2);
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(1).equals(new Task("task2", 5)));
		}
		
		// try to replace with task with details equivalent to
		// some preexisting task's details
		// catch error
		// check that nothing changed
		try
		{
			l0.replace(0, new Task("task2", 2));
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "replace(index, replacement)");
			assert(l0.get_size() == 2);
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(1).equals(new Task("task2", 5)));
		}
	}
	
	@Test
	void test_remove_by_task()
			throws ListException, TaskException
	{
		// create list
		List l0 = new List();
		
		// append a few tasks
		l0.append(new Task("task1"));
		l0.append(new Task("task2", 4));
		l0.append(new Task("task3", 5));
		
		Task temp_task = new Task("task3");
		
		// remove by "new Task("...")"
		Task placeholder = l0.remove(new Task("task2"));
		
		// check that everything went alright
		assert(l0.get_size() == 2);
		assert(placeholder.equals(new Task("task2", 4)));
		assert(l0.get_task(0).equals(new Task("task1")));
		assert(l0.get_task(1).equals(new Task("task3", 5)));
		
		// remove by Task instance identifier
		placeholder = l0.remove(temp_task);
		
		// check that everything went alright
		assert(l0.get_size() == 1);
		assert(placeholder.equal_details(temp_task));
		assert(placeholder.get_user() == 5);
		assert(l0.get_task(0).equals(new Task("task1")));
	}
	
	@Test
	void test_remove_by_task_error_handling()
			throws ListException, TaskException
	{
		// create list
		List l0 = new List();
		
		// append some tasks
		l0.append(new Task("task1"));
		l0.append(new Task("task2"));
		l0.append(new Task("task3"));
		
		// create task instance which is not in list
		Task temp_task = new Task("task4");
		
		// try to remove nonexistent task
		// catch exception
		// check that nothing changed
		try
		{
			l0.remove(temp_task);
			System.err.println("remove(removed_task) worked for nonexistent task.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(removed_task)");
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(1).equals(new Task("task2")));
			assert(l0.get_task(2).equals(new Task("task3")));
		}
		
		// try to remove based on created task instance
		// catch exception
		// check that nothing changed
		try
		{
			l0.remove(new Task("tak1"));
			System.err.println("remove(removed_task) worked for nonexistent task.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(removed_task)");
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(1).equals(new Task("task2")));
			assert(l0.get_task(2).equals(new Task("task3")));
		}
	}
	
	@Test
	void test_remove_by_index() throws ListException
	{
		// create a list
		List l0 = new List();
		
		// append some tasks
		l0.append(new Task("task1"));
		l0.append(new Task("task2"));
		l0.append(new Task("task3"));
		l0.append(new Task("task4"));
		
		// create an int instance representing the index
		//   within the bounds of the list
		int int_in_bounds = 2;
		
		// remove with that variable as argument
		Task temp_task = l0.remove(int_in_bounds);
		
		// check that everything went alright
		assert(temp_task.equals(new Task("task3")));
		
		assert(l0.get_size() == 3);
		assert(l0.get_task(0).equals(new Task("task1")));
		assert(l0.get_task(1).equals(new Task("task2")));
		assert(l0.get_task(2).equals(new Task("task4")));
		
		// remove with given int, say 0
		temp_task = l0.remove(0);
		
		// check that everything went alright
		assert(temp_task.equals(new Task("task1")));
		
		assert(l0.get_size() == 2);
		assert(l0.get_task(0).equals(new Task("task2")));
		assert(l0.get_task(1).equals(new Task("task4")));
	}
	
	@Test
	void test_remove_by_index_error_handling() 
			throws ListException, TaskException
	{
		// create a list
		List l0 = new List();
		
		// append some tasks
		l0.append(new Task("task1"));
		l0.append(new Task("task2"));
		l0.append(new Task("task3"));
		
		// create an int instance outside the bounds of the list
		int invalid_index = 3;
		
		// try to remove with that instance as an argument
		// catch exception
		// check that nothing changed
		try
		{
			l0.remove(invalid_index);
			System.err.println("remove(removed_idx) successfully removed past end of List.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(removed_idx)");
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(1).equals(new Task("task2")));
			assert(l0.get_task(2).equals(new Task("task3")));
		}
		
		// try to remove with int input as instance
		// catch exception
		// check that nothing changed
		try
		{
			l0.remove(-1);
			System.err.println("remove(removed_idx) successfully removed past end of List.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(removed_idx)");
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(1).equals(new Task("task2")));
			assert(l0.get_task(2).equals(new Task("task3")));
		}
	}
	
	@Test
	void test_transfer_task()
			throws ListException, TaskException
	{
		// Ways to transfer by task (not index):
		// - With identical task instance variable
		// - With task instance variable with identical details
		//     but null user number
		// - With string instance containing same details
		
		// create two lists, orig_list (original) and trans_list
		//   (transfer)
		List orig_list = new List();
		List trans_list = new List();
		
		// append a few tasks to original_list
		orig_list.append(new Task("task1"));
		orig_list.append(new Task("task2"));
		orig_list.append(new Task("task3", 3));
		orig_list.append(new Task("task4", 4));
		orig_list.append(new Task("task5"));
		orig_list.append(new Task("task6"));
		
		// create a task instance identical to a particular task
		//   in original_list
		Task task3 = new Task("task3", 3);
		
		// create a task instance with details identical to that
		//   of a particular task in original_list but null user
		//   number
		Task task4 = new Task("task4");
		
		// create a string which is identical to the details of a
		//   a particular task in original_list
		String task2_string = "task2";
		
		// transfer to transfer_list with identical task as arg
		orig_list.transfer(task3, trans_list);
		
		// check that everything went alright
		assert(orig_list.get_size() == 5);
		assert(trans_list.get_size() == 1);
		assert(orig_list.get_task(0).equals(new Task("task1")));
		assert(orig_list.get_task(1).equals(new Task("task2")));
		assert(orig_list.get_task(2).equals(new Task("task4", 4)));
		assert(orig_list.get_task(3).equals(new Task("task5")));
		assert(orig_list.get_task(4).equals(new Task("task6")));
		assert(trans_list.get_task(0)).equals(task3);
		
		// transfer to transfer_list with task with identical
		//   details but null user number
		orig_list.transfer(task4, trans_list);
		
		// check that everything went alright
		assert(orig_list.get_size() == 4);
		assert(trans_list.get_size() == 2);
		assert(orig_list.get_task(0).equals(new Task("task1")));
		assert(orig_list.get_task(1).equals(new Task("task2")));
		assert(orig_list.get_task(2).equals(new Task("task5")));
		assert(orig_list.get_task(3).equals(new Task("task6")));
		assert(trans_list.get_task(0)).equals(task3);
		assert(trans_list.get_task(1).equals(new Task("task4", 4)));

		// transfer to transfer_list with string
		orig_list.transfer(task2_string, trans_list);
		
		// check that everything went alright
		assert(orig_list.get_size() == 3);
		assert(trans_list.get_size() == 3);
		assert(orig_list.get_task(0).equals(new Task("task1")));
		assert(orig_list.get_task(1).equals(new Task("task5")));
		assert(orig_list.get_task(2).equals(new Task("task6")));
		assert(trans_list.get_task(0)).equals(task3);
		assert(trans_list.get_task(1).equals(new Task("task4", 4)));
		assert(trans_list.get_task(2).equals(new Task("task2")));
	}
	
	@Test
	void test_transfer_task_error_handling()
			throws ListException, TaskException
	{
		// Ways a transfer-by-task can cause errors:
		// - Passed task, but no such task exists in original list
		// - Passed task, but task already exists in transfer list
		// - Passed string, but no task with those details exists
		//     in original list
		
		// create orig_list and trans_list
		List orig_list = new List();
		List trans_list = new List();
		
		// append several tasks to orig_list
		orig_list.append(new Task("task1"));
		orig_list.append(new Task("task2"));
		orig_list.append(new Task("task3", 3));
		
		// create task instance unique from anything in orig_list
		Task uniq_task = new Task("task4");
		
		// create task instance with details equivalent to a task
		//   in orig_list but different, nonnull user number
		Task diff_user_task = new Task("task3", 4);
		
		// create string instance unique from any task's details
		//   contained within orig_list
		String uniq_str = "task4";
		
		// try to transfer based on unique task
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(uniq_task, trans_list);
			System.err.println("transfer(trans_task, dest_list) worked for task not in original list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_task, dest_list)");
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 0);
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3", 3)));
		}
		
		// try to transfer based on same task details but
		//   different, nonnull user number
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(diff_user_task, trans_list);
			System.err.println("transfer(trans_task, dest_list) worked for task not in original list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_task, dest_list)");
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 0);
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3", 3)));
		}
		
		// try to transfer based on unique string
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(uniq_str, trans_list);
			System.err.println("transfer(task_details, dest_list) worked for task not in original list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(task_details, dest_list)");
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 0);
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3", 3)));
		}
	}
	
	@Test
	void test_transfer_index()
			throws ListException, TaskException
	{
		// This one is much simpler than tranferring-by-task.
		// There's one way to transfer-by-index, and it works
		// provided the index is not past the end of the list.
		
		// create orig_list and trans_list instances
		List orig_list = new List();
		List trans_list = new List();
		
		// append a few tasks to orig_list
		orig_list.append(new Task("task1", 1));
		orig_list.append(new Task("task2"));
		orig_list.append(new Task("task3", 4));
		orig_list.append(new Task("task4"));
		
		// create int instance whose value is within the bounds
		//   of the list
		int valid_idx = 1;
		
		// transfer-by-index with int instance to trans_list
		orig_list.transfer(valid_idx, trans_list);

		// check that everything went alright
		assert(orig_list.get_size() == 3);
		assert(trans_list.get_size() == 1);
		assert(orig_list.get_task(0).equals(new
				Task("task1", 1)));
		assert(orig_list.get_task(1).equals(new
				Task("task3", 4)));
		assert(orig_list.get_task(2).equals(new Task("task4")));
		assert(trans_list.get_task(0).equals(new Task("task2")));
		
		// transfer-by-index with new int arg to trans_list
		orig_list.transfer(2, trans_list);

		// check that everything went alright
		assert(orig_list.get_size() == 2);
		assert(trans_list.get_size() == 2);
		assert(orig_list.get_task(0).equals(new
				Task("task1", 1)));
		assert(orig_list.get_task(1).equals(new
				Task("task3", 4)));
		assert(trans_list.get_task(0).equals(new Task("task2")));
		assert(trans_list.get_task(1).equals(new Task("task4")));
	}
	
	@Test
	void test_transfer_index_error_handling()
			throws ListException, TaskException
	{
		// Transfer-by-index fails if:
		//   - Passed index is outside of the bounds of the list
		//     whether before list (negative index) or past end
		//     of list (index >= list.get_size())
		// That's all I've got right now, but I can't help but
		// think that I'm missing something...
		
		// create orig_list and trans_list
		List orig_list = new List();
		List trans_list = new List();
		
		// append a few tasks to orig_list
		// I'm not going to bother with any user numbers here
		// because we're not dealing with the tasks themselves,
		// so we don't need to worry about user numbers.
		orig_list.append(new Task("task1"));
		orig_list.append(new Task("task2"));
		orig_list.append(new Task("task3"));
		
		// try to transfer-by-index past end of orig_list
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(-1, trans_list);
			System.err.println("transfer(trans_idx, dest_list) worked for a negative index.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_idx, dest_list)");
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 0);
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new Task("task3")));
		}
		
		// try to transfer by negative index
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(3, trans_list);
			System.err.println("transfer(trans_idx, dest_list) worked end of the list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_idx, dest_list)");
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 0);
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new Task("task3")));
		}
	}
	
	@Test
	void test_transfer_task_to_index()
			throws ListException, TaskException
	{
		// This is going to be very similar to the test
		// method for transferring-by-task, but here we're
		// transferring-by-task to a paarticular index, so
		// that's where the difference will come in.
		
		// create orig_list and trans_list
		List orig_list = new List();
		List trans_list = new List();
		
		// append some tasks to orig_list
		orig_list.append(new Task("task1", 1));
		orig_list.append(new Task("task2", 1));
		orig_list.append(new Task("task3", 2));
		orig_list.append(new Task("task4", 3));
		orig_list.append(new Task("task5", 3));
		
		
		// create a task instance w/ nonzero user number which
		//   is equivalent to a task contaned in orig_list
		Task task_with_user = new Task("task2", 1);
		
		// create a task instance w/ null user number which
		//   has details equivalent to that of a task
		//   contained within orig_list
		Task task_no_user = new Task("task4");
		
		// transfer to trans_list, index 0 with predefined task
		//   with nonzero task number
		orig_list.transfer(task_with_user, trans_list, 0);
		
		// check that everything went alright
		assert(orig_list.get_size() == 4);
		assert(trans_list.get_size() == 1);
		assert(orig_list.get_task(0).equals(new
				Task("task1", 1)));
		assert(orig_list.get_task(1).equals(new
				Task("task3", 2)));
		assert(orig_list.get_task(2).equals(new
				Task("task4", 3)));
		assert(orig_list.get_task(3).equals(new
				Task("task5", 3)));
		assert(trans_list.get_task(0).equals(new
				Task("task2", 1)));
		
		// append a few unique tasks to trans_list just to
		//   fill it up a bit
		trans_list.append(new Task("task6"));
		trans_list.append(new Task("task7"));
		trans_list.append(new Task("task8"));
		
		// transfer to trans_list, whatever valid index, with
		//   predefined task with null user number
		orig_list.transfer(task_no_user, trans_list, 2);
		
		// check that everything went alright
		assert(orig_list.get_size() == 3);
		assert(trans_list.get_size() == 5);
		assert(orig_list.get_task(0).equals(new
				Task("task1", 1)));
		assert(orig_list.get_task(1).equals(new
				Task("task3", 2)));
		assert(orig_list.get_task(2).equals(new
				Task("task5", 3)));
		assert(trans_list.get_task(0).equals(new
				Task("task2", 1)));
		assert(trans_list.get_task(1).equals(new Task("task6")));
		assert(trans_list.get_task(2).equals(new
				Task("task4", 3)));
		assert(trans_list.get_task(3).equals(new Task("task7")));
		assert(trans_list.get_task(4).equals(new Task("task8")));
		
		// transfer to trans_list, whatever valid index, with
		//   task defined inside function call
		orig_list.transfer("task3", trans_list, 4);
		
		// check that everything went aright
		assert(orig_list.get_size() == 2);
		assert(trans_list.get_size() == 6);
		assert(orig_list.get_task(0).equals(new
				Task("task1", 1)));
		assert(orig_list.get_task(1).equals(new
				Task("task5", 3)));
		assert(trans_list.get_task(0).equals(new
				Task("task2", 1)));
		assert(trans_list.get_task(1).equals(new Task("task6")));
		assert(trans_list.get_task(2).equals(new
				Task("task4", 3)));
		assert(trans_list.get_task(3).equals(new Task("task7")));
		assert(trans_list.get_task(4).equals(new
				Task("task3", 2)));
		assert(trans_list.get_task(5).equals(new Task("task8")));
	}
	
	@Test
	void test_transfer_task_to_index_error_handling()
	throws ListException, TaskException
	{
		// Ways transferring-by-task to specifed index can fail:
		//   - Passed task not existent in original list
		//   - Given destination index is less than zero or
		//     past the end of the destination list
		
		// create orig_list and trans_list
		List orig_list = new List();
		List trans_list = new List();
		
		// append a few tasks to orig_list and trans_list
		orig_list.append(new Task("task1"));
		orig_list.append(new Task("task2"));
		orig_list.append(new Task("task3"));
		orig_list.append(new Task("task4"));
		
		trans_list.append(new Task("task11"));
		trans_list.append(new Task("task12"));
		trans_list.append(new Task("task13"));
		
		// create a task instance which is unique to any task
		//   contained within either of the two lists
		Task uniq_task = new Task("task20");
		
		// create a task instance identical to one contained in
		//   trans_list, not orig_list
		Task in_trans_task = new Task("task12");
		
		// create a task instance identical to one contained
		//   within orig_list
		Task in_orig_task = new Task("task1");
		
		// create a string whose value is unique from the details
		//   of any task contained within the two lists
		String uniq_details = "task21";
		
		// create a string whose value is equivalent to the
		//   details of a given task contained in trans_list,
		//   not orig_list
		String in_trans_details = "task13";
		
		// create a string whose value is equivalent to the
		//   details of a given task contained within
		//   orig_list
		String in_orig_details = "task2";
		
		// try to transfer based on the unique task
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(uniq_task, trans_list, 1);
			System.err.println("transfer(trans_task, dest_list, dest_idx) worked for nonexistent task.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_task, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
		
		// try to transfer based on nonunique (contained within
		//   destination list) task
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(in_trans_task, trans_list, 2);
			System.err.println("transfer(trans_task, dest_list, dest_idx) worked for task already in dest_list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_task, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
		
		// try to transfer based on unique string details
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(uniq_details, trans_list, 2);
			System.err.println("transfer(task_details, dest_list, dest_idx) worked for nonexistent task.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(task_details, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
		
		// try to transfer based on nonunique (contained within
		//   destination list) string details
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(in_trans_details, trans_list, 2);
			System.err.println("transfer(task_details, dest_list, dest_idx) worked for task already in trans_list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(task_details, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
		
		// try to transfer valid task to negative index
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(in_orig_task, trans_list, -1);
			System.err.println("transfer(trans_task, dest_list, dest_idx) worked for negative destination index.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_task, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
		
		// try to transfer valid task to index past end of
		//   trans_list
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(in_orig_task, trans_list, 4);
			System.err.println("transfer(trans_task, dest_list, dest_idx) worked past end of destination list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_task, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
		
		// try to transfer with valid details to negative
		//   index
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(in_orig_details, trans_list, -1);
			System.err.println("transfer(task_details, dest_list, dest_idx) worked for negative destination index.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(task_details, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
		
		// try to transfer with valid details to index
		//   past end of trans_list
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(in_orig_details, trans_list, 4);
			System.err.println("transfer(task_details, dest_list, dest_idx) worked past end of destination list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(task_details, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 4);
			assert(trans_list.get_size() == 3);
			assert(orig_list.get_task(0).equals(new
					Task("task1")));
			assert(orig_list.get_task(1).equals(new
					Task("task2")));
			assert(orig_list.get_task(2).equals(new
					Task("task3")));
			assert(orig_list.get_task(3).equals(new
					Task("task4")));
			assert(trans_list.get_task(0).equals(new
					Task("task11")));
			assert(trans_list.get_task(1).equals(new
					Task("task12")));
			assert(trans_list.get_task(2).equals(new
					Task("task13")));
		}
	}
	
	@Test
	void test_transfer_index_to_index()
			throws ListException, TaskException
	{
		// Just as with transferring w/o destination index,
		// here, there's really only one way that transferring-
		// by-index-to-index can work, so these tests are going
		// to be much simpler than the test methods for
		// transferring-by-task-to-index.
		
		// create orig_list and trans_list
		List orig_list = new List();
		List trans_list = new List();
		
		// append a few tasks to orig_list
		orig_list.append(new Task("task1"));
		orig_list.append(new Task("task2", 2));
		orig_list.append(new Task("task3"));
		orig_list.append(new Task("task4", 4));
		
		// transfer by index to index 0 of trans_list
		orig_list.transfer(1, trans_list, 0);
		
		// check that everything went alright
		assert(orig_list.get_size() == 3);
		assert(trans_list.get_size() == 1);
		assert(orig_list.get_task(0).equals(new Task("task1")));
		assert(orig_list.get_task(1).equals(new Task("task3")));
		assert(orig_list.get_task(2).equals(new
				Task("task4", 4)));
		assert(trans_list.get_task(0).equals(new
				Task("task2", 2)));
		
		// append a few tasks to trans_list
		trans_list.append(new Task("task5"));
		trans_list.append(new Task("task6", 6));
		trans_list.append(new Task("task7"));
		
		// transfer by index to nonzero, valid index of
		//   trans_list
		orig_list.transfer(1, trans_list, 2);
		
		// check that everything went alright
		assert(orig_list.get_size() == 2);
		assert(trans_list.get_size() == 5);
		assert(orig_list.get_task(0).equals(new Task("task1")));
		assert(orig_list.get_task(1).equals(new
				Task("task4", 4)));
		assert(trans_list.get_task(0).equals(new
				Task("task2", 2)));
		assert(trans_list.get_task(1).equals(new Task("task5")));
		assert(trans_list.get_task(2).equals(new Task("task3")));
		assert(trans_list.get_task(3).equals(new
				Task("task6", 6)));
		assert(trans_list.get_task(4).equals(new Task("task7")));
	}
	
	@Test
	void test_transfer_index_to_index_error_handling()
			throws ListException, TaskException
	{		
		// create orig_list and trans_list
		List orig_list = new List();
		List trans_list = new List();
		
		// append some tasks to orig_list and trans_list
		orig_list.append(new Task("task1"));
		orig_list.append(new Task("task2"));
		orig_list.append(new Task("task3"));
		
		trans_list.append(new Task("task11"));
		trans_list.append(new Task("task12"));
		trans_list.append(new Task("task13"));
		
		// try to transfer from negative index
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(-2, trans_list, 1);
			System.err.println("transfer(trans_idx, dest_list, dest_idx) worked for negative original index.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_idx, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 3);
			
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new Task("task3")));
			
			assert(trans_list.get_task(0).equals(new Task("task11")));
			assert(trans_list.get_task(1).equals(new Task("task12")));
			assert(trans_list.get_task(2).equals(new Task("task13")));
		}
		
		// try to transfer from index past end of orig_list
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(3, trans_list, 1);
			System.err.println("transfer(trans_idx, dest_list, dest_idx) worked past end of original list.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_idx, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 3);
			
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new Task("task3")));
			
			assert(trans_list.get_task(0).equals(new Task("task11")));
			assert(trans_list.get_task(1).equals(new Task("task12")));
			assert(trans_list.get_task(2).equals(new Task("task13")));
		}
		
		// try to transfer to negative index
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(1, trans_list, -1);
			System.err.println("transfer(trans_idx, dest_list, dest_idx) worked for negative destination index.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_idx, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 3);
			
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new Task("task3")));
			
			assert(trans_list.get_task(0).equals(new Task("task11")));
			assert(trans_list.get_task(1).equals(new Task("task12")));
			assert(trans_list.get_task(2).equals(new Task("task13")));
		}
		
		// try to transfer to index past end of trans_list
		// catch exception
		// check that nothing changed
		try
		{
			orig_list.transfer(2, trans_list, 4);
			System.err.println("transfer(trans_idx, dest_list, dest_idx) worked past end of destination index.");
			fail();
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "transfer(trans_idx, dest_list, dest_idx)");
			
			assert(orig_list.get_size() == 3);
			assert(trans_list.get_size() == 3);
			
			assert(orig_list.get_task(0).equals(new Task("task1")));
			assert(orig_list.get_task(1).equals(new Task("task2")));
			assert(orig_list.get_task(2).equals(new Task("task3")));
			
			assert(trans_list.get_task(0).equals(new Task("task11")));
			assert(trans_list.get_task(1).equals(new Task("task12")));
			assert(trans_list.get_task(2).equals(new Task("task13")));
		}
	}
}
