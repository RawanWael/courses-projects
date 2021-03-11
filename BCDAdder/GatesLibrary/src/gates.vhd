--Invertor gate entity and architecture
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY not_gate IS 
	PORT ( a: IN std_logic;
	b: OUT std_logic);
END ENTITY not_gate;
ARCHITECTURE not_gate1 of not_gate IS
BEGIN
	b<= not a after 4 ns;
END ARCHITECTURE not_gate1;
--nand gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY nand_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY nand_gate;
ARCHITECTURE nand_gate1 of nand_gate IS
BEGIN
	c <= a nand b after 5 ns;
END ARCHITECTURE nand_gate1;
--nor gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY nor_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY nor_gate;
ARCHITECTURE nor_gate1 of nor_gate IS
BEGIN
	c <= a nor b after 5 ns;
END ARCHITECTURE nor_gate1;
--and gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY and_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY and_gate;
ARCHITECTURE and_gate1 of and_gate IS
BEGIN
	c <= a and b after 7 ns;
END ARCHITECTURE and_gate1;	 
--or gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY or_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY or_gate;
ARCHITECTURE or_gate1 of or_gate IS
BEGIN
	c <= a or b after 7 ns;
END ARCHITECTURE or_gate1; 
--xnor gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY xnor_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY xnor_gate;
ARCHITECTURE xnor_gate1 of xnor_gate IS
BEGIN
	c <= a xnor b after 9 ns;
END ARCHITECTURE xnor_gate1; 
--xor gate entity and architectur
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;

ENTITY xor_gate IS 
	PORT ( a,b: IN std_logic;
	c: OUT std_logic);
END ENTITY xor_gate;
ARCHITECTURE xor_gate1 of xor_gate IS
BEGIN
	c <= a xor b after 11 ns;
END ARCHITECTURE xor_gate1;
