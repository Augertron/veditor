--
-- VHDL 2008 New Syntax
-- 

context uvvm_util_context is
  library uvvm_util;
  use uvvm_util.types_pkg.all;
  context uvvm_util.test;
  use uvvm_util.hierarchy_linked_list_pkg.all;
  use uvvm_util.string_methods_pkg.all;
  use uvvm_util.adaptations_pkg.all;
  use uvvm_util.methods_pkg.all;
  use uvvm_util.bfm_common_pkg.all;
  use uvvm_util.alert_hierarchy_pkg.all;
  use uvvm_util.license_pkg.all;
  use uvvm_util.protected_types_pkg.all;
end context;

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

library STD;

use std.env.all; 
--
-- Context key word
library uvvm_util;
context uvvm_util.test;



--
-- Generics in Package
-- 
package ti_generic_queue_pkg is
generic (type t_generic_element;
         GC_QUEUE_COUNT_MAX       : natural := 1000;
         GC_QUEUE_COUNT_THRESHOLD : natural := 950);

end package ti_generic_queue_pkg;


entity irqc_tb is
end entity;


architecture func of irqc_tb is



begin

  p_main: process is
    alias start_store        is  <<signal uut.start_store          : std_logic>>;
    alias store_complete     is  <<signal uut.store_complete       : std_logic>>;
  begin
    store_complete <= force '0';
    wait for 10 nS;
    store_complete <= release;

  end process p_main;

end func;