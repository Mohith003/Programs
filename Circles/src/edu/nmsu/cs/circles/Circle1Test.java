package edu.nmsu.cs.circles;

/***
 * Example JUnit testing class for Circle1 (and Circle)
 *
 * - must have your classpath set to include the JUnit jarfiles - to run the test do: java
 * org.junit.runner.JUnitCore Circle1Test - note that the commented out main is another way to run
 * tests - note that normally you would not have print statements in a JUnit testing class; they are
 * here just so you see what is happening. You should not have them in your test cases.
 ***/

import org.junit.*;

public class Circle1Test {
	// Data you need for each test case
	private Circle1 circle1;

	//
	// Stuff you want to do before each test case
	//
	@Before
	public void setup() {
		System.out.println("\nTest starting...");
		circle1 = new Circle1(1, 2, 3);
	}

	//
	// Stuff you want to do after each test case
	//
	@After
	public void teardown() {
		System.out.println("\nTest finished.");
	}

	//
	// Test a simple positive move
	//
	@Test
	public void simpleMove() {
		Point p;
		System.out.println("Running test simpleMove.");
		p = circle1.moveBy(1, 1);
		Assert.assertTrue(p.x == 2 && p.y == 3);
	}

	//
	// Test a simple negative move
	//
	@Test
	public void simpleMoveNeg() {
		Point p;
		System.out.println("Running test simpleMoveNeg.");
		p = circle1.moveBy(-1, -1);
		Assert.assertTrue(p.x == 0 && p.y == 1);
	}

	//
	// Test by increasing the scale
	@Test
	public void testIncreaseScale() {
		System.out.println("Running test testIncreaseScale.");
		double b = circle1.scale(0);
		double r = circle1.scale(5.0);
		Assert.assertTrue((b - r) == -5);
	}

	//
	// Test by decerasing the scale
	//
	@Test
	public void testDecreaseScale() {
		System.out.println("Running test testDecreaseScale.");
		double b = circle1.scale(0);
		double r = circle1.scale(-0.5);
		Assert.assertTrue((b - r) == 0.5);
	}

	//
	// Test intersection in 1st quadrant
	//
	@Test
	public void testIntersectFirstQuad() {
		System.out.println("Running test testIntersectFirstQuad.");
		Point p = circle1.moveToCenter();
		Circle1 one = new Circle1(2, 2, 2);
		Assert.assertTrue(circle1.intersects(one));
	}

	//
	// Test intersection in 2nd quadrant
	//
	@Test
	public void testIntersectSecondQuad() {
		System.out.println("Running test testIntersectSecondQuad.");
		Point p = circle1.moveToCenter();
		Circle1 two = new Circle1(-2, 2, 2);
		Assert.assertTrue(circle1.intersects(two));
	}

	//
	// Test intersection in 3rd quadrant
	//
	@Test
	public void testIntersectThirdQuad() {
		System.out.println("Running test testIntersectThirdQuad.");
		Point p = circle1.moveToCenter();
		Circle1 three = new Circle1(-2, -2, 2);
		Assert.assertTrue(circle1.intersects(three));
	}

	//
	// Test intersection in 4th quadrant
	//
	@Test
	public void testIntersectFourthQuad() {
		System.out.println("Running test testIntersectFourthQuad.");
		Point p = circle1.moveToCenter();
		Circle1 four = new Circle1(2, -2, 2);
		Assert.assertTrue(circle1.intersects(four));
	}

	//
	// tests intersects with non intersecting circle
	//
	@Test
	public void testNonIntersectingCircle() {
		System.out.println("Running test testNonIntersectingCircle.");
		Point p = circle1.moveToCenter();
		Circle1 nonIntersector = new Circle1(7, 5, 2);
		Assert.assertTrue(!circle1.intersects(nonIntersector));
	}

	/***
	 * NOT USED public static void main(String args[]) { try {
	 * org.junit.runner.JUnitCore.runClasses(
	 * java.lang.Class.forName("Circle1Test")); } catch (Exception e) {
	 * System.out.println("Exception:
	 * " + e); } }
	 ***/

}
