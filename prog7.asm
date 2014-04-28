;[prog7.asm]
;Kristin Hamilton
;date created: 12-Apr-2014
;description: tests subroutines to be found in asg7subs.asm
;
;cosc2331-01. asg7.
extern writeString, newline, readString, isValidHanoiInput
extern charToInt, hanoi, writeHanoiString, intToChar

;SECTION: data, uninitialized
 section .bss

  numRings resb 1                ;number of rings entered by user (either 0, 1, 2, or 3)

;SECTION: data, initialized
 section .data

 ;messages, standalone
  asteriskDelimiter db "***********************************", 10
  len_asteriskDelimiter equ $ - asteriskDelimiter
  welcomeMsg db "Welcome to asg7 - Towers of Hanoi!", 10, 10 ;welcome message
  len_welcomeMsg equ $ - welcomeMsg
  exitMsg1 db 10, "Thanks for using the program!", 10    ;exit message 1
  len_exitMsg1 equ $ - exitMsg1

 ;messages, prompts
  prompt1 db "Please choose number of rings. You may enter 0, 1, 2, or 3.", 10
  len_prompt1 equ $ - prompt1
  
 ;messages, result of isValidInput subroutine
  invalidInputMsg db 10, "Your entry was invalid: You entered something other than 0, 1, 2, or 3."
  len_invalidInputMsg equ $ - invalidInputMsg

 ;other data
  linefeed db 10                 ;linefeed/carriage return
  A db 65                        ;ascii 65 = 'A'
  B db 66                        ;ascii 66 = 'B'
  C db 67                        ;ascii 67 = 'C'
  len_numRings dd 1              ;number of chars entered by user == 1

 global _start


 ;SECTION: instructions
  section .text

_start:


 ;DISPLAY ASTERISK DELIMITER. call the following subroutine.
 ;void writeString(String asteriskDelimiter, int len_asteriskDelimiter)
  push len_asteriskDelimiter
  push asteriskDelimiter         ;"***********************************", 10
  call writeString
    
    
 ;DISPLAY WELCOME MESSAGE. call the following subroutine.
 ;void writeString(String welcomeMsg, int len_welcomeMsg)
  push len_welcomeMsg
  push welcomeMsg                ;"Welcome to asg7 - Towers of Hanoi!", 10, 10
  call writeString


b1:
 ;PROMPT USER TO ENTER NUMBER OF RINGS.
 ;readString subroutine will store user input into numRings.
 ;dont need to store return value (#chars read in). we only want 1 char regardless of what
 ;was entered by the user, so length of String numRings is established in .data section.
 ;user input will still be validated below though.
  push len_prompt1
  push prompt1                   ;"Please choose number of rings. You may enter 0, 1, 2,
  call writeString               ; or 3.", 10

  push dword 50                  ;# chars to read in: 50 (really we just want 1, but dont
  push numRings                  ;want overflow). regardless of what user entered, only 1
  call readString                ;char will be used for numRings.


b2:
 ;VALIDATE USER INPUT. only one char was saved into numRings, as we only resb'ed it,
 ;but we need to make sure that the char was either '0', '1', '2', or '3' (could have
 ;entered a letter, punctuation, or even just whitespace, which would be invalid for the
 ;program as well).
 ;check return value: 0 = false ("is invalid input"). otherwise, input considered valid.
 ;if eax == 0, print invalidMsg and exit program. otherwise, proceed to callHanoi.
  push eax                       ;length of numRings String = 1 char or "dword 1"
  push numRings                  ;numRings is a char still.
  call isValidHanoiInput
b3:
  cmp eax, 1                     ;if(eax == 1), numRings is valid. "proceed to hanoi"
  je proceedToHanoi
  
  push len_invalidInputMsg       ;10, "Your entry was invalid: You entered something
  push invalidInputMsg           ;  other than 0, 1, 2, or 3."
  call writeString
  jmp endProgram
    
proceedToHanoi:
 ;CONVERT NUMRINGS CHAR TO INT.
  push numRings
  call charToInt
    
 ;CALL HANOI SUBROUTINE. 
  push C
  push B
  push A
  push eax                       ;eax = (int) numRings, returned by charToInt above
  call hanoi
  
  push linefeed
  call newline

endProgram:
 ;DISPLAY EXIT MESSAGE.
  push len_exitMsg1
  push exitMsg1                  ;10, "Thanks for using the program!", 10
  call writeString


 ;DISPLAY ASTERISK DELIMITER.
  push len_asteriskDelimiter
  push asteriskDelimiter         ;"***********************************", 10
  call writeString


 ;TERMINATE PROGRAM
  mov ebx, 0
  mov eax, 1
  int 0x80                       ;end prog7.asm