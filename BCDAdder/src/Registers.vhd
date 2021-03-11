--Rawan Yassin 1182224
--Creating a 1_bit register(D-flipflop)
LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY D_FlipFlop IS
 PORT(d, clk, reset : IN STD_LOGIC;
 q: OUT STD_LOGIC);
END ENTITY D_FlipFlop;				
--synchronous reset
ARCHITECTURE D_FlipFlop1 OF D_FlipFlop IS
BEGIN
    PROCESS (clk)
    BEGIN
        IF ( rising_edge(clk) ) THEN
            IF ( reset='1' ) THEN
                q <= '0';
            ELSE
                q <= d;
            END IF;
        END IF;
    END PROCESS;
END ARCHITECTURE D_FlipFlop1;

--Creating an n_bit register
LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY n_bit_register IS
	GENERIC(n : positive := 4);
	PORT(clk, reset : IN STD_LOGIC;
	d : IN std_logic_vector(n-1 DOWNTO 0);
	q: OUT STD_LOGIC_vector(n-1 DOWNTO 0));
END ENTITY n_bit_register;

ARCHITECTURE register_n_bit of n_bit_register is 
BEGIN 
	register_loop:FOR I IN 0 TO n-1 GENERATE	
		 D_FF: ENTITY work.D_FlipFlop(D_FlipFlop1) 
				PORT MAP(d(i), clk, reset,q(i)); 
	END GENERATE register_loop;
END ARCHITECTURE register_n_bit;