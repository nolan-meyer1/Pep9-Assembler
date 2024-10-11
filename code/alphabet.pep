LDBA 0x0041, i ; Loads 'A' into A reg

loop: STBA 0xFC16, d ; Outputs what's in A reg 
      STBA 0x008B, d ; Save the current char in memory
      LDBA 0x002D, i ; Loads '-' into A reg
      STBA 0xFC16, d ; Outputs '-'
      LDBA 0x008B, d ; Restore current char
      ADDA 0x0001, i ; Adds one to A reg
      CPBA 0x005A, i ; Compare to see if we're at 0x005B
      BRNE loop, i     ; Branches back to top if comparision is false

STBA 0xFC16, d ; Prints last letter outside of loop to get rid of last '-'
STOP
.END