--Invertor gate entity and architecture
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY not_gate IS 
	PORT ( a: IN std_logic;
	b: OUT std_logic);
END ENTITY not_gate;
ARCHITECTURE not_gate2 of not_gate IS
BEGIN
	b<= not a after 4 ns;
END ARCHITECTURE not_gate2;
--nand gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY nand_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY nand_gate;
ARCHITECTURE nand_gate2 of nand_gate IS
BEGIN
	c <= a nand b after 5 ns;
END ARCHITECTURE nand_gate2;
--nor gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY nor_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY nor_gate;
ARCHITECTURE nor_gate2 of nor_gate IS
BEGIN
	c <= a nor b after 5 ns;
END ARCHITECTURE nor_gate2;
--and gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY and_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY and_gate;
ARCHITECTURE and_gate2 of and_gate IS
BEGIN
	c <= a and b after 7 ns;
END ARCHITECTURE and_gate2;	  
--and gate with three inputs, entity and architecture
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY and3_gate IS 
	PORT ( a,b,c: IN std_logic;
	d: OUT std_logic);
END ENTITY and3_gate;
ARCHITECTURE and_gate3 of and3_gate IS
BEGIN
	d <= (a and b and c) after 7 ns;
END ARCHITECTURE and_gate3;	
--and gate with four inputs, entity and architecture
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY and4_gate IS 
	PORT ( a,b,c,d: IN std_logic;
	e: OUT std_logic);
END ENTITY and4_gate;
ARCHITECTURE and_gate4 of and4_gate IS
BEGIN
	e <= (a and b and c and d) after 7 ns;
END ARCHITECTURE and_gate4;	 
--and gate with five inputs, entity and architecture
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY and5_gate IS 
	PORT ( a,b,c,d,e: IN std_logic;
	f: OUT std_logic);
END ENTITY and5_gate;
ARCHITECTURE and_gate5 of and5_gate IS
BEGIN
	f <= (a and b and c and d and e) after 7 ns;
END ARCHITECTURE and_gate5;	
--or gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY or_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY or_gate;
ARCHITECTURE or_gate2 of or_gate IS
BEGIN
	c <= a or b after 7 ns;
END ARCHITECTURE or_gate2; 		 
--or gate with three input, entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY or3_gate IS 
	PORT ( a,b,c: IN std_logic;
	d: OUT std_logic);
END ENTITY or3_gate;
ARCHITECTURE or_gate3 of or3_gate IS
BEGIN
	d <= (a or b or c )after 7 ns;
END ARCHITECTURE or_gate3; 	  
--or gate with four input, entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY or4_gate IS 
	PORT ( a,b,c,d: IN std_logic;
	e: OUT std_logic);
END ENTITY or4_gate;
ARCHITECTURE or_gate4 of or4_gate IS
BEGIN
	e <= (a or b or c or d)after 7 ns;
END ARCHITECTURE or_gate4; 		
--or gate with five input, entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY or5_gate IS 
	PORT ( a,b,c,d,e: IN std_logic;
	f: OUT std_logic);
END ENTITY or5_gate;
ARCHITECTURE or_gate5 of or5_gate IS
BEGIN
	f <= (a or b or c or d or e)after 7 ns;
END ARCHITECTURE or_gate5; 
--xnor gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY xnor_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY xnor_gate;
ARCHITECTURE xnor_gate2 of xnor_gate IS
BEGIN
	c <= a xnor b after 9 ns;
END ARCHITECTURE xnor_gate2; 
--xor gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY xor_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY xor_gate;
ARCHITECTURE xor_gate2 of xor_gate IS
BEGIN
	c <= a xor b after 11 ns;
END ARCHITECTURE xor_gate2;
