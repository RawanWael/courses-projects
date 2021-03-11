--Rawan Yassin 1182224
--Number at the end of each used name indicates the number of bits 

--Creating the one bit full adder structurally
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
LIBRARY gates;
USE gates.ALL;
--entity of the one bit full adder
ENTITY full_adder1 IS
	PORT ( x,y,cin : IN std_logic;
	sum,cout: OUT std_logic);
END ENTITY  full_adder1;	  
--architecture of the one bit full adder
ARCHITECTURE structural_full_adder1 of full_adder1 IS 
Signal n1,n2,n3,n4: std_logic;
Begin						  
  g1:  ENTITY gates.xor_gate(xor_gate2) PORT MAP (x,y,n1);
  g2:  ENTITY gates.xor_gate(xor_gate2) PORT MAP (n1,cin,sum);
  g3:  ENTITY gates.and_gate(and_gate2) PORT MAP (x,y,n2);
  g4:  ENTITY gates.and_gate(and_gate2) PORT MAP (x,cin,n3);
  g5:  ENTITY gates.and_gate(and_gate2) PORT MAP (y,cin,n4);
  g6:  ENTITY gates.or3_gate(or_gate3) PORT MAP (n2,n3,n4,cout); 
END ARCHITECTURE structural_full_adder1;
  
--Creating the n_bit ripple adder structurally  
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
--entity of the four_bit adder
ENTITY RipAdder IS	
  GENERIC (n: POSITIVE:=4 );
  PORT ( x, y: IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
          cin:  IN STD_LOGIC;
          sum: OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0);
		  cout: OUT STD_LOGIC);
END ENTITY Ripadder;
--architecture of the n_bit ripple adder
ARCHITECTURE structural_Ripadder OF Ripadder IS
	  SIGNAL carry: STD_LOGIC_VECTOR(n DOWNTO 0);
BEGIN
  carry(0) <= cin;
  cout <= carry(n);
  gen1: FOR i IN 0 TO n-1 GENERATE  
    g:    ENTITY work.full_adder1(structural_full_adder1) PORT MAP(x(i),y(i),carry(i),sum(i),carry(i+1));
        END GENERATE gen1;
END ARCHITECTURE structural_Ripadder; 

--Creating the BCD adder from a four_bit ripple adder
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
--entity of the BCD adder
ENTITY BCD_adder IS 
	PORT (a,b : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
	cin: IN STD_Logic;
	sum: OUT STD_LOGIC_VECTOR(3 DOWNTO 0);
    cout: OUT STD_LOGIC);	
END ENTITY BCD_adder;
--architecture of the BCD adder built structurally using the structured_ripple carry adder
ARCHITECTURE BCD_adder4_R of BCD_adder IS
Signal first_sum : STD_LOGIC_VECTOR(3 DOWNTO 0);
Signal first_carry: STD_LOGIC; 
Signal usedInCorrection: STD_LOGIC_VECTOR(3 DOWNTO 0);
Signal ignoredCout: STD_LOGIC;
Signal correctionFlag: STD_LOGIC;
Begin		   
	FirstAddition: ENTITY work.Ripadder(structural_Ripadder) GENERIC MAP (4) PORT MAP (a,b,cin,first_sum, first_carry);
	correctionDetect: ENTITY work.correctionDetection(Detecttion_Circuit) PORT MAP (first_sum(3),first_sum(2),first_sum(1),first_carry,correctionFlag);	
	usedInCorrection<="0110" WHEN correctionFlag ='1'
	ELSE "0000";
	Cout<='1' WHEN correctionFlag='1'
	ELSE '0';
	SecondAddition: ENTITY work.Ripadder(structural_Ripadder) GENERIC MAP (4) PORT MAP (first_sum, usedInCorrection,'0', sum,ignoredCout); 		
END ARCHITECTURE BCD_adder4_R;	 

--architecture of the BCD adder built structurally using the carry look ahead adder
ARCHITECTURE BCD_adder4_CLA of BCD_adder IS
Signal first_sum : STD_LOGIC_VECTOR(3 DOWNTO 0);
Signal first_carry: STD_LOGIC; 
Signal usedInCorrection: STD_LOGIC_VECTOR(3 DOWNTO 0);
Signal ignoredCout: STD_LOGIC;
Signal correctionFlag: STD_LOGIC;
Begin		   		
	FirstAddition: ENTITY work.CLAadder(CLAadder4) PORT MAP (a,b,cin,first_sum, first_carry);
	correctionDetect: ENTITY work.correctionDetection(Detecttion_Circuit) PORT MAP (first_sum(3),first_sum(2),first_sum(1),first_carry,correctionFlag);	
	usedInCorrection<="0110" WHEN correctionFlag ='1'
	ELSE "0000";
	Cout<='1' WHEN correctionFlag='1'
	ELSE '0';
	SecondAddition: ENTITY work.adder4(structural_adder4) PORT MAP (first_sum, usedInCorrection,'0', sum,ignoredCout); 		
END ARCHITECTURE BCD_adder4_CLA;	

--combinational circuit used in deciding whether adding 6 is needed in the BCD_Addition
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
LIBRARY gates;
USE gates.ALL;

ENTITY correctionDetection IS
	PORT( sum_bit3, sum_bit2,sum_bit1,carry: IN STD_LOGIC;
	flag: OUT STD_LOGIC);
END ENTITY correctionDetection;

ARCHITECTURE Detecttion_Circuit of correctionDetection IS
SIGNAL n1,n2: STD_LOGIC;
BEGIN 
	test1: ENTITY gates.and_gate(and_gate2) PORT MAP (sum_bit3,sum_bit2, n1);
	test2: ENTITY gates.and_gate(and_gate2) PORT MAP (sum_bit3,sum_bit1, n2);
	test3: ENTITY gates.or3_gate(or_gate3) PORT MAP (n1,n2,carry, flag);
END ARCHITECTURE Detecttion_Circuit;  


--Creating the Carry Look Ahead Adder
LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
LIBRARY gates;
USE gates.ALL;
--the entity of the carry look ahead adder
ENTITY CLAadder IS  
	PORT(x, y : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		  cin : IN STD_LOGIC;
 		 sum : OUT STD_LOGIC_VECTOR(3 DOWNTO 0);
  		 cout : OUT STD_LOGIC);
END ENTITY CLAadder;
--the architeture of the carry look ahead adder, made structurally from the basic gates in the created library (gates)
ARCHITECTURE CLAadder4 OF CLAadder IS 
SIGNAL p 	: STD_LOGIC_VECTOR(3 DOWNTO 0);	-- carryPropagte
SIGNAL g 	: STD_LOGIC_VECTOR(3 DOWNTO 0); -- carryGenerate
SIGNAL c    : STD_LOGIC_VECTOR(4 DOWNTO 0); -- inadditionCarr y 

SIGNAL p0c0 : STD_logic; 
SIGNAL p1g0 : STD_logic;	
SIGNAL p1p0c0 : STD_logic;
SIGNAL p2g1 : STD_logic;	
SIGNAL p2p1g0 : STD_logic;	
SIGNAL p2p1p0c0 : STD_logic;
SIGNAL p3g2 : STD_logic;	
SIGNAL p3p2g1 : STD_logic;	
SIGNAL p3p2p1g0 : STD_logic;	
SIGNAL p3p2p1p0c0 : STD_logic;
SIGNAL c1,c2,c31,c32,c41,c42,c43: STD_logic;

BEGIN
	--Carry propagate is the name for x xor y 
	carryPropagate: FOR i IN 3 DOWNTO 0 GENERATE 
		carryPropLoop: ENTITY gates.xor_gate(xor_gate2) PORT MAP(x(i), y(i), p(i));
	END GENERATE carryPropagate;  
	--Carry Generate is the name for x and y
	carryGenerate: FOR i IN 3 DOWNTO 0 GENERATE 
		carryGeneLoop: ENTITY gates.and_gate(and_gate2) PORT MAP(x(i), y(i), g(i)); 
	END GENERATE carryGenerate;	
	
	
	
	--assigning the carry in input to c(0) 
	c(0) <= cin; 	
	--assigning c(4) to the carry output
	cout <= c(4); 
	
	--We have three levels of and and four levels of xor
	--calculating each needed signal in the addition operation
	
	p0c0_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p(0), c(0), p0c0);   
		
	p1g0_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p(1), g(0), p1g0); 		
	
	p1p0c0_cal1: ENTITY gates.and_gate(and_gate2) PORT MAP(p0c0,p(1) ,p1p0c0); 
			
	p2g1_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p(2), g(1), p2g1);
	
	p2p1g0_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p1g0,p(2), p2p1g0);   
			
	p2p1p0c0_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p1p0c0,p(2), p2p1p0c0);    
			
	p3g2_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p(3), g(2), p3g2);
	
	p3p2g1_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p2g1, p(3), p3p2g1);
	
	p3p2p1g0_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p2p1g0, p(3), p3p2p1g0);	
		
	p3p2p1p0c0_cal: ENTITY gates.and_gate(and_gate2) PORT MAP(p2p1p0c0,p(3), p3p2p1p0c0); 
		
	--Calculating the carries	 
	
	c1_cal: ENTITY gates.or_gate(or_gate2) PORT MAP(p0c0, g(0), c(1)); 
		
	c2_cal1: ENTITY gates.or_gate(or_gate2) PORT MAP(g(1), p1g0, c2);  
		
	c2_cal2: ENTITY gates.or_gate(or_gate2) PORT MAP(c2,p1p0c0, c(2));
	
	c3_cal1: ENTITY gates.or_gate(or_gate2) PORT MAP(g(2), p2g1, c31); 	
		
	c3_cal2: ENTITY gates.or_gate(or_gate2) PORT MAP(c31, p2p1g0, c32); 	
		
	c3_cal3: ENTITY gates.or_gate(or_gate2) PORT MAP(c32, p2p1p0c0, c(3));
		
	c4_cal1: ENTITY gates.or_gate(or_gate2) PORT MAP(g(3), p3g2, c41);  
	
	c4_cal2: ENTITY gates.or_gate(or_gate2) PORT MAP(c41, p3p2g1, c42);
		
	c4_cal3: ENTITY gates.or_gate(or_gate2) PORT MAP(c42, p3p2p1g0, c43);
		
	c4_cal4: ENTITY gates.or_gate(or_gate2) PORT MAP(c43, p3p2p1p0c0, c(4)); 	
		
		
		
    summing : FOR i IN 0 TO 3 GENERATE  
		 s : ENTITY gates.xor_gate(xor_gate2) PORT MAP (p(i),c(i),sum(i));   			 
	END GENERATE summing;
	
END ARCHITECTURE CLAadder4;

