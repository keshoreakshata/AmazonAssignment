# AmazonAssignment
Automated Selenium Test Script for filtering and validating wristwatch listings on Amazon India using TestNG and Java.


# 🕒 Amazon Wrist Watch Filter Automation (Selenium + TestNG)

This project automates the process of searching and filtering **Wrist Watches** on [Amazon India](https://www.amazon.in) using **Selenium WebDriver**, **TestNG**, and **Java**.  
It demonstrates practical automation testing skills such as element handling, dynamic filtering, JavaScript execution, and XPath usage.

---

## 🚀 Features
- Launches Amazon and searches for “Wrist Watches”.
- Applies multiple filters dynamically:
  - Display Type → *Analogue*  
  - Band Material → *Leather*  
  - Brand → *Titan*  
  - Discount → *25% Off or more*
- Adjusts **price range** using a **custom JavaScript-based slider handler**.
- Extracts and prints:
  - Discounted price
  - Original MRP
  - Discount percentage
- Implements clean and reusable methods for slider automation.

---

## 🧰 Tech Stack
| Component | Technology Used |
|------------|----------------|
| Language | Java |
| Automation Tool | Selenium WebDriver |
| Test Framework | TestNG |
| Browser | Google Chrome |
| Build Tool | Maven (optional) |

---

## 🧩 Key Concepts Demonstrated
- Web element location using **XPath and `contains()`**
- Handling **dynamic filters** on e-commerce sites
- Using **JavaScriptExecutor** to manipulate sliders
- Implementing **explicit waits** and clean teardown
- Writing **readable and maintainable test automation scripts**

---

## 📂 Project Structure

