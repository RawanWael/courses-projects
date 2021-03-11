--Rawan Yassin 1182224
--Here we will be building the required BCD adder, with a register for input and a register for the output
LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
USE IEEE.STD_LOGIC_UNSIGNED.ALL; 
--the entity of the desired BCD adder, is the same as the regular BCD adder, excpet that it accepts two clk inputs to enable the registers and a reset for the registers as well
ENTITY Final_BCD_Adder IS
	PORT (a,b : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
	cin,clk1,clk2,reset: IN STD_Logic;
	sum: OUT STD_LOGIC_VECTOR(3 DOWNTO 0);
    cout: OUT STD_LOGIC);	
END ENTITY Final_BCD_Adder;
--the first architeture uses the BCD adder made from the ripple carry adder
ARCHITECTURE Final_BCD_Adder_Circuit_R of Final_BCD_Adder IS
Signal registerInput: STD_LOGIC_VECTOR(8 DOWNTO 0);	   --a and b and c
Signal BCDInput : STD_LOGIC_VECTOR(8 DOWNTO 0);		   --output of input_register and inputs to the BCD adder
Signal BCDSum: STD_LOGIC_VECTOR(3 DOWNTO 0);		   --output of the BCD adder and inputs to the output_register
Signal BCDCarry: STD_LOGIC;							   --output of the BCD adder and inputs to the output_register
Begin
	registerInput<=a&b&cin;	 --concatinating the BCD input for the register
	input: ENTITY work.n_bit_register(register_n_bit) GENERIC MAP (9) PORT MAP (clk1,reset,registerInput,BCDInput); --so now the BCD input is ready from the registers
	adding: ENTITY work.BCD_adder(BCD_adder4_R) PORT MAP (BCDInput(8 DOWNTO 5),BCDInput(4 DOWNTO 1), BCDInput(0), BCDSum,BCDCarry); --calculating using the BCD_adder made of ripple adder 	
	output: ENTITY work.n_bit_register(register_n_bit) GENERIC MAP (4) PORT MAP (clk2,reset,BCDSum,sum); --sending the output of the adder to the output_registers
    carry: ENTITY  work.D_FlipFlop(D_FlipFlop1) PORT MAP (BCDCarry,clk2,reset,cout);	--sending the output of the adder to the output_registers
END ARCHITECTURE Final_BCD_Adder_Circuit_R;

--the second architeture uses the BCD adder made from the look ahead carry adder 
--the same approach that is used in the above architecture is applied here
ARCHITECTURE Final_BCD_Adder_Circuit_CLA of Final_BCD_Adder IS
Signal registerInput: STD_LOGIC_VECTOR(8 DOWNTO 0);	  
Signal BCDInput : STD_LOGIC_VECTOR(8 DOWNTO 0);
Signal BCDSum: STD_LOGIC_VECTOR(3 DOWNTO 0);
Signal BCDCarry: STD_LOGIC;	
Begin
	registerInput<=a&b&cin;
	input: ENTITY work.n_bit_register(register_n_bit) GENERIC MAP (9) PORT MAP (clk1,reset,registerInput,BCDInput); 
	adding: ENTITY work.BCD_adder(BCD_adder4_CLA) PORT MAP (BCDInput(8 DOWNTO 5),BCDInput(4 DOWNTO 1), BCDInput(0), BCDSum,BCDCarry); 	
	output: ENTITY work.n_bit_register(register_n_bit) GENERIC MAP (4) PORT MAP (clk2,reset,BCDSum,sum);
    carry: ENTITY  work.D_FlipFlop(D_FlipFlop1) PORT MAP (BCDCarry,clk2,reset,cout);	
END ARCHITECTURE Final_BCD_Adder_Circuit_CLA;		
	