;[prog6.asm]
;Kristin Hamilton
;date created: 04-Apr-2014
;description: tests subroutines to be found in KBHsubs.asm
;
;cosc2331-01. asg6.
extern newline, writeString, readString, isNumeric, stringToInt, intToString
extern populateList, printList, sortList

;SECTION: data, uninitialized
 section .bss

  intArray resd 5                ;string thats been converted to an array of (4-byte) ints
  arrayCount resd 1              ;number of ints in the array

  intArraySorted resd 5          ;int array, sorted


;SECTION: data, initialized
 section .data

 ;messages, standalone
  asteriskDelimiter db "***********************************", 10
  len_asteriskDelimiter equ $ - asteriskDelimiter
  welcomeMsg db "Welcome to asg6 - Integer Array Sorting!", 10         ;welcome message
  len_welcomeMsg equ $ - welcomeMsg
  exitMsg1 db "Thanks for using the program!", 10                      ;exit message 1
  len_exitMsg1 equ $ - exitMsg1
  exitMsg2 db "This was splendid, and super fun."                      ;exit message 2
  len_exitMsg2 equ $ - exitMsg2

 ;messages, presenters
  presenter0_unsorted db "Here is your original (UNSORTED) list:", 10
  len_presenter0_unsorted equ $ - presenter0_unsorted
  presenter0_sorted db "Here is your list, sorted:", 10
  len_presenter0_sorted equ $ - presenter0_sorted

 ;other data
  linefeed db 10                 ;linefeed/carriage return


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
  push welcomeMsg                ;"Welcome to asg6 - Integer Array Sorting!", 10
  call writeString
  
  push linefeed
  call newline

b0:
 ;POPULATE ARRAY OF INTS. call the following subroutine.
 ;int populateList(address of array of ints, int maxNumberOfIntsToRead)
 ;subroutine will store user input into dword intArray.
 ;store return value in dword [arrayCount].
  push dword 5                   ;max allowable # ints to store in intArray
  push dword intArray            ;array of integers
  call populateList
b1:
  mov dword [arrayCount], eax    ;save # ints loaded into intArray by populateList()


 ;PRESENT, THEN DISPLAY, ORIGINAL (UNSORTED) ARRAY OF INTEGERS TO USER.
 ;void printList(address of array of ints, number of ints in array)
  push len_presenter0_unsorted
  push presenter0_unsorted       ;"Here is your original (UNSORTED) list:", 10
  call writeString

  push dword [arrayCount]
  push dword intArray
  call printList
  
  push linefeed
  call newline
  
 
 ;CHECK ARRAY COUNT. if array is empty, printList will have shown a msg to that effect but
 ;now we want the program to go ahead and terminate if that is the case (is empty).
  cmp dword [arrayCount], 0
  je endProgram

 ;if array isnt empty, go ahead and copy intArray over to the other uninitialized arrays
 ;that will each be sorted by a different method.
   ;mov edi, dword intArray
   ;mov intArraySorted, edi
  
  
b2:
 ;CALL EACH OF THE SORT METHODS.
 ;void sortList(addr of int array, array count)
  push dword [arrayCount]
  push dword intArray
  call sortList
  

b6:
 ;SORTED ARRAY: PRESENT,THEN DISPLAY, ARRAY OF INTEGERS TO USER.
 ;void printList(address of array of ints, number of ints in array)
  push len_presenter0_sorted
  push presenter0_sorted         ;"Here is your list, sorted:", 10
  call writeString

  push dword [arrayCount]
  push dword intArray
  call printList

  push linefeed
  call newline
  

endProgram:   
 ;DISPLAY EXIT MESSAGES. call writeString() to write msgs to stdout.
 ;display exit message 1
  push len_exitMsg1
  push exitMsg1                  ;"Thanks for using the program!", 10
  call writeString

 ;display exit message 2
  push len_exitMsg2
  push exitMsg2                  ;"This was splendid, and super fun."
  call writeString

 ;output newline
  push linefeed
  call newline


 ;DISPLAY ASTERISK DELIMITER. call the following subroutine.
 ;void writeString(String asteriskDelimiter, int len_asteriskDelimiter)
  push len_asteriskDelimiter
  push asteriskDelimiter         ;"***********************************", 10
  call writeString


 ;TERMINATE PROGRAM
  mov ebx, 0
  mov eax, 1
  int 0x80                       ;end of prog6.asm