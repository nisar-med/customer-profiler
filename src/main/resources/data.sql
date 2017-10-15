INSERT INTO Transaction (CustomerId, Date, Amount, Description)
SELECT CONVERT(CustomerId, INT), PARSEDATETIME(Date, 'd/M/y HH:mm:ss'), CONVERT(Amount, DOUBLE), Description
FROM CSVREAD('classpath:data.csv');