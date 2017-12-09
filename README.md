# Royalty-Parser-Project

Purpose: This program was built to take in a set of unique Amazon book royalty spreadsheets, compile their individual data into one large dataset and then output a custom book royalty spreadsheet for each author I work with that contains only the books belonging to them.


--------------------------------------
Step 1. The Reports Panel
 
I.	First Click the ‘Browse’ button. This will allow you to browse to the "input" folder to select the excel reports.
II.	Select the Amazon spreadsheet file(s) you wish to parse. (Note: the file names must begin with “KDP Prior Month Royalties” and end in the .xlsx file extension).
III.	You may select more than one file at a time.
IV.	Once the file(s) are selected you must click the “Add Report” button to add the report to the report files text window. Look for confirmation in the “Status Messages” section for successful inclusion of your selected file.
V.	An individual file may be removed by clicking on it in the Reports window and then clicking “Remove Report”.

--------------------------------------
Step 2. The Currency Conversions Panel
 
I.	Here you have the option of inputting the current foreign exchange rate conversion for each currency type included in the Amazon Book Royalty Reports. (For Example: as of 12/1/2017, €1.00EUR == $1.19USD, so put 1.19 as the EUR conversion rate).
II.	You may click “Save Conversions” at any point to save the conversion rates.
III.	You must click “Save Conversions” before they will take effect in the outputted royalty reports. (Note: The reports will generate without any entered currency conversion rates; however, the resulting royalties will be inaccurate for currencies other than USD.)
 
--------------------------------------
Step 3. The Author Profiles Panel
 
I.	A list of pre-saved example author profiles will load automatically. Every author profile listed here will have a custom royalty report generated for them.
II.	You may enter a new (unique) profile name in the “Enter New Author” Field and then click “Add Author” to add the author profile name to the list.
III.	You may also click on an author profile name in the list and click “Remove Author” to remove any author from the list that you do not wish to generate a report for.

--------------------------------------
Step 4. The Author’s ASINs Panel
 
I.	ASINs are a book’s unique identification number on Amazon, much like an ISBN. Click an author profile name in the Author Profiles Panel list window to see a list of their pre-saved ASINs of the books belonging to that author.
II.	Once an author profile is selected, their ASINs list will populate. You may add or remove ASINs to include or exclude the books associated with that ASIN from the royalty report that is generated.

--------------------------------------
Step 5. Status Report Panel
 
I.	The status report panel contains the status log to present the user with important information about the program state.
II.	When you are ready to generate your reports you may click the “Create Reports” button.
(NOTE: this may take a few seconds).
III.	A set of custom reports, one for each author profile, will be created and saved in the project's root folder.
When you are finished:
1.	Please delete the reports that are generated in the Output folder so that other beta testers may more readily see their results.
2.	Please close the program by clicking the “Cancel” button.
