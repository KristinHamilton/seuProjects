;[prog5.asm]
;Kristin Hamilton
;date created: 21-Mar-2014
;description: tests subroutines to be found in KBHsubs.asm
;
;cosc2331-01. asg5.
extern newline, writeString, readString, toUpperCase, toLowerCase
extern isNumeric, isHexadecimal, stringToInt, findProduct, intToString
extern populateList, printList, findMin, findMax, computeAverage

;SECTION: data, uninitialized
 section .bss

  ;intString resb 50              ;user-provided string of "ints", original
  ;len_intString resd 1           ;length of user-provided string of ints

  intArray resd 5                ;string thats been converted to an array of (4-byte) ints
  arrayCount resd 1              ;number of ints in the array

  maxInt resd 1                  ;largest integer in array
  maxString resb 8               ;where to store maxInt once converted to String form
  len_maxString resd 1           ;length of maxString
  
  minInt resd 1                  ;smallest integer in array
  minString resb 8               ;where to store minInt once converted to String form
  len_minString resd 1           ;length of minString
  
  arrayAvg resd 1                ;average of all ints in array (rounded to nearest int)
  avgString resb 8               ;where to store arrayAvg once converted to String form
  len_avgString resd 1           ;length of avgString


;SECTION: data, initialized
 section .data

 ;messages, standalone
  asteriskDelimiter db "***********************************", 10
  len_asteriskDelimiter equ $ - asteriskDelimiter
  welcomeMsg db "Welcome to asg5 - Integer Array Descriptive Statistics!", 10 ;welcome message
  len_welcomeMsg equ $ - welcomeMsg
  exitMsg1 db "Thanks for using the program!", 10                      ;exit message 1
  len_exitMsg1 equ $ - exitMsg1
  exitMsg2 db "You, sir/m'lady, are indeed a scholar and a gentleman." ;exit message 2
  len_exitMsg2 equ $ - exitMsg2

 ;messages, presenters
  presenter1Max db "Here is the largest integer in your list: "
  len_presenter1Max equ $ - presenter1Max
  presenter2Min db "Here is the smallest integer in your list: "
  len_presenter2Min equ $ - presenter2Min
  presenter3Avg db "Here is the average of your list (rounded to the nearest int): "
  len_presenter3Avg equ $ - presenter3Avg

 ;other data
  linefeed db 10      ;linefeed/carriage return


 global _start


 ;SECTION: instructions
  section .text

_start:


 ;DISPLAY ASTERISK DELIMITER. call the following subroutine.
 ;void writeString(String asteriskDelimiter, int len_asteriskDelimiter)
  push len_asteriskDelimiter
  push asteriskDelimiter         ;"***********************************"
  call writeString
    
 ;DISPLAY WELCOME MESSAGE. call the following subroutine.
 ;void writeString(String welcomeMsg, int len_welcomeMsg)
  push len_welcomeMsg
  push welcomeMsg                ;"Welcome to asg5 - Int Array Descriptive Statistics!", 10
  call writeString
  
  push linefeed
  call newline

b1:
 ;POPULATE ARRAY OF INTS. call the following subroutine.
 ;int populateList(address of array of ints, int maxNumberOfIntsToRead)
 ;subroutine will store user input into dword intArray.
 ;store return value in dword [arrayCount].
  push dword 5                   ;max allowable # ints to store in intArray
  push dword intArray            ;array of integers
  call populateList
b2:
  mov dword [arrayCount], eax    ;save # ints loaded into intArray by populateList()
b3:

 ;PRESENT ARRAY OF INTEGERS TO USER. call the following subroutine.
 ;void printList(address of array of ints, number of ints in array)
  push dword [arrayCount]
  push dword intArray
  call printList
  
  push linefeed
  call newline
  
 
 ;CHECK ARRAY COUNT. if array is empty, printList will have shown a msg to that effect but
 ;now we want the program to go ahead and terminate if that is the case (is empty).
  cmp dword [arrayCount], 0
  je endProgram
  

 ;FIND MAX INTEGER IN ARRAY OF INTS. findMax subroutine will return a sentinel value of
 ;-99999 if arrayCount is 0, but since we're checking for an empty list before calling
 ;this subroutine and terminating program if count is 0, don't really need to check it
 ;below, but im putting in a check just to demonstrate the use of the sentinel value.
 ;if return value != sentinel value, store return value in dword [maxInt].
  push dword [arrayCount]             
  push dword intArray
  call findMax
b4: 
  cmp eax, -99999
  je endProgram
  
  mov dword [maxInt], eax        ;load max integer returned by findMax() into maxInt

b5:
 ;DETERMINE THE STRING REPRESENTATION FOR MAX INT.
 ;subroutine will store converted value in maxString.
  push maxString                 ;where to store the max int once converted to String
  push dword [maxInt]            ;4-byte max integer in array
  call intToString
  
  mov dword [len_maxString], eax

 ;FIND MIN INTEGER IN ARRAY OF INTS. findMin subroutine will return sentinel value of
 ;99999 if arrayCount = 0. see notes for "FIND MAX INTEGER IN ARRAY OF INTS" (above).
 ;if return value != sentinel value, store return value in dword [minInt].
  push dword [arrayCount]
  push dword intArray
  call findMin
  
  cmp eax, 99999
  je endProgram
  
  mov dword [minInt], eax        ;load min integer returned by findMax() into maxInt

 ;DETERMINE STRING REPRESENTATION FOR MIN INT.
 ;subroutine will store converted value in minString.
  push minString
  push dword [minInt]
  call intToString
  
  mov dword [len_minString], eax
  
b6:  
 ;CALCULATE AVERAGE OF INTS IN ARRAY (ROUNDED TO NEAREST INTEGER).
 ;store return value in dword [arrayAvg]
  push dword [arrayCount]
  push dword intArray
  call computeAverage
  
  mov dword [arrayAvg], eax      ;load int returned by computeAverage() into arrayAvg

 ;DETERMINE STRING REPRESENTATION FOR ARRAY AVERAGE.
 ;subroutine will store converted value in avgString.
  push avgString
  push dword [arrayAvg]
  call intToString

  mov dword [len_avgString], eax

b7:
 ;PRESENT MAX INTEGER IN ARRAY TO USER.
 ;write maxInt presenter msg to stdout
  push len_presenter1Max          
  push presenter1Max             ;"Here is the largest integer in your list: "
  call writeString

 ;write maxInt to stdout
  push dword [len_maxString]
  push maxString
  call writeString
  
 ;output newline
  push linefeed
  call newline


b8:
 ;PRESENT MIN INTEGER IN ARRAY TO USER.
 ;write minInt presenter msg to stdout
  push len_presenter2Min          
  push presenter2Min             ;"Here is the smallest integer in your list: "
  call writeString

 ;write minInt to stdout
  push dword [len_minString]
  push minString
  call writeString
  
 ;output newline
  push linefeed
  call newline


b9:
 ;write arrayAvg presenter msg to stdout
  push len_presenter3Avg          
  push presenter3Avg             ;"...Heres avg of list (rounded to nearest int): ..."
  call writeString

 ;write arrayAvg (as avgString) to stdout
  push dword [len_avgString]
  push avgString
  call writeString
  
 ;output newline
  push linefeed
  call newline
  
  push linefeed
  call newline


endProgram:
   
 ;DISPLAY EXIT MESSAGES. call writeString() to write msgs to stdout.
 ;display exit message 1
  push len_exitMsg1
  push exitMsg1                  ;"Thanks for using the program!"
  call writeString

 ;display exit message 2
  push len_exitMsg2
  push exitMsg2                  ;"You, sir/m'lady, are indeed a scholar and a gentleman."
  call writeString

 ;output newline
  push linefeed
  call newline


 ;DISPLAY ASTERISK DELIMITER. call the following subroutine.
 ;void writeString(String asteriskDelimiter, int len_asteriskDelimiter)
  push len_asteriskDelimiter
  push asteriskDelimiter         ;"***********************************"
  call writeString


 ;TERMINATE PROGRAM
  mov ebx, 0
  mov eax, 1
  int 0x80                       ;end of prog5.asm