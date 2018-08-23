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
			assert(le.get_error_src() == "get_index(Task)");
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
			System.exit(1);
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
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(Task)");
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(0).equals(new Task("task2")));
			assert(l0.get_task(0).equals(new Task("task3")));
		}
		
		// try to remove based on created task instance
		// catch exception
		// check that nothing changed
		try
		{
			l0.remove(new Task("tak1"));
			System.err.println("remove(removed_task) worked for nonexistent task.");
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(Task)");
			assert(l0.get_task(0).equals(new Task("task1")));
			assert(l0.get_task(0).equals(new Task("task2")));
			assert(l0.get_task(0).equals(new Task("task3")));
		}
	}
	
	@Test
	void test_remove_by_index() throws ListException
	{
		fail();
		
		// create a list
		// append some tasks
		// create an int instance representing the index
		// within the bounds of the list
		// remove with that variable as argument
		// check that everything went alright
		// remove with given int, say 0
		// check that everything went alright
	}
	
	@Test
	void test_remove_by_index_error_handling() 
			throws ListException, TaskException
	{
		// create a list
		List l0 = new List();
		
		// append some tasks
		for(int idx=0; idx < 3; idx++)
		{
			l0.append(new Task("task" + Integer.toString(idx)));
		}
		
		// create an int instance outside the bounds of the list
		int invalid_index = 3;
		
		// try to remove with that instance as an argument
		// catch exception
		// check that nothing changed
		try
		{
			l0.remove(invalid_index);
			System.err.println("remove(index) successfully removed past end of List.");
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(index)");
			for(int idx=0; idx < 3; idx++)
			{
				assert(l0.get_task(idx).equals(new
						Task("task" + Integer.toString(idx) ) ) );
			}
		}
		
		// try to remove with int input as instance
		// catch exception
		// check that nothing changed
		try
		{
			l0.remove(-1);
			System.err.println("remove(index) successfully removed past end of List.");
		}
		catch(ListException le)
		{
			assert(le.get_error_src() == "remove(index)");
			for(int idx=0; idx < 3; idx++)
			{
				assert(l0.get_task(idx).equals(new
						Task("task" + Integer.toString(idx) ) ) );
			}
		}
	}
	
	@Test
	void test_transfer_task() throws ListException
	{
		fail();
		
		// Ways to transfer by task (not index):
		// - With identical task instance variable
		// - With task instance variable with identical details
		//     but null user number
		// - With string instance containing same details
		
		// create two lists, original_list and transfer_list
		// append a few tasks to original_list
		// create a task instance identical to a particular task
		//   in original_list
		// create a task instance with details identical to that
		//   of a particular task in original_list but null user
		//   number
		// create a string which is identical to the details of a
		//   a particular task in original_list
		// transfer to transfer_list with identical task as arg
		// check that everything went alright
		// transfer to transfer_list with task with identical
		//   details but null user number
		// check that everything went alright
		// transfer to transfer_list with string
		// check that everything went alright
	}
	
	@Test
	void test_transfer_task_error_handling() throws ListException
	{
		fail();
		
		// Ways a transfer-by-task can cause errors:
		// - Passed task, but no such task exists in original list
		// - Passed task, but task already exists in transfer list
		// - Passed string, but no task with those details exists
		//     in original list
	}
	
	@Test
	void test_transfer_index() throws ListException
	{
		fail();
	}
	
	@Test
	void test_transfer_index_error_handling() throws ListException
	{
		fail();
	}
	
	@Test
	void test_transfer_task_to_index() throws ListException
	{
		fail();
	}
	
	@Test
	void test_transfer_task_to_index_error_handling()
	throws ListException
	{
		fail();
	}
	
	@Test
	void test_transfer_index_to_index() throws ListException
	{
		fail();
	}
	
	@Test
	void test_transfer_index_to_index_error_handling()
	throws ListException
	{
		fail();
	}
}
