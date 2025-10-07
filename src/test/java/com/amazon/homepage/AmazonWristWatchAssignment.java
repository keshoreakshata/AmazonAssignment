package com.amazon.homepage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AmazonWristWatchAssignment {

	// ===== Test Configuration Constants =====
	final String appUrl = "https://amazon.in/";
	final String searchText = "Wrist Watches";
	final String filter1_Display = "Analogue";
	final String filter2_BandMaterial = "Leather";
	final String filter3_Brand = "Titan";
	final String filter4_Discount = "25% Off or more";
	final int minPrice = 3500;
	final int maxPrice = 5500;

	@Test
	public void amazonFilterTestCases() throws InterruptedException {

		// ===== Step 1: Initialize Chrome Browser =====
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// ===== Step 2: Launch Amazon =====
		driver.get(appUrl);

		// ===== Step 3: Search for the product =====
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchText);
		driver.findElement(By.id("nav-search-submit-button")).click();

		// ===== Step 4: Apply Filters =====
		// Filter by Display Type
		driver.findElement(By.xpath("//span[text()='" + filter1_Display + "']")).click();
		// Filter by Band Material
		driver.findElement(By.xpath("//span[text()='" + filter2_BandMaterial + "']")).click();
		// Filter by Brand
		driver.findElement(By.xpath("//span[@class='a-size-base a-color-base' and text()='" + filter3_Brand + "']")).click();
		// Filter by Discount
		driver.findElement(By.xpath("//span[text()='" + filter4_Discount + "']")).click();

		// ===== Step 5: Adjust Price Range Slider =====
		WebElement min = driver.findElement(By.id("p_36/range-slider_slider-item_lower-bound-slider"));
		WebElement max = driver.findElement(By.id("p_36/range-slider_slider-item_upper-bound-slider"));

		// Set minimum and maximum price values dynamically
		setSliderToPrice(driver, min, minPrice);
		setSliderToPrice(driver, max, maxPrice);

		// Click on the 'Go' button to apply price range
		driver.findElement(By.xpath("//input[@class='a-button-input' and @aria-label='Go - Submit price range']")).click();

		// ===== Step 6: Capture Product Details =====
		// Print the first product's discounted price
		WebElement actualPrice = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[position()=1]"));
		System.out.println("Price of the final product - " + actualPrice.getText());

		// Print the MRP of the first product
		WebElement actualMrp = driver.findElement(By.xpath("(//span[@class='a-price a-text-price']//span)[2]"));
		System.out.println("MRP of the final product - " + actualMrp.getText());

		// Print the discount percentage
		WebElement actualDiscount = driver
				.findElement(By.xpath("//span[@class='a-letter-space']//following-sibling::span[1]"));
		System.out.println("Discount of the final product - " + actualDiscount.getText());

		// ===== Step 7: Close Browser =====
		driver.quit();
	}

	/**
	 * Adjusts the Amazon price slider dynamically to reach the target price. Works
	 * by mapping slider step values to displayed prices via JS events.
	 *
	 * @param driver      WebDriver instance
	 * @param slider      WebElement of the slider handle
	 * @param targetPrice Desired price value to set on slider
	 */
	public static void setSliderToPrice(WebDriver driver, WebElement slider, int targetPrice)
			throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Get slider min and max step range
		int sliderMin = Integer.parseInt(slider.getAttribute("min"));
		int sliderMax = Integer.parseInt(slider.getAttribute("max"));

		// Map to hold step-to-price relationships
		Map<Integer, Integer> stepPriceMap = new HashMap<>();

		// Step through each slider value and record the mapped price
		for (int step = sliderMin; step <= sliderMax; step++) {
			js.executeScript("arguments[0].value = arguments[1];" + "arguments[0].dispatchEvent(new Event('input'));"
					+ "arguments[0].dispatchEvent(new Event('change'));", slider, step);

			Thread.sleep(50); // small wait for DOM update

			String priceText = slider.getAttribute("aria-valuetext").replaceAll("[^0-9]", "");
			if (!priceText.isEmpty()) {
				int price = Integer.parseInt(priceText);
				stepPriceMap.put(step, price);
			}
		}

		// Find the slider step closest to target price
		int nearestStep = sliderMin;
		int minDiff = Integer.MAX_VALUE;
		for (Map.Entry<Integer, Integer> entry : stepPriceMap.entrySet()) {
			int diff = Math.abs(entry.getValue() - targetPrice);
			if (diff < minDiff) {
				minDiff = diff;
				nearestStep = entry.getKey();
			}
		}

		// Set the slider to the nearest valid step for target price
		js.executeScript("arguments[0].value = arguments[1];" + "arguments[0].dispatchEvent(new Event('input'));"
				+ "arguments[0].dispatchEvent(new Event('change'));", slider, nearestStep);
	}
}
