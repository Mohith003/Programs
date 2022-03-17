package edu.nmsu.cs.circles;

/***
 * Example JUnit testing class for Circle2 (and Circle)
 *
 * - must have your classpath set to include the JUnit jarfiles - to run the test do: java
 * org.junit.runner.JUnitCore Circle2Test - note that the commented out main is another way to run
 * tests - note that normally you would not have print statements in a JUnit testing class; they are
 * here just so you see what is happening. You should not have them in your test cases.
 ***/

import org.junit.*;

public class Circle2Test
{
	// Data you need for each test case
	private Circle2 circle2;

	//
	// Stuff you want to do before each test case
	//
	@Before
	public void setup()
	{
		System.out.println("\nTest starting...");
		circle2 = new Circle2(1, 2, 3);
	}

	//
	// Stuff you want to do after each test case
	//
	@After
	public void teardown()
	{
		System.out.println("\nTest finished.");
	}

	//
	// Test a simple positive move
	//
	@Test
	public void simpleMove()
	{
		Point p;
		System.out.println("Running test simpleMove.");
		p = circle2.moveBy(1, 1);
		Assert.assertTrue(p.x == 3 && p.y == 2);
	}

	//
	// Test a simple negative move
	//
	@Test
	public void simpleMoveNeg()
	{
		Point p;
		System.out.println("Running test simpleMoveNeg.");
		p = circle2.moveBy(-1, -1);
		Assert.assertTrue(p.x == 1 && p.y == 0);
	}

		//
	// Test by increasing the scale
	@Test
	public void testIncreaseScale() {
		System.out.println("Running test testIncreaseScale.");
		double b = circle2.scale(0);
		double r = circle2.scale(5.0);
		Assert.assertTrue((b - r) == -5);
	}

	//
	// Test by decerasing the scale
	//
	@Test
	public void testDecreaseScale() {
		System.out.println("Running test testDecreaseScale.");
		double b = circle2.scale(0);
		double r = circle2.scale(-0.5);
		Assert.assertTrue((b - r) == 0.5);
	}

	//
	// Test intersection in 1st quadrant
	//
	@Test
	public void testIntersectFirstQuad() {
		System.out.println("Running test testIntersectFirstQuad.");
		Point p = circle2.moveToCenter();
		Circle2 one = new Circle2(2, 2, 2);
		Assert.assertTrue(circle2.intersects(one));
	}

	//
	// Test intersection in 2nd quadrant
	//
	@Test
	public void testIntersectSecondQuad() {
		System.out.println("Running test testIntersectSecondQuad.");
		Point p = circle2.moveToCenter();
		Circle2 two = new Circle2(-2, 2, 2);
		Assert.assertTrue(circle2.intersects(two));
	}

	//
	// Test intersection in 3rd quadrant
	//
	@Test
	public void testIntersectThirdQuad() {
		System.out.println("Running test testIntersectThirdQuad.");
		Point p = circle2.moveToCenter();
		Circle2 three = new Circle2(-2, -2, 2);
		Assert.assertTrue(circle2.intersects(three));
	}

	//
	// Test intersection in 4th quadrant
	//
	@Test
	public void testIntersectFourthQuad() {
		System.out.println("Running test testIntersectFourthQuad.");
		Point p = circle2.moveToCenter();
		Circle2 four = new Circle2(2, -2, 2);
		Assert.assertTrue(circle2.intersects(four));
	}

	//
	// tests intersects with non intersecting circle
	//
	@Test
	public void testNonIntersectingCircle() {
		System.out.println("Running test testNonIntersectingCircle.");
		Point p = circle2.moveToCenter();
		Circle2 nonIntersector = new Circle2(7, 5, 2);
		Assert.assertTrue(!circle2.intersects(nonIntersector));
	}

	/***
	 * NOT USED public static void main(String args[]) { try { org.junit.runner.JUnitCore.runClasses(
	 * java.lang.Class.forName("Circle2Test")); } catch (Exception e) { System.out.println("Exception:
	 * " + e); } }
	 ***/

}
