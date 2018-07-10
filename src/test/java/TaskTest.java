package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import workflow_app.Task;
import workflow_app.Task.TaskException;

// Tests as of 2018 June 21 do not take into account that tasks
// will have assigned users to them as the user class has not yet
// been written.  At a later date when I have a firmer idea of
// what that user class will look like, I will update the tests
// to take users into account.  For the time being, tasks will
// use ints to stand for users.  As an aside, I think that might
// stay the case as I will likely use ints as identifiers for
// users.  We'll see.

class TaskTest
{

	@Test
	void testDefaultConstructor()
	{
		Task t0 = new Task();
		assertEquals(t0.get_details(), "");
		assertEquals(t0.get_user(), 0);
	}
	
	@Test
	void testExplicitAConstructor()
	{
		Task t0 = new Task("testtask");
		assertEquals(t0.get_details(), "testtask");
		assertEquals(t0.get_user(), 0);
	}
	
	@SuppressWarnings("unused")
	@Test
	void testExplicitBConstructor() throws TaskException
	{
		Task t0 = new Task(5);
		assertEquals(t0.get_details(), "");
		assertEquals(t0.get_user(), 5);
		
		try
		{
			Task t1 = new Task(-1);
		}
		catch(Task.TaskException te)
		{
			assertEquals(te.get_error_src(), "Task(int)");
		}
	}
	
	@Test
	void testGetDetails() throws TaskException
	{
		Task t0 = new Task();
		Task t1 = new Task("testdeets");
		Task t2 = new Task(2);
		Task t3 = new Task("taskdeets", 3);
		
		assertEquals(t0.get_details(), "");
		assertEquals(t1.get_details(), "testdeets");
		assertEquals(t2.get_details(), "");
		assertEquals(t3.get_details(), "taskdeets");
	}
	
	@Test
	void testGetUser() throws TaskException
	{
		Task t0 = new Task();
		Task t1 = new Task("testdeets");
		Task t2 = new Task(2);
		Task t3 = new Task("taskdeets", 3);
		
		assertEquals(t0.get_user(), 0);
		assertEquals(t1.get_user(), 0);
		assertEquals(t2.get_user(), 2);
		assertEquals(t3.get_user(), 3);
	}
	
	@Test
	void testSetDetails() throws TaskException
	{
		Task t0 = new Task();
		
		t0.set_details("newdeets");
		assertEquals(t0.get_details(), "newdeets");
		
		t0.set_details("newnewdeets");
		assertEquals(t0.get_details(), "newnewdeets");
	}
	
	@Test
	void testSetUser() throws TaskException
	{
		Task t0 = new Task();
		
		t0.set_user(3);
		assertEquals(t0.get_user(), 3);
		
		t0.set_user(2099669);
		assertEquals(t0.get_user(), 2099669);
		
		try
		{
			t0.set_user(-2);
		}
		catch(Task.TaskException te)
		{
			assertEquals(te.get_error_src(), "set_user(int)");
		}
	}
	
	@Test
	void test_equal_details() throws TaskException
	{
		Task t0 = new Task();
		Task t1 = new Task("task1");
		Task t2 = new Task(5);
		Task t3 = new Task("task1");
		Task t4 = new Task(5);
		Task t5 = new Task("task1", 5);
		Task t6 = new Task("task1", 5);
		Task t7 = new Task("task2");
		Task t8 = new Task(4);
		Task t9 = new Task("task2", 4);
		Task t10 = new Task();
		
		assertEquals(t0.equal_details(t10), true);
		assertEquals(t0.equal_details(t1), false);
		assertEquals(t0.equal_details(t2), true);
		assertEquals(t0.equal_details(t5), false);
		
		assertEquals(t1.equal_details(t3), true);
		assertEquals(t1.equal_details(t2), false);
		assertEquals(t1.equal_details(t5), true);
		assertEquals(t1.equal_details(t7), false);
		assertEquals(t1.equal_details(t8), false);
		
		assertEquals(t2.equal_details(t4), true);
		assertEquals(t2.equal_details(t3), false);
		assertEquals(t2.equal_details(t5), false);
		assertEquals(t2.equal_details(t7), false);
		assertEquals(t2.equal_details(t8), true);
		
		assertEquals(t5.equal_details(t6), true);
		assertEquals(t5.equal_details(t0), false);
		assertEquals(t5.equal_details(t1), true);
		assertEquals(t5.equal_details(t2), false);
		assertEquals(t5.equal_details(t9), false);
	}
	
	@Test
	void test_not_equal_details() throws TaskException
	{
		Task t0 = new Task();
		Task t1 = new Task("task1");
		Task t2 = new Task(5);
		Task t3 = new Task("task1");
		Task t4 = new Task(5);
		Task t5 = new Task("task1", 5);
		Task t6 = new Task("task1", 5);
		Task t7 = new Task("task2");
		Task t8 = new Task(4);
		Task t9 = new Task("task2", 4);
		Task t10 = new Task();
		
		assertEquals(t0.not_equal_details(t10), false);
		assertEquals(t0.not_equal_details(t1), true);
		assertEquals(t0.not_equal_details(t2), false);
		assertEquals(t0.not_equal_details(t5), true);
		
		assertEquals(t1.not_equal_details(t3), false);
		assertEquals(t1.not_equal_details(t2), true);
		assertEquals(t1.not_equal_details(t5), false);
		assertEquals(t1.not_equal_details(t7), true);
		assertEquals(t1.not_equal_details(t8), true);
		
		assertEquals(t2.not_equal_details(t4), false);
		assertEquals(t2.not_equal_details(t3), true);
		assertEquals(t2.not_equal_details(t5), true);
		assertEquals(t2.not_equal_details(t7), true);
		assertEquals(t2.not_equal_details(t8), false);
		
		assertEquals(t5.not_equal_details(t6), false);
		assertEquals(t5.not_equal_details(t0), true);
		assertEquals(t5.not_equal_details(t1), false);
		assertEquals(t5.not_equal_details(t2), true);
		assertEquals(t5.not_equal_details(t9), true);
	}
}
