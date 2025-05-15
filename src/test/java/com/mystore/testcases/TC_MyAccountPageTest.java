package com.mystore.testcases;

import java.io.IOException;

import com.mystore.utilities.ReadExcelFile;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mystore.pageobject.accountCreationDetails;
import com.mystore.pageobject.indexPage;
import com.mystore.pageobject.myAccountPage;
import com.mystore.pageobject.registeredUserAccount;


public class TC_MyAccountPageTest extends BaseClass {


	@Test(enabled = false)
	public void verifyRegistrationAndLogin()//This test cases has an issues.Every email id has showing already registered.
	{

		logger.info("***************TestCase Verify Registration and Login starts*****************"); 


		indexPage pg = new indexPage(driver);

		pg.clickOnSignIn();
		logger.info("Clicked on sign in link");

		myAccountPage myAcpg = new myAccountPage(driver);
		myAcpg.enterCreateEmailAddress("mdtoufique1096@gmail.com");
		logger.info("Email address entered in create account section.");

		myAcpg.clickSubmitCreate();

		logger.info("clicked on create an account button");

		accountCreationDetails accCreationPg = new accountCreationDetails(driver);

		accCreationPg.selectTitleMr();
		logger.info("clicked on gender");
		accCreationPg.enterCustomerFirstName("toufique");
		logger.info("Entered first name ");
		accCreationPg.enterCustomerLastName("Ansari");
		logger.info("Entered last name ");
		accCreationPg.enterPassword("Toufique@825");
		logger.info("Entered password ");
//		accCreationPg.enterAddressFirstName("toufique");
//		accCreationPg.enterAddressLastName("");

		accCreationPg.enterAddress("18/8 worli road");
		logger.info("Entered Address");

		accCreationPg.enterCity("Mumbai");
		accCreationPg.selectState("Alabama");

		accCreationPg.enterPostcode("00000");
		accCreationPg.selectCountry("United States");
		accCreationPg.enterMobilePhone("8051261825");
		accCreationPg.enterAlias("Home");

		logger.info("entered user details on account creation page.");

		accCreationPg.clickOnRegister();
		logger.info("clicked on Register button");

		registeredUserAccount regUser = new registeredUserAccount(driver);
		String userName = regUser.getUserName();

		Assert.assertEquals("toufique Ansari", userName);

		logger.info("***************TestCase Verify Registration and Login ends*****************"); 

	}

	@Test(dataProvider = "LoginDataProvider")
	public void VerifyLogin(String userEmail, String userPassword, String expectedUserName) throws IOException
	{

		logger.info("***************TestCase Verify Login starts*****************"); 

		indexPage pg = new indexPage(driver);

		pg.clickOnSignIn();
		logger.info("Clicked on sign in link");

		myAccountPage myAcpg = new myAccountPage(driver);

	myAcpg.enterEmailAddress(userEmail);
	logger.info("Entered email address");

	myAcpg.enterPassword(userPassword);
		logger.info("Entered password");

	myAcpg.clickSignIn();
		logger.info("Clicked on sign in link..");


	registeredUserAccount regUser = new registeredUserAccount(driver);
		String userName = regUser.getUserName();


		if(userName.equals(expectedUserName))
		{
			logger.info("VerifyLogin - Passed");
			regUser.clickOnSignOut();
			Assert.assertTrue(true);
			regUser.clickOnSignOut();
			logger.info("Sign out");
		}
		else
		{
			logger.info("VerifyLogin - Failed");
			captureScreenShot(driver,"VerifyLogin");
			Assert.assertTrue(false);

		}

		logger.info("***************TestCase Verify Login ends*****************"); 


	}


	@Test(enabled = false)
	public void VerifySignOut() throws IOException 
	{

		logger.info("***************TestCase Verify Sign out starts*****************"); 

		indexPage pg = new indexPage(driver);

		pg.clickOnSignIn();
		logger.info("Clicked on sign in link");

		myAccountPage myAcpg = new myAccountPage(driver);

		myAcpg.enterEmailAddress("cs923@gmail.com");
		logger.info("Entered email address");

		myAcpg.enterPassword("cs923");
		logger.info("Entered password");

		myAcpg.clickSignIn();
		logger.info("Clicked on sign in link..");
		// VerifySignOut - Failed


		registeredUserAccount regUser = new registeredUserAccount(driver);
		regUser.clickOnSignOut();

		if(pg.getPageTitle().equals("Login - My Shop"))
		{
			logger.info("VerifySignOut - Passed");
			Assert.assertTrue(true);
		}

		else
		{
			logger.info("VerifySignOut - Failed");
			captureScreenShot(driver,"VerifySignOut");
			Assert.assertTrue(false);
		}

	
		logger.info("***************TestCase Verify Sign out ends*****************"); 

	}
	@DataProvider(name="LoginDataProvider")
	public String[][] LoginDataProvider(){

		String fileName=System.getProperty("user.dir")+"\\TestData\\MyStoreTestData.xlsx";
		int ttlRows= ReadExcelFile.getRowCount(fileName,"LoginTestData");
		int ttlColumns=ReadExcelFile.getColCount(fileName,"LoginTestData");
		String data[][]=new String[ttlRows-1][ttlColumns];
		for(int i=1;i<ttlRows;i++){
			for(int j=0;j<ttlColumns;j++){
				data[i-1][j]=ReadExcelFile.getCellValue(fileName,"LoginTestData",i,j);
			}

		}
		return data;
	}


}
