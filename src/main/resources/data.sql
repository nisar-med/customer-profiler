INSERT INTO Transaction (CustomerId, Date, Amount, Description)
SELECT CONVERT(CustomerId, INT), PARSEDATETIME(Date, 'd/M/y h:mm:ss a'), CONVERT(Amount, DOUBLE), Description
FROM CSVREAD('classpath:data.csv');