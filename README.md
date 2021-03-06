# AI-COVID-19-Predictor
A Java Personal Project, purposed to create an Artificial Neural Network (ANN) that can predict COVID-19 cases in Greece


This Artificial Neural Network consists of:

- 1 input layer of twelve data inputs
- 2 hidden layers size 16
- 1 output layer of one value (projected COVID-19 new cases for the next day)




In this project, we give 12 type of data to the NN:

  1. Total active cases per day
  2. Daily new cases
  3. Reproduction rate
  4. New daily covid-19 tests 
  5. Total covid-19 tests until today
  6. Positive rate
  7. Tests until positive case
  8. Total number of Vaccinations
  9. Total number of people Vaccinated
  10. Number of people fully vaccinated
  11. Number of daily new vaccinations
  12. Stringency index
  
  
The output is the NN's prediction for the number of new cases that Greece is going to have in the next day.

Notes:

1. The program reads Data through MS Excel (it may take some time to load and process all the data)
2. After the ANN is trained, it tries to predict COVID-19 progress in Greece from early March until today (current date: 11/01/202)
3.Finally, the ANN tries to predict what will follow the day after.



Please consider:

The network is still very weak. It shows inability to predict very accurately the cases when there is a lot of volatility on a daily basis. However, it always finds the right direction of the cases (e.g. it is always right whether cases will increase or decrease).

A big flaw that i'm currently trying to find out, is that in times where COVID starts to step back and the number of cases decrease, the ANN struggles to adopt fast.


The Data I put in the neural network origin from : https://ourworldindata.org/coronavirus-testing

The .xlsx file in this project is modified to contain only Greece's data and specific columns. If you wish on working on this on your own, please download the latest version from the source above, to have the most recent and up-to-date data to work with. It is important to note that this can work for any country you want or any data you wish to put into (with the proper modification of course).


One of the biggest pros of this project is that it does not suffer from overfitting bias. In fact, if you modify the code and test it with foreign data, you will see that it projects COVID-19 cases for other countries with the same accuracy with Greece; even though the ANN has never interacted with the specified Data, while training. *A method will be uploaded soon*



Credits:


As a sophomore Management Science and Technology student, it is very demandful and hard to create a proper Neural Network for such a complex problem, such as COVID-19. The knowledge I acquired came from personal research. However, I need to recognise:

-Michał Wieczorek
-Jakub Siłka
-Marcin Woz ́niak
-Suyash Sonawane

as their personal research, scientific papers, and knowledge on the matter, helped me a lot into understanding the complexity of A.N.N.





EXTERNAL SOURCES AND RECOMMENDING READING

- https://www.amazon.com/Hands-Machine-Learning-Scikit-Learn-TensorFlow/dp/1492032646/ref=zg_bs_3887_1?_encoding=UTF8&psc=1&refRID=E99CJYG68C991WC4BJZC
- https://www.amazon.com/Mathematics-Machine-Learning-Peter-Deisenroth/dp/110845514X/ref=zg_bs_3887_38?_encoding=UTF8&psc=1&refRID=E99CJYG68C991WC4BJZC

