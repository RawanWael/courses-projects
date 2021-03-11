--Rawan Yassin 1182224
--In this part the functional verification is done, and the results are printed in a file 
LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
USE IEEE.STD_LOGIC_ARITH.ALL; 
USE IEEE.STD_LOGIC_UNSIGNED.ALL;  
--the following two libraries are used for file writing 
USE STD.TEXTIO.ALL;				  
use IEEE.std_logic_textio.ALL;

--In verification entity, there are only three main inputs; the inputs for registers: two clocks and a reset
ENTITY verification IS
	PORT(clk1, clk2 , reset: IN STD_LOGIC);
END ENTITY verification; 

--The first architecture to verify the BCD adder made from the ripple adder
ARCHITECTURE verAdder_R OF verification IS   
SIGNAL expectedResult: STD_LOGIC_VECTOR(4 DOWNTO 0);  --this will be computed behavioraly
SIGNAL adderResult: STD_LOGIC_VECTOR(3 DOWNTO 0);	  --this is the actual sum of the BCD adder
SIGNAL adderCarry: STD_LOGIC;					      --this is the actual carry of the BCD adder
SIGNAL test_in1, test_in2: STD_LOGIC_VECTOR(3 DOWNTO 0):="0000";  --these signals will be inputs and will be generated in a for loop 
SIGNAL test_in3: STD_LOGIC:='0';  
SIGNAL actualResult: STD_LOGIC_VECTOR(4 DOWNTO 0);	  --this signal is only used to print the actual sum and carry of the BCD adder concatenated in the file
                                                      --so comparing for reader between the actual result and the expected result would be easier
BEGIN		   
	--unit under test
UNT:ENTITY work.Final_BCD_Adder(Final_BCD_Adder_Circuit_R)PORT MAP(test_in1,test_in2,test_in3,clk1,clk2,reset,adderResult,adderCarry);
actualResult<= adderCarry&adderResult;	--so yes again, this signal is used to print the BCD_adder result to the file
																														  
PROCESS    
    --preparing the file
    FILE outFile : TEXT;
	VARIABLE l : LINE; 
	VARIABLE sum_temp: integer;

BEGIN		
	FILE_OPEN( outFile, "Verification1.txt", WRITE_MODE);	
	 -- generator
	FOR I IN 0 TO 9 LOOP
		FOR J IN 0 TO 9 LOOP
			FOR K IN 0 TO 1 LOOP
			--set the inputs to the adder
			test_in1 <=CONV_STD_LOGIC_VECTOR(i,4);
			test_in2 <=CONV_STD_LOGIC_VECTOR(j,4);
			IF (k=0) THEN test_in3<='0' ;
			ELSE test_in3<='1';
			END IF; 
			WAIT until rising_edge(clk2);	
			--calculate what the output of the adder should be behaviorly 
			sum_temp:=i+j+k;
			IF( sum_temp>9) THEN 
				sum_temp:=sum_temp+6;
				END IF;
			expectedResult<= CONV_STD_LOGIC_VECTOR(sum_temp,5) ; 
			------------------------------------------------------ 
			wait for 5 ns; --this time according to the clk formulas I have used
			--to print in a file	
			--The file format is input1 + input2 + cin => expected_Result || DBC_Adder_result
			IF (expectedResult(3 DOWNTO 0) =  adderResult) and (ExpectedResult(4) = adderCarry)THEN 
			WRITE(l, test_in1);
			WRITE(l, " + ");
			WRITE(l, test_in2);
			WRITE(l, " + ");
			WRITE(l, test_in3);
			WRITE(l, " => ");
			WRITE(l, expectedResult );	
			WRITE(l, " = ");	 
			WRITE(l, actualResult);
			WRITELINE(outFile, l);	 
			--if the expected result and the actual result are not the same, the error will be indicated in the file 
			ELSE
			WRITE(l, test_in1);
			WRITE(l, " + ");
			WRITE(l, test_in2);
			WRITE(l, " + ");
			WRITE(l, test_in3);
			WRITE(l, " => ");
			WRITE(l, expectedResult );	
			WRITE(l, " != ");	 
			WRITE(l, actualResult);
			WRITE(l, " SOMETHING WRONG!");
			WRITELINE(outFile, l);
			END IF;
			--after generating all possible inputs for the adder, we will be reporting that the file may be checked
			IF (i = 9 and j = 9 and k=1) THEN
				ASSERT FALSE
				REPORT ("Done, You May Check The File!")
				SEVERITY WARNING;	
			END IF;	  
			END LOOP; 
			END LOOP;
		END LOOP;
		FILE_CLOSE(outFile); --need to close the file 
		WAIT; 
		
	END PROCESS;  	 		 
	       --analyzer
	 	    ASSERT   (expectedResult(3 DOWNTO 0) =  adderResult) and (ExpectedResult(4) = adderCarry)
			REPORT   ("Adder output is INCORRECT!!")
            SEVERITY WARNING;  
END ARCHITECTURE verAdder_R;

--The second architecture to verify the BCD adder made from the carry look ahead adder
--The same approach of the above architecture is used here
ARCHITECTURE verAdder_CLA OF verification IS   
SIGNAL expectedResult: STD_LOGIC_VECTOR(4 DOWNTO 0); 
SIGNAL adderResult: STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL adderCarry: STD_LOGIC;	
SIGNAL test_in1, test_in2: STD_LOGIC_VECTOR(3 DOWNTO 0):="0000"; 
SIGNAL test_in3: STD_LOGIC:='0';  
SIGNAL actualResult: STD_LOGIC_VECTOR(4 DOWNTO 0);
BEGIN		   
	--unit under test
UNT:ENTITY work.Final_BCD_Adder(Final_BCD_Adder_Circuit_CLA)PORT MAP(test_in1,test_in2,test_in3,clk1,clk2,reset,adderResult,adderCarry);
actualResult<= adderCarry&adderResult;	
																														  
PROCESS 
    FILE outFile : TEXT;
	VARIABLE l : LINE; 
	VARIABLE sum_temp: integer;

BEGIN		
	FILE_OPEN( outFile, "Verification2.txt", WRITE_MODE);
	 -- generator
	FOR I IN 0 TO 9 LOOP
		FOR J IN 0 TO 9 LOOP
			FOR K IN 0 TO 1 LOOP
			--set the inputs to the adder
			test_in1 <=CONV_STD_LOGIC_VECTOR(i,4);
			test_in2 <=CONV_STD_LOGIC_VECTOR(j,4);
			IF (k=0) THEN test_in3<='0' ;
			ELSE test_in3<='1';
			END IF; 
			WAIT until rising_edge(clk2);	
			--calculate what the output of the adder should be behaviorly 
			sum_temp:=i+j+k;
			IF( sum_temp>9) THEN 
				sum_temp:=sum_temp+6;
				END IF;
			expectedResult<= CONV_STD_LOGIC_VECTOR(sum_temp,5) ; 
			------------------------------------------------------ 
			wait for 5 ns; --this time according to the clk formulas I have used
			--to print in a file	
			--The file format is input1 + input2 + cin => expected_Result || DBC_Adder_result
			IF (expectedResult(3 DOWNTO 0) =  adderResult) and (ExpectedResult(4) = adderCarry)THEN 
			WRITE(l, test_in1);
			WRITE(l, " + ");
			WRITE(l, test_in2);
			WRITE(l, " + ");
			WRITE(l, test_in3);
			WRITE(l, " => ");
			WRITE(l, expectedResult );	
			WRITE(l, " || ");	 
			WRITE(l, actualResult);
			WRITELINE(outFile, l);
			ELSE 
			WRITE(l, test_in1);
			WRITE(l, " + ");
			WRITE(l, test_in2);
			WRITE(l, " + ");
			WRITE(l, test_in3);
			WRITE(l, " => ");
			WRITE(l, expectedResult );	
			WRITE(l, " != ");	 
			WRITE(l, actualResult);
			WRITE(l, " SOMETHING WRONG!");
			WRITELINE(outFile, l);
			END IF;			
			IF (i = 9 and j = 9 and k=1) THEN
				ASSERT FALSE
				REPORT ("Done, You May Check The File!")
				SEVERITY WARNING;	
			END IF;	  
			END LOOP; 
			END LOOP;
		END LOOP;
		FILE_CLOSE(outFile);
		WAIT; 
		
	END PROCESS;  	 	  
	--analyzer
	 	    ASSERT   (expectedResult(3 DOWNTO 0) =  adderResult) and (ExpectedResult(4) = adderCarry)
			REPORT   ("Adder output is incorrect")
            SEVERITY WARNING;  
END ARCHITECTURE verAdder_CLA;